package com.arconsis.benchmarks.quarkus.endpoints.jaxrs.coroutinesmutiny

import io.smallrye.mutiny.Uni
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


abstract class CoroutineScopedResource : CoroutineScope {

	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + CoroutineName("CoroutineScopedResource")


	fun <T> asyncUni(block: suspend () -> T): Uni<T> {
		return Uni.createFrom().emitter {
			launch {
				try {
					it.complete(block())
				} catch (e: Exception) {
					it.fail(e)
				}
			}
		}
	}
}
