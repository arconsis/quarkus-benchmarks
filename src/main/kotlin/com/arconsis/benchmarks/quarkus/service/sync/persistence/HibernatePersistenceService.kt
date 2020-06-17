package com.arconsis.benchmarks.quarkus.service.sync.persistence

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.persistence.EntityManager
import javax.transaction.Transactional

@ApplicationScoped
class HibernatePersistenceService(private val entityManager: EntityManager) {
	@Transactional
	fun getPersonById(id: UUID): PersonDto? {
		return entityManager.find(PersonEntity::class.java, id)?.toDto()
	}

	@Transactional
	fun insertPerson(personDto: PersonDto): PersonDto {
		val entity = personDto.toEntityWithoutId()
		entityManager.persist(entity)
		return entity.toDto()
	}

	@Transactional
	fun deleteAllPersons(): Int {
		return entityManager.createQuery("DELETE FROM persons").executeUpdate()
	}
}
