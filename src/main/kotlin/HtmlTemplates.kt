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
            img(src = "/static/permaImages/TopImage.JPG", alt = "Hintergrundbild", classes = "background-image")
        }
        div(classes = "scroll-container") {
            div(classes = "spacer") { }
            div(classes = "content-wrapper") {
                h1 { text("KEY-TREE") }

                div(classes = "company-section") {
                    div(classes = "company-info") {
                        h2 { text("Über unsere Firma") }
                        p { text("Wir sind ein führendes Unternehmen im Bereich innovativer Lösungen und digitaler Erlebnisse.") }
                        p { text("Unser Ziel ist es, durch modernste Technologien und kreative Ansätze nachhaltige Werte zu schaffen.") }
                        p { text("Mit einem engagierten Team entwickeln wir maßgeschneiderte Lösungen für unsere Kunden weltweit.") }
                    }
                    div(classes = "company-image") {
                        img(src = "/static/permaImages/mainImage.png", alt = "Unsere Firma", classes = "company-photo")
                    }
                }

                div(classes = "product-section") {
                    div(classes = "product-image") {
                        a(href = "/werbevideo") {
                            img(src = "/static/permaImages/ProductImage.JPG", alt = "Unser Produkt", classes = "product-photo")
                        }
                    }
                    div(classes = "product-info") {
                        h2 { text("Unser Produkt") }
                        p { text("Unser innovatives Produkt revolutioniert den Markt und bietet einzigartige Funktionen.") }
                        p { text("Es wurde mit modernster Technologie entwickelt, um Ihnen die besten Ergebnisse zu liefern.") }
                        p { text("Erfahren Sie mehr über unser Produkt im Werbevideo.") }

                        a(href = "/werbevideo", classes = "product-link") {
                            text("➡ Mehr erfahren")
                        }
                    }
                }

                div(classes = "full-product-image") {
                    img(src = "/static/permaImages/ProductImage.JPG", alt = "Großes Produktbild", classes = "large-product-photo")
                }

                div(classes = "team-section") {
                    div(classes = "team-info") {
                        h2 { text("Unser Team") }
                        p { text("Unser Team besteht aus erfahrenen Experten, die mit Leidenschaft an innovativen Lösungen arbeiten.") }
                        p { text("Jeder einzelne bringt seine einzigartigen Fähigkeiten ein, um unseren Kunden das beste Erlebnis zu bieten.") }
                    }
                    div(classes = "team-image") {
                        img(src = "/static/permaImages/TeamImage.JPG", alt = "Unser Team", classes = "team-photo")
                    }
                }
            }
        }

        script(src = "/static/scripts/adjustSpacer.js") {}
    }
}



