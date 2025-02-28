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

        style {
            unsafe {
                +"""
                .back-button {
                    position: fixed;
                    top: 20px;
                    left: 20px;
                    font-size: 20px;
                    font-weight: bold;
                    background-color: rgba(0, 0, 0, 0.3);
                    color: white;
                    padding: 10px 15px;
                    border-radius: 5px;
                    text-decoration: none;
                    z-index: 1000;
                    opacity: 0.2;  /* Nur leicht sichtbar */
                    transition: opacity 0.3s ease-in-out, background-color 0.3s;
                }

                .back-button:hover {
                    opacity: 1; /* Beim Hovern wird er sichtbar */
                    background-color: rgba(0, 0, 0, 0.8);
                }
                """
            }
        }

        script {
            unsafe {
                +"""
                document.addEventListener("DOMContentLoaded", () => {
                    const video = document.querySelector(".full-screen-video");
                    document.addEventListener("keydown", (event) => {
                        if (event.code === "Space" && video) {
                            event.preventDefault(); // Verhindert Scrollen der Seite
                            if (video.paused) {
                                video.play();
                            } else {
                                video.pause();
                            }
                        }
                    });
                });
                """
            }
        }
    }
}


fun HTML.homePage() {
    head {
        title("Home")
        link(rel = "stylesheet", href = "/static/styles/styles.css")
    }
    body {
        h1 { text("Willkommen zurück!") }
    }
}
