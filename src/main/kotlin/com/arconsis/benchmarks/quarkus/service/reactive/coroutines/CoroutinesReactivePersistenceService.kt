package com.arconsis.benchmarks.quarkus.service.reactive.coroutines

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.reactive.mutiny.ReactivePersistenceService
import com.arconsis.benchmarks.quarkus.service.reactive.suspendAwait
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CoroutinesReactivePersistenceService(private val reactivePersistenceService: ReactivePersistenceService) {

	suspend fun getPersonById(id: UUID): PersonDto? {
		return reactivePersistenceService.getPersonById(id).suspendAwait()
	}

	suspend fun insertPerson(personDto: PersonDto): PersonDto {
		return reactivePersistenceService.insertPerson(personDto).suspendAwait()
	}
}




