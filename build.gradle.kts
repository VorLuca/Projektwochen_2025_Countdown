plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("io.ktor:ktor-server-core:2.3.3")
    implementation("io.ktor:ktor-server-netty:2.3.3")
    implementation("io.ktor:ktor-server-html-builder:2.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.3.3")
    implementation("io.ktor:ktor-server-default-headers-jvm:2.3.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

// Setzt den Einstiegspunkt für die Anwendung
application {
    mainClass.set("org.example.MainKt")
}

// Setzt das Manifest explizit für die Fat JAR
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.MainKt"
    }
}

// Definiert den fatJar Task: erstellt eine ausführbare JAR mit allen Abhängigkeiten
tasks.register<Jar>("fatJar") {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")

    // Füge die Haupt-Klassen des Projekts ein
    from(sourceSets.main.get().output)

    // Schließe alle Abhängigkeiten aus der runtimeClasspath ein
    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get().map { zipTree(it) })

    // Setze den Umgang mit doppelten Dateien (überspringe sie)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Manifest-Eintrag für die ausführbare JAR setzen
    manifest {
        attributes["Main-Class"] = "org.example.MainKt"
    }
}

// Kotlin-Toolchain konfigurieren (keine Notwendigkeit, languageVersion separat zu setzen)
kotlin {
    jvmToolchain(17) // Hier wird die JVM Toolchain auf Java 17 gesetzt
}

// Fat JAR bei Build ausführen
tasks.build {
    dependsOn(tasks.named("fatJar"))
}

