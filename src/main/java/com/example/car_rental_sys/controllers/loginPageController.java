package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Tools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class loginPageController {
    @FXML
    Button signUpBtn;

    @FXML
    private void signUpBtnClick(){
        new Tools().reSetScene( "signUp.fxml");
    }
}
