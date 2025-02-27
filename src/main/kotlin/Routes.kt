package org.example.routes

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import org.example.templates.countdownPage
import java.io.File

fun Routing.configureRouting() {
    get("/") {
        val imageDir = File("public/testimages")
        val imageFiles = imageDir.listFiles()?.map { it.name } ?: emptyList()

        call.respondHtml {
            countdownPage(imageFiles)
        }
    }
}
