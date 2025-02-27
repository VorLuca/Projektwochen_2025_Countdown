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

        protectedAreas = [
            { x: titleRect.left, y: titleRect.top, width: titleRect.width, height: titleRect.height },
            { x: countdownRect.left, y: countdownRect.top, width: countdownRect.width, height: countdownRect.height },
            { x: mainImageRect.left, y: mainImageRect.top, width: mainImageRect.width, height: mainImageRect.height }
        ];
    }
}

// **Checkt, ob eine Position frei ist**
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

// **Gibt eine zufällige, freie Position zurück**
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

// **Countdown aktualisieren**
function updateCountdown() {
    const targetDate = new Date('February 27, 2025 7:30:00').getTime();
    const now = new Date().getTime();
    const timeLeft = targetDate - now;

    const countdownElement = document.getElementById('countdown');
    const mainImage = document.getElementById('main-image');

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

        // **Ab 20 Sekunden: Keine Bilder mehr & Logo ausblenden**
        if (timeLeft <= 20000) {
            allowSpawning = false;
            if (mainImage) {
                mainImage.style.opacity = '0';
                mainImage.style.transition = 'opacity 2s ease-in-out';
            }
        }

        // **Ab 10 Sekunden: Countdown wächst & Schatten verstärkt sich**
        if (timeLeft <= 10000) {
            let remainingSeconds = Math.ceil(timeLeft / 1000);
            let scaleFactor = 1 + (10 - remainingSeconds) * 0.05;
            let shadowIntensity = (10 - remainingSeconds) * 5;

            countdownElement.style.fontSize = `${4 * scaleFactor}rem`;
            countdownElement.style.textShadow = `0px 0px ${shadowIntensity}px rgba(255, 255, 255, 0.8)`;
            countdownElement.style.position = 'absolute';
            countdownElement.style.top = '50%';
            countdownElement.style.left = '50%';
            countdownElement.style.transform = 'translate(-50%, -50%)';
            countdownElement.style.transition = 'all 0.3s ease-out';
        }
    } else {
        countdownElement.innerHTML = "Time's up!";
    }
}

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
