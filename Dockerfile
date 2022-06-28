# Maven base image
FROM maven:3.8.2-openjdk-11 AS builder

WORKDIR /app

# Copy original pom.xml
COPY pom.xml /app/pom.xml

# Fetch all dependencies and cache them.
# This will a docker image layer which is cached when the pom.xml is unmodified, thus it won't download again the same dependencies.
# This improves the build loop since the application code changes more frequently than the pom.xml
RUN mvn --batch-mode dependency:go-offline

# Copy sources
COPY src /app/src

RUN mvn --batch-mode install -DskipTests

#  OpenJDK base image
FROM openjdk:11.0.15-slim

RUN mkdir -p /app

# Copy from first step the jar
COPY --from=builder /app/target/*.jar /app/maretha.jar
WORKDIR /app

ENV MYSQL_HOST=mysql-demo
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:8001,server=y,suspend=n

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/maretha.jar"]