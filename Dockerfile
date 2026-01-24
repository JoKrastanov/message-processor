FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/message-processor-0.0.1-SNAPSHOT.jar app.jar

# Expose port if you plan to run REST or H2 console (optional)
EXPOSE 8080

# Entry point
ENTRYPOINT ["java", "-jar", "app.jar"]
