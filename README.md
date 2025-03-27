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
- Publishes orders to a Kafka topic (`processed-orders`)
- Listens for processing confirmation and updates order status

#### Endpoints
| Method | Endpoint               | Description                         |
|--------|------------------------|-------------------------------------|
| POST   | /orders/receive        | Add a new order                    |
| GET    | /orders/all-unprocessed | Get all unprocessed orders         |
| GET    | /orders/addHardcoded    | Add sample orders                  |

#### Kafka Configuration
- **Producer Topic:** `processed-orders`

### 2. Archiver Service
- Listens for processed orders
- Archives them in a database
- Exposes REST API to retrieve archived orders

#### Endpoints
| Method | Endpoint               | Description                        |
|--------|------------------------|------------------------------------|
| POST   | /archived-orders       | Archive an order                  |
| GET    | /archived-orders/all   | Get all archived orders           |

#### Kafka Configuration
- **Consumer Topic:** `processed-orders`

## Setup Instructions

### Prerequisites
- Java 17+
- Apache Kafka 4.0+
- PostgreSQL/MongoDB
- Maven

### Installing and Running Kafka

#### Step 1: Download and Extract Kafka
```sh
wget https://downloads.apache.org/kafka/4.0.0/kafka_2.13-4.0.0.tgz
tar -xzf kafka_2.13-4.0.0.tgz
cd kafka_2.13-4.0.0
```

#### Step 2: Start the Kafka Environment
**NOTE:** Your local environment must have **Java 17+** installed.

Kafka can be run using local scripts and downloaded files or the Docker image.

##### Generate a Cluster UUID  
```sh  
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"  
```

##### Format Log Directories  
```sh  
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties  
```

##### Start Zookeeper (Kafka requires Zookeeper to run)
```sh
bin/zookeeper-server-start.sh config/zookeeper.properties &
```

##### Start Kafka Broker
```sh
bin/kafka-server-start.sh config/server.properties &
```

##### Verify Kafka is Running
```sh
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

### Steps to Run Microservices
1. **Create Kafka Topic**
   ```sh
   bin/kafka-topics.sh --create --topic processed-orders --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   ```
2. **Run Order Processing Service**
   ```sh
   mvn spring-boot:run
   ```
3. **Run Archiver Service**
   ```sh
   mvn spring-boot:run
   ```

## Testing the Workflow
1. **Submit an order:**
   ```sh
   curl -X POST http://localhost:8080/orders/receive -H "Content-Type: application/json" -d '{"orderName": "Laptop"}'
   ```
2. **Retrieve all unprocessed orders:**
   ```sh
   curl -X GET http://localhost:8080/orders/all-unprocessed
   ```
3. **Add hardcoded test orders:**
   ```sh
   curl -X GET http://localhost:8080/orders/addHardcoded
   ```
4. **Check archived orders using API:**
   ```sh
   curl -X GET http://localhost:8081/archived-orders/all
   ```
5. **Test archiving an order manually:**
   ```sh
   curl -X POST http://localhost:8081/archived-orders -H "Content-Type: application/json" -d '{
     "id": "123",
     "orderName": "Smartphone"
   }'
   ```
   **Expected Responses:**
   - `200 OK` - Order archived successfully.
   - `400 Bad Request` - If order ID is missing.
   - `500 Internal Server Error` - If saving fails.

## Future Enhancements
- Implement retries for failed messages
- Add monitoring with Prometheus and Grafana
- Deploy using Kubernetes

## Contributors
- Arun
