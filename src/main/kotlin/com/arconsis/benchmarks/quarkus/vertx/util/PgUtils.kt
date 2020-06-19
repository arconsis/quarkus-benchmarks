package com.arconsis.benchmarks.quarkus.vertx.util

object PgUtils {

    val DELETE_STATEMENT =
            """
        DELETE FROM persons
      """.trimIndent()

    val SELECT_STATEMENT =
            """
        SELECT * FROM persons WHERE id = ${'$'}1 LIMIT 1
      """.trimIndent()

    val INSERT_STATEMENT =
            """
        INSERT INTO persons (id, name, street, city) VALUES (${'$'}1, ${'$'}2, ${'$'}3, ${'$'}4) RETURNING (id)
      """.trimIndent()

    val CREATE_DDL =
            """
        create table IF NOT EXISTS persons
        (
        id uuid not null
        constraint persons_pkey
        primary key,
        city varchar(255),
        name varchar(255),
        street varchar(255)
        );

        alter table persons owner to "server-benchmarks";
      """.trimIndent()
}
