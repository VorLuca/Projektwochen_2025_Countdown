plugins {
    kotlin("jvm") version "2.1.0"
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

kotlin {
    jvmToolchain(17)
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("app")
    archiveClassifier.set("")
    archiveVersion.set("")

    // Manifest mit Main-Class
    manifest {
        attributes["Main-Class"] = "org.example.MainKt"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get().map { zipTree(it) })
}

tasks.build {
    dependsOn(tasks.named("fatJar"))
}
