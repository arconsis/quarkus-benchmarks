package com.arconsis.benchmarks.quarkus.service.sync.persistence

import com.arconsis.benchmarks.quarkus.dto.PersonDto

fun PersonDto.toEntityWithoutId() = PersonEntity(null, name, street, city)

fun PersonEntity.toDto() = PersonDto(id, name, street, city)
