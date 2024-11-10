# Use a base image with Java (specifically OpenJDK)
FROM eclipse-temurin:22-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file to the container
COPY target/Taller-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
