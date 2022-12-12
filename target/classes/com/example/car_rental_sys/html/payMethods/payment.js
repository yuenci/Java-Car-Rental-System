window.onload = function () {
    let email = JSON.parse(localStorage.getItem("user-data"))["email"]
    document.getElementsByClassName("contact-email-text")[0].innerHTML = email;

    let price = localStorage.getItem("plan-price");
    if (price) {
        document.getElementsByClassName("payment-left-plan-price")[0].innerHTML = price;
    }
}




let payment = document.getElementById("payment-container");
if (payment) {
    payment.style.height = `${document.documentElement.clientHeight}px`;
}


let backBtn = document.getElementById("arraw-left");
backBtn.onclick = function () {
    console.log("backBtn click");
}

var paypalDisplay = false;
let bankCard = document.getElementsByClassName("pay-content-card-bank")[0];
if (bankCard) {
    bankCard.onclick = function () {
        clearBgc();
        this.style.background = "#f7f7f7";

        document.getElementsByClassName("cardinfo-container")[0].style.display = "block";
        document.getElementsByClassName("cardname-container")[0].style.display = "block";
        document.getElementsByClassName("paypalInfo")[0].style.display = "none";
        paypalDisplay = false;
    }
}


let paypal = document.getElementsByClassName("pay-content-paypal")[0];
if (paypal) {
    paypal.onclick = function () {
        clearBgc();
        this.style.background = "#f7f7f7";

        document.getElementsByClassName("cardinfo-container")[0].style.display = "none";
        document.getElementsByClassName("cardname-container")[0].style.display = "none";
        document.getElementsByClassName("paypalInfo")[0].style.display = "block";
        paypalDisplay = true;
    }
}



function clearBgc() {
    bankCard.style.background = "#fff";
    paypal.style.background = "#fff";
}




let payBtn = document.getElementById("pay-btn");

payBtn.onclick = async function () {
    let cardCVC = document.getElementById("card-cvc").value;
    if (cardCVC !== "" && cardCVC.length === 3) {
        console.log("payBtn click");
    } else {
        alert("Invalid input: CVC");
    }
}

function getValue(type) {
    let cardID = document.getElementById("card-cardID").value;
    let cardYear = document.getElementById("card-year").value;
    let cardCVC = document.getElementById("card-cvc").value;
    let cardName = document.getElementById("cardname").value;

    let paypalAcc = document.getElementById("paypal-account").value;
    let paypalPws = document.getElementById("paypal-pws").value;

    let price = localStorage.getItem("plan-price");
    let PlanType = null;
    if (price === "RM 0") {
        PlanType = "free";
    }
    else if (price === "RM 58") {
        PlanType = "personal";
    }
    else if (price === "RM 88") {
        PlanType = "professional";
    } {

    }

    let email = JSON.parse(localStorage.getItem("user-data"))["email"]

    let data = {};
    if (type === "bankCard") {

        if (cardID && cardYear && cardCVC && cardName) {
            data["type"] = PlanType;
            // data["cardID"] = cardID;
            // data["cardYear"] = cardYear;
            // data["cardCVC"] = cardCVC;
            // data["cardName"] = cardName;
            data["email"] = email;
        }
        else {
            alert("Invalid input: Empty value")
        }
    }
    else if (type === "paypal") {
        if (paypalAcc && paypalPws) {
            data["type"] = PlanType;
            // data["paypalAcc"] = paypalAcc;
            // data["paypalPws"] = paypalPws;
            data["email"] = email;
        }
        else {
            alert("Invalid input: Empty value")
        }
    }
    return data;
}

//fake click on button after document loaded
window.onload = function () {
    document.getElementsByClassName("pay-content-card-bank")[0].click();
}


