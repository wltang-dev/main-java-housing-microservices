# Use the official OpenJDK image
FROM openjdk:17-jdk-slim

# Copy the built JAR file into the container (modify the path if necessary)
ARG JAR_FILE=target/house-client-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
