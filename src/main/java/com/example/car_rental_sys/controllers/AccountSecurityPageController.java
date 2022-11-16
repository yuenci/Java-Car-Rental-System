package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AccountSecurityPageController {

    @FXML
    private TextField txtSecurityAnswer;
    @FXML
    private ComboBox<String> comboQuestion;
    @FXML
    private Button saveBtn;
    private boolean showPassword = false;

    @FXML
    private ImageView ivAvatar;

    @FXML
    private TextField txtOldPwd;

    @FXML
    private TextField txtReEnterPwd;

    @FXML
    private ImageView imgOldPwd;

    @FXML
    private Label ivUsername;

    @FXML
    private TextField txtDrivingLicense;

    @FXML
    private TextField txtNewPwd;

    @FXML
    private void initialize(){
        initDefaultText();
        initAvatar();
        initComboBox();
    }

    private void initDefaultText(){
        ivUsername.setText("");
        changeDisplayPwdStyle();
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

    private void changeDisplayPwdStyle(){
        if(showPassword){
            txtOldPwd.setText("");
        }else{
            //String defaultTxt = "abcdfgsd";
            //replace the characters with *
            //txtOldPwd.setText(defaultTxt.replaceAll("\\.", "*"));
        }
    }

    private void checkChangePassword(){
        String newPwd = txtNewPwd.getText();
        String reEnterPwd = txtReEnterPwd.getText();
        if(!newPwd.equals("") && !reEnterPwd.equals("")){
            if(newPwd.equals(reEnterPwd)){
                //change password
                System.out.println("Change password");
            }else{
                //show warning
                System.out.println("Password not match or empty");
            }
        }
    }

    @FXML
    void changePasswordClicked(MouseEvent event) {
        checkChangePassword();
    }

    @FXML
    void imgOldPwdClicked(MouseEvent event) {
        if(showPassword){
            imgOldPwd.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/view.png"));
            showPassword = false;
        }else{
            imgOldPwd.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/hidden.png"));
            showPassword = true;
        }
        changeDisplayPwdStyle();
    }

    @FXML
    public void saveBtnClicked(MouseEvent mouseEvent) {
        //get the answer and question
        String answer = txtSecurityAnswer.getText();
        String question = comboQuestion.getValue();

        //save the security question and answer
        System.out.println("Save security question and answer");

    }
}
