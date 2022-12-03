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
    private Button btnAccSecurity;

    @FXML
    private Button btnPersonInfo;

    @FXML
    private Button btnSysSetting;

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
        btnPersonInfo.getStyleClass().add("btnMiddleFocusStyle");
        btnPersonInfo.getStyleClass().remove("defaultMiddleBtnStyle");
        Button[] btns = {btnAccSecurity, btnSysSetting, btnAccPwd};
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
    void btnPersonInfoClicked(MouseEvent event) throws IOException {
        barButtonClickEvent(event);
        UserSettingPageController.instance.showPersonalInfo();
        System.out.println("btnPersonInfoClicked");
    }


    @FXML
    void btnSysSettingClicked(MouseEvent event) throws IOException {
        barButtonClickEvent(event);
        UserSettingPageController.instance.showSetting();
        System.out.println("btnSysSettingClicked");
    }


    @FXML
    void btnAccSettingClicked(MouseEvent event) throws IOException {
        barButtonClickEvent(event);
        UserSettingPageController.instance.showAccSecurity();
        System.out.println("btnAccSettingClicked");
    }


    @FXML
    void btnAccPasswordClicked(MouseEvent event) throws IOException {
        barButtonClickEvent(event);
        UserSettingPageController.instance.showPassword();
        System.out.println("btnAccPasswordClicked");
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
        Button[] btns = {btnAccSecurity, btnPersonInfo, btnSysSetting, btnAccPwd};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnMiddleFocusStyle");
            btn.getStyleClass().add("defaultMiddleBtnStyle");
        }
    }

}
