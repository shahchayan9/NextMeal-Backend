# NextMeal Backend Repository

At **NextMeal**, we are building an intelligent dining platform that seamlessly integrates a full suite of technologies: secure user authentication via **Keycloak** (SSO and 2FA), microservices built with **Python Flask**, **Node.js**, and **Spring Boot (Java)**, persistent data storage using **PostgreSQL** (RDS), **DynamoDB**, and **OpenSearch** for vector embeddings, **Redis (Elasticache)** for caching, **SQS** for messaging, **S3** for media storage, and **OpenAI** for AI-driven recommendations. All components are containerized with **Docker**, stored in **AWS ECR**, and orchestrated on **AWS ECS**, providing a scalable and maintainable ecosystem that dynamically adapts to user preferences and enhances their dining experience.

## Table of Contents

- [Overview](#overview)
- [Microservices](#microservices)
  - [AI Assistant Service](#ai-assistant-service)
  - [User Auth Service](#user-auth-service)
  - [Reservation Handler Service](#reservation-handler-service)
  - [Reservation Queue Service](#reservation-queue-service)
  - [Restaurant Service](#restaurant-service)
  - [Review Management Service](#review-management-service)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Deployment](#deployment)
- [Contributing](#contributing)

---

## Overview

This backend system is designed as a collection of six microservices that handle various aspects of the **NextMeal** platform. These services include user authentication, restaurant information, reservation handling, review management, queue processing, and AI-powered assistance.

All microservices are containerized using **Docker**, their images are pushed to **AWS ECR (Elastic Container Registry)**, and they are deployed on **AWS ECS (Elastic Container Service)**. Databases and other AWS services such as S3, SQS, Elasticache, and OpenSearch are fully integrated.

---

## Microservices

### 1. AI Assistant Service
- Backend Framework: Python Flask
- Database: OpenSearch (AWS)
- External API: OpenAI API
- Description: Provides AI-driven assistance for user queries, such as recommending meals, answering FAQs, or providing guidance.
- Recent Changes: Initial implementation with AI assistant logic added.

### 2. User Auth Service
- Backend Framework: Node.js
- Database: PostgreSQL
- Description: Handles user authentication, authorization, and session management.
- Recent Changes: Service added with authentication endpoints.

### 3. Reservation Handler Service
- Backend Framework: Spring Boot
- Database: PostgreSQL (RDS in AWS)
- Queue: Amazon SQS
- Description: Manages restaurant reservations, integrates with Reservation Queue Service for real-time updates.
- Recent Changes: Added CORS config and updated request handling logic.

### 4. Reservation Queue Service
- Backend Framework: Spring Boot
- Database: PostgreSQL (RDS in AWS)
- Queue: Amazon SQS
- Description: Manages reservation queues for real-time status updates.
- Recent Changes: Configured CORS and updated request logic.

### 5. Restaurant Service
- Backend Framework: Python Flask
- Database: PostgreSQL (primary), Redis (AWS Elasticache)
- Storage: Amazon S3 (for images)
- Description: Manages restaurant info, menus, locations, availability. Uses Redis caching and S3 for images.
- Recent Changes: Added Redis caching and S3 integration.

### 6. Review Management Service
- Backend Framework: Python Flask
- Database: DynamoDB (AWS)
- Description: Handles user reviews (CRUD operations).
- Recent Changes: Fixed minor issues and improved stability.

---

## Technologies Used
- Backend: Python Flask, Spring Boot, Node.js
- Databases: PostgreSQL (RDS), DynamoDB, OpenSearch
- Caching: Redis (Elasticache)
- Queue: Amazon SQS
- Storage: Amazon S3
- External APIs: OpenAI
- Authentication: Keycloak (SSO, 2FA)
- Containerization: Docker
- Registry: AWS ECR
- Orchestration: AWS ECS
- Monitoring: AWS CloudWatch

---

## Setup Instructions

### Prerequisites
- Python 3.9+
- Node.js
- Spring Boot (Java 17+)
- Redis
- PostgreSQL
- AWS CLI configured for DynamoDB, OpenSearch
- Docker

---

## Deployment

1. Clone the repository:
   `git clone <repository-url>`
   `cd <repository-folder>`

2. Preparing Microservices:
   
   - Navigate to each microservice directory:  
     `cd <microservice-name>`
   
   - Install dependencies:  
     For Python (Flask): `pip3 install -r requirements.txt`  
     For Node.js: `npm install`  
     For Spring Boot (Java 21): Build with Maven/Gradle in IDE.
   
   - Configure environment variables in `.env` files:  
     - Database endpoints  
     - Redis connection strings  
     - SQS queue URLs  
     - API keys (e.g., OpenAI)
   
   - Run microservices locally:  
     Python (Flask): `python3 app.py`  
     Node.js: `npm start`  
     Spring Boot (Java 17): `mvn spring-boot:run`
   
   - Containerize and push to AWS ECR:  
     Create Docker images: `docker build -t <image-name> .`  
     Authenticate and push images:  
     `aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <account-id>.dkr.ecr.<region>.amazonaws.com`  
     `docker tag <image-name>:latest <account-id>.dkr.ecr.<region>.amazonaws.com/<repository-name>`  
     `docker push <account-id>.dkr.ecr.<region>.amazonaws.com/<repository-name>`
   
   - Deploy to AWS ECS:  
     - Create task definitions (AWS Management Console or CLI).  
     - Deploy tasks to an ECS cluster.  
     - Use a load balancer for public-facing services if needed.
   
   - Additional Resources:  
     - Databases: PostgreSQL (RDS), DynamoDB  
     - Caching: Redis (Elasticache)  
     - Messaging: SQS  
     - Storage: S3  
     - Search: OpenSearch  
     - Monitoring: CloudWatch
   
   - CI/CD Integration:  
     - Use AWS CodePipeline and CodeBuild for automated deployments.  
     - Add tests for all services before deployment.
   
   - Scaling and Monitoring:  
     - Enable ECS auto-scaling.  
     - Monitor via CloudWatch.

---

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature/fix.
3. Submit a pull request with a detailed description.
