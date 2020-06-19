package com.arconsis.benchmarks.quarkus.vertx.reactive.reactive

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.vertx.util.endOK
import com.arconsis.benchmarks.quarkus.vertx.util.getUuidParameter
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import javax.inject.Singleton

@Singleton
class ReactiveRouter(private val vertx: Vertx, private val personService: ReactivePersonService) {

    fun router(): Router {
        return Router.router(vertx).apply {

            get("/persons/:personId")
                    .produces("application/json")
                    .handler { ctx ->
                        val uuid = ctx.getUuidParameter("personId")
                        personService.getPersonById(uuid, Handler<AsyncResult<PersonDto>> {
                            ctx.endOK(it.result())
                        })
                    }

            post("/persons")
                    .consumes("application/json")
                    .handler(BodyHandler.create())
                    .produces("application/json")
                    .handler { ctx ->
                        val person = Json.decodeValue(ctx.bodyAsString, PersonDto::class.java)
                        personService.createPerson(person, Handler {
                            ctx.endOK(it.result())
                        })
                    }
        }
    }
}
