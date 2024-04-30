const States = {
    Start: 0,
    Number: 1,
    Operator: 2,
    Submit: 3,
};

let state = States.Start;
let current = 0;
let prev = 0;
let result = 0;
let operator = "";
let hasPrev = false;
let hasCurrent = false;
let error = false;
let isFloat = false;

function display(val) {
    if (val == 'c') {
        clear();
    }
    else if (state == States.Start && !error) {
        if (isDigit(val)) {
            if (val == '.') {
                if (document.getElementById("result").value == "") {
                    document.getElementById("result").value = "0";
                }
                isFloat = true;
            }

            document.getElementById("result").value += val;

            if (isFloat) {
                current = parseFloat(document.getElementById("result").value);
            }
            else {
                current = parseInt(document.getElementById("result").value);
            }
            hasCurrent = true;
            state = States.Number
        }
    }
    else if (state == States.Number && !error) {
        if (isDigit(val)) {
            if (val == '.') {
                if (document.getElementById("result").value == "") {
                    document.getElementById("result").value = "0";
                }
                isFloat = true;
            }

            document.getElementById("result").value += val;

            if (isFloat) {
                current = parseFloat(document.getElementById("result").value);
            }
            else {
                current = parseInt(document.getElementById("result").value);
            }
            hasCurrent = true;
            state = States.Number;
        }
        else if (val == "(+/-)" && hasCurrent) {
            current *= -1;
            document.getElementById("result").value = current;
        }
        else if (val == '=' && hasPrev && hasCurrent) {
            console.log("Calculating");
            calculate();
            state = States.Submit;
            operator = "";
            hasPrev = true;
            hasCurrent = false;
            isFloat = false;
        }
        else if (isOperator(val)) {
            if (hasPrev) {
                calculate();
            }
            else {
                document.getElementById("result").value = "";
            }

            operator = val;
            prev = current;
            state = States.Operator;
            hasPrev = true;
            hasCurrent = false;
            isFloat = false;
        }
    }
    else if (state == States.Operator && !error) {
        if (isDigit(val)) {
            document.getElementById("result").value = "";

            if (val == '.') {
                if (document.getElementById("result").value == "") {
                    document.getElementById("result").value = "0";
                }
                isFloat = true;
            }

            document.getElementById("result").value += val;

            if (isFloat) {
                current = parseFloat(document.getElementById("result").value);
            }
            else {
                current = parseInt(document.getElementById("result").value);
            }
            hasCurrent = true;
            state = States.Number;
        }
        else if (val == '=') {
            state = States.Submit;
            operator = "";
            hasPrev = true;
            hasCurrent = false;
            isFloat = false;
        }
        else if (isOperator(val)) {
            operator = val;
            state = States.Operator;
            hasPrev = true;
            hasCurrent = false;
            isFloat = false;
        }
    }
    else if (state == States.Submit && !error) {
        if (isDigit(val)) {
            clear();

            if (val == '.') {
                if (document.getElementById("result").value == "") {
                    document.getElementById("result").value = "0";
                }
                isFloat = true;
            }

            document.getElementById("result").value += val;

            if (isFloat) {
                current = parseFloat(document.getElementById("result").value);
            }
            else {
                current = parseInt(document.getElementById("result").value);
            }
            hasCurrent = true;
            state = States.Number;
        }
        else if (isOperator(val)) {
            operator = val;
            state = States.Operator;
            hasPrev = true;
            hasCurrent = false;
            isFloat = false;
        }
    }
}

function calculate() {
    if (operator == '+') {
        result = prev + current;
    }
    else if (operator == '-') {
        result = prev - current;
    }
    else if (operator == '*') {
        result = prev * current;
    }
    else if (operator == '=') {
        return;
    }
    else {
        if (current == 0) {
            document.getElementById("result").value = "Error";
            error = true;
            return;
        }
        else {
            result = prev / current;
        }
    }

    console.log(result);

    document.getElementById("result").value = result;
    current = result;
    prev = result;

    let table = document.getElementById("history");
    let row = table.insertRow(table.rows.length);
    let cell = row.insertCell(0);
    cell.innerHTML = result;

    let histDiv = document.getElementById("hist");
    histDiv.scrollTop = histDiv.scrollHeight;
}

function changeSign() {
    if (!error) {
        current *= -1;
        document.getElementById("result").value = current;
    }
}

function isDigit(val) {
    if (val == '0' || val == '1'
        || val == '2' || val == '3'
        || val == '4' || val == '5'
        || val == '6' || val == '7'
        || val == '8' || val == '9'
        || val == '.') {
        return true;
    }

    return false;
}

function isOperator(val) {
    if (val == '+' || val == '-'
        || val == '*' || val == '/') {
        return true;
    }

    return false;
}

function clear() {
    document.getElementById("result").value = "";
    state = States.Start;
    current = 0;
    prev = 0;
    result = 0;
    operator = "";
    hasPrev = false;
    hasCurrent = false;
    error = false;
    isFloat = false;
}

function clearHistory() {
    let table = document.getElementById("history");
    while (table.rows.length != 0) {
        table.deleteRow(0);
    }
}

function fromHistory(val) {
    if (document.getElementById("result").value == "") {
        document.getElementById("result").value = val;
        if (state == States.Operator) {
            prev = current;
            hasPrev = true;
        }
        state = States.Number;
        current = val;
        hasCurrent = true;
    }
}


window.addEventListener("DOMContentLoaded", (e) => {
    let buttons = document.getElementsByClassName("calcbtn");
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener("click", (e) => {
            display(buttons[i].value);
        });
    }

    document.addEventListener('keydown', (e) => {
        display(e.key);
    });

    let clrBtn = document.getElementById("clearhist");
    clrBtn.addEventListener("click", (e) => {
        clearHistory();
    });

    let table = document.getElementById("history");
    table.addEventListener("mouseover", (e) => {
        let cells = table.querySelectorAll("tr");
        for (let i = 0; i < cells.length; i++) {
            cells[i].addEventListener("click", (e) => {
                fromHistory(parseFloat(cells[i].innerText));
            });
        }
    });

})