let goldenTokenElement = document.querySelector("#goldenTokenField");
let copyTokenButton = document.querySelector("#copyTokenButton");
let copiedMessage = document.querySelector("#copiedMessage");

if(copyTokenButton) {
    copyTokenButton.addEventListener('click', (e) => {
        e.preventDefault();
        navigator.clipboard.writeText(goldenTokenElement.textContent);
        copiedMessage.removeAttribute("hidden");
    });
}
