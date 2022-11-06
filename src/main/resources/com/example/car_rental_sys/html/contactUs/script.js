let servicers = ["Nigel Walsh",
    "Kirk Gallacher",
    "Blair Christopher",
    "Gerald Buckle",
    "Boyd Chamberlain",
    "Louis Gresham",
    "Marcia Mary",
    "Janet Wordsworth",
    "Yedda Dierser",
    "Veromca Harry",
    "Vanessa Lambert",
    "Olga Nick"]
let index = Math.round(Math.random() * (11));
let servicer = servicers[index];

// let message = "hihiiiiiiii"
addServerMessage(`Hi, welcome to Rent, I'm ${servicer}, how can I help you?`);

function addServerMessage(message) {
    let newDate = new Date();
    let time = newDate.toLocaleTimeString().substring(0, 10);

    let messageRes = `
    <div class="message-container-left">
        <div class="avatar">
            <img src="./logo.png" alt="Logo" class="logoImage">
        </div>
        <div class="message-area">
            <div class="message-topic">
                <div class="message-sender">${servicer}</div>
                <div class="message-time">${time}</div>
            </div>
            <div class="message">${message}</div>
        </div>
    </div>
    `
    $("#char-area-bottom").before(messageRes);
    scrollToBottom();
}

function addCustomerMessage(message) {
    let newDate = new Date();
    let format = (x) => x.toString().padStart(2, "0");
    let time = format(newDate.getHours()) + ":" + format(newDate.getMinutes()) + ":" + format(newDate.getSeconds());

    let messageRes = `
    <div class="message-container-right">
        <div class="message-area">
            <div class="message-topic">
                <div class="message-time">${time}</div>
            </div>
            <div class="message">${message}</div>
        </div>
    </div>
    `
    $("#char-area-bottom").before(messageRes);
}


$("#sendBtn").click(function () {
    sendMessage();
});

let $inputBox = $("#inputBox");

$inputBox.keypress(function (e) {
    if (e.which === 13) {
        sendMessage();
    }
});

async function sendMessage() {
    let message = $inputBox.val();
    if (message.length === 0) {
        return;
    }
    addCustomerMessage(message);
    $inputBox.val("");
    scrollToBottom();

    await getResponse().then(function (data) {
        addServerMessage(data["response"]);
    }).catch(function (error) {
        netWorkError();
    });

}

function getResponse() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: 'POST',
            url: `http://101.43.138.40:81/response`,
            data: JSON.stringify({ "sentences": getMessageList() }),
            success: function (data) {
                console.log(data);
                resolve(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                reject(errorThrown);
            },
            contentType: "application/json",
            dataType: 'json'
        });
    });
}

function getMessageList() {
    let res = [];
    let messageList = document.getElementsByClassName("message");
    for (let i = 0; i < messageList.length; i++) {
        res.push(messageList[i].innerHTML);
    }
    return res;
}

function heightToTop(ele) {
    //ele为指定跳转到该位置的DOM节点
    let root = document.body;
    let height = 0;
    do {
        height += ele.offsetTop;
        ele = ele.offsetParent;
    } while (ele !== root)
    return height;
}

function scrollToBottom() {
    let bottom = document.getElementById("char-area-bottom");
    let chatArea = document.getElementById("chat-area");
    chatArea.scrollTo({
        top: heightToTop(bottom),
        behavior: 'smooth'
    })
}

$inputBox.focus(function () {
    let container = document.getElementById("inputBox-comtainer");
    // add css class
    container.classList.add("comtainer-active");
}).blur(function () {
    let container = document.getElementById("inputBox-comtainer");
    container.classList.remove("comtainer-active");
});

function netWorkError() {
    let message = "Network error, please try again later";
    let messageRes = `
        <div class="error-message-container"><p class="error-message">${message}</p></div>
    `
    $("#char-area-bottom").before(messageRes);
}