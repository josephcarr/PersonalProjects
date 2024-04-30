import api from './APIClient.js';

function usernameValidate(usrName) {
    if (usrName == "") {
        return false;
    }

    return true;
}

window.addEventListener("DOMContentLoaded", (e) => {
    let errorBox = document.getElementById('error');

    let usrName = document.getElementById('userName');
    usrName.addEventListener("invalid", (e) => {
        usrName.setCustomValidity('Must input a user name');
    });

    usrName.addEventListener("input", (e) => {
        usrName.setCustomValidity('');
        errorBox.textContent = '';
    });

    let form = document.getElementById('loginForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const userName = usrName.value;
        if (!usernameValidate(userName)) {
            alert("Must input a valid user name");
            return;
        }

        api.logIn(userName).then((userData) => {
            document.location = './main'; // Redirect to main page after successful login
        }).catch((err) => {
            if(err.status === 401) {
                errorBox.innerHTML = "User not found";
            }
            else {
                errorBox.innerHTML = err;
            }
        });
    });
});