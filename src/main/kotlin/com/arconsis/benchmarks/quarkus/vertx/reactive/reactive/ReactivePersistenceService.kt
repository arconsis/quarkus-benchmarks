package com.arconsis.benchmarks.quarkus.vertx.reactive.reactive

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.vertx.util.PgUtils.INSERT_STATEMENT
import com.arconsis.benchmarks.quarkus.vertx.util.PgUtils.SELECT_STATEMENT
import com.arconsis.benchmarks.quarkus.vertx.util.toPersonDto
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.Tuple
import java.util.*
import javax.inject.Singleton

@Singleton
class ReactivePersistenceService(private val client: PgPool) {

    fun getPersonById(id: UUID, completionHandler: Handler<AsyncResult<PersonDto>>) {
        client.begin { res ->
            if (res.succeeded()) {
                val tx = res.result()
                tx.preparedQuery(SELECT_STATEMENT)
                        .execute(Tuple.of(id)) { ar ->
                            if (ar.succeeded()) {
                                tx.commit()
                                val result = ar.result()?.firstOrNull()?.toPersonDto()
                                completionHandler.handle(Future.succeededFuture(result))
                            } else {
                                tx.rollback()
                                completionHandler.handle(Future.failedFuture("Did not work"))
                            }
                        }
            }
        }
    }

    fun insertPerson(personDto: PersonDto, completionHandler: Handler<AsyncResult<PersonDto>>) {
        client.begin { res ->
            if (res.succeeded()) {
                val tx = res.result()
                tx.preparedQuery(INSERT_STATEMENT)
                        .execute(Tuple.of(UUID.randomUUID(), personDto.name, personDto.street, personDto.city)) { ar ->
                            if (ar.succeeded()) {
                                val rowSet = ar.result()
                                val id = rowSet?.first()!!.getUUID("id")
                                tx.commit()
                                completionHandler.handle(Future.succeededFuture(PersonDto(id, personDto.name, personDto.street, personDto.city)))
                            } else {
                                tx.rollback()
                                completionHandler.handle(Future.failedFuture("Did not work"))
                            }
                        }
            }
        }
    }
}
