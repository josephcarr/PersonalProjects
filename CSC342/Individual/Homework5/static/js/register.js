import api from './APIClient.js';

function usernameValidate(usrName) {
    if (usrName == "") {
        return false;
    }

    return true;
}

function passwordValidate(password) {
    if (password == "") {
        return false;
    }

    return true;
}

function confirmPassword(userPass, confirmPass) {
    if (userPass != confirmPass) {
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

    let passInput = document.getElementById('password');
    passInput.addEventListener("invalid", (e) => {
        passInput.setCustomValidity('Must input a password');
    });

    passInput.addEventListener("input", (e) => {
        passInput.setCustomValidity('');
        errorBox.textContent = '';
    });

    let confirm = document.getElementById('confirm');
    confirm.addEventListener("invalid", (e) => {
        confirm.setCustomValidity('Password must be the same');
    });

    confirm.addEventListener("input", (e) => {
        confirm.setCustomValidity('');
        errorBox.textContent = '';
    });

    let firstName = document.getElementById('firstName');
    firstName.addEventListener("invalid", (e) => {
        firstName.setCustomValidity('Must have first name');
    });

    firstName.addEventListener("input", (e) => {
        firstName.setCustomValidity('');
        errorBox.textContent = '';
    });

    let lastName = document.getElementById('lastName');
    lastName.addEventListener("invalid", (e) => {
        lastName.setCustomValidity('Must have last name');
    });

    lastName.addEventListener("input", (e) => {
        lastName.setCustomValidity('');
        errorBox.textContent = '';
    });

    let form = document.getElementById('registerForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const userName = usrName.value;
        if (!usernameValidate(userName)) {
            alert("Must input a valid user name");
            return;
        }

        const first_name = firstName.value;
        if (!usernameValidate(first_name)) {
            alert("Must input a valid name");
            return;
        }

        const last_name = lastName.value;
        if (!usernameValidate(last_name)) {
            alert("Must input a valid name");
            return;
        }

        const password = passInput.value;
        if (!passwordValidate(password)) {
            alert("Must input a password");
            return;
        }

        const confirmPass = confirm.value;
        if (!confirmPassword(password, confirmPass)) {
            alert("Passwords must be the same");
            return;
        }

        api.postUser(userName, first_name, last_name, password).then((userData) => {
            document.location = './login'; // Redirect to login page after successful login
        }).catch((err) => {
            errorBox.innerHTML = err;
        });
    });

    let homeButton = document.getElementById('home');
    homeButton.addEventListener('click', (e) => {
        document.location = './';
    });
});