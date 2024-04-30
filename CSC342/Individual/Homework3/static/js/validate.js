function imageValidate(imgPrev) {
    let filePath = String(imgPrev.value);
    if (filePath == "") {
        return false;
    }

    return true;
}

function nameValidate(first, last) {
    let firstName = String(first.value);
    let lastName = String(last.value);
    if (firstName == "" || lastName == "") {
        return false;
    }

    return true;
}

function messageValidate(message) {
    let msg = String(message.value);
    if (msg.length < 10) {
        return false;
    }

    return true;
}

function notifyValidate(emailRadio, smsRadio, notifyRadio) {
    if (emailRadio.checked != true && smsRadio.checked != true && notifyRadio.checked != true) {
        return false;
    }

    return true;
}

function emailValidate(emailRadio, email) {
    let regex = /[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$/;

    if (emailRadio.checked == true && !email.value.match(regex)) {    
        return false;
    }

    return true;
}

function phoneValidate(smsRadio, phone) {
    let regex = /^[0-9]{10}$/;

    if (smsRadio.checked == true && !phone.value.match(regex)) {    
        return false;
    }

    return true;
}

function cardValidate(card) {
    let cardType = String(card.value);

    if (cardType != "Visa" && cardType != "Mastercard" && cardType != "Discover") {
        return false;
    }

    return true;
}

function cardNumValidate(cardNum) {
    let regex = /^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$/;

    if (!cardNum.value.match(regex)) {    
        return false;
    }

    return true;
}

function expireValidate(expire) {
    if (expire.value == "") {
        return false;
    }

    let current = new Date();
    let inputDate = new Date(expire.value);

    if (inputDate < current) {
        return false;
    }

    return true;
}

function ccvValidate(ccv) {
    let regex = /^[0-9]{3,4}$/;

    if (!ccv.value.match(regex)) {    
        return false;
    }

    return true;
}

function amountValidate(amount) {
    if (amount.value == "") {
        return false;
    }

    money = parseFloat(amount.value).toFixed(2);
    if (money < 0) {
        return false;
    }

    amount.value = parseFloat(amount.value).toFixed(2);

    return true;
}

function agreeValidate(agree) {
    if (agree.checked != true) {
        return false;
    }

    return true;
}

document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const imgPrev = document.querySelector('#imgInput');
    const senderFirst = document.querySelector('#sFirst');
    const senderLast = document.querySelector('#sLast');
    const recipientFirst = document.querySelector('#rFirst');
    const recipientLast = document.querySelector('#rLast');
    const message = document.querySelector('#message');
    const emailRadio = document.querySelector('#eRadio');
    const smsRadio = document.querySelector('#sRadio');
    const notifyRadio = document.querySelector('#nRadio');
    const email = document.querySelector('#email');
    const phone = document.querySelector('#phone');
    const card = document.querySelector('#card');
    const cardNum = document.querySelector('#cNumber');
    const expire = document.querySelector('#expiration');
    const ccv = document.querySelector('#ccv');
    const amount = document.querySelector('#amount');
    const agree = document.querySelector('#agree');

    imgPrev.addEventListener("invalid", (e) => {
        imgPrev.setCustomValidity('Requires a valid image');
    });

    imgPrev.addEventListener("input", (e) => {
        imgPrev.setCustomValidity('');
    });

    senderFirst.addEventListener("invalid", (e) => {
        senderFirst.setCustomValidity('Sender requires first name');
    });

    senderFirst.addEventListener("input", (e) => {
        senderFirst.setCustomValidity('');
    });

    senderLast.addEventListener("invalid", (e) => {
        senderLast.setCustomValidity('Sender requires last name');
    });

    senderLast.addEventListener("input", (e) => {
        senderLast.setCustomValidity('');
    });

    recipientFirst.addEventListener("invalid", (e) => {
        recipientFirst.setCustomValidity('Recipient requires first name');
    });

    recipientFirst.addEventListener("input", (e) => {
        recipientFirst.setCustomValidity('');
    });

    recipientLast.addEventListener("invalid", (e) => {
        recipientLast.setCustomValidity('Recipient requires last name');
    });

    recipientLast.addEventListener("input", (e) => {
        recipientLast.setCustomValidity('');
    });

    message.addEventListener("invalid", (e) => {
        message.setCustomValidity('Requires a message with at least 10 characters');
    });

    message.addEventListener("input", (e) => {
        message.setCustomValidity('');
    });

    email.addEventListener("invalid", (e) => {
        email.setCustomValidity('Email must be in format example@web.type');
    });

    email.addEventListener("input", (e) => {
        email.setCustomValidity('');
    });

    phone.addEventListener("invalid", (e) => {
        phone.setCustomValidity('Phone must be in format XXXXXXXXXX (X are numbers 0 - 9)');
    });

    phone.addEventListener("input", (e) => {
        phone.setCustomValidity('');
    });

    card.addEventListener("invalid", (e) => {
        card.setCustomValidity('Payment requires card type (Visa, Mastercard, Discover)');
    });

    card.addEventListener("input", (e) => {
        card.setCustomValidity('');
    });

    cardNum.addEventListener("invalid", (e) => {
        cardNum.setCustomValidity('Card number must be in format XXXX-XXXX-XXXX-XXXX (X are numbers 0 - 9)');
    });

    cardNum.addEventListener("input", (e) => {
        cardNum.setCustomValidity('');
    });

    expire.addEventListener("invalid", (e) => {
        expire.setCustomValidity('Payment requires expiration date that is not expired');
    });

    expire.addEventListener("input", (e) => {
        expire.setCustomValidity('');
    });

    ccv.addEventListener("invalid", (e) => {
        ccv.setCustomValidity('Payment requires ccv number of 3 or 4 digits');
    });

    ccv.addEventListener("input", (e) => {
        ccv.setCustomValidity('');
    });

    amount.addEventListener("invalid", (e) => {
        amount.setCustomValidity('Payment requires an amount greater than 0');
    });

    amount.addEventListener("input", (e) => {
        amount.setCustomValidity('');
    });

    agree.addEventListener("invalid", (e) => {
        agree.setCustomValidity('Must agree to the Terms and Conditions');
    });

    agree.addEventListener("input", (e) => {
        agree.setCustomValidity('');
    });
  
    form.addEventListener("submit", (e) => {
        let error = false;
        let errors = "";

        if (!imageValidate(imgPrev)) {
            error = true;
            errors += " - Must upload image\n";
            e.preventDefault();
        }

        if (!nameValidate(senderFirst, senderLast) || !nameValidate(recipientFirst, recipientLast)) {
            error = true;
            errors += " - Must have first and last name for sender and recipient\n";
            e.preventDefault();
        }

        if (!messageValidate(message)) {
            error = true;
            errors += " - Must have a message that has at least 10 characters\n";
            e.preventDefault();
        }

        if (!notifyValidate(emailRadio, smsRadio, notifyRadio)) {
            error = true;
            errors += " - Must choose a notification method\n";
            e.preventDefault();
        }

        if (!emailValidate(emailRadio, email)) {
            error = true;
            errors += " - Email must be in format example@web.type\n";
            e.preventDefault();
        }

        if (!phoneValidate(smsRadio, phone)) {
            error = true;
            errors += " - Phone must be in format XXXXXXXXXX (X are numbers 0 - 9)\n";
            e.preventDefault();
        }

        if (!cardValidate(card)) {
            error = true;
            errors += " - Card type must be Visa, Mastercard, or Discover\n";
            e.preventDefault();
        }

        if (!cardNumValidate(cardNum)) {
            error = true;
            errors += " - Card number must be in format XXXX-XXXX-XXXX-XXXX (X are numbers 0 - 9)\n";
            e.preventDefault();
        }

        if (!expireValidate(expire)) {
            error = true;
            errors += " - Must be a valid date\n";
            e.preventDefault();
        }

        if (!ccvValidate(ccv)) {
            error = true;
            errors += " - ccv must be 3 - 4 numbers (0 - 9)\n";
            e.preventDefault();
        }

        if (!amountValidate(amount)) {
            error = true;
            errors += " - Amount must be greater than 0\n";
            e.preventDefault();
        }

        if (!agreeValidate(agree)) {
            error = true;
            errors += " - Must agree to Terms and Conditions\n";
            e.preventDefault();
        }

        if(error) {
            alert("There were errors\n\n" + errors);
            e.preventDefault();
        }
    });
});