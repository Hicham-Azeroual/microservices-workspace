# EazyBank Microservices Project

This repository contains the EazyBank microservices project, a comprehensive banking application built with Spring Boot and deployed using Kubernetes and Helm. The project is structured to demonstrate best practices in microservices development, containerization, and orchestration.

## Project Structure

The project is organized into several microservices, each responsible for a specific business capability:

- **accounts**: Manages customer account information.
- **cards**: Handles credit card details and operations.
- **configserver**: Centralized configuration management for all microservices.
- **eurekaserver**: Service discovery server for microservices registration and lookup.
- **gatewayserver**: API Gateway for routing and securing external requests.
- **loans**: Manages loan applications and details.
- **message**: Handles messaging functionalities within the application.

Each microservice has its own directory containing its source code, `pom.xml` for dependency management, and other related files.

## Technologies Used

- **Spring Boot**: Framework for building robust, production-ready microservices.
- **Maven**: Build automation tool for Java projects.
- **Docker**: Containerization platform for packaging applications.
- **Kubernetes**: Container orchestration platform for deploying and managing containerized applications.
- **Helm**: Package manager for Kubernetes, used to define, install, and upgrade complex Kubernetes applications.
- **Git**: Version control system.

## Getting Started

To get this project up and running, follow these steps:

### 1. Prerequisites

Ensure you have the following installed:

- Java 17 or higher
- Maven 3.8.x or higher
- Docker Desktop (or Docker Engine)
- kubectl (Kubernetes command-line tool)
- Helm 3.x or higher
- A Kubernetes cluster (e.g., Minikube, Docker Desktop Kubernetes, or a cloud-based cluster)

### 2. Build the Microservices

Navigate to the root directory of each microservice and build the Docker images. You can use the `build-all.ps1` script to build all services.

```bash
./build-all.ps1
```

Alternatively, for each service:

```bash
cd accounts
mvn clean package spring-boot:build-image
docker tag accounts:0.0.1-SNAPSHOT hichamazeroual2002/accounts:s11
docker push hichamazeroual2002/accounts:s11
# Repeat for cards, configserver, eurekaserver, gatewayserver, loans, message
```

### 3. Deploy to Kubernetes using Kustomize

The `kubernetes` directory contains Kustomize configurations for deploying the microservices.

```bash
cd kubernetes
kubectl apply -k .
```

### 4. Deploy to Kubernetes using Helm

The `helm` directory contains Helm charts for deploying the microservices.

#### Install Common Chart

```bash
helm install eazybank-common ./helm/eazybank-common
```

#### Install Microservices Charts

For each microservice, navigate to its chart directory and install:

```bash
helm install accounts ./helm/eazybank-services/accounts
helm install cards ./helm/eazybank-services/cards
# Repeat for other services
```

### 5. Accessing the Application

Once deployed, you can access the application through the `gatewayserver` service. Depending on your Kubernetes setup, you might need to use `kubectl port-forward` or check the LoadBalancer IP.

```bash
kubectl get services -n default
# Look for the external IP or use port-forward
kubectl port-forward service/gatewayserver 8072:8072
```

Then, you can access the API Gateway at `http://localhost:8072`.

## Configuration

Centralized configuration is managed by the `configserver` microservice. Each microservice fetches its configuration from the `configserver` at startup.

## Service Discovery

`eurekaserver` acts as the service discovery mechanism, allowing microservices to register themselves and discover other services dynamically.

## API Gateway

The `gatewayserver` provides a single entry point for all client requests, handling routing, load balancing, and security concerns.

## Contributing

Feel free to fork this repository, make improvements, and submit pull requests.

## License

This project is licensed under the MIT License.