package com.arconsis.benchmarks.quarkus.vertx

import com.arconsis.benchmarks.quarkus.vertx.coroutines.hibernate.BenchmarkCoroutineVerticle
import com.arconsis.benchmarks.quarkus.vertx.reactive.reactive.BenchmarkReactiveVerticle
import io.quarkus.runtime.StartupEvent
import io.vertx.mutiny.core.Vertx
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes


@ApplicationScoped
class VerticleDeployer {

    fun init(@Observes e: StartupEvent?, vertx: Vertx, benchmarkReactiveVerticle: BenchmarkReactiveVerticle, benchmarkCoroutineVerticle: BenchmarkCoroutineVerticle) {
        vertx.deployVerticle(benchmarkReactiveVerticle).await().indefinitely()
        vertx.deployVerticle(benchmarkCoroutineVerticle).await().indefinitely()
    }
}
