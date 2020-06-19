package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesmutiny.hibernate

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesmutiny.CoroutineScopedResource
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/frameworks/jaxrs/type/coroutines/database/hibernate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JaxrsCoroutinesHibernatePersonResource : CoroutineScopedResource() {

    @Inject
    lateinit var personService: SyncPersonService

    @GET
    @Path("/persons/{personId}")
    fun getPerson(@PathParam("personId") personId: UUID) = asyncUni {
        personService.getPerson(personId) ?: throw NotFoundException("Person with id $personId not found.")
    }

    @POST
    @Path("/persons")
    fun savePerson(person: PersonDto) = asyncUni {
        personService.createPerson(person)
    }
}
