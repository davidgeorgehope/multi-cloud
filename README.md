# Kafka-Confluent-Elastic-Observability

This repository provides a comprehensive solution for monitoring Kafka-based applications implemented on Confluent Cloud with Elastic Observability. The solution covers instrumentation with Elastic APM, JMX for Kafka Producer/Consumer metrics, Prometheus integration, and machine learning anomaly detection.

## Architecture

- **AWS EKS**: Spring WebFlux service for REST Endpoint to Kafka topic.
- **Confluent Cloud**: Kafka cluster and topics.
- **GCP**: Spring WebFlux service that forwards messages to another service for BigQuery storage.

## Prerequisites

- Java
- Maven
- Docker
- Kubernetes CLI (kubectl)

## Quick Start

1. Clone the repository: `git clone https://github.com/davidgeorgehope/multi-cloud`
2. Build the project for each service: `mvn clean install`
3. Run the Java project locally: `java -jar gcp-bigdata-consumer-multi-cloud-0.0.1-SNAPSHOT.jar --spring.config.location=/Users/davidhope/applicaiton-gcp.properties`
4. Create a Docker image: Run `./dockerBuild.sh`
5. Deploy to Kubernetes: Follow the provided guide in the blog.
6. Configure the properties files with Elastic Cloud and Confluent Cloud credentials.
7. Add the configuration for each service in Kubernetes.
8. Create Kubernetes deployments and services.
9. Access the application and test the service endpoint using curl.
