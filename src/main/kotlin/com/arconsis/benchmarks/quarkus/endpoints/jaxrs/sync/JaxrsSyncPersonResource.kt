package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.sync

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/frameworks/jaxrs/type/sync/database/hibernate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JaxrsSyncPersonResource(private val personService: SyncPersonService) {

    @GET
    @Path("/persons/{personId}")
    fun getPerson(@PathParam("personId") personId: UUID): PersonDto {
        return personService.getPerson(personId)
                ?: throw NotFoundException("Person with id $personId not found.")
    }

    @POST
    @Path("/persons")
    fun savePerson(person: PersonDto): PersonDto {
        return personService.createPerson(person)
    }
}
