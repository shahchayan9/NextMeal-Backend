# Use a lightweight Java runtime image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/reservation-queue-service-1.0.0.jar reservation-queue-service.jar

# Expose the service port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "reservation-queue-service.jar"]