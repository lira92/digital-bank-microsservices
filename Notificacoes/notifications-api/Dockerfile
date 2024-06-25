FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests=true

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 3002

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
