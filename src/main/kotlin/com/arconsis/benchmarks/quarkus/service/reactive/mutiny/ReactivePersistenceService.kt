package com.arconsis.benchmarks.quarkus.service.reactive.mutiny

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.reactive.toPersonDto
import io.smallrye.mutiny.Uni
import io.vertx.mutiny.pgclient.PgPool
import io.vertx.mutiny.sqlclient.Tuple
import org.jboss.logging.Logger
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ReactivePersistenceService(private val client: PgPool) {
    private val logger = Logger.getLogger(ReactivePersistenceService::class.java.name)

    fun getPersonById(id: UUID): Uni<PersonDto?> {
        return client.begin().flatMap { tx ->
            tx.preparedQuery("SELECT * FROM persons WHERE id = $1 LIMIT 1")
                    .execute(Tuple.of(id))
                    .map { it.firstOrNull()?.toPersonDto() }
                    .onItem().invoke { tx.commitAndForget() }
                    .onFailure().invoke { tx.rollbackAndForget() }
                    .onFailure().invoke { logger.error("Error retrieving person with id $id", it) }
        }
    }

    fun insertPerson(personDto: PersonDto): Uni<PersonDto> {
        return client.begin().flatMap { tx ->
            tx.preparedQuery("INSERT INTO persons (id, name, street, city) VALUES ($1, $2, $3, $4) RETURNING (id)")
                    .execute(Tuple.of(UUID.randomUUID(), personDto.name, personDto.street, personDto.city))
                    .map { pgRowSet -> pgRowSet.iterator().next().getUUID("id") }
                    .map { PersonDto(it, personDto.name, personDto.street, personDto.city) }
                    .onItem().invoke { tx.commitAndForget() }
                    .onFailure().invoke { tx.rollbackAndForget() }
                    .onFailure().invoke { logger.error("Error persisting person $personDto", it) }
        }
    }
}
