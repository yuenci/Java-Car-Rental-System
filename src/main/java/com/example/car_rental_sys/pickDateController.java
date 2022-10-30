package com.example.car_rental_sys;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class pickDateController {
    @FXML
    private WebView webView1;

    @FXML
    private void initialize()
    {
        WebEngine engine = webView1.getEngine();
//        engine.load("https://www.google.com");
        engine.load("http://127.0.0.1:8081/");
    }
}
