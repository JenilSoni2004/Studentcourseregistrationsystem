# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:3.9.9-amazoncorretto-21-alpine AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
FROM alpine/java:21-jdk

WORKDIR /app

COPY --from=builder /app/target/StudentCourseRegistrationSystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5053

ENTRYPOINT ["java", "-jar", "app.jar"]
