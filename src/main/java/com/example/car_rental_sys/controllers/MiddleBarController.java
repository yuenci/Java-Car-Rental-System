package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MiddleBarController {

    @FXML
    private Pane userMiddleBar;

    @FXML
    private Button btnAccSecurity;

    @FXML
    private Button btnPersonInfo;

    @FXML
    private Button btnSysSetting;

    @FXML
    private Button btnAccPwd;

    public static MiddleBarController instance;

    @FXML
    private void initialize() {
        instance = this;
        initButtonStyle();
    }

    private void initButtonStyle(){
        btnPersonInfo.getStyleClass().add("btnFocusStyle");
        btnPersonInfo.getStyleClass().remove("defaultBtnStyle");
        Button[] btns = {btnAccSecurity, btnSysSetting, btnAccPwd};
        for (Button btn : btns) {
            btn.getStyleClass().add("defaultBtnStyle");
        }
    }

    @FXML
    void btnPersonInfoClicked(MouseEvent event) {
        barButtonClickEvent(event);
        System.out.println("btnPersonInfoClicked");
    }


    @FXML
    void btnSysSettingClicked(MouseEvent event) {
        barButtonClickEvent(event);
        System.out.println("btnSysSettingClicked");
    }


    @FXML
    void btnAccSettingClicked(MouseEvent event) {
        barButtonClickEvent(event);
        System.out.println("btnAccSettingClicked");
    }


    @FXML
    void btnAccPasswordClicked(MouseEvent event) {
        barButtonClickEvent(event);
        System.out.println("btnAccPasswordClicked");
    }


    @FXML
    private void barButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("btnFocusStyle");
        btn.getStyleClass().remove("defaultBtnStyle");
    }


    private void clearFocusStyle(){
        Button[] btns = {btnAccSecurity, btnPersonInfo, btnSysSetting, btnAccPwd};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnFocusStyle");
            btn.getStyleClass().add("defaultBtnStyle");
        }
    }


}
