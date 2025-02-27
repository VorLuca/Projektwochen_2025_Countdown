package org.example.staticfiles

import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Routing.configureStaticFiles() {
    static("/static") {
        files("public")
    }
}
