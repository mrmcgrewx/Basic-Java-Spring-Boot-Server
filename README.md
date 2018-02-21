# Basic Server - Java Spring Boot

This is a simple RESTful server running on Java Spring Boot. This project is meant to compliment the [MLApp Demo](https://github.com/mrmcgrewx/MLApp-Demo),
providing user login and verification and storing GSP and photo data from the MLApp Demo to a mongodb database.

This project provides maven scripts so you can build and run the server. Dockerfile and docker-compose.yml have been included as well.

## Running Locally
To run the server locally, build the project using maven and execute the jar with the following command:

`./mvnw package && java -jar target/javaserver-0.1.0.jar`

## Running with docker
You can build the docker image using maven in the command line like this:
`$ ./mvnw install dockerfile:build`
You can also build the docker image using docker compose like so:
`docker-compose build`
Then run:
```
docker-compose up -d
or if you want to see logging
docker-compose up
```
