function adjustSpacerHeight() {
    let contentWrapper = document.querySelector('.content-wrapper');
    let image = document.querySelector('.background-image');
    let spacer = document.querySelector('.spacer');

    if (!contentWrapper || !image || !spacer) return;

    let contentHeight = contentWrapper.offsetHeight;
    let imageHeight = image.offsetHeight;

    let spacerHeight = Math.max(50, contentHeight - imageHeight);

    spacer.style.height = `${spacerHeight}px`;
}

window.addEventListener('resize', adjustSpacerHeight);
window.addEventListener('load', adjustSpacerHeight);
adjustSpacerHeight();
