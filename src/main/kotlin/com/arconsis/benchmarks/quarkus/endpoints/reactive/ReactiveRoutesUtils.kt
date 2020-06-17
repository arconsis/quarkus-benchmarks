package com.arconsis.benchmarks.quarkus.endpoints.reactive

import io.quarkus.vertx.web.RoutingExchange
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import java.util.*
import javax.ws.rs.core.MediaType

fun <T> HttpServerResponse.endOKOrNotFound(body: T?) {
	if (body != null) {
		endOK(body)
	} else {
		end(404)
	}
}

fun <T> HttpServerResponse.endOK(body: T) {
	statusCode = 200
	putHeader("Content-Type", MediaType.APPLICATION_JSON)
	end(Json.encodePrettily(body))
}

fun HttpServerResponse.end(status: Int) {
	statusCode = status
	end()
}

fun HttpServerResponse.end(status: Int, message: String?) {
	statusCode = status
	end(message)
}

fun HttpServerResponse.end(exception: Throwable) {
	end(500, exception.message)
}

fun RoutingExchange.getUuidParameter(name: String): UUID {
	return getParam(name).map { UUID.fromString(it) }.orElseThrow { IllegalArgumentException("Missing personId parameter") }
}
