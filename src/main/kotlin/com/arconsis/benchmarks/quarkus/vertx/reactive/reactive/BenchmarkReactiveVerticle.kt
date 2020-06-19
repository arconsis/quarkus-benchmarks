package com.arconsis.benchmarks.quarkus.vertx.reactive.reactive

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BenchmarkReactiveVerticle(private val router: Router, private val reactiveRouter: ReactiveRouter) : AbstractVerticle() {

    override fun start() {
        router.mountSubRouter("/frameworks/vertx/type/reactive/database/reactive", reactiveRouter.router())
    }
}
