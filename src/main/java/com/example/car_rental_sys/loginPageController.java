package com.example.car_rental_sys;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class loginPageController {
    @FXML
    Button signUpBtn;

    @FXML
    private void signUpBtnClick(){
        new Tools().reSetScence( "signUp.fxml");
    }
}