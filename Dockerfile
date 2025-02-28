FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY gradlew gradlew
COPY gradle.properties gradle.properties

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew clean fatJar --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

COPY public /app/public

CMD ["java", "-jar", "app.jar"]
