package com.arconsis.benchmarks.quarkus.vertx.util

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import io.vertx.core.json.Json
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.sqlclient.Row
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

fun RoutingContext.getUuidParameter(name: String): UUID {
    val param = request().getParam(name)
    if (param.isNullOrEmpty()) {
        throw IllegalArgumentException("Missing personId parameter")
    }
    return UUID.fromString(param)
}

fun <T> RoutingContext.endOK(body: T) {
    response().statusCode = 200
    response().putHeader("Content-Type", "application/json")
    response().end(Json.encode(body))
}

fun Route.coroutineHandler(block: suspend (RoutingContext) -> Unit): Route {
    return handler { ctx ->
        GlobalScope.launch(ctx.vertx().dispatcher()) {
            try {
                block(ctx)
            } catch (e: Exception) {
                e.printStackTrace()
                ctx.fail(e)
            }
        }
    }
}

fun Row.toPersonDto(): PersonDto {
    val savedId = this.getUUID("id")
    val name = this.getString("name")
    val street = this.getString("street")
    val city = this.getString("city")
    return PersonDto(id = savedId, name = name, street = street, city = city)
}


