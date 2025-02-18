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

# Copy the built jar from the build stage to the runtime image
COPY --from=build /app/target/STUDENTCURSEREGISTRATIONSYSTEM-0.0.1-SNAPSHOT.jar /app/STUDENTCURSEREGISTRATIONSYSTEM.jar

# Expose port 8080 (this is the port your application will run on)
EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/STUDENTCURSEREGISTRATIONSYSTEM.jar"]
