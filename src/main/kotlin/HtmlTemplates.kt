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
            style = "width: 700px; height: auto;"
        }

        div { id = "image-wrapper" }
        h1 { id = "title"; +"Countdown to launch" }
        div { id = "countdown" }

        script {
            +"""let images = ${imageFiles.map { "'/static/images/$it'" }};"""
        }
        script { src = "/static/scripts/countdown.js" }
    }
}

fun HTML.werbevideoPage() {
    head {
        title("Werbevideo")
        link(rel = "stylesheet", href = "/static/styles/werbevideo.css")
    }
    body {
        classes = setOf("werbevideo-page")

        a(href = "/home") {
            classes = setOf("back-button")
            text("⬅ Zurück")
        }

        video {
            classes = setOf("full-screen-video")
            attributes["playsinline"] = "true"
            attributes["controls"] = "true"
            attributes["preload"] = "auto"
            source {
                attributes["src"] = "/videos/werbevideo.mp4"
                attributes["type"] = "video/mp4"
            }
        }

        script(src = "/static/scripts/werbevideo.js") {}
    }
}



fun HTML.homePage() {
    head {
        title("Home")
        link(rel = "stylesheet", href = "/static/styles/styles.css")
        link(rel = "stylesheet", href = "/static/styles/home.css")
    }
    body {
        div(classes = "background-container") {
            img(src = "/static/bsTopImage.png", alt = "Hintergrundbild", classes = "background-image")
        }
        div(classes = "scroll-container") {
            div(classes = "spacer") { }
            div(classes = "content-wrapper") {
                h1 { text("Willkommen zurück!") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
            }
        }

        script(src = "/static/scripts/adjustSpacer.js") {}
    }
}

