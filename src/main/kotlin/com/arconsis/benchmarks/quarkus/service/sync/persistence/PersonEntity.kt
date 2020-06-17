package com.arconsis.benchmarks.quarkus.service.sync.persistence

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "persons")
data class PersonEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: UUID? = null,
        var name: String,
        var street: String,
        var city: String
)
