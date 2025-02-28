package org.example.routes

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.templates.countdownPage
import org.example.templates.homePage
import org.example.templates.werbevideoPage
import java.io.File
import java.time.Instant
import java.time.ZonedDateTime
import java.time.ZoneId

fun Routing.configureRouting() {
    val countdownEndTime = ZonedDateTime.of(2025, 3, 14, 14, 0, 0, 0, ZoneId.of("UTC")).toInstant()

    get("/") {
        val imageDir = File("public/images")
        val imageFiles = imageDir.listFiles()?.map { it.name } ?: emptyList()
        call.respondHtml {
            countdownPage(imageFiles)
        }
    }

    get("/werbevideo") {
        val currentTime = Instant.now()
        if (currentTime.isBefore(countdownEndTime)) {
            call.respondRedirect("/")
        } else {
            call.respondHtml {
                werbevideoPage()
            }
        }
    }

    get("/home") {
        val currentTime = Instant.now()
        if (currentTime.isBefore(countdownEndTime)) {
            call.respondRedirect("/")
        } else {
            call.respondHtml {
                homePage()
            }
        }
    }
}
