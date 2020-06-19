package com.arconsis.benchmarks.quarkus.vertx.reactive.reactive

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import java.util.*
import javax.inject.Singleton

@Singleton
class ReactivePersonService(private val reactivePersistenceService: ReactivePersistenceService) {

    fun createPerson(person: PersonDto, completionHandler: Handler<AsyncResult<PersonDto>>) {
        return reactivePersistenceService.insertPerson(person, completionHandler)
    }

    fun getPersonById(personId: UUID, completionHandler: Handler<AsyncResult<PersonDto>>) {
        return reactivePersistenceService.getPersonById(personId, completionHandler)
    }
}
