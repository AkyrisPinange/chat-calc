# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src src

# Set environment variables
ENV URL_DATABASE mongodb+srv://chatCalc:VPwvhOFflZDthucC@cluster0.ruvp1om.mongodb.net/chat?retryWrites=true&w=majority

ENV PORT 8080

# JWT secret key
# node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
ENV SECRET_KEY 48a868a4042f634ac04a117f00a87202131dd7c46c4b32c4acb3edc5e15f4511

# JWT expiration is 24 hour
ENV TOKEN_EXPIRATION 10024060000

# Build the application
RUN mvn package

# Stage 2: Create a lightweight JRE image to run the application
FROM openjdk:17.0.1-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /app/target/chat-calc-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port your application will listen on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]