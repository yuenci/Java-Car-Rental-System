package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
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
    private User user = StatusContainer.currentUser;

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
        if(user instanceof Customer){
            userMiddleBar.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/middleBarDark.css")
                            .toURI().toString());
        }
    }


    @FXML
    void btnSysSettingClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(user instanceof Customer){
            CustomerServiceController.instance.showSettingPage();
        }else if(user instanceof Admin){
            AdminServiceController.instance.showSettingPage();
        }else{
            DriverMainPageController.driverMainPageInstance.showSettingPage();
        }
    }

    @FXML
    void btnAccSettingClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(user instanceof Customer){
            CustomerServiceController.instance.showAccSecurity();
        }else if(user instanceof Admin){
            AdminServiceController.instance.showAccSecurity();
        }else{
            DriverMainPageController.driverMainPageInstance.showAccSecurity();
        }
    }


    @FXML
    void btnAccPasswordClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(user instanceof Customer){
            CustomerServiceController.instance.showPasswordPage();
        }else if(user instanceof Admin){
            AdminServiceController.instance.showPasswordPage();
        }else{
            DriverMainPageController.driverMainPageInstance.showPasswordPage();
        }
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
