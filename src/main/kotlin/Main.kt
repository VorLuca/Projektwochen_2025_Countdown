package org.example

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.http.content.*
import kotlinx.html.*
import java.io.File

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080  // ✅ Port aus Railway-Umgebungsvariable lesen

    embeddedServer(Netty, port = port) {
        install(DefaultHeaders)
        install(CallLogging)

        routing {
            static("/static") {
                files("public") // ✅ Statische Dateien aus "public/" bereitstellen
            }

            get("/") {
                val imageDir = File("public/testimages") // ✅ Bilder aus "public/testimages" laden
                val imageFiles = imageDir.listFiles()?.map { it.name } ?: emptyList()

                call.respondHtml {
                    head {
                        meta { charset = "UTF-8" }
                        title("Countdown to Dockdoor Key")
                        style {
                            +"""
                            body {
                                font-family: Arial, sans-serif;
                                text-align: center;
                                background-color: #067f38;
                                color: white;
                                display: flex;
                                flex-direction: column;
                                justify-content: center;
                                align-items: center;
                                height: 100vh;
                                margin: 0;
                                position: relative;
                                overflow: hidden;
                            }
                            .main-image {
                                width: 300px;
                                height: auto;
                                border-radius: 20px;
                                box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
                                position: relative;
                                z-index: 10;
                                margin-bottom: 20px;
                            }
                            h1 {
                                font-size: 5rem;
                                font-weight: 1500;
                                color: black;
                                margin-bottom: 120px;
                                text-transform: uppercase;
                                font-stretch: condensed;
                                letter-spacing: -2px;
                                text-shadow: 0px 8px 15px rgba(0, 0, 0, 0.4);
                                position: relative;
                                z-index: 10;
                            }
                            #countdown {
                                font-size: 4rem;
                                font-weight: 900;
                                margin-top: 20px;
                                background-color: white;
                                color: #067f38;
                                padding: 20px 40px;
                                border-radius: 15px;
                                box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
                                position: relative;
                                z-index: 10;
                            }
                            .image-container {
                                position: absolute;
                                border-radius: 20px;
                                opacity: 0;
                                transform: scale(0.5);
                                transition: opacity 1s ease-in-out, transform 1s ease-in-out, box-shadow 1.5s ease-in-out;
                                box-shadow: 5px 5px 10px rgba(255, 255, 255, 0.2);
                            }
                            .visible {
                                opacity: 1;
                                transform: scale(1);
                                box-shadow: 15px 15px 40px rgba(255, 255, 255, 0.6), 5px 5px 15px rgba(255, 255, 255, 0.3);
                            }
                            .fading-out {
                                opacity: 0;
                                transform: scale(0.5);
                                transition: opacity 1s ease-in-out, transform 1s ease-in-out;
                            }
                            """
                        }
                    }
                    body {
                        img {
                            src = "/static/mainImage.png"
                            classes = setOf("main-image")
                            id = "main-image"
                            style = "width: 450px; height: auto;"
                        }

                        div {
                            id = "image-wrapper"
                        }
                        h1 { id = "title"; +"Countdown to PRODUKTNAME" }
                        div { id = "countdown" }
                        script {
                            unsafe {
                                +"""
                                let images = ${imageFiles.map { "'/static/testimages/$it'" }};
                                let activeImages = new Set();
                                let imageWrapper = document.getElementById("image-wrapper");
                                let protectedAreas = [];

                                function updateProtectedAreas() {
                                    let title = document.getElementById("title");
                                    let countdown = document.getElementById("countdown");
                                    let mainImage = document.getElementById("main-image");

                                    if (title && countdown && mainImage) {
                                        let titleRect = title.getBoundingClientRect();
                                        let countdownRect = countdown.getBoundingClientRect();
                                        let mainImageRect = mainImage.getBoundingClientRect();

                                        protectedAreas = [
                                            { x: titleRect.left, y: titleRect.top, width: titleRect.width, height: titleRect.height },
                                            { x: countdownRect.left, y: countdownRect.top, width: countdownRect.width, height: countdownRect.height },
                                            { x: mainImageRect.left, y: mainImageRect.top, width: mainImageRect.width, height: mainImageRect.height }
                                        ];
                                    }
                                }
                                
                                window.onload = () => {
                                    updateProtectedAreas();
                                    window.onresize = updateProtectedAreas;
                                };

                                function isValidSpawnPosition(x, y, width, height) {
                                    return !protectedAreas.some(area =>
                                        x < area.x + area.width &&
                                        x + width > area.x &&
                                        y < area.y + area.height &&
                                        y + height > area.y
                                    ) && 
                                    !Array.from(imageWrapper.children).some(img => {
                                        let rect = img.getBoundingClientRect();
                                        return !(rect.right < x || rect.left > x + width || rect.bottom < y || rect.top > y + height);
                                    });
                                }
                                
                                function getRandomPosition(width, height) {
                                    let x, y, attempts = 0;
                                    do {
                                        x = Math.random() * (window.innerWidth - width);
                                        y = Math.random() * (window.innerHeight - height);
                                        attempts++;
                                        if (attempts > 50) return null;
                                    } while (!isValidSpawnPosition(x, y, width, height));
                                    return { x, y };
                                }

                                function spawnImage() {
                                    let availableImages = images.filter(img => !activeImages.has(img));
                                    if (availableImages.length === 0) return;

                                    let imgSrc = availableImages[Math.floor(Math.random() * availableImages.length)];
                                    let img = document.createElement("img");
                                    img.src = imgSrc;
                                    img.classList.add("image-container");
                                    activeImages.add(imgSrc);
                                    
                                    let size = Math.floor(Math.random() * 100) + 150;
                                    let position = getRandomPosition(size, size);
                                    if (!position) return;

                                    img.style.left = position.x + "px";
                                    img.style.top = position.y + "px";
                                    img.style.width = size + "px";
                                    img.style.height = size + "px";
                                    imageWrapper.appendChild(img);

                                    setTimeout(() => img.classList.add("visible"), 100);

                                    setTimeout(() => {
                                        img.classList.add("fading-out");
                                        setTimeout(() => {
                                            img.remove();
                                            activeImages.delete(imgSrc);
                                        }, 1000);
                                    }, Math.random() * 5000 + 2000);
                                }
                                
                                function updateCountdown() {
                                    const targetDate = new Date('March 14, 2025 10:00:00').getTime();
                                    const now = new Date().getTime();
                                    const timeLeft = targetDate - now;

                                    if (timeLeft > 0) {
                                        const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
                                        const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                                        const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
                                        const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);
                                        document.getElementById('countdown').innerHTML = days + "d " + hours + "h " + minutes + "m " + seconds + "s";
                                    } else {
                                        document.getElementById('countdown').innerHTML = "Time's up!";
                                    }
                                }

                                setInterval(updateCountdown, 1000);
                                updateCountdown();
                                setInterval(spawnImage, 3000);
                                """
                            }
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}
