# Stage 1: Build the application securely in an isolated environment
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Create the optimized package, skipping tests so Render deployments are fast
RUN mvn clean package -DskipTests

# Stage 2: Create a tiny, production-ready runtime container
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy only the compiled JAR file from the first stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render automatically maps this based on $PORT environment var)
EXPOSE 8080

# Spin up the Spring Boot application!
ENTRYPOINT ["java", "-jar", "app.jar"]
