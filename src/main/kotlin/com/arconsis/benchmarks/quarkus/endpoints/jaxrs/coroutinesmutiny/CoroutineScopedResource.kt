package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesmutiny

import io.smallrye.mutiny.Uni
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletionStage
import kotlin.coroutines.CoroutineContext


abstract class CoroutineScopedResource : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + CoroutineName("CoroutineScopedResource")

    fun <T> asyncUni(block: suspend () -> T): Uni<T> = future { block() }.toUni()

    private fun <T> CompletionStage<T>.toUni(): Uni<T> = Uni.createFrom().completionStage(this)
}
