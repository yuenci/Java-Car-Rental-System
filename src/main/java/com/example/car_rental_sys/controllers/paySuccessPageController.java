package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;

public class paySuccessPageController {
    @FXML
    WebView webview;

    @FXML
    private void initialize() {
        System.out.println("Hello World!");
        initWebView();
        initWebViewEvent();
    }

    @FXML
    private void initWebView() {
        WebEngine engine = webview.getEngine();
        String path = "/com/example/car_rental_sys/html/paySuccess/index.html";
        engine.load( Objects.requireNonNull(getClass().getResource(path)).toString() );
    }

    private void    initWebViewEvent(){




    }
}
