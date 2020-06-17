package com.arconsis.benchmarks.quarkus.service.reactive.mutiny

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import io.smallrye.mutiny.Uni
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ReactivePersonService(private val persistenceService: ReactivePersistenceService) {

    fun createPerson(person: PersonDto): Uni<PersonDto> {
        return persistenceService.insertPerson(person)
    }

    fun getPerson(personId: UUID): Uni<PersonDto?> {
        return persistenceService.getPersonById(personId)
    }
}
