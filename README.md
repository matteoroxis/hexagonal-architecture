# Hexagonal Architecture with Spring Boot and MongoDB

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.0+-green.svg)](https://www.mongodb.com/)

A comprehensive example demonstrating **Hexagonal Architecture** (also known as **Ports and Adapters**) implementation using Spring Boot and MongoDB. This project showcases clean architecture principles, domain-driven design, and separation of concerns.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)
- [Design Patterns](#design-patterns)
- [Contributing](#contributing)

---

## 🎯 Overview

This project implements an **Order Management System** following hexagonal architecture principles. The architecture ensures:

- ✅ **Domain Independence**: Business logic is isolated from external concerns
- ✅ **Testability**: Easy to test components in isolation
- ✅ **Flexibility**: Easy to swap adapters (e.g., switch from MongoDB to PostgreSQL)
- ✅ **Maintainability**: Clear separation of concerns and responsibilities

---

## 🏗️ Architecture

### Hexagonal Architecture Layers

```
┌─────────────────────────────────────────────────────────────┐
│                    External World (Adapters)                 │
│  ┌─────────────────┐              ┌──────────────────────┐  │
│  │   REST API      │              │   MongoDB Adapter    │  │
│  │  (Controller)   │              │   (Persistence)      │  │
│  └────────┬────────┘              └──────────┬───────────┘  │
│           │                                   │              │
│           │ Input Port              Output Port│             │
├───────────▼───────────────────────────────────▼─────────────┤
│                     Application Layer                        │
│              (Use Cases / Business Logic)                    │
│                    ShipOrderService                          │
├────────────────────────��─────────────────────────────────────┤
│                       Domain Layer                           │
│        (Entities, Value Objects, Domain Logic)               │
│          Order | OrderId | Money | OrderStatus              │
└──────────────────────────────────────────────────────────────┘
```

### Key Components

#### **Domain Layer** (Core)
- **Entities**: `Order` - Core business entity with state and behavior
- **Value Objects**: `OrderId`, `Money`, `OrderStatus` - Immutable objects representing domain concepts
- **Business Rules**: Enforced within entities (e.g., only paid orders can be shipped)

#### **Ports** (Interfaces)
- **Input Ports**: `ShipOrderUseCase` - Define application entry points
- **Output Ports**: `LoadOrder`, `SaveOrder` - Define external dependencies

#### **Adapters**
- **Input Adapters**: `OrderController` - REST API endpoints
- **Output Adapters**: `MongoLoadOrderAdapter`, `MongoSaveOrderAdapter` - MongoDB persistence

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── it/matteoroxis/hexagonal_architecture/
│   │       ├── domain/                    # Domain Layer (Core)
│   │       │   ├── Order.java
│   │       │   ├── OrderId.java
│   │       │   ├── Money.java
│   │       │   └── OrderStatus.java
│   │       │
│   │       ├── ports/                     # Ports (Interfaces)
│   │       │   ├── in/
│   │       │   │   └── ShipOrderUseCase.java
│   │       │   └── out/
│   │       │       ├── LoadOrder.java
│   │       │       └── SaveOrder.java
│   │       │
│   │       ├── adapters/                  # Adapters
│   │       │   ├── controller/            # Input Adapter (REST)
│   │       │   │   ├── OrderController.java
│   │       │   │   └── GlobalExceptionHandler.java
│   │       │   │
│   │       │   ├── service/               # Application Services
│   │       │   │   └── ShipOrderService.java
│   │       │   │
│   │       │   ├── repository/            # Output Adapter (Persistence)
│   │       │   │   └── MongoOrderRepository.java
│   │       │   │
│   │       │   ├── documents/             # MongoDB Documents
│   │       │   │   └── OrderDocument.java
│   │       │   │
│   │       │   ├── mapper/                # Domain ↔ Document Mapping
│   │       │   │   └── OrderMapper.java
│   │       │   │
│   │       │   ├── MongoLoadOrderAdapter.java
│   │       │   └── MongoSaveOrderAdapter.java
│   │       │
│   │       ├── config/                    # Configuration
│   │       │   └── OrderAdapterConfiguration.java
│   │       │
│   │       └── exception/                 # Custom Exceptions
│   │           └── OrderNotFoundException.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    ��── java/
        └── it/matteoroxis/hexagonal_architecture/
            ├── domain/                    # Domain Tests
            │   ├── OrderTest.java
            │   ├── MoneyTest.java
            │   └── OrderIdTest.java
            │
            ├── adapters/
            │   ├── controller/
            │   │   └── OrderControllerTest.java
            │   ├── service/
            │   │   └── ShipOrderServiceTest.java
            │   ├── mapper/
            │   │   └── OrderMapperTest.java
            │   ├── MongoLoadOrderAdapterTest.java
            │   └── MongoSaveOrderAdapterTest.java
            │
            └── integration/
                └── OrderIntegrationTest.java
```

---

## 🛠️ Technologies

- **Java 25** - Programming language
- **Spring Boot 3.5.10** - Application framework
- **Spring Data MongoDB** - MongoDB integration
- **MongoDB 6.0+** - NoSQL database
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Testcontainers** - Integration testing with containers
- **Maven** - Build tool

---

## 🚀 Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.8+
- MongoDB 6.0+ (or Docker)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/matteoroxis/exagonal-architecture.git
   cd exagonal-architecture
   ```

2. **Start MongoDB** (using Docker)
   ```bash
   docker run -d -p 27017:27017 --name mongodb mongo:6.0
   ```

3. **Configure MongoDB** (Optional)
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/hexagonal-db
   spring.data.mongodb.database=hexagonal-db
   ```

4. **Build the project**
   ```bash
   ./mvnw clean install
   ```

5. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

---

## 📡 API Endpoints

### Ship an Order

**Endpoint**: `POST /api/orders/{orderId}/ship`

**Description**: Ships a paid order. Only orders with status `PAID` can be shipped.

**Example Request**:
```bash
curl -X POST http://localhost:8080/api/orders/123/ship
```

**Success Response**:
- **Code**: `200 OK`

**Error Responses**:
- **Code**: `500 Internal Server Error`
  - Order not found
  - Order is not in PAID status

**Example Error Response**:
```json
{
  "status": 500,
  "message": "Only paid orders can be shipped"
}
```

---

## 🧪 Running Tests

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Classes
```bash
# Domain tests
./mvnw test -Dtest=OrderTest

# Service tests
./mvnw test -Dtest=ShipOrderServiceTest

# Controller tests
./mvnw test -Dtest=OrderControllerTest

# Integration tests
./mvnw test -Dtest=OrderIntegrationTest
```

### Test Coverage

The project includes comprehensive tests at multiple levels:

- **Unit Tests**: Domain entities, value objects, and services
- **Integration Tests**: Adapters and repositories
- **End-to-End Tests**: Full application flow with Testcontainers

---

## 🎨 Design Patterns

### 1. **Hexagonal Architecture (Ports & Adapters)**
Separates business logic from external concerns through well-defined interfaces.

### 2. **Dependency Inversion Principle**
Domain layer defines interfaces (ports); adapters implement them.

### 3. **Repository Pattern**
Abstracts data persistence logic through `LoadOrder` and `SaveOrder` ports.

### 4. **Mapper Pattern**
Converts between domain entities and persistence models (`OrderMapper`).

### 5. **Value Objects**
Immutable objects like `OrderId` and `Money` ensure domain integrity.

### 6. **Use Case Pattern**
Application services (`ShipOrderService`) orchestrate domain operations.

---

## 📊 Order State Machine

```
┌──────────┐      markAsPaid()     ┌──────────┐
│ PENDING  │ ───────────────────► │   PAID   │
└──────────┘                       └────┬─────┘
                                        │
                                        │ markAsShipped()
                                        ▼
                                   ┌──────────┐
                                   │ SHIPPED  │
                                   └────┬─────┘
                                        │
                                        │ markAsDelivered()
                                        ▼
                                   ┌──────────┐
                                   │DELIVERED │
                                   └──────────���
```

---

## 🔑 Key Benefits

### ✨ Domain-Centric Design
- Business logic is isolated in the domain layer
- No framework dependencies in core domain
- Easy to understand and maintain

### 🧪 High Testability
- Domain can be tested without any infrastructure
- Adapters can be easily mocked
- Fast unit tests execution

### 🔄 Flexibility
- Easy to change persistence mechanism (MongoDB → PostgreSQL)
- Easy to add new adapters (REST → gRPC)
- No vendor lock-in

### 📦 Single Responsibility
- Each layer has a clear purpose
- Components are focused and cohesive
- Easy to navigate codebase

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Matteo Roxis**

---

## 📚 Further Reading

- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Ports and Adapters Pattern](https://herbertograca.com/2017/09/14/ports-adapters-architecture/)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [Clean Architecture by Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

⭐ If you find this project helpful, please consider giving it a star!
