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
    embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        install(CallLogging)

        routing {
            static("/static") {
                resources("static")
            }

            get("/") {
                val imageDir = File("src/main/resources/static/images")
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
                                background-color: #2ABFA4;
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
                                color: #2ABFA4;
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
                            """
                        }
                    }
                    body {
                        div {
                            id = "image-wrapper"
                        }
                        h1 { +"Countdown to Dockdoor Key" }
                        div { id = "countdown" }
                        script {
                            unsafe {
                                +"""
                                let images = ${imageFiles.map { "'/static/images/$it'" }};
                                let usedImages = [];
                                let activeImages = [];
                                let imageWrapper = document.getElementById("image-wrapper");

                                // Geschützte Bereiche (keine Bilder in diesen Zonen)
                                const protectedAreas = [
                                    { x: window.innerWidth / 2 - 350, y: 20, width: 700, height: 300 }, 
                                    { x: window.innerWidth / 2 - 200, y: window.innerHeight / 2 - 100, width: 400, height: 300 } 
                                ];

                                function getRandomImage() {
                                    if (usedImages.length === images.length) {
                                        usedImages = []; 
                                    }

                                    let availableImages = images.filter(img => !usedImages.includes(img));
                                    let selectedImage = availableImages[Math.floor(Math.random() * availableImages.length)];
                                    
                                    usedImages.push(selectedImage);
                                    return selectedImage;
                                }

                                function isOverlapping(x, y, width, height) {
                                    for (let area of protectedAreas) {
                                        if (
                                            x < area.x + area.width &&
                                            x + width > area.x &&
                                            y < area.y + area.height &&
                                            y + height > area.y
                                        ) {
                                            return true; 
                                        }
                                    }

                                    for (let img of activeImages) {
                                        if (
                                            x < img.x + img.width &&
                                            x + width > img.x &&
                                            y < img.y + img.height &&
                                            y + height > img.y
                                        ) {
                                            return true; 
                                        }
                                    }
                                    return false;
                                }

                                function getRandomPosition(width, height) {
                                    let x, y;
                                    let maxWidth = window.innerWidth - width;
                                    let maxHeight = window.innerHeight - height;
                                    let attempts = 0;

                                    do {
                                        x = Math.random() * maxWidth;
                                        y = Math.random() * maxHeight;
                                        attempts++;
                                        if (attempts > 50) return null; 
                                    } while (isOverlapping(x, y, width, height));

                                    return { x, y };
                                }

                                function getRandomSize() {
                                    let size = Math.floor(Math.random() * 100) + 150; 
                                    return { width: size, height: size };
                                }

                                function spawnImage() {
                                    let img = document.createElement("img");
                                    img.src = getRandomImage();
                                    img.classList.add("image-container");

                                    let { width, height } = getRandomSize();
                                    let position = getRandomPosition(width, height);
                                    if (!position) return; 

                                    let { x, y } = position;

                                    img.style.left = x + "px";
                                    img.style.top = y + "px";
                                    img.style.width = width + "px";
                                    img.style.height = height + "px";

                                    imageWrapper.appendChild(img);
                                    setTimeout(() => img.classList.add("visible"), 100);

                                    let displayTime = Math.floor(Math.random() * 5000) + 2000;
                                    activeImages.push({ img, x, y, width, height });

                                    setTimeout(() => removeImage(img), displayTime);
                                }

                                function removeImage(img) {
                                    img.classList.remove("visible");
                                    setTimeout(() => {
                                        activeImages = activeImages.filter(i => i.img !== img);
                                        img.remove();
                                    }, 1000);
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

                                function startSpawning() {
                                    spawnImage();
                                    setInterval(spawnImage, 3000);
                                }

                                startSpawning();
                                """
                            }
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}
