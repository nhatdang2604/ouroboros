FROM gradle:8.10.0-jdk22-alpine AS build
LABEL authors="dangkl123"

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY src ./src

RUN gradle bootJar

FROM openjdk:22-slim-bullseye

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
