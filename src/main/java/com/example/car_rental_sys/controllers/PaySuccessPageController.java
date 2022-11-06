package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.funtions.JsInvoke;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;

import java.util.Objects;

import netscape.javascript.JSObject;

public class PaySuccessPageController {
    @FXML
    WebView webview;

    @FXML
    Pane mainPane;

    private WebEngine webEngine;

    @FXML
    private void initialize() {
        //System.out.println("Hello World!");
        initWebView();
        initPaneEvent();
    }

    @FXML
    private void initWebView() {
        webEngine = webview.getEngine();
        String path = "/com/example/car_rental_sys/html/paySuccess/index.html";
        webEngine.load(Objects.requireNonNull(getClass().getResource(path)).toString());

        JsInvoke jsInvoke = new JsInvoke(); // create a new instance of the class. avoid GC
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != State.SUCCEEDED) {
                        return;
                    }
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("invoke", jsInvoke);

                    webEngine.executeScript("console.log = function(message)\n" +
                            "{\n" +
                            "    invoke.log(message);\n" +
                            "};");
                }
        );

    }

    private void initPaneEvent() {
        mainPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                case ENTER:
                    //System.out.println("Enter space pressed");
                    webEngine.executeScript("stopVideo();");
                    break;
            }
        });



}
}


