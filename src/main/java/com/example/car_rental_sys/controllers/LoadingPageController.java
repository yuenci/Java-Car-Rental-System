package com.example.car_rental_sys.controllers;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;

public class LoadingPageController {
    @FXML
    WebView webView;

    @FXML
    Label loadingText;

    public static LoadingPageController instance;

    public void initialize() {
        loadWebPage();
        webView.setVisible(false);
        loadingText.setAlignment(Pos.CENTER);
        instance = this;
    }

    private void loadWebPage(){
        WebEngine webEngine = webView.getEngine();
        String path = "/com/example/car_rental_sys/html/loading/loading.html";
        webEngine.load(Objects.requireNonNull(getClass().getResource(path)).toString());

        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                try {
                    Thread.sleep(200);
                    webView.setVisible(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setLoadingTest(String text){
        Platform.runLater(() -> {
            loadingText.setText(text);
        });


    }
}
