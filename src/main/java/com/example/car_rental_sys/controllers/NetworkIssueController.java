package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.ToolsLib.PlatformTools;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class NetworkIssueController {
    @FXML
    Button cancelBtn,okBtn;

    @FXML
    Label tipLabel;



    private static boolean isCancel = false;

    @FXML
    public void cancelBtnClick(){
        // Seems that you have lost the internet. Do you want to reconnect?
        // We can't make orders For you without internet. Do you want to reconnect?
        if(isCancel){
            System.exit(0);
        }else{
            isCancel = true;
            tipLabel.setText("We can't make orders For you without internet. Do you want to reconnect?");
        }
    }

    @FXML
    public void okBtnClick(){
        new Thread(() -> {
            try {
                PlatformTools.startWindowNetworkSetting();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // get stage from okBtn
//

        //wait for 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            private int i = 1;

            @Override
            public void handle(ActionEvent event) {
                try {
                    Application.startStage("mainPage.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        timeline.setCycleCount(1);
        timeline.play();
        okBtn.getScene().getWindow().hide();
    }
}
