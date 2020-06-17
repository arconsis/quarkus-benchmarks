package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.mutiny.reactivepostgres

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.reactive.mutiny.ReactivePersonService
import io.smallrye.mutiny.Uni
import org.apache.http.HttpStatus
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/frameworks/jaxrs/type/mutiny/database/reactive")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JaxrsMutinyReactivePersonResource(private val personService: ReactivePersonService) {

	@GET
	@Path("/persons/{personId}")
	fun getPerson(@PathParam("personId") personId: UUID): Uni<Response> {
		return personService.getPerson(personId).map {
			if (it != null) {
				Response.ok(it)
			} else {
				Response.status(HttpStatus.SC_NOT_FOUND)
			}.build()
		}
	}

	@POST
	@Path("/persons")
	fun savePerson(person: PersonDto): Uni<PersonDto> {
		return personService.createPerson(person)
	}
}
