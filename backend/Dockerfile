# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from your build to the container
COPY target/BackendApplication.jar app.jar

# Expose the port your Spring app is listening on
EXPOSE 8005

# Command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]
