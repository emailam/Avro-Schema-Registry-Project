# Avro Schema Registry Project

This project demonstrates a basic microservices architecture using Avro schemas for event serialization and a schema registry for managing Avro schemas. It includes two services:

- **notification-service**: Handles notifications and consumes events.
- **payment-service**: Handles payment processing and produces events.

## Getting Started

1. Clone the repository.
2. Use `docker-compose up` to start Kafka + Zookeeper + Schema Registry.
3. cd payment-service
mvn clean install
4. cd ../notification-service
mvn clean install
5. cd payment-service
mvn spring-boot:run
6. cd notification-service
mvn spring-boot:run

## Folder Structure
- `notification-service/`: Notification microservice (Java, Spring Boot)
- `payment-service/`: Payment microservice (Java, Spring Boot)

## Requirements
- Docker & Docker Compose
- Java 17 or higher

