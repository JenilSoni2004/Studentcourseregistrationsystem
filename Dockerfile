# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:3.9.6-amazoncorretto-21 AS build

# Set working directory
WORKDIR /app

# Copy all files to the container
COPY . .

# Run the Maven clean and package command (skipping tests)
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image using Amazon Corretto 21 Alpine
FROM amazoncorretto:21-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/STUDENTCOURSEREGISTRATIONSYSTEM-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose port 5053 (update if needed)
EXPOSE 5053

# Run the application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
