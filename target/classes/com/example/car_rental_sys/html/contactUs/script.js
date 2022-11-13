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
        // set the servicer as global variable
        window.servicer = Servicer.servicers[index];
        return Servicer.servicers[index];
    }
}

class Tools {
    static messageToLocalStorage = null;
    static avatarRootPath = null;
    static getTime() {
        let newDate = new Date();
        let format = (x) => x.toString().padStart(2, "0");
        return format(newDate.getHours()) + ":" + format(newDate.getMinutes()) + ":" + format(newDate.getSeconds());
    }

    static cleanResponse(response) {
        let charater = [",", "!", "?", ".", ":", ";"];
        if (charater.includes(response[0])) response = response.slice(1);


        let nowords = [".Human: ", "Human:"]
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

    static heightToTop(ele) {
        //ele为指定跳转到该位置的DOM节点
        let root = document.body;
        let height = 0;
        do {
            height += ele.offsetTop;
            ele = ele.offsetParent;
        } while (ele !== root)
        return height;
    }


    static scrollToBottom() {

        let bottom = document.getElementById("char-area-bottom");
        let chatArea = document.getElementById("chat-area");
        chatArea.scrollTo({
            top: Tools.heightToTop(bottom),
            behavior: 'smooth'
        })
    }


    static initAvatars() {
        let avatarArgsList = currentUserAvatarPath.split("/");
        avatarArgsList.pop()
        let avatarPath = avatarArgsList.join("/") + "/";
        Tools.avatarRootPath = avatarPath;

        document.getElementById("current-user-avatar-img").src = currentUserAvatarPath;
    }

    static getMessageDataFromLcalStorage() {
        Tools.messageToLocalStorage = JSON.parse(localStorage.getItem("messageData"));
        return Tools.messageToLocalStorage;
    }

    static addMessages(chatterName) {
        let data = Tools.messageToLocalStorage;
        let messageDataIDList = Object.keys(data);

        $(".message-container-left").remove();
        $(".message-container-right").remove();

        for (let i = 0; i < messageDataIDList.length; i++) {
            let messgae = data[messageDataIDList[i]];
            if (messgae["chatter"] === chatterName) {
                if (messgae.isFromMe === 0) {
                    let message = new Message(messgae.message, messgae.chatter, true, messgae.time);
                    message.send();
                } else {
                    let message = new Message(messgae.message, messgae.chatter, false, messgae.time,);
                    message.send();
                }
            }
        }
    }

    static getUnreadMessageCount() {
        let data = Tools.messageToLocalStorage;
        let messageDataIDList = Object.keys(data);
        let res = {};

        for (let i = 0; i < messageDataIDList.length; i++) {
            let messgae = data[messageDataIDList[i]];

            let chatter = messgae["chatter"];

            if (messgae.status === 0) {
                if (chatter in res) {
                    res[chatter] += 1;
                } else {
                    res[chatter] = 1;
                }
            }
        }
        console.log(res);
        return res;
    }
}

class Message {
    constructor(message, sender, isViewer, time = Tools.getTime(), avatarPath = "./logo.png") {
        this.message = message;
        this.sender = sender;
        this.isViewer = isViewer;
        this.time = time;
        this.avatarPath = avatarPath;
    }

    send() {
        console.log("yes, send");
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
                                <img src="${this.avatarPath}" alt="Logo" class="logoImage">
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
        Tools.scrollToBottom();
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

    static addMessageList() {
        let messageData = Tools.getMessageDataFromLcalStorage();
        let messageDataIDList = Object.keys(Tools.getMessageDataFromLcalStorage());
        let chatterList = []
        let unreadMsgMap = Tools.getUnreadMessageCount();

        for (let i = messageDataIDList.length - 1; i >= 0; i--) {

            let messageID = messageDataIDList[i];
            //console.log(messageID);

            let chatter = messageData[messageID]["chatter"];
            let unreadNum = unreadMsgMap[chatter];
            if (!chatterList.includes(chatter)) {
                chatterList.push(chatter);
            } else {
                continue;
            }
            let messageText = messageData[messageID]["message"];

            let messageRes = `
                        <div class="message-box">
                <img class="message-box-avatar" src="./13.png">
                <div class="message-box-right">
                    <div class="sender-name">${chatter}</div>
                    <div class="message-box-text">${messageText}</div>
                    <div class="unread">${unreadNum}</div>
                </div>
            </div>
                        `
            let newMessageItem = $(messageRes);
            // add click event
            newMessageItem.click(function () {
                let chatter = $(this).find(".sender-name").text()
                console.log(chatter);
                Tools.addMessages(chatter);
            });


            $("#message-container").append(newMessageItem);
        }


    }
}

class OpenAI {
    static async sendMessage() {
        let $inputBox = $("#inputBox");
        let message = $inputBox.val();
        if (message.length === 0) {
            return;
        }
        //// addCustomerMessage(message);
        let newMessage = new Message(message, "me", true);
        newMessage.send();
        $inputBox.val("");
        Tools.scrollToBottom();

        await OpenAI.getResponse().then(function (data) {
            //// addServerMessage(data["response"]);

            let newMessage = new Message(data["response"], servicer, false);
            newMessage.send();
        }).catch(function (error) {
            console.log(error);
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
                    //console.log(data);
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

class Application {
    static start() {
        Application.initEvent();
        Application.sendWelcomeMessage();
        //Tools.loadMessageData();
        Tools.initAvatars();
        Message.addMessageList();
    }

    static initEvent() {

        $("#sendBtn").click(function () {
            OpenAI.sendMessage();
        });

        let $inputBox = $("#inputBox");

        $inputBox.keypress(function (e) {
            if (e.which === 13) {
                OpenAI.sendMessage();
            }
        });

        $inputBox.focus(function () {
            let container = document.getElementById("inputBox-comtainer");
            // add css class
            container.classList.add("comtainer-active");
        }).blur(function () {
            let container = document.getElementById("inputBox-comtainer");
            container.classList.remove("comtainer-active");
        });

    }

    static sendWelcomeMessage() {
        let servicer = Servicer.getRandomServicer();
        let welcomeMessage = new Message(`Hi, welcome to Rent, I'm ${servicer}, how can I help you?`, servicer, false);
        welcomeMessage.send();
    }
}


let jsonDataInsert = document.createElement("script")
jsonDataInsert.src = "./messageData.json"
jsonDataInsert.type = "text/javascript";
document.body.appendChild(jsonDataInsert);



function getJson(data) {
    //console.log(data);
    localStorage.setItem("messageData", JSON.stringify(data));
}

Application.start();