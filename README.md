# Ship Service

## Overview
Ship Service is a Spring Boot microservice responsible for managing ships and their ship-specific components.  
Each ship is owned by a user, and all operations are locked to that user using Oauth 2.0 authorization.

## Responsibilities
* Manage ship and ship-component entities
* Store data ins PostgreSQL
* Expose REST endpoints 
* Document REST endpoints
* Enforce user ownership
* Run as a containerized workload in Kubernetes

## API
The service exposes a REST API to create, obtain or change ship data and their components.

### Ship-controller
| Method | Path         | Description                                        |
|--------|--------------|----------------------------------------------------|
| GET    | /            | Get the list of all ships owned by the user.       |
| POST   | /            | Create a new ship.                                 |
| GET    | /{shipId}    | Get the ship with the matching given {shipId}.     |
| PUT    | /{shipId}    | Update the ship with the matching given {shipId}.  |
| DELETE | /{shipId}    | Delete the ship with the matching given {shipId}.  |

### Ship-component-controller
| Method  | Path                               | Description                                                                          |
|---------|------------------------------------|--------------------------------------------------------------------------------------|
| GET     | /{shipId}/components               | Get the list of all ship components that belong to the ship with {shipId}.           |
| PUT     | /{shipId}/components               | Update the list of all ship components that belong to the ship with {shipId}.        |
| POST    | /{shipId}/components               | Create a new ship component tha belongs to the ship with {shipId}.                   |
| GET     | /{shipId}/components/{componentId} | Get the ship component with {componentId} that belongs to the ship with {shipId}.    |
| PUT     | /{shipId}/components/{componentId} | Update the ship component with {componentId} that belongs to the ship with {shipId}. |
| DELETE  | /{shipId}/components/{componentId} | Delete the ship component with {componentId} that belongs to the ship with {shipId}. |

## Authorization
User authorization is handled using a JSON Web Token (JWT). The authenticated user ID is obtained form the JWT and used to enforce ownership. Requests without a valid JWT are rejected.

## Database 
The database is a PostgreSQL which schema is managed automatically by Hibernate.

## Swagger / OpenAPI
Swagger UI is available at: /swagger-ui/index.html.

## Deployment
The service is part of a cloud-native microservices system deployed on Azure Kubernetes Service (AKS) and integrated via Ingress-NGINX.

### Docker
The service is containerized and published to the GitHub Container Registry (GHCR).

### CI/CD
Continuous integration and continuous deployment (CI/CD) is implemented using GitHub Actions.
#### GitHub Actions pipeline:
* Build
* Test
* Build Docker image
* Push to GHCR
* Trigger infrastructure deployment