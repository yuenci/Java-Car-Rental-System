package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Customer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class PasswordPageController {

    @FXML
    private Label lblPasswordLen, lblPwdNotice, ivUsername, lblPasswordTemplate;
    @FXML
    private Pane panelSettings;
    @FXML
    private Button btnChangePwd;
    @FXML
    private ImageView ivAvatar, imgOldPwd;
    @FXML
    private TextField txtOldPwd, txtNewPwd, txtReEnterPwd;

    private boolean showPassword = false;
    private boolean passwordStatus = false;

    @FXML
    private void initialize(){
        initTheme();
        initDefaultText();
        initAvatar();
        initTextEventListener();
        btnChangePwd.setDisable(true);
    }

    private void initDefaultText(){
        ivUsername.setText(StatusContainer.currentUser.getUserName());
        txtOldPwd.setText(StatusContainer.currentUser.getPassword());
        changeDisplayPwdStyle();
    }

    private void initAvatar(){
        Image circleAvatar = ImageTools.getCircleImages(StatusContainer.currentUser.getAvatar());
        ivAvatar.setImage(circleAvatar);
    }

    private void changeDisplayPwdStyle(){
        if(showPassword){
            txtOldPwd.setText(StatusContainer.currentUser.getPassword());
        }else{
            String defaultTxt = StatusContainer.currentUser.getPassword();
            for (int i = 0; i < defaultTxt.length(); i++) {
                defaultTxt = defaultTxt.replace(defaultTxt.charAt(i), '●');
            }
            txtOldPwd.setText(defaultTxt);
        }
    }

    private void checkChangePassword(){
        String newPwd = txtNewPwd.getText();
        String reEnterPwd = txtReEnterPwd.getText();
        if(!newPwd.equals("") && !reEnterPwd.equals("")){
            if(newPwd.equals(reEnterPwd) && passwordStatus){
                //change password
                System.out.println("Change password");
                btnChangePwd.setDisable(false);
            }
        }
    }

    @FXML
    void changePasswordClicked() {
        //checkChangePassword();
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

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelSettings.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }

    private void initPasswordCount(){
        lblPasswordLen.setVisible(true);
        lblPasswordTemplate.setVisible(true);
        lblPasswordLen.setText(String.valueOf(txtNewPwd.getText().length()));
    }

    private void initNoticeVisible(boolean visible){
        lblPwdNotice.setVisible(visible);
        lblPasswordLen.setVisible(visible);
        lblPasswordTemplate.setVisible(visible);
    }

    private void initPasswordNotice(TextField textField, String newValue, String oldValue){

//        if (newValue.length() == 0){
//            textField.setStyle("");
////            textField.setStyle("-fx-text-fill: #4e5969; -fx-background-color: transparent; -fx-border-radius: 6px; " +
////                    "-fx-border-color: #c3c3c3; -fx-border-insets: 0;");
////            Platform.runLater(() -> {
////                textField.setStyle("");
//                textField.setStyle("-fx-text-fill: #4e5969; -fx-background-color: transparent; -fx-border-radius: 6px; " +
//                        "-fx-border-color: #c3c3c3 !important; -fx-border-insets: 0;");
//                textField.getStyleClass().add("txtBoxFocus");
//                //textField.setStyle(textField.getStyle() + "-fx-border-color: #c3c3c3 !important;");
////            });
//            System.out.println(textField.getStyle());
//            System.out.println("Empty");
//            return;
//        }

        if(!(newValue.length() > 0)){
            initNoticeVisible(false);
            passwordStatus = false;
            return;
        }
        initPasswordCount();
        textField.getStyleClass().add("txtFieldError");

        if(newValue.length() < 8) {
            lblPwdNotice.setText("Password must be at least 8 characters");
            initNoticeVisible(true);
            textField.getStyleClass().add("txtFieldError");
            return;
        }

        if(newValue.length() > 16){
            initNoticeVisible(false);
            textField.setText(oldValue);
            return;
        }

        if(!(newValue.matches(".*[A-Z].*"))) {
            lblPwdNotice.setText("Password must contain at least one uppercase letter");
            initNoticeVisible(true);
            textField.getStyleClass().add("txtFieldError");
            return;
        }

        if(!(newValue.matches(".*[a-z].*"))) {
            lblPwdNotice.setText("Password must contain at least one lowercase letter");
            initNoticeVisible(true);
            textField.getStyleClass().add("txtFieldError");
            return;
        }

        if(!(newValue.matches(".*[0-9].*"))) {
            lblPwdNotice.setText("Password must contain at least one number");
            initNoticeVisible(true);
            textField.getStyleClass().add("txtFieldError");
            return;
        }

        if(!(newValue.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*"))){
            lblPwdNotice.setText("Password must contain at least one special character");
            initNoticeVisible(true);
            textField.getStyleClass().add("txtFieldError");
            //return;
        }
        textField.getStyleClass().remove("txtFieldError");

        initNoticeVisible(true);
        lblPwdNotice.setVisible(false);
        passwordStatus = true;
        if(newValue.length() > 8){
            textField.getStyleClass().remove("txtFieldError");
            textField.getStyleClass().add("txtFieldWeak");
        }

        if(newValue.length() == 16){
            lblPwdNotice.setVisible(true);
            lblPwdNotice.setText("Strong password");
            textField.getStyleClass().remove("txtFieldWeak");
            textField.getStyleClass().add("txtFieldStrong");
        }

    }

    private void initTextEventListener(){
        txtNewPwd.textProperty().addListener((observable, oldValue, newValue) -> {
            initPasswordNotice(txtNewPwd, txtNewPwd.getText(), oldValue);
        });
        txtReEnterPwd.textProperty().addListener((observable, oldValue, newValue) -> {
            checkChangePassword();
        });
    }
}