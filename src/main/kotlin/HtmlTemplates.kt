package org.example.templates

import kotlinx.html.*

fun HTML.countdownPage(imageFiles: List<String>) {
    head {
        meta { charset = "UTF-8" }
        title("Countdown to launch")
        link(rel = "stylesheet", href = "/static/styles/styles.css")
    }
    body {
        img {
            src = "/static/mainImage.png"
            classes = setOf("main-image")
            id = "main-image"
            style = "width: 450px; height: auto;"
        }

        div { id = "image-wrapper" }
        h1 { id = "title"; +"Countdown to launch" }
        div { id = "countdown" }

        script {
            +"""let images = ${imageFiles.map { "'/static/testimages/$it'" }};"""
        }
        script { src = "/static/scripts/countdown.js" }
    }
}
