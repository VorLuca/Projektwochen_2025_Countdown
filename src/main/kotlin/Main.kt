package org.example

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.defaultheaders.*
import kotlinx.html.*


fun main() {
    embeddedServer(Netty, port = 8080) {
        install(DefaultHeaders)
        install(CallLogging)

        routing {
            get("/") {
                call.respondHtml {
                    head {
                        meta {
                            charset = "UTF-8"
                        }
                        title("Countdown to Keywerk")
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
                            }
                            """
                        }
                    }
                    body {
                        h1 { +"Countdown to Keywerk" }
                        div { id = "countdown" }
                        script {
                            unsafe {
                                +"""
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
                                """
                            }
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}