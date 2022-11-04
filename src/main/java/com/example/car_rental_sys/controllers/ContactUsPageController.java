package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;

public class ContactUsPageController {
    @FXML
    private WebView webview;

    @FXML
    private  void initialize(){
        // inint webview
        System.out.println("hiiii");
        WebEngine engine = webview.getEngine();
        String path = "/com/example/car_rental_sys/html/contactUs/index.html";
        engine.load( Objects.requireNonNull(getClass().getResource(path)).toString() );
        //engine.load("https://www.google.com");
    }
}
