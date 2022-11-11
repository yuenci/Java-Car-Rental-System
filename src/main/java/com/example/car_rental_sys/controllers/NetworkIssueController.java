package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ToolsLib.PlatformTools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        PlatformTools.startWindowNetworkSetting();
    }
}
