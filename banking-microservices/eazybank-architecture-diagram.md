# 🏦 EazyBank Microservices Architecture Diagram

## Complete System Architecture

```mermaid
graph TB
    %% Client Layer
    subgraph "Client Layer"
        WEB[Web Applications]
        MOBILE[Mobile Apps]
        API_CLIENT[API Clients]
    end

    %% API Gateway Layer
    subgraph "API Gateway Layer"
        GATEWAY[Gateway Server<br/>Port: 8072<br/>Spring Cloud Gateway<br/>OAuth2 + JWT<br/>Circuit Breaker]
    end

    %% Microservices Layer
    subgraph "Microservices Layer"
        ACCOUNTS[Accounts Service<br/>Port: 8080<br/>Account Management<br/>Transactions & Balances]
        LOANS[Loans Service<br/>Port: 8090<br/>Loan Applications<br/>Interest Calculations]
        CARDS[Cards Service<br/>Port: 9000<br/>Card Issuance<br/>Card Management]
        MESSAGE[Message Service<br/>Port: 8072<br/>Notifications<br/>Event Processing]
    end

    %% Infrastructure Services
    subgraph "Infrastructure Services"
        CONFIG[Config Server<br/>Port: 8071<br/>Spring Cloud Config<br/>Git Backend]
        EUREKA[Eureka Server<br/>Port: 8070<br/>Service Discovery<br/>Health Monitoring]
        KEYCLOAK[Keycloak<br/>Port: 7080<br/>OAuth2/OIDC Provider<br/>Identity Management]
    end

    %% Data Layer
    subgraph "Data Layer"
        ACCOUNT_DB[(AccountDB<br/>MySQL:3307<br/>accountdb)]
        LOANS_DB[(LoansDB<br/>MySQL:3308<br/>loansdb)]
        CARDS_DB[(CardsDB<br/>MySQL:3309<br/>cardsdb)]
    end

    %% Messaging Layer
    subgraph "Messaging Layer"
        RABBITMQ[RabbitMQ<br/>Ports: 5672, 15672<br/>Message Broker<br/>Event Streaming]
    end

    %% Observability Stack
    subgraph "Observability Stack"
        PROMETHEUS[Prometheus<br/>Port: 9090<br/>Metrics Collection<br/>Time Series DB]
        GRAFANA[Grafana<br/>Port: 3000<br/>Visualization<br/>Dashboards]
        LOKI_GATEWAY[Loki Gateway<br/>Port: 3100<br/>Nginx Proxy]
        LOKI_READ[Loki Read<br/>Port: 3101<br/>Query Service]
        LOKI_WRITE[Loki Write<br/>Port: 3102<br/>Ingest Service]
        LOKI_BACKEND[Loki Backend<br/>Port: 7946<br/>Storage Service]
        ALLOY[Alloy Agent<br/>Port: 12345<br/>Log Collection]
        MINIO[MinIO<br/>Port: 9001<br/>Object Storage<br/>S3 Compatible]
    end

    %% Client Connections
    WEB --> GATEWAY
    MOBILE --> GATEWAY
    API_CLIENT --> GATEWAY

    %% Gateway Routes
    GATEWAY -->|/eazybank/accounts| ACCOUNTS
    GATEWAY -->|/eazybank/loans| LOANS
    GATEWAY -->|/eazybank/cards| CARDS

    %% Service Discovery
    ACCOUNTS --> EUREKA
    LOANS --> EUREKA
    CARDS --> EUREKA
    MESSAGE --> EUREKA
    GATEWAY --> EUREKA

    %% Configuration Management
    ACCOUNTS --> CONFIG
    LOANS --> CONFIG
    CARDS --> CONFIG
    MESSAGE --> CONFIG
    GATEWAY --> CONFIG
    EUREKA --> CONFIG

    %% Authentication
    GATEWAY --> KEYCLOAK

    %% Database Connections
    ACCOUNTS --> ACCOUNT_DB
    LOANS --> LOANS_DB
    CARDS --> CARDS_DB

    %% Event-Driven Communication
    ACCOUNTS -->|Events| RABBITMQ
    RABBITMQ -->|Consume| MESSAGE

    %% Observability Connections
    ACCOUNTS -->|Metrics| PROMETHEUS
    LOANS -->|Metrics| PROMETHEUS
    CARDS -->|Metrics| PROMETHEUS
    MESSAGE -->|Metrics| PROMETHEUS
    GATEWAY -->|Metrics| PROMETHEUS

    ACCOUNTS -->|Logs| ALLOY
    LOANS -->|Logs| ALLOY
    CARDS -->|Logs| ALLOY
    MESSAGE -->|Logs| ALLOY
    GATEWAY -->|Logs| ALLOY

    ALLOY --> LOKI_WRITE
    LOKI_WRITE --> LOKI_BACKEND
    LOKI_READ --> LOKI_BACKEND
    LOKI_GATEWAY --> LOKI_READ
    LOKI_GATEWAY --> LOKI_WRITE
    LOKI_BACKEND --> MINIO

    PROMETHEUS --> GRAFANA
    LOKI_GATEWAY --> GRAFANA

    %% Styling
    classDef clientStyle fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef gatewayStyle fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef serviceStyle fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef infraStyle fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef dataStyle fill:#fce4ec,stroke:#880e4f,stroke-width:2px
    classDef messageStyle fill:#f1f8e9,stroke:#33691e,stroke-width:2px
    classDef obsStyle fill:#e0f2f1,stroke:#004d40,stroke-width:2px

    class WEB,MOBILE,API_CLIENT clientStyle
    class GATEWAY gatewayStyle
    class ACCOUNTS,LOANS,CARDS,MESSAGE serviceStyle
    class CONFIG,EUREKA,KEYCLOAK infraStyle
    class ACCOUNT_DB,LOANS_DB,CARDS_DB dataStyle
    class RABBITMQ messageStyle
    class PROMETHEUS,GRAFANA,LOKI_GATEWAY,LOKI_READ,LOKI_WRITE,LOKI_BACKEND,ALLOY,MINIO obsStyle
```

## Technology Stack Overview

### Core Technologies

- **Java 21** (LTS)
- **Spring Boot 3.5.x**
- **Spring Cloud 2025.0.0** (Ilford)
- **Maven 3.8+**

### Spring Cloud Components

- **Spring Cloud Config** - Centralized configuration
- **Spring Cloud Eureka** - Service discovery
- **Spring Cloud Gateway** - API gateway
- **Spring Cloud OpenFeign** - REST client
- **Spring Cloud Bus** - Configuration refresh
- **Spring Cloud Stream** - Event-driven messaging
- **Circuit Breaker Resilience4j** - Fault tolerance

### Data & Storage

- **MySQL 8.0** - Per-service databases
- **Spring Data JPA** - Data access
- **Hibernate** - ORM framework

### Security

- **Spring Security** - Authentication & authorization
- **OAuth2 Resource Server** - JWT validation
- **Keycloak 24.0.1** - Identity management

### Messaging

- **RabbitMQ 3-management** - Message broker
- **Spring AMQP** - RabbitMQ integration

### Observability

- **Prometheus** - Metrics collection
- **Grafana** - Visualization
- **Loki** - Log aggregation
- **Alloy** - Log collection agent
- **Micrometer** - Metrics instrumentation

### Container & Orchestration

- **Docker** - Containerization
- **Docker Compose** - Local orchestration
- **Kubernetes** - Production orchestration
- **Helm** - Package management
- **Jib** - Docker image building

## Service Ports & Endpoints

| Service          | Port        | Purpose            | Key Endpoints                                              |
| ---------------- | ----------- | ------------------ | ---------------------------------------------------------- |
| Gateway Server   | 8072        | API Gateway        | `/eazybank/accounts`, `/eazybank/loans`, `/eazybank/cards` |
| Accounts Service | 8080        | Account Management | `/api/create`, `/api/fetch`, `/api/update`, `/api/delete`  |
| Loans Service    | 8090        | Loan Processing    | `/api/create`, `/api/fetch`, `/api/update`, `/api/delete`  |
| Cards Service    | 9000        | Card Management    | `/api/create`, `/api/fetch`, `/api/update`, `/api/delete`  |
| Message Service  | 8072        | Notifications      | Event consumers                                            |
| Config Server    | 8071        | Configuration      | Configuration management                                   |
| Eureka Server    | 8070        | Service Discovery  | Service registry                                           |
| Keycloak         | 7080        | Authentication     | OAuth2/OIDC provider                                       |
| RabbitMQ         | 5672, 15672 | Messaging          | Message broker                                             |
| Prometheus       | 9090        | Metrics            | Metrics collection                                         |
| Grafana          | 3000        | Visualization      | Dashboards                                                 |
| Loki Gateway     | 3100        | Log Gateway        | Log aggregation                                            |
| Alloy            | 12345       | Log Collection     | Log forwarding                                             |

## Database Schema

| Database  | Port | Purpose      | Schema      |
| --------- | ---- | ------------ | ----------- |
| AccountDB | 3307 | Account data | `accountdb` |
| LoansDB   | 3308 | Loan data    | `loansdb`   |
| CardsDB   | 3309 | Card data    | `cardsdb`   |

## Key Features

### ✅ Microservices Architecture

- Decoupled, independently deployable services
- Database per service pattern
- Event-driven communication

### ✅ Security

- OAuth2/JWT authentication
- Keycloak identity management
- Secure API gateway

### ✅ Observability

- Comprehensive metrics collection
- Centralized logging
- Real-time monitoring dashboards

### ✅ Resilience

- Circuit breaker patterns
- Health checks
- Graceful degradation

### ✅ Scalability

- Horizontal scaling support
- Load balancing
- Container orchestration

## Deployment Options

### Local Development

```bash
# Start infrastructure
cd docker-compose/prod
docker compose up -d

# Build and run services
./build-all.ps1
```

### Production Deployment

```bash
# Kubernetes deployment
kubectl apply -f kubernetes/

# Helm deployment
helm install eazybank-services ./helm/eazybank-services/
```

This architecture demonstrates enterprise-grade microservices patterns with comprehensive observability, security, and scalability features.
