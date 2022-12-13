

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
    static messageDataJson = null;
    static avatarRootPath = null;
    static chatterNameIDMap = {};
    static currentChatWith = null;
    static currentServicer = null;
    static senderAndMessageBoxMap = {};
    static getTime() {
        let newDate = new Date();
        let format = (x) => x.toString().padStart(2, "0");
        return format(newDate.getHours()) + ":" + format(newDate.getMinutes()) + ":" + format(newDate.getSeconds());
    }

    static getDataTime() {
        let newDate = new Date();
        let format = (x) => x.toString().padStart(2, "0");
        return newDate.getFullYear() + "-" + format(newDate.getMonth() + 1) + "-" + format(newDate.getDate()) + " " + format(newDate.getHours()) + ":" + format(newDate.getMinutes()) + ":" + format(newDate.getSeconds());
    }

    static cleanResponse(response) {
        //let charater = [",", "!", "?", ".", ":", ";"];
        if (charater.includes(response[0])) response = response.slice(1);


        //let nowords = [".Human: ", "Human:"]
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

    // use this one!!!!
    static initAvatars() {
        let avatarArgsList = currentUserAvatarPath.split("/");
        avatarArgsList.pop()
        let avatarPath = avatarArgsList.join("/") + "/";
        Tools.avatarRootPath = avatarPath;
        document.getElementById("current-user-avatar-img").src = currentUserAvatarPath;
    }

    // static initAvatars() {

    //     Tools.avatarRootPath = "avatar/";
    //     document.getElementById("current-user-avatar-img").src = "avatar/9.png";
    // }

    static addMessages(chatterName) {
        let data = JSON.parse(messageData);
        let messageDataIDList = Object.keys(data);


        $(".message-container-left").remove();
        $(".message-container-right").remove();

        for (let i = 0; i < messageDataIDList.length; i++) {
            let messgae = data[messageDataIDList[i]];
            let chatterID = messgae["chatterID"];

            if (messgae["chatter"] === chatterName) {
                if (messgae.isFromMe === 1) {
                    let message = new Message(messgae.message, messgae.chatter, true, messgae.time);
                    message.send();
                } else {
                    let message = new Message(messgae.message, messgae.chatter, false, messgae.time, chatterID);
                    message.send();
                }
            }
        }
    }

    static getUnreadMessageCount() {
        let data = JSON.parse(messageData);
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
        //console.log(res);
        if (res.length === 0) {
            return null;
        }
        return res;
    }

    static getCurrentInput() {
        let input = document.getElementById("inputBox");
        let res = input.value;
        input.value = "";
        input.focus();
        return res;
    }

    static sendMessage() {
        let time = Tools.getTime();
        if (Tools.currentChatWith === "System") {
            //console.log("sent to System");
            OpenAI.sendCustomerMessage();
        } else if (Tools.currentChatWith === Tools.currentServicer) {
            // console.log("sent to Customer service");
            let message = new Message(Tools.getCurrentInput(), "me", true, time);
            message.sendCustomerMessage();
        } else {
            // console.log("sent to other Customer");
            let message = new Message(Tools.getCurrentInput(), "me", true, time);
            message.sendCustomerMessage();
        }
    }

    static getSenterAndMsgBox() {
        let messageBoxes = $("#message-container").find(".message-box");

        for (let i = 0; i < messageBoxes.length; i++) {
            let $messageBox = $(messageBoxes[i]);
            // get data-sender value
            let sender = $messageBox.data("sender");
            Tools.senderAndMessageBoxMap[sender] = $messageBox;
        }
        console.log(Tools.senderAndMessageBoxMap);
    }
}

class Message {
    constructor(message, sender, isViewer, time = Tools.getTime(), chatterID = 0) {
        this.message = message;
        this.sender = sender.replace("-", " ");
        this.isViewer = isViewer;
        this.time = time;
        this.chatterID = chatterID;
    }

    send() {
        //console.log("yes, send");
        if (this.isViewer) {
            this.addCustomerMessage();
        } else {
            this.addServerMessage();
        }
    }

    addServerMessage() {
        this.message = Tools.cleanResponse(this.message);
        let avatarPath = Tools.avatarRootPath + this.chatterID + ".png";
        //console.warn(avatarPath)
        let messageRes = `
                        <div class="message-container-left">
                            <div class="avatar">
                                <img src="${avatarPath}" alt="Logo" class="avatarImage">
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

    sendCustomerMessage() {

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

        let type = 2;
        if (Tools.currentChatWith === "System") {
            type = 0;
        } else if (Tools.currentChatWith = Tools.currentServicer) {
            type = 1;
        }
        let senderID = currentUserID;
        let status = 0;

        let chatwith = $("#message-container").find(".message-box-selected .sender-name").text();
        chatwith = chatwith.replace(" ", "-");

        let reciverID = Tools.chatterNameIDMap[chatwith];

        let time1 = Tools.getDataTime();
        let message1 = this.message;
        message1.replaceAll(",", "，");

        let data = [type, senderID, reciverID, time1, message1];
        let res = "[" + data.join(",") + "]";
        console.log(res);
    }

    static addMessageList() {
        let messageDataJson = JSON.parse(messageData);
        let messageDataIDList = Object.keys(messageDataJson);
        let chatterNameList = []
        let chatterIDList = []
        let unreadMsgMap = Tools.getUnreadMessageCount();


        for (let i = messageDataIDList.length - 1; i >= 0; i--) {

            let messageID = messageDataIDList[i];
            //console.log(messageID);

            let chatter = messageDataJson[messageID]["chatter"];
            let chatterID = messageDataJson[messageID]["chatterID"];
            let unreadNum = unreadMsgMap[chatter];
            if (!chatterNameList.includes(chatter)) {
                chatterNameList.push(chatter);
                chatterIDList.push(chatterID);
            } else {
                continue;
            }
            let messageText = messageDataJson[messageID]["message"];

            let chatNameStr = chatter.replace("-", " ");

            let messageRes = `
                        <div class="message-box" data-sender="${chatNameStr.toLowerCase()}">
                <img class="message-box-avatar" src="${Tools.avatarRootPath}${chatterID}.png">
                <div class="message-box-right">
                    <div class="sender-name">${chatNameStr}</div>
                    <div class="message-box-text">${messageText}</div>
                    <div class="unread">${unreadNum}</div>
                </div>
            </div>
                        `
            let newMessageItem = $(messageRes);

            //remove if no unread message
            if (unreadNum === undefined) {
                //console.warn("remove ing");
                newMessageItem.find(".unread").remove();
            }



            // add click event
            newMessageItem.click(function () {
                let $this = $(this);
                let chatter = $this.find(".sender-name").text()
                $("#message-container").find(".message-box").removeClass("message-box-selected");

                $this.addClass("message-box-selected");

                $this.find(".unread").remove();


                Tools.currentChatWith = chatter;
                chatter = chatter.replace(" ", "-");

                console.log(`chatWith:${currentUserID}:${Tools.chatterNameIDMap[chatter]}`);
                Tools.addMessages(chatter);
            });


            $("#message-container").append(newMessageItem);
        }

        for (let i = 0; i < chatterNameList.length; i++) {
            Tools.chatterNameIDMap[chatterNameList[i]] = chatterIDList[i];
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
        Tools.initAvatars();
        //Application.sendWelcomeMessage();
        //Tools.loadMessageData();
        Message.addMessageList();
        Tools.getSenterAndMsgBox();
    }

    static initEvent() {

        $("#sendBtn").click(function () {
            Tools.sendMessage();

        });

        let $inputBox = $("#inputBox");

        $inputBox.keypress(function (e) {
            if (e.which === 13) {
                Tools.sendMessage();
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

        $("#back-icon-img").click(function () {
            console.log("back to service");
        });

        $("#current-user-avatar").click(function () {
            console.log("back to service");
        });

        $("#closeIcon").click(function () {
            console.log("close");
        });

        // run every input
        $("#search-input").on("input", function () {
            let $this = $(this);
            let value = $this.val().toLowerCase();


            // if senderAndMessageBoxMap keys containes value then show it, unless hide it
            for (let key in Tools.senderAndMessageBoxMap) {
                if (key.includes(value)) {
                    Tools.senderAndMessageBoxMap[key].show();
                } else {
                    Tools.senderAndMessageBoxMap[key].hide();
                }
            }
        });

        $("#search-icon").click(function () {
            $("#search-input").focus();
        });
    }

    static sendWelcomeMessage() {
        let servicer = Servicer.getRandomServicer();
        Tools.currentServicer = servicer;
        let welcomeMessage = new Message(`Hi, welcome to Rent, I'm ${servicer}, how can I help you?`, servicer, false, 0);
        welcomeMessage.send();
    }
}



Application.start();



if (window.screen.height > 800) {
    $("#contact-list").css("height", 832);
    $("#chat-container").css("height", 832);
}

//console.log(currentUserID);