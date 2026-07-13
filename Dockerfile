# syntax=docker/dockerfile:1.7

ARG JAVA_VERSION=21

FROM eclipse-temurin:${JAVA_VERSION}-jdk-jammy AS builder

WORKDIR /workspace

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon build -x test || true

COPY src src

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon clean bootJar -x test

###

FROM eclipse-temurin:${JAVA_VERSION}-jre-jammy

WORKDIR /app

RUN groupadd --system spring && \
    useradd --system --gid spring --uid 1001 --create-home spring

ARG JAR_FILE=build/libs/*.jar

COPY --from=builder /workspace/${JAR_FILE} /app/app.jar

RUN chown -R spring:spring /app

USER spring

EXPOSE 3710

ENTRYPOINT ["java", "-jar", "/app/app.jar"]