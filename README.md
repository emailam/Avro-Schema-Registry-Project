# Avro Schema Registry Project

This project demonstrates a basic microservices architecture using Avro schemas for event serialization and a schema registry for managing Avro schemas. It includes two services:

- **notification-service**: consumes events.
- **payment-service**: produces events.

## Getting Started

1. Clone the repository.
2. Use `docker-compose up` to start Kafka + Zookeeper + Schema Registry.
3. `cd payment-service` then `mvn clean install`
4. `cd ../notification-service` then `mvn clean install`
5. `cd payment-service` then `mvn spring-boot:run`
6. `cd notification-service` then `mvn spring-boot:run`

## Folder Structure
- `notification-service/`: Notification microservice (Java, Spring Boot)
- `payment-service/`: Payment microservice (Java, Spring Boot)

## Requirements
- Docker & Docker Compose
- Java 17 or higher
