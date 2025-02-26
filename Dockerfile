# Verwende das offizielle OpenJDK-Image
FROM openjdk:17-jdk-slim

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere das fertige Fat JAR aus dem Build-Verzeichnis
COPY build/libs/app.jar app.jar

# Setze das Startkommando
CMD ["java", "-jar", "app.jar"]
