# Kafka-Based Order Processing System

## Overview
This project consists of two microservices that communicate using Apache Kafka:
1. **Order Processing Service** - Receives orders and publishes them to Kafka.
2. **Archiver Service** - Consumes processed orders from Kafka and archives them.

## Architecture
- **Spring Boot** for microservices
- **Kafka** for event-driven communication
- **PostgreSQL/MongoDB** for persistence
- **Spring Data JPA** for database interactions
- **Docker (optional)** for containerization

## Microservices

### 1. Order Processing Service
- Exposes REST API to receive orders
- Publishes orders to a Kafka topic (`order-events`)
- Listens for processing confirmation and updates order status

#### Endpoints
| Method | Endpoint         | Description              |
|--------|----------------|--------------------------|
| POST   | /orders/add    | Add a new order         |
| GET    | /orders/all    | Get all pending orders  |
| GET    | /orders/hardcoded | Add sample orders |

#### Kafka Configuration
- **Producer Topic:** `order-events`
- **Consumer Topic:** `archived-orders`

### 2. Archiver Service
- Listens for processed orders
- Archives them in a database
- Sends a confirmation message back to Kafka

#### Kafka Configuration
- **Consumer Topic:** `order-events`
- **Producer Topic:** `archived-orders`

## Setup Instructions

### Prerequisites
- Java 17+
- Apache Kafka 4.0+
- PostgreSQL/MongoDB
- Maven

### Steps to Run
1. **Start Kafka**
   ```sh
   kafka-server-start.sh config/server.properties
   ```
2. **Create Kafka Topics**
   ```sh
   kafka-topics.sh --create --topic order-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   kafka-topics.sh --create --topic archived-orders --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   ```
3. **Run Order Processing Service**
   ```sh
   mvn spring-boot:run
   ```
4. **Run Archiver Service**
   ```sh
   mvn spring-boot:run
   ```

## Testing the Workflow
1. **Submit an order:**
   ```sh
   curl -X POST http://localhost:8080/orders/add -H "Content-Type: application/json" -d '{"orderName": "Laptop"}'
   ```
2. **Verify processing in logs**
3. **Check archived orders in the database**

## Future Enhancements
- Implement retries for failed messages
- Add monitoring with Prometheus and Grafana
- Deploy using Kubernetes

## Contributors
- Arun Bhati
