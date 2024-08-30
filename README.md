# Country Routing Service

This Spring Boot application calculates land routes between countries based on their border information. The service
provides a REST API to fetch routes and handles various scenarios, including validation errors.

## Project Setup

### Prerequisites

- Java 21 or later
- Maven 3.6 or later

### Dependencies

- Spring Boot
- Spring Web
- Spring Boot DevTools
- Lombok (optional, for reducing boilerplate code)
- Mockito and JUnit (for testing)

## Getting Started

* Build application `mvn clean package`
* Run application `mvn spring-boot:run`
* Run tests `mvn test`


## Usage
``
curl 'http://localhost:8080/routing/CZE/ITA' -i -X GET
``