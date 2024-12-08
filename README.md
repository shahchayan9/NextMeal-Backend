# NextMeal Backend Repository

This repository contains the backend services for the **NextMeal** application, implemented as microservices. Each microservice has a distinct responsibility and is designed for scalability and maintainability.

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

### 1. **AI Assistant Service**
- **Backend Framework**: Python Flask
- **Database**: OpenSearch (vector database in AWS)
- **External API**: OpenAI API
- **Description**: Provides AI-driven assistance for user queries, such as recommending meals, answering FAQs, or providing guidance on using the application.
- **Recent Changes**: Initial implementation with AI assistant logic added.

### 2. **User Auth Service**
- **Backend Framework**: Node.js
- **Database**: PostgreSQL
- **Description**: Handles user authentication, authorization, and session management. It ensures secure access to the platform using industry-standard authentication protocols.
- **Recent Changes**: Service added with authentication endpoints.

### 3. **Reservation Handler Service**
- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL (RDS in AWS)
- **Queue**: Amazon SQS
- **Description**: Manages restaurant reservation requests, including creating, updating, and canceling reservations. It also integrates with the Reservation Queue Service for real-time updates.
- **Recent Changes**: Added configuration for CORS and updated request handling logic.

### 4. **Reservation Queue Service**
- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL (RDS in AWS)
- **Queue**: Amazon SQS
- **Description**: Handles queue management for reservations, ensuring that users are notified about their reservation status in real-time.
- **Recent Changes**: Configured CORS and updated view request logic for smoother integration.

### 5. **Restaurant Service**
- **Backend Framework**: Python Flask
- **Database**: PostgreSQL (primary database), Redis (for caching via AWS Elasticache)
- **Storage**: Amazon S3 (for storing restaurant images)
- **Description**: Manages restaurant information, including details about menus, locations, and availability. It uses Redis caching for faster data retrieval and stores images in S3.
- **Recent Changes**: Added Redis caching and S3 storage integration.

### 6. **Review Management Service**
- **Backend Framework**: Python Flask
- **Database**: DynamoDB (created in AWS)
- **Description**: Handles user reviews for restaurants, including creating, reading, updating, and deleting reviews.
- **Recent Changes**: Fixed minor issues and improved stability.

---

## Technologies Used

- **Backend Frameworks**: Python Flask, Spring Boot, Node.js
- **Databases**: PostgreSQL (via AWS RDS), DynamoDB, OpenSearch (AWS)
- **Caching**: Redis (via AWS Elasticache)
- **Message Queues**: Amazon SQS
- **Storage**: Amazon S3
- **External APIs**: OpenAI API for AI-related functionalities
- **Containerization**: Docker
- **Container Registry**: AWS ECR
- **Orchestration**: AWS ECS
- **Frontend Integration**: Services are integrated with a ReactJS frontend.

---

## Setup Instructions

Follow these steps to set up and run the services locally:

### Prerequisites

- Python 3.9 or higher
- Node.js
- Spring Boot (Java 17)
- Redis
- PostgreSQL
- DynamoDB (AWS CLI configured)
- OpenSearch (or a compatible vector database)
- Docker (for local testing)
