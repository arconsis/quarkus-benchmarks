#!/bin/bash

./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-runtime=docker

docker-compose --compatibility up -d --build
