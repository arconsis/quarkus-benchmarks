package com.arconsis.benchmarks.quarkus.service.reactive

import io.smallrye.mutiny.Uni
import kotlinx.coroutines.future.await

suspend fun <T> Uni<T>.suspendAwait(): T {
	return this.subscribeAsCompletionStage().await()
}
