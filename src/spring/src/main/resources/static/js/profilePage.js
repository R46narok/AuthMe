let frontImageInput = document.querySelector("#frontImageInput");
let backImageInput = document.querySelector("#backImageInput");

frontImageInput.addEventListener("change", readURL);
backImageInput.addEventListener("change", readURL);

function readURL(e) {
    let input = e.target;
    if(input.files && input.files[0]){
        let reader = new FileReader();

        reader.onload = function (e) {
            let imgField = input.parentNode.querySelector("img");
            imgField.setAttribute("src", e.target.result)
        }

        reader.readAsDataURL(input.files[0]);
    }
}