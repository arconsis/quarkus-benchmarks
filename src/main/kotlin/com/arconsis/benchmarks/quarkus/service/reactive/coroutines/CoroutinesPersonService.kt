package com.arconsis.benchmarks.quarkus.service.reactive.coroutines

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CoroutinesPersonService(private val persistenceService: CoroutinesReactivePersistenceService) {

	suspend fun createPerson(person: PersonDto): PersonDto {
		return persistenceService.insertPerson(person)
	}

	suspend fun getPerson(personId: UUID): PersonDto? {
		return persistenceService.getPersonById(personId)
	}
}
