package com.arconsis.benchmarks.quarkus.service.reactive

import com.arconsis.benchmarks.quarkus.dto.PersonDto
import io.vertx.mutiny.sqlclient.Row

fun Row.toPersonDto() = PersonDto(getUUID("id"), getString("name"), getString("street"), getString("city"))
