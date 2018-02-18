#!/usr/bin/env bash

echo "building and running jar..."

./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar