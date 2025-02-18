# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy all files to the container
COPY . .

# Run the Maven clean and package command (skipping tests for now)
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image using a slim OpenJDK image
FROM openjdk:21-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built JAR from the build stage to the runtime image
COPY --from=build /app/target/STUDENTCOURSEREGISTRATIONSYSTEM-0.0.1-SNAPSHOT.jar /app/STUDENTCOURSEREGISTRATIONSYSTEM.jar

# Expose port 5053 (or update to match your app's configuration)
EXPOSE 5053

# Run the application with the correct path
ENTRYPOINT ["java", "-jar", "/app/STUDENTCOURSEREGISTRATIONSYSTEM.jar"]
