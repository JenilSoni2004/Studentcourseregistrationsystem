# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:amazoncorretto-21-alpine AS build

# Copy all files to the container
COPY SCRS .

# Run the Maven clean and package command (skipping tests for now)
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image using a slim OpenJDK image
FROM openjdk:21-jdk-slim

# Copy the built jar from the build stage to the runtime image
COPY --from=build /target/STUDENTCURSEREGISTRATIONSYSTEM-0.0.1-SNAPSHOT.jar /STUDENTCURSEREGISTRATIONSYSTEM.jar

# Expose port 8080 (this is the port your application will run on)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/STUDENTCURSEREGISTRATIONSYSTEM.jar"]
