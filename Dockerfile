# Step 1: Build stage using Maven with Amazon Corretto (JDK 21)
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/target/STUDENTCOURSEREGISTRATIONSYSTEM-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5053

ENTRYPOINT ["java", "-jar", "app.jar"]
