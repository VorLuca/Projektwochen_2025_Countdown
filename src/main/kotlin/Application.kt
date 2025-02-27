package org.example

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import org.example.routes.configureRouting
import org.example.staticfiles.configureStaticFiles

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    embeddedServer(Netty, port = port) {
        install(DefaultHeaders)
        install(CallLogging)

        routing {
            configureStaticFiles()
            configureRouting()
        }
    }.start(wait = true)
}
