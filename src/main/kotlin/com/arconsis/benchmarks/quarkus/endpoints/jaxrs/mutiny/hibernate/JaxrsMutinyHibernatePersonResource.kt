package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.mutiny.hibernate

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import io.smallrye.mutiny.Uni
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/frameworks/jaxrs/type/mutiny/database/hibernate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JaxrsMutinyHibernatePersonResource(private val personService: SyncPersonService) {

	@GET
	@Path("/persons/{personId}")
	fun getPerson(@PathParam("personId") personId: UUID): Uni<PersonDto> {
		return Uni.createFrom().item(personId).onItem().apply {
			personService.getPerson(it) ?: throw NotFoundException("Person with id $personId not found.")
		}
	}

	@POST
	@Path("/persons")
	fun savePerson(person: PersonDto): Uni<PersonDto> {
		return Uni.createFrom().item(person).onItem().apply {
			personService.createPerson(person)
		}
	}
}
