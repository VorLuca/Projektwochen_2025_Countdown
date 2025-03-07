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
