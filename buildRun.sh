#!/usr/bin/env bash

echo "building and running jar..."

./mvnw package && java -jar target/javaserver-0.1.0.jar