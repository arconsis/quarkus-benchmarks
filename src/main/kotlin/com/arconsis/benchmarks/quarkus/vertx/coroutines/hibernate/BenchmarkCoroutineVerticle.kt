package com.arconsis.benchmarks.quarkus.vertx.coroutines.hibernate

import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BenchmarkCoroutineVerticle(private val router: Router, private val coroutineRouter: CoroutineRouter) : CoroutineVerticle() {
    override suspend fun start() {
        router.mountSubRouter("/frameworks/vertx/type/coroutines/database/hibernate", coroutineRouter.router())
    }
}
