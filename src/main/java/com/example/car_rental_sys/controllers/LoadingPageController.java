package com.example.car_rental_sys.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Objects;

import static com.example.car_rental_sys.Application.stageInstance;

public class LoadingPageController {
    @FXML
    WebView webView;

    @FXML
    Label loadingTest;

    public void initialize() {
        loadWebPage();
        webView.setVisible(false);
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

    public void setLoadingTest(String test){
        loadingTest.setText(test);
    }
}
