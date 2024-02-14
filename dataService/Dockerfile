FROM openjdk:21

# Set the working directory in the image
WORKDIR /usr/src/app

# Copy the Maven wrapper and the project files
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# Build the application
RUN ./mvnw dependency:go-offline

# Copy the application code
COPY src src

# Build the JAR file
RUN ./mvnw package -DskipTests

# Expose the port that the application will run on
EXPOSE 8082

# Run the JAR file
CMD ["java", "-jar", "target/dataService-0.0.1-SNAPSHOT.jar"]