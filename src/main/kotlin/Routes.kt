package org.example.routes

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.title
import org.example.templates.countdownPage
import org.example.templates.homePage
import org.example.templates.werbevideoPage
import java.io.File

fun Routing.configureRouting() {
    get("/") {
        val imageDir = File("public/testimages")
        val imageFiles = imageDir.listFiles()?.map { it.name } ?: emptyList()
        call.respondHtml {
            countdownPage(imageFiles)
        }
    }

    get("/werbevideo") {
        call.respondHtml {
            werbevideoPage()
        }
    }

    get("/home") {
        call.respondHtml {
            homePage()
        }
    }
}
