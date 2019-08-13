# our base build image
FROM maven:3-jdk-8-slim as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package

# our final base image
FROM openjdk:8u171-jre-alpine

# set deployment directory
WORKDIR /scholars

# copy over the built artifact from the maven image
COPY --from=maven /target/middleware*.jar ./scholars-discovery.jar

# set the startup command to run your binary
CMD ["java", "-jar", "./scholars-discovery.jar"]