package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.funtions.JsInvoke;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;
import java.util.Objects;
import netscape.javascript.JSObject;

public class paySuccessPageController {
    @FXML
    WebView webview;

    @FXML
    private void initialize() {
        //System.out.println("Hello World!");
        initWebView();
    }

    @FXML
    private void initWebView() {
        WebEngine webEngine = webview.getEngine();
        String path = "/com/example/car_rental_sys/html/paySuccess/index.html";
        webEngine.load( Objects.requireNonNull(getClass().getResource(path)).toString() );




        webEngine.getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                /* The two objects are named using the setMember() method. */
                window.setMember("invoke", new JsInvoke());

                webEngine.executeScript("console.log = function(message)\n" +
                        "{\n" +
                        "    invoke.log(message);\n" +
                        "};");
            }
        });
    }
}


