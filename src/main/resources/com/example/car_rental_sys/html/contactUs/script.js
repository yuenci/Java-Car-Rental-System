class Servicer {
    static servicers =
        ["Nigel Walsh",
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

    static getRandomServicer() {
        let index = Math.round(Math.random() * (11));
        return Servicer.servicers[index];
    }
}

class Tools {
    static getTime() {
        let newDate = new Date();
        let format = (x) => x.toString().padStart(2, "0");
        let time = format(newDate.getHours()) + ":" + format(newDate.getMinutes()) + ":" + format(newDate.getSeconds());
        return time;
    }

    static cleanResponse(response) {
        let charater = [",", "!", "?", ".", ":", ";"];
        if (charater.includes(response[0])) response = response.slice(1);


        let nowords = [".Human: "]
        for (let i = 0; i < nowords.length; i++) {
            response = response.replace(nowords[i], "");
        }
        return response;

    }

    static getMessageList() {
        let res = [];
        let messageList = document.getElementsByClassName("message");
        for (let i = 0; i < messageList.length; i++) {
            res.push(messageList[i].innerHTML);
        }
        return res;
    }
}

class Message {
    constructor(message, sender, isViewer, time = Tools.getTime()) {
        this.message = message;
        this.sender = sender;
        this.isViewer = isViewer;
        this.time = time
        this.addMsg();
    }

    addMsg() {
        if (this.isViewer) {
            this.addCustomerMessage();
        } else {
            this.addServerMessage();
        }
    }

    addServerMessage() {
        this.message = Tools.cleanResponse(this.message);
        let messageRes = `
                        <div class="message-container-left">
                            <div class="avatar">
                                <img src="./logo.png" alt="Logo" class="logoImage">
                            </div>
                            <div class="message-area">
                                <div class="message-topic">
                                    <div class="message-sender">${this.sender}</div>
                                    <div class="message-time">${this.time}</div>
                                </div>
                                <div class="message">${this.message}</div>
                            </div>
                        </div>
                        `
        $("#char-area-bottom").before(messageRes);
        scrollToBottom();
    }

    addCustomerMessage() {

        let messageRes = `
                        <div class="message-container-right">
                            <div class="message-area">
                                <div class="message-topic">
                                    <div class="message-time">${this.time}</div>
                                </div>
                                <div class="message">${this.message}</div>
                            </div>
                        </div>
                        `
        $("#char-area-bottom").before(messageRes);
    }

}

class OpenAI {
    static async sendMessage() {
        let message = $inputBox.val();
        if (message.length === 0) {
            return;
        }
        //// addCustomerMessage(message);
        let newMessage = new Message(message, "me", true);
        $inputBox.val("");
        scrollToBottom();

        await OpenAI.getResponse().then(function (data) {
            //// addServerMessage(data["response"]);

            let newMessage = new Message(data["response"], servicer, false);
        }).catch(function (error) {
            OpenAI.netWorkError();
        });
    }

    static getResponse() {
        return new Promise(function (resolve, reject) {
            $.ajax({
                type: 'POST',
                url: `http://101.43.138.40:81/response`,
                data: JSON.stringify({ "sentences": Tools.getMessageList() }),
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

    static netWorkError() {
        let message = "Network error, please try again later";
        let messageRes = `
        <div class="error-message-container"><p class="error-message">${message}</p></div>
    `
        $("#char-area-bottom").before(messageRes);
    }
}



let servicer = Servicer.getRandomServicer();
let welcomeMessage = new Message(`Hi, welcome to Rent, I'm ${servicer}, how can I help you?`, servicer, false);



$("#sendBtn").click(function () {
    sendMessage();
});

let $inputBox = $("#inputBox");

$inputBox.keypress(function (e) {
    if (e.which === 13) {
        OpenAI.sendMessage();
    }
});

// async function sendMessage() {
//     let message = $inputBox.val();
//     if (message.length === 0) {
//         return;
//     }
//     //// addCustomerMessage(message);
//     let newMessage = new Message(message, "me", true);
//     $inputBox.val("");
//     scrollToBottom();

//     await getResponse().then(function (data) {
//         //// addServerMessage(data["response"]);

//         let newMessage = new Message(data["response"], servicer, false);
//     }).catch(function (error) {
//         netWorkError();
//     });

// }

// function getResponse() {
//     return new Promise(function (resolve, reject) {
//         $.ajax({
//             type: 'POST',
//             url: `http://101.43.138.40:81/response`,
//             data: JSON.stringify({ "sentences": getMessageList() }),
//             success: function (data) {
//                 console.log(data);
//                 resolve(data);
//             },
//             error: function (jqXHR, textStatus, errorThrown) {
//                 reject(errorThrown);
//             },
//             contentType: "application/json",
//             dataType: 'json'
//         });
//     });
// }

// function getMessageList() {
//     let res = [];
//     let messageList = document.getElementsByClassName("message");
//     for (let i = 0; i < messageList.length; i++) {
//         res.push(messageList[i].innerHTML);
//     }
//     return res;
// }



function scrollToBottom() {
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

// function netWorkError() {
//     let message = "Network error, please try again later";
//     let messageRes = `
//         <div class="error-message-container"><p class="error-message">${message}</p></div>
//     `
//     $("#char-area-bottom").before(messageRes);
// }