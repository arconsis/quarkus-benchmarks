package com.arconsis.benchmarks.quarkus.endpoints.reactive.reactivepostgres

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.endpoints.reactive.end
import com.arconsis.benchmarks.quarkus.endpoints.reactive.endOK
import com.arconsis.benchmarks.quarkus.endpoints.reactive.endOKOrNotFound
import com.arconsis.benchmarks.quarkus.endpoints.reactive.getUuidParameter
import com.arconsis.benchmarks.quarkus.service.reactive.mutiny.ReactivePersonService
import io.quarkus.vertx.web.Route
import io.quarkus.vertx.web.RouteBase
import io.quarkus.vertx.web.RoutingExchange
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import javax.ws.rs.core.MediaType

@RouteBase(path = "/frameworks/reactive/type/reactive/database/reactive", produces = [MediaType.APPLICATION_JSON], consumes = [MediaType.APPLICATION_JSON])
class ReactivePersonResource(private val personService: ReactivePersonService) {

	@Route(methods = [HttpMethod.GET], path = "/persons/:personId")
	fun getPerson(exchange: RoutingExchange) {
		val personId = exchange.getUuidParameter("personId")
		val response = exchange.response()
		personService.getPerson(personId).subscribe().with(response::endOKOrNotFound, response::end)
	}

	@Route(methods = [HttpMethod.POST], path = "/persons")
	fun savePerson(exchange: RoutingExchange) {
		val person = Json.decodeValue(exchange.context().bodyAsString, PersonDto::class.java)
		val response = exchange.response()
		personService.createPerson(person).subscribe().with(response::endOK, response::end)
	}
}
