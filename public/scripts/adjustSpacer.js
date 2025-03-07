document.addEventListener("DOMContentLoaded", function () {
    const sections = document.querySelectorAll(".company-section, .product-section, .team-section");

    function checkVisibility() {
        const triggerBottom = window.innerHeight * 0.9;
        const triggerTop = window.innerHeight * 0.1;

        sections.forEach(section => {
            const sectionTop = section.getBoundingClientRect().top;
            const sectionBottom = section.getBoundingClientRect().bottom;

            if (sectionTop < triggerBottom && sectionBottom > triggerTop) {
                section.classList.add("visible");
            } else {
                section.classList.remove("visible");
            }
        });
    }

    window.addEventListener("scroll", checkVisibility);
    checkVisibility();
});
