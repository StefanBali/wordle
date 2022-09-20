const wordLength = 5;
const numberOfTries = 6;

const notContained = "NOT_CONTAINED";
const wrongSpot = "WRONG_SPOT";
const correctSpot = "CORRECT_SPOT";

const resultClassMap = new Map([
    [notContained, "not-contained"],
    [wrongSpot, "wrong-spot"],
    [correctSpot, "correct-spot"],
]);

let currentGameId;

function initialize() {
    document.querySelectorAll("#guess1 > input").forEach(element => {
        element.disabled = false
        element.classList.remove(...resultClassMap.values());
    });
    document.querySelectorAll(":not(#guess1) > input").forEach(element => {
        element.disabled = true;
        element.classList.remove(...resultClassMap.values());
    });
    document.getElementById("start").classList.remove("d-none");
    document.getElementById("game").classList.add("d-none");
}

document.getElementById("play").addEventListener("click", async e => {
    const response = await fetch("http://localhost:8080/api/wordle/pick");
    currentGameId = await response.text();
    document.getElementById("start").classList.add("d-none");
    document.getElementById("game").classList.remove("d-none");
});

Array.from(document.getElementsByTagName("input")).forEach(element => {
    element.addEventListener("keyup", async e => {
        if ((e.keyCode >= 65 && e.keyCode <= 90)
                || (e.keyCode >= 97 && e.keyCode <= 122)
                || e.key === 'ä' || e.key === 'ö' || e.key === 'ü' || e.key === 'Ä' || e.key === 'Ö' || e.key === 'Ü') {
            e.target.value = e.key;
            e.target.nextElementSibling?.focus();
        }
        if (e.keyCode === 8) {
            e.target.previousElementSibling?.focus();
        }
        if (e.keyCode === 13) {
            const parentElement = e.target.parentElement;
            const word = Array.from(parentElement.children).map(e => e.value).reduce((e1, e2) => {
                return e1 + e2;
            }, "");
            if (word.length === wordLength) {
                const row = Number(parentElement.id.charAt(parentElement.id.length - 1));
                await guess(currentGameId, row, word);
            }
        }
    })
});

async function guess(id, row, word) {
    disableRow(row);
    const response = await fetch(`http://localhost:8080/api/wordle/${id}/guess?word=${word}`);
    const result = await response.json();
    displayResult(row, result);
    if (hasWon(result)) {
        alert("Congratulations! You have won!");
    } else {
        if (row <= numberOfTries) {
            enableRow(row + 1);
            focusRow(row + 1);
        } else {
            alert("Sorry. You have lost.");
        }
    }
}

function displayResult(row, result) {
    result.forEach(r => {
        document.querySelector(`#guess${row} .letter${r.position}`).classList.add(resultClassMap.get(r.result));
    });
}

function disableRow(row) {
    document.querySelectorAll(`#guess${row} > input`).forEach(element => {
        element.disabled = true;
    });
}

function enableRow(row) {
    document.querySelectorAll(`#guess${row} > input`).forEach(element => {
        element.disabled = false;
    });
}

function focusRow(row) {
    document.querySelector(`#guess${row} > input:first-child`).focus();
}

function hasWon(result) {
    return result.filter(r => r.result !== correctSpot).length === 0;
}

initialize();
