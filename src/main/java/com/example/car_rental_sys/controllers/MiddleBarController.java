package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class MiddleBarController {

    @FXML
    private Pane userMiddleBar;

    @FXML
    private Button btnSysSetting;
    @FXML
    private Button btnAccSecurity;
    @FXML
    private Button btnAccPwd;

    public static MiddleBarController instance;

    public MiddleBarController() {
        instance = this;
    }

    @FXML
    private void initialize() {
        instance = this;
        initButtonStyle();
        initTheme();
    }

    private void initButtonStyle(){
        btnSysSetting.getStyleClass().add("btnMiddleFocusStyle");
        btnSysSetting.getStyleClass().remove("defaultMiddleBtnStyle");
        Button[] btns = {btnAccSecurity, btnAccPwd};
        for (Button btn : btns) {
            btn.getStyleClass().add("defaultMiddleBtnStyle");
        }
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            userMiddleBar.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/middleBarDark.css")
                            .toURI().toString());
        }
    }


    @FXML
    void btnSysSettingClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(StatusContainer.currentUser instanceof Customer){
            CustomerServiceController.instance.showSettingPage();
        }else{
            AdminServiceController.instance.showSettingPage();
        }
        //System.out.println("btnSysSettingClicked");
    }

    @FXML
    void btnAccSettingClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(StatusContainer.currentUser instanceof Customer){
            CustomerServiceController.instance.showAccSecurity();
        }else{
            AdminServiceController.instance.showAccSecurity();
        }
        //System.out.println("btnAccSettingClicked");
    }


    @FXML
    void btnAccPasswordClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(StatusContainer.currentUser instanceof Customer){
            CustomerServiceController.instance.showPasswordPage();
        }else{
            AdminServiceController.instance.showPasswordPage();
        }
        //System.out.println("btnAccPasswordClicked");
    }


    @FXML
    private void barButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("btnMiddleFocusStyle");
        btn.getStyleClass().remove("defaultMiddleBtnStyle");
    }


    private void clearFocusStyle(){
        Button[] btns = {btnSysSetting,btnAccSecurity, btnAccPwd};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnMiddleFocusStyle");
            btn.getStyleClass().add("defaultMiddleBtnStyle");
        }
    }

}
