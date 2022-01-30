let uploadImageButton = document.querySelector("#uploadImageButton");
let fileInput = document.querySelector("#fileInput");

uploadImageButton.addEventListener("click", imageUploadEvent);

function imageUploadEvent(e) {
    e.preventDefault();
    fileInput.trigger("click");
}