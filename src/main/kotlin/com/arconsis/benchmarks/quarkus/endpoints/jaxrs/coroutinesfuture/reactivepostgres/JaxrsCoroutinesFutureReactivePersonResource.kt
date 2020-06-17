package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesfuture.reactivepostgres

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesmutiny.CoroutineScopedResource
import com.arconsis.benchmarks.quarkus.service.reactive.coroutines.CoroutinesPersonService
import kotlinx.coroutines.future.future
import java.util.*
import java.util.concurrent.CompletionStage
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/frameworks/jaxrs/type/coroutines-future/database/reactive")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class JaxrsCoroutinesFutureReactivePersonResource : CoroutineScopedResource() {

    @Inject
    lateinit var personService: CoroutinesPersonService

    @GET
    @Path("/persons/{personId}")
    fun getPerson(@PathParam("personId") personId: UUID): CompletionStage<PersonDto> = future {
        personService.getPerson(personId) ?: throw NotFoundException("Person with id $personId not found.")
    }

    @POST
    @Path("/persons")
    fun savePerson(person: PersonDto): CompletionStage<PersonDto> = future {
        personService.createPerson(person)
    }
}
