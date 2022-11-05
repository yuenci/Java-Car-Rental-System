#!/usr/bin/env python3
from flask_cors import CORS
from flask import Flask, request
import openai


app = Flask(__name__)
CORS(app, resources=r'/*')


@app.route('/response', methods=["POST"])
def postExplainToCache():
    jsonData = request.json
    sentence = jsonData["sentences"]
    return chat(sentence)



def chat(messageList):
    openai.api_key ="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"

    start_sequence = "\nAI:"
    restart_sequence = "\nHuman: "

    message = "The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly car rental system customer service robot."


    i = 1
    for ele in messageList:
        if i % 2 == 0:
            message += start_sequence + ele
        else:
            message += restart_sequence + ele
        i += 1


    response = openai.Completion.create(
        model="text-curie-001",
        # model="text-davinci-002",
        prompt=message,
        temperature=0.9,
        max_tokens=150,
        top_p=1,
        frequency_penalty=0,
        presence_penalty=0.6,
        stop=[" Human:", " AI:"]
    )
    response = response["choices"][0]["text"].replace(
        "\nAI:", "").replace("\n", "")

    return {"response": response}


if __name__ == '__main__':
    app.run(debug=True, host='10.0.24.5', port="81")
