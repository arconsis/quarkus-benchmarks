package com.arconsis.benchmarks.quarkus.service.sync

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.sync.persistence.HibernatePersistenceService
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SyncPersonService(private val persistenceService: HibernatePersistenceService) {

    fun createPerson(person: PersonDto): PersonDto {
        return persistenceService.insertPerson(person)
    }

    fun getPerson(personId: UUID): PersonDto? {
        return persistenceService.getPersonById(personId)
    }

    fun deleteAllPersons(): Int {
        return persistenceService.deleteAllPersons()
    }
}
