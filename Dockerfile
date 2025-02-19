# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:3.9.9-amazoncorretto-17-al2023 AS builder
#hd
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
FROM openjdk:24-slim-bullseye

WORKDIR /app

COPY --from=builder /app/target/SCRS-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5053

ENTRYPOINT ["java", "-jar", "app.jar"]
