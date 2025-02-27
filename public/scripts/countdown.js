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

        if (attempts > 50) return null;

        let countdown = document.getElementById("countdown");
        let countdownRect = countdown.getBoundingClientRect();
        let padding = 25;

        let inCountdownArea = (
            x + width > countdownRect.left - padding &&
            x < countdownRect.right + padding &&
            y + height > countdownRect.top - padding &&
            y < countdownRect.bottom + padding
        );

        if (inCountdownArea) continue;

    } while (!isValidSpawnPosition(x, y, width, height));

    return { x, y };
}

let animationStarted = false;

const backgroundOverlay = document.createElement("div");
backgroundOverlay.style.position = "fixed";
backgroundOverlay.style.top = "0";
backgroundOverlay.style.left = "0";
backgroundOverlay.style.width = "100%";
backgroundOverlay.style.height = "100%";
backgroundOverlay.style.backgroundColor = "rgba(0, 0, 0, 0)";
backgroundOverlay.style.zIndex = "-1";
backgroundOverlay.style.transition = "background-color 1s linear";
document.body.appendChild(backgroundOverlay);

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

        if (timeLeft <= 27000) {
            allowSpawning = false;
        }

        if (timeLeft <= 10000) {
            let progress = (10000 - timeLeft) / 10000;

            countdownElement.style.transform = `scale(${1 + progress})`;

            backgroundOverlay.style.backgroundColor = `rgba(12, 12, 12, ${0.8 * progress})`;
        }

        if (timeLeft <= 20000 && !animationStarted) {
            animationStarted = true;

            if (mainImage) {
                mainImage.style.transition = 'opacity 2.5s ease-out';
                mainImage.style.opacity = '0';

                setTimeout(() => {
                    moveElementsToPositions(titleElement, countdownElement, 3000);
                }, 2500);
            }
        }
    } else {
        countdownElement.innerHTML = "Time's up!";
        clearInterval(countdownInterval);
        setTimeout(() => {
            window.location.href = "/werbevideo";
        }, 1000);
    }
}

function moveElementsToPositions(titleElement, countdownElement, duration) {
    let start = null;

    let startTopTitle = titleElement.getBoundingClientRect().top;
    let startTopCountdown = countdownElement.getBoundingClientRect().top;

    let titleMoveDistance = -(startTopTitle - window.innerHeight * 0.15);
    let countdownMoveDistance = -(startTopCountdown - window.innerHeight * 0.5);

    titleElement.style.position = "relative";
    countdownElement.style.position = "relative";

    function easeInOutQuad(t) {
        return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

    function step(timestamp) {
        if (!start) start = timestamp;
        let progress = (timestamp - start) / duration;
        if (progress > 1) progress = 1;

        let easedProgress = easeInOutQuad(progress);

        titleElement.style.transform = `translateY(${titleMoveDistance * easedProgress}px)`;
        countdownElement.style.transform = `translateY(${countdownMoveDistance * easedProgress}px)`;

        if (progress < 1) {
            requestAnimationFrame(step);
        }
    }

    requestAnimationFrame(step);
}

const countdownInterval = setInterval(updateCountdown, 1000);

function spawnImage() {
    if (!allowSpawning) return;

    let availableImages = images.filter(img => !activeImages.has(img));
    if (availableImages.length === 0) return;

    let imgSrc = availableImages[Math.floor(Math.random() * availableImages.length)];
    let img = document.createElement("img");
    img.src = imgSrc;
    img.classList.add("image-container");
    activeImages.add(imgSrc);

    let width = Math.floor(Math.random() * 100) + 150;
    let height = Math.round(width * (2160 / 3840));
    let position = getRandomPosition(width, height);
    if (!position) return;

    img.style.left = `${position.x}px`;
    img.style.top = `${position.y}px`;
    img.style.width = `${width}px`;
    img.style.height = `${height}px`;
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
