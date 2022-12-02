package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class AccountSecurityPageController {

    @FXML
    private Pane panelSetting;

    @FXML
    private TextField txtSecurityAnswer;
    @FXML
    private ComboBox<String> comboQuestion;

    @FXML
    private ImageView ivAvatar;

    @FXML
    private Label ivUsername;

    @FXML
    private TextField txtDrivingLicense;

    @FXML
    private void initialize(){
        initTheme();
        initDefaultText();
        initAvatar();
        initComboBox();
    }

    private void initDefaultText(){
        ivUsername.setText("");    /// change to username
        txtDrivingLicense.setText("");
        comboQuestion.setValue("");
        txtSecurityAnswer.setText("");
    }

    private void initComboBox(){
        comboQuestion.getItems().addAll("What is your favorite color?","What is your favorite food?","What is your favorite movie?");
    }

    private void initAvatar(){
//        Image image = new Image("file:src/main/resources/images/avatar.png");
//        ivAvatar.setImage(image);
    }

    @FXML
    public void saveBtnClicked(MouseEvent mouseEvent) {
        //get the answer and question
        String answer = txtSecurityAnswer.getText();
        String question = comboQuestion.getValue();

        //save the security question and answer
        System.out.println("Save security question and answer");

    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelSetting.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }
}
