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
            src = "/static/permaImages/mainImage.png"
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
                attributes["src"] = "/videos/Key_Tree_Demostration_small.m4v"
                attributes["type"] = "video/x-m4v"
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
            img(src = "/static/permaImages/TopImageGood.jpg", alt = "Hintergrundbild", classes = "background-image")
        }
        div(classes = "scroll-container") {
            div(classes = "spacer") { }
            div(classes = "content-wrapper") {
                h1 { text("KEY-TREE") }

                div(classes = "company-section") {
                    div(classes = "company-info") {
                        h2 { text("Unser Projekt – Innovation aus Leidenschaft") }

                        p {
                            text("Wir sind ein ")
                            em { text("kreatives Team") }
                            text(" von Auszubildenden, das mit Engagement und Teamgeist eine eigene Geschäftsidee entwickelt hat.")
                        }

                        p {
                            text("Unsere Vision? Eine Lösung schaffen, die ")
                            strong { text("praktisch, elegant und einzigartig") }
                            text(" ist. So entstand der ")
                            em { text("Key Tree") }
                            text(" – ein Produkt, das sowohl ")
                            strong { text("ästhetische Schönheit") }
                            text(" als auch ")
                            strong { text("clevere Funktionalität") }
                            text(" vereint.")
                        }
                    }

                    div(classes = "company-image") {
                        img(src = "/static/permaImages/mainImage.png", alt = "Unser Projekt", classes = "company-photo")
                    }
                }

                div(classes = "product-section") {
                    div(classes = "product-image") {
                        a(href = "/werbevideo") {
                            img(src = "/static/permaImages/WerbevideoImage.png", alt = "Unser Produkt", classes = "product-photo")
                        }
                    }

                    div(classes = "product-info") {
                        h2 { text("🌿 Key Tree – Der natürliche Weg zur perfekten Ordnung") }

                        p {
                            strong { text("Ästhetik trifft Funktionalität – für ein stilvolles Zuhause") }
                        }

                        p {
                            text("Der ")
                            strong { text("Key Tree") }
                            text(" ist mehr als nur eine gewöhnliche Schlüsselaufbewahrung – er vereint ")
                            em { text("natürliche Materialien, elegantes Design") }
                            text(" und eine ")
                            strong { text("clevere, drehbare Konstruktion") }
                            text(", die Ihnen den Alltag erleichtert.")
                        }

                        p {
                            text("Inspiriert von der Natur, bringt dieses Schlüsselbrett die ")
                            em { text("schlichte Eleganz eines Baumes") }
                            text(" in Ihren Eingangsbereich und sorgt gleichzeitig für eine ")
                            strong { text("perfekte Organisation Ihrer Schlüssel.") }
                        }

                        p {
                            text("🔹 ")
                            strong { text("Massives Eichenholz") }
                            text(" bildet den robusten Stamm – ein Naturmaterial mit einzigartiger Maserung, das für Wärme und Stabilität steht.")
                        }

                        p {
                            text("🔹 ")
                            strong { text("Fein gearbeitete Äste aus dezentem, mattem Messing") }
                            text(" dienen als stilvolle Haken und setzen elegante Akzente.")
                        }

                        p {
                            text("🔹 ")
                            strong { text("Sanft drehbar für maximalen Komfort") }
                            text(" – dank der stabilen ")
                            em { text("Edelstahlkonstruktion im Sockel") }
                            text(", können Sie Ihre Schlüssel immer ")
                            strong { text("bequem und mühelos erreichen.") }
                        }

                        p {
                            text("Ob als ")
                            em { text("edle Ergänzung für Ihren Eingangsbereich") }
                            text(" oder als ")
                            strong { text("besonderes Geschenk") }
                            text(", der Key Tree überzeugt durch ")
                            em { text("hochwertige Handwerkskunst und zeitloses Design") }
                            text(".")
                        }

                        a(href = "/werbevideo", classes = "product-link") {
                            text("🎥 ➡ Mehr erfahren")
                        }
                    }
                }

                div(classes = "full-product-image") {
                    img(src = "/static/permaImages/ProductImageGood.jpg", alt = "Großes Produktbild", classes = "large-product-photo")
                }

                div(classes = "team-section") {
                    div(classes = "team-info") {
                        h2 { text("Unser Team – Gemeinsam zur Innovation") }

                        p {
                            text("Dieses Projekt wäre nicht möglich gewesen ohne unser ")
                            strong { text("leidenschaftliches und engagiertes Team.") }
                        }

                        p {
                            text("Jede Person hat ihre ")
                            em { text("einzigartigen Fähigkeiten") }
                            text(" eingebracht – von Design über Technik bis hin zur Umsetzung.")
                        }

                        p {
                            text("Unsere gemeinsame Vision? ")
                            strong { text("Ein Produkt, das begeistert, inspiriert und den Alltag erleichtert.") }
                        }
                    }

                    div(classes = "team-image") {
                        img(src = "/static/permaImages/TeamImage.jpeg", alt = "Unser Team", classes = "team-photo")
                    }
                }
            }
        }
        script(src = "/static/scripts/adjustSpacer.js") {}
    }
}
