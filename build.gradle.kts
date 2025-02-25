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
    implementation("io.ktor:ktor-server-netty:2.3.3") // Ktor Netty Engine
    implementation("io.ktor:ktor-server-html-builder:2.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.3.3")
    implementation("io.ktor:ktor-server-default-headers-jvm:2.3.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.example.MainKt")
}

kotlin {
    jvmToolchain(17)
}

// Setzt das Manifest explizit für die Fat JAR
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.MainKt" // Den Einstiegspunkt auf die korrekte Klasse setzen
    }
}

// Fat JAR erstellen
tasks.register<Jar>("fatJar") {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")

    // Alle Dateien und Abhängigkeiten einfügen
    from(sourceSets.main.get().output)

    // Abhängigkeiten aus runtimeClasspath einfügen
    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get().map { zipTree(it) })

    // Umgang mit doppelten Dateien
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Manifest explizit setzen
    manifest {
        attributes["Main-Class"] = "org.example.MainKt"
    }
}

tasks.build {
    dependsOn(tasks.named("fatJar"))
}
