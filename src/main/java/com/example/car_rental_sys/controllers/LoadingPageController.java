package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.SelfTestTools;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.car_rental_sys.StatusContainer.selfTestResult;
import static com.example.car_rental_sys.StatusContainer.isRunable;

public class LoadingPageController {
    @FXML
    WebView webView;

    @FXML
    Label loadingText;

    public static LoadingPageController instance;

    @FXML
    public void initialize() {
        loadWebPage();
        webView.setVisible(false);
        loadingText.setAlignment(Pos.CENTER);
        SelfTestTools.executeSelfTest();
        setLoadingTest();
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



    private void setLoadingTest(){
        System.out.println("----------------");
        ArrayList<SelfTestTools.Test> testObjs =  SelfTestTools.testList;
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Loading...");
        for (SelfTestTools.Test testObj : testObjs) {
                messages.add(testObj.message);
        }
        messages.add("Initializing...");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

            private int i = 1;

            @Override
            public void handle(ActionEvent event) {
                System.out.println(messages.get(i));
                loadingText.setText(messages.get(i));
                i++;
            }
        }));
        timeline.setCycleCount(messages.size()-1);

        // after timeline finished, start the main page
        timeline.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.millis(1000));
            pause.setOnFinished(e1 -> {
                System.out.println( "timeline finished");
                // close this stage
                Stage stage = (Stage) loadingText.getScene().getWindow();
                stage.close();
                starNextPage();
            });
            pause.play();
        });

        timeline.play();
    }

    private void starNextPage(){
        if(selfTestResult && isRunable){
            openMainPage();
        }else if(!selfTestResult && !isRunable){
            openErrorReportPage();
        }else if(!selfTestResult){
            openNetErrorPage();
        }else {
            throw new RuntimeException("selfTestResult and isRunable values have problems");
        }

    }
    private void openMainPage(){
        try {
            Application.startStage("mainPage.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openNetErrorPage(){
        try {
            FXTools.showNetworkErrorPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openErrorReportPage(){
        try {
            FXTools.showErrorsPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
