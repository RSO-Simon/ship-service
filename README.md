# Ship Service

## Overview
Ship Service is a Spring Boot microservice responsible for managing ships and their ship-specific components.  
Each ship is owned by a user, and all operations are locked to that user.

This service exposes a REST API and persists data in a PostgreSQL database.

---

## Tasks
- Create, read, update, and delete ships
- Manage components installed on ships (e.g. ballistas, sails)

---

## Technology Stack
- Java 21
- Spring Boot 3.3.x
- Spring Data JPA
- PostgreSQL
- MapStruct
- OpenAPI (springdoc)
- Maven

---

## Running Locally

### Prerequisites
- Java 21
- Maven
- PostgreSQL running locally

