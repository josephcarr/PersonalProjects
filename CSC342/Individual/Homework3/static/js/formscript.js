function previewImage(event) {
    let input = event.target;
    let image = document.getElementById("imgpreview");
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
            image.src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}

window.addEventListener("DOMContentLoaded", (e) => {

    let inputFile = document.getElementById("imgInput");
    inputFile.addEventListener("change", (e) => {
        previewImage(e);
    });

    let emailRadio = document.getElementById('eRadio');
    emailRadio.addEventListener("change", (e) => {
        console.log("Email Selected");
        document.getElementById('email').required = true;
        document.getElementById('phone').required = false;
        document.getElementById('phone').setCustomValidity('');
    })

    let smsRadio = document.getElementById('sRadio');
    smsRadio.addEventListener("change", (e) => {
        console.log("SMS Selected");
        document.getElementById('email').required = false;
        document.getElementById('email').setCustomValidity('');
        document.getElementById('phone').required = true;
    })

    let notifyRadio = document.getElementById('nRadio');
    notifyRadio.addEventListener("change", (e) => {
        console.log("Do not notify Selected");
        document.getElementById('email').required = false;
        document.getElementById('email').setCustomValidity('');
        document.getElementById('phone').required = false;
        document.getElementById('phone').setCustomValidity('');
    })

})