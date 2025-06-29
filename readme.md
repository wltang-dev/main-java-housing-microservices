# WeChat Housing Microservices

This project is a microservices-based backend system that **replicates the core functionality** of a WeChat mini-program I previously developed while working in a real-world production environment. The system was designed to support flash sale ("抢房") features for a housing application.

## Project Description

The current version focuses on rebuilding the backend architecture using Spring Cloud and related technologies. While MySQL has not been integrated yet (as it's not essential at this stage), Redis has been added to simulate distributed caching and session control.

The project is still under active development and will continue to be enhanced with more components.


##  Technology Stack

- **Java 17+Spring Boot 3.x**
- **Spring Cloud**
- **Spring Cloud Gateway**
- **Eureka (Service Discovery)**
- **Spring Security + JWT** — Authentication & Authorization
- **Spring Cloud OpenFeign** — Declarative REST client for inter-service communication
- **Lombok, MapStruct, JPA** — Code generation & ORM tools
- **Redis** — Caching & Distributed Locking
- **H2 Database** — Lightweight in-memory database for development/testing
- **Maven** — Project build tool
- **Postman** — API testing
- **ThreadLocal (UserContext Management)** — Track logged-in user info across threads
- **JUnit 5, Mockito, WebTestClient** — Unit & integration testing
- **CI/CD** — GitHub Actions (or Jenkins) for automated builds & deployment
- **Docker & Kubernetes** — Containerization and orchestration support




## Modules Overview

- **eureka-server**: Service discovery and registry
- **api-gateway**: Central gateway for routing and filtering
- **user-service**: Handles user registration and login
- **house-admin-service**: Manages housing listings and admin operations
- **house-client-service**: Exposes endpoints for client users to browse or reserve properties
- **common**: Shared utilities, enums, DTOs

##  Test Coverage

This project includes both unit and integration tests to validate core functionality and microservice interactions:

###  user-service
- `AuthControllerTest` uses **JUnit 5 + Mockito** to test:
    - User registration
    - Login endpoint
    - Profile retrieval for authenticated users

###  api-gateway

The `AuthGlobalFilterTest` verifies the custom JWT-based global filter using **WebTestClient**, testing real HTTP routing behavior.
Test Scenarios:
- `/api/auth/login` and `/api/auth/register` are **allowed** without tokens
- Accessing protected endpoints without a token returns **401 Unauthorized**
- Invalid tokens are **rejected**
- Valid Bearer tokens (simulated) are **allowed** to pass through
> See `screenshots/gateway-login-register-test-success.png` for example output.


## How to Run

### Prerequisites

- JDK 17 installed
- Redis running locally (default port 6379)
- Maven installed
- IntelliJ IDEA or any Java IDE

### Steps

1. Clone this repository
2. Start Eureka Server (`eureka-server` module)
3. Start Redis server
4. Start functional Service and API Gateway
5. Use Postman to call API endpoints (see below)

## Example Endpoints

| Method | Endpoint             | Description         |
|--------|----------------------|---------------------|
| POST   | `/api/auth/register` | Register a new user |
| POST   | `/api/auth/login`    | Authenticate user   |

### Screenshots

All screenshots used for testing are located in the `screenshots/` folder.

## Directory Structure
wechat-housing-microservices/
├── eureka-server/
├── api-gateway/
├── user-service/
├── common/
├── screenshots/
└── README.md

### Containerization & Kubernetes Support
This project is designed with production-grade deployment in mind. Each microservice is fully containerized and ready for orchestration in cloud-native environments.

#### Docker Support
Every deployable module (e.g., user-service, api-gateway, eureka-service) includes its own Dockerfile, enabling modular container builds:
```bash
docker build -t user-service:latest ./user-service
docker build -t api-gateway:latest ./api-gateway
```
The common module serves as a shared dependency and is not containerized.

#### Multi-Profile Environment Support
The application supports environment-specific configuration via Spring Boot profiles:

application-dev.yaml — For local development
application-k8s.yaml — For Kubernetes deployment

Profile activation is managed through the spring.profiles.active property, which can be overridden using environment variables in your CI/CD pipeline or Kubernetes manifests.

#### Kubernetes Manifests Included
For each microservice, Kubernetes manifests are provided:

*-deployment.yaml — Defines container, environment, ports, and profile

*-service.yaml — Cluster-internal networking

Example deployment:

```bash
kubectl apply -f user-service-deployment.yaml
kubectl apply -f user-service-service.yaml
```
All services register with eureka-service via Kubernetes internal DNS (e.g., http://eureka-service:8761/eureka), making service discovery and routing fully dynamic.

#### CI/CD Ready
The structure supports modern CI/CD workflows:

Container builds via Dockerfile

Environment-specific config via profiles

Kubernetes manifests ready for kubectl or GitOps pipelines

Easily integrated into GitHub Actions, GitLab CI, Jenkins, or ArgoCD


## Roadmap

- Implement Swagger/OpenAPI documentation
- Add more domain services (e.g., listing, booking)

## License

This project is licensed under the MIT License.

