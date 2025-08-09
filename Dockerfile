# Stage 1: Build the application with Maven
FROM maven:3.8-openjdk-17-slim AS builder
WORKDIR /app
COPY ./backend/pom.xml ./pom.xml
COPY ./backend/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final, smaller image with only the JRE
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]