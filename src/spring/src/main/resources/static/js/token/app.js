let table = document.querySelector("table");

table.addEventListener('click', (e) => {
    if (e.target.tagName === 'INPUT' && e.target.type === 'checkbox') {
        let row = e.target.parentNode.parentNode;
        row.classList.add('table-active');
    } else if (e.target.tagName === 'BUTTON' && e.target.classList.contains("copy-btn")) {
        let token = e.target.parentNode.parentNode.querySelector(".token-id").textContent;
        navigator.clipboard.writeText(token);
        e.target.textContent = "Copied!";
    }
});