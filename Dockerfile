# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/*.jar app.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
