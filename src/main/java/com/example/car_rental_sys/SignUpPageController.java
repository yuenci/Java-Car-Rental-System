package com.example.car_rental_sys;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


public class SignUpPageController {

    @FXML
    void loginClicked(MouseEvent event) {
//        System.out.println("login clicked");
        Tools.changeScence("loginPage.fxml");
    }

    @FXML
    void btnCreateClicked(MouseEvent event) {
        System.out.println("create clicked");
    }
}

