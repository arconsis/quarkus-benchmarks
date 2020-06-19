package com.arconsis.benchmarks.quarkus.vertx.coroutines.hibernate

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import com.arconsis.benchmarks.quarkus.service.sync.SyncPersonService
import com.arconsis.benchmarks.quarkus.vertx.util.coroutineHandler
import com.arconsis.benchmarks.quarkus.vertx.util.endOK
import com.arconsis.benchmarks.quarkus.vertx.util.getUuidParameter
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Singleton
class CoroutineRouter(private val vertx: Vertx, private val personService: SyncPersonService) {

    fun router(): Router {
        return Router.router(vertx).apply {

            get("/persons/:personId")
                    .produces("application/json")
                    .coroutineHandler { ctx ->
                        val uuid = ctx.getUuidParameter("personId")
                        withContext(Dispatchers.IO) {
                            ctx.endOK(personService.getPerson(uuid))
                        }
                    }

            post("/persons")
                    .consumes("application/json")
                    .handler(BodyHandler.create())
                    .coroutineHandler { ctx ->
                        val person = Json.decodeValue(ctx.bodyAsString, PersonDto::class.java)
                        withContext(Dispatchers.IO) {
                            ctx.endOK(personService.createPerson(person))
                        }
                    }
        }
    }
}
