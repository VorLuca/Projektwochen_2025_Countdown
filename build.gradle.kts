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
    mainClass.set("org.example.MainKt") // Hier den Einstiegspunkt angeben
}

// Setze das Manifest explizit, wenn `fatJar` erstellt wird
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.example.MainKt" // Den Einstiegspunkt hier sicherstellen
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.build {
    dependsOn(tasks.named("fatJar"))
}



