# build base image
FROM maven:3-openjdk-11-slim as maven

# copy pom.xml
COPY ./pom.xml ./pom.xml

# copy src files
COPY ./src ./src

# build
RUN mvn package

# final base image
FROM openjdk:11-jre-slim

# set deployment directory
WORKDIR /scholars

# copy over the built artifact from the maven image
COPY --from=maven /target/middleware*.jar ./scholars-discovery.jar

# set the startup command to run your binary
CMD ["java", "-jar", "./scholars-discovery.jar"]
