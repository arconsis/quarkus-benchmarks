package com.arconsis.benchmarks.quarkus.endpoints.reactive.coroutines.hibernate

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesmutiny.CoroutineScopedResource
import com.arconsis.benchmarks.quarkus.endpoints.reactive.endOK
import com.arconsis.benchmarks.quarkus.endpoints.reactive.endOKOrNotFound
import com.arconsis.benchmarks.quarkus.endpoints.reactive.getUuidParameter
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import io.quarkus.vertx.web.Route
import io.quarkus.vertx.web.RouteBase
import io.quarkus.vertx.web.RoutingExchange
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.ws.rs.core.MediaType

@RouteBase(path = "/frameworks/reactive/type/coroutines/database/hibernate", produces = [MediaType.APPLICATION_JSON], consumes = [MediaType.APPLICATION_JSON])
class ReactiveCoroutinesHibernatePersonResource(private val personService: SyncPersonService) {

    @Route(methods = [HttpMethod.GET], path = "/persons/:personId")
    fun getPerson(exchange: RoutingExchange) {
        GlobalScope.launch(exchange.context().vertx().dispatcher()) {
            val personId = exchange.getUuidParameter("personId")

            withContext(Dispatchers.IO) {
                exchange.response().endOKOrNotFound(personService.getPerson(personId))
            }
        }
    }

    @Route(methods = [HttpMethod.POST], path = "/persons")
    fun savePerson(exchange: RoutingExchange) {
        GlobalScope.launch(exchange.context().vertx().dispatcher()) {
            val person = Json.decodeValue(exchange.context().bodyAsString, PersonDto::class.java)

            withContext(Dispatchers.IO) {
                exchange.response().endOK(personService.createPerson(person))
            }
        }
    }
}
