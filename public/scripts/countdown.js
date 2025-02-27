let allowSpawning = true;
let activeImages = new Set();
let protectedAreas = [];
let imageWrapper;

window.onload = () => {
    imageWrapper = document.getElementById("image-wrapper");
    updateProtectedAreas();
    window.onresize = updateProtectedAreas;
    setInterval(updateCountdown, 1000);
    updateCountdown();
    setInterval(spawnImage, 3000);
};

function updateProtectedAreas() {
    let title = document.getElementById("title");
    let countdown = document.getElementById("countdown");
    let mainImage = document.getElementById("main-image");

    if (title && countdown && mainImage) {
        let titleRect = title.getBoundingClientRect();
        let countdownRect = countdown.getBoundingClientRect();
        let mainImageRect = mainImage.getBoundingClientRect();
        let padding = 25;

        protectedAreas = [
            { x: titleRect.left - padding, y: titleRect.top - padding, width: titleRect.width + 2 * padding, height: titleRect.height + 2 * padding },
            { x: countdownRect.left - padding, y: countdownRect.top - padding, width: countdownRect.width + 2 * padding, height: countdownRect.height + 2 * padding },
            { x: mainImageRect.left - padding, y: mainImageRect.top - padding, width: mainImageRect.width + 2 * padding, height: mainImageRect.height + 2 * padding }
        ];
    }
}


function isValidSpawnPosition(x, y, width, height) {
    // Aktuelle geschützte Bereiche nochmal updaten (z.B. nach Resize)
    updateProtectedAreas();

    return !protectedAreas.some(area =>
            x + width > area.x && x < area.x + area.width &&
            y + height > area.y && y < area.y + area.height
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

        if (attempts > 50) return null; // Sicherheitsabbruch nach 50 Versuchen

        // **Hier nochmal live checken, ob es im Countdown-Bereich ist**
        let countdown = document.getElementById("countdown");
        let countdownRect = countdown.getBoundingClientRect();
        let padding = 25; // Abstand halten

        let inCountdownArea = (
            x + width > countdownRect.left - padding &&
            x < countdownRect.right + padding &&
            y + height > countdownRect.top - padding &&
            y < countdownRect.bottom + padding
        );

        if (inCountdownArea) continue; // Falls zu nah am Countdown: neue Position suchen

    } while (!isValidSpawnPosition(x, y, width, height));

    return { x, y };
}

let animationStarted = false;

function updateCountdown() {
    const targetDate = new Date('March 14, 2025 14:00:00').getTime();
    const now = new Date().getTime();
    const timeLeft = targetDate - now;

    const countdownElement = document.getElementById('countdown');
    const mainImage = document.getElementById('main-image');
    const titleElement = document.getElementById('title');

    if (timeLeft > 0) {
        const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
        const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

        let countdownText = [];
        if (days > 0) countdownText.push(days + "d");
        if (hours > 0) countdownText.push(hours + "h");
        if (minutes > 0) countdownText.push(minutes + "m");
        countdownText.push(seconds + "s");

        countdownElement.innerHTML = countdownText.join(" ");

        if (timeLeft <= 20000 && !animationStarted) {
            animationStarted = true; // Animation nur einmal starten
            allowSpawning = false;
            if (mainImage) {
                mainImage.style.transition = 'opacity 3s ease-out';
                mainImage.style.opacity = '0';
                setTimeout(() => {
                    animateTitleUp(titleElement);
                }, 3000);
            }
        }

        if (timeLeft <= 10000) {
            let remainingSeconds = Math.ceil(timeLeft / 1000);
            let scaleFactor = 1 + (10 - remainingSeconds) * 0.05;
            let shadowIntensity = (10 - remainingSeconds) * 5;

            countdownElement.style.fontSize = `${4 * scaleFactor}rem`;
            countdownElement.style.textShadow = `0px 0px ${shadowIntensity}px rgba(255, 255, 255, 0.8)`;
        }
    } else {
        countdownElement.innerHTML = "Time's up!";
        clearInterval(countdownInterval); // Stoppt das Intervall, wenn die Zeit abgelaufen ist
    }
}

function animateTitleUp(element) {
    let start = null;
    const startTop = element.offsetTop; // **Ermittle die aktuelle Position des Elements**
    const endTop = 10; // Endposition in px
    const duration = 5000; // Animationszeit in ms

    function step(timestamp) {
        if (!start) start = timestamp;
        let progress = (timestamp - start) / duration;
        if (progress > 1) progress = 1;

        let currentTop = startTop + (endTop - startTop) * progress;
        element.style.position = 'absolute';
        element.style.top = `${currentTop}px`;
        element.style.left = '50%';
        element.style.transform = 'translateX(-50%)';

        if (progress < 1) {
            requestAnimationFrame(step);
        }
    }

    requestAnimationFrame(step);
}


// Startet das Countdown-Intervall
const countdownInterval = setInterval(updateCountdown, 1000);

// **Bild spawnen**
function spawnImage() {
    if (!allowSpawning) return;

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

    img.style.left = `${position.x}px`;
    img.style.top = `${position.y}px`;
    img.style.width = `${size}px`;
    img.style.height = `${size}px`;
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
