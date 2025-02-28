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
                            event.preventDefault(); 
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
        style {
            unsafe {
                +"""
                body {
                    margin: 0;
                    padding: 0;
                    height: 100vh;
                    overflow-x: hidden;
                    overflow-y: auto; 
                    background-color: #111; 
                }

                .background-container {
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100vh;
                    display: flex;
                    justify-content: center;
                    align-items: flex-start;
                    padding-top: 20px;
                    z-index: -1; 
                }

                .background-image {
                    width: 75%; 
                    max-width: 1200px;
                    height: auto;
                    object-fit: contain;
                    border-radius: 15px; 
                    box-shadow: -10px 0px 20px rgba(0, 0, 0, 0.8), 10px 0px 20px rgba(0, 0, 0, 0.8);

                    -webkit-mask-image: linear-gradient(to right, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.2) 25%, rgba(0, 0, 0, 0.2) 75%, rgba(0, 0, 0, 0) 100%);
                    mask-image: linear-gradient(to right, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.9) 25%, rgba(0, 0, 0, 0.9) 75%, rgba(0, 0, 0, 0) 100%);
                }

                .scroll-container {
                    width: 100%;
                    display: flex;
                    justify-content: center;
                    margin-top: 90vh; 
                }

                .content-wrapper {
                    width: 90%;
                    max-width: 98vw;
                    background: #0d1b14; 
                    color: white;
                    padding: 40px;
                    text-align: justify;
                    font-size: 1.2rem;
                    border-radius: 10px;
                    box-shadow: 0px 0px 15px rgba(255, 255, 255, 0.2);
                    position: relative;
                    z-index: 1;
                }
                """
            }
        }
    }
    body {
        div(classes = "background-container") {
            img(src = "/static/bsTopImage.png", alt = "Hintergrundbild", classes = "background-image")
        }
        div(classes = "scroll-container") {
            div(classes = "content-wrapper") {
                h1 { text("Willkommen zurück!") }
                p { text("Scrolle nach unten, um den gesamten Inhalt zu sehen.") }
                p { text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.") }
                p { text("Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.") }
                p { text("Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.") }
                p { text("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") }
                p { text("Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra.") }
                p { text("Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh.") }
                p { text("Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes.") }
                p { text("Nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem.") }
            }
        }
    }
}
