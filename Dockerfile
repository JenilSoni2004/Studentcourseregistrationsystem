# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:3.9.9-amazoncorretto-21-alpine AS builder

# Set working directory inside container
WORKDIR /app

# Copy the necessary files
COPY ./pom.xml .
COPY ./src ./src

# Run Maven build (excluding tests for faster build)
RUN mvn clean package -DskipTests

# Step 2: Create a minimal runtime image with JDK 21
FROM alpine/java:21-jdk

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/SCRS-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 5053

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
