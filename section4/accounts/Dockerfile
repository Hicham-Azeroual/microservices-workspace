# Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

# Add Maintainer Info
LABEL maintainer="hicham@eb.com"
# Add a jar file to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]

