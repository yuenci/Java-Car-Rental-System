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

public class InfoMiddleBarController {

    @FXML
    private Pane userMiddleBar;
    @FXML
    private Button btnPersonInfo;

    public static InfoMiddleBarController instance;
    private User user = StatusContainer.currentUser;

    public InfoMiddleBarController() {
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
    }

    private void initTheme(){
        if(user instanceof Customer){
            userMiddleBar.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/middleBarDark.css")
                            .toURI().toString());
        }
    }

    @FXML
    void btnPersonInfoClicked(MouseEvent event) {
        barButtonClickEvent(event);
        if(user instanceof Customer){
            CustomerServiceController.instance.showProfilePage();
        }else if(user instanceof Admin){
            AdminServiceController.instance.showProfilePage();
        }else{
            DriverMainPageController.driverMainPageInstance.showProfilePage();
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
        Button[] btns = {btnPersonInfo};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnMiddleFocusStyle");
            btn.getStyleClass().add("defaultMiddleBtnStyle");
        }
    }

}
