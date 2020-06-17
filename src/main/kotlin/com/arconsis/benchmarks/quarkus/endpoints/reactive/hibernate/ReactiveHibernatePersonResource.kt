package com.arconsis.benchmarks.quarkus.endpoints.reactive.hibernate

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.endpoints.reactive.endOK
import com.arconsis.benchmarks.quarkus.endpoints.reactive.endOKOrNotFound
import com.arconsis.benchmarks.quarkus.endpoints.reactive.getUuidParameter
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import io.quarkus.vertx.web.Route
import io.quarkus.vertx.web.RouteBase
import io.quarkus.vertx.web.RoutingExchange
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import javax.ws.rs.core.MediaType

@RouteBase(path = "/frameworks/reactive/type/blocking/database/hibernate", produces = [MediaType.APPLICATION_JSON], consumes = [MediaType.APPLICATION_JSON])
class ReactiveHibernatePersonResource(private val personService: SyncPersonService) {

    @Route(methods = [HttpMethod.GET], path = "/persons/:personId", type = Route.HandlerType.BLOCKING)
    fun getPerson(exchange: RoutingExchange) {
        val personId = exchange.getUuidParameter("personId")
        exchange.response().endOKOrNotFound(personService.getPerson(personId))
    }

    @Route(methods = [HttpMethod.POST], path = "/persons", type = Route.HandlerType.BLOCKING)
    fun savePerson(exchange: RoutingExchange) {
        val person = Json.decodeValue(exchange.context().bodyAsString, PersonDto::class.java)
        exchange.response().endOK(personService.createPerson(person))
    }
}
