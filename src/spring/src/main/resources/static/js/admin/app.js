let usernameField = document.querySelector('#username');
let usernameResult = document.querySelector('#usernameResult');

usernameField.addEventListener('change', check);

async function check() {
    let response = await fetch("/admin/roles/check?username=" + usernameField.value)
    let result = await response.text();
    usernameResult.textContent = result;
}

if(usernameField.value !== "")
    check();