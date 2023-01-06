# Settings.
ARG USER_ID=3001
ARG USER_NAME=scholars
ARG HOME_DIR=/$USER_NAME
ARG SOURCE_DIR=$HOME_DIR/source

# Maven stage.
FROM maven:3-eclipse-temurin-17-alpine as maven
ARG USER_ID
ARG USER_NAME
ARG HOME_DIR
ARG SOURCE_DIR

# Create the group (use a high ID to attempt to avoid conflits).
RUN addgroup -g $USER_ID $USER_NAME

# Create the user (use a high ID to attempt to avoid conflits).
RUN adduser -h $HOME_DIR -u $USER_ID -G $USER_NAME -D $USER_NAME

# Update the system.
RUN apk -U upgrade

# Set deployment directory.
WORKDIR $SOURCE_DIR

# Copy files over.
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Assign file permissions.
RUN chown -R ${USER_ID}:${USER_ID} ${SOURCE_DIR}

# Login as user.
USER $USER_NAME

# Build.
RUN mvn package

# Switch to Normal JRE Stage.
FROM eclipse-temurin:17-alpine
ARG USER_ID
ARG USER_NAME
ARG HOME_DIR
ARG SOURCE_DIR

# Create the group (use a high ID to attempt to avoid conflits).
RUN addgroup -g $USER_ID $USER_NAME

# Create the user (use a high ID to attempt to avoid conflits).
RUN adduser -h $HOME_DIR -u $USER_ID -G $USER_NAME -D $USER_NAME

# Login as user.
USER $USER_NAME

# Set deployment directory.
WORKDIR $HOME_DIR

# Copy over the built artifact from the maven image.
COPY --from=maven $SOURCE_DIR/target/middleware*.jar ./scholars-discovery.jar

# Run java command.
CMD ["java", "-jar", "./scholars-discovery.jar"]
