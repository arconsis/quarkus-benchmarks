package com.arconsis.benchmarks.quarkus.dto

import java.util.*

@Dto
data class PersonDto(
        val id: UUID? = null,
        val name: String,
        val street: String,
        val city: String
)
