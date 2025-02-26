# 1️⃣ Basis-Image mit OpenJDK 17
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# 3️⃣ Kopiere die Gradle-Dateien & lade Abhängigkeiten (Caching optimiert)
COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY gradlew gradlew
COPY gradle.properties gradle.properties

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# 4️⃣ Kopiere den gesamten Quellcode & baue das JAR
COPY . .
RUN ./gradlew clean fatJar --no-daemon

# 5️⃣ Erstelle das finale Laufzeit-Image
FROM openjdk:17-jdk-slim

WORKDIR /app

# 6️⃣ Kopiere das gebaute JAR aus dem vorherigen Schritt
COPY --from=build /app/build/libs/*.jar app.jar

# 7️⃣ Kopiere die statischen Dateien (Bilder, CSS, JS)
COPY public /app/public

# 8️⃣ Starte die Anwendung
CMD ["java", "-jar", "app.jar"]
