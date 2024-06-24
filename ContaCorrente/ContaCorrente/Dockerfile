FROM maven:3.8.5-openjdk-17-slim AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean install -DskipTests
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=build /usr/src/app/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
