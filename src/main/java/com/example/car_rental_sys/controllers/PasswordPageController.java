package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
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

    private User user = StatusContainer.currentUser;

    @FXML
    private void initialize(){
        initTheme();
        initDefaultText();
        initAvatar();
        initTextEventListener();
        btnChangePwd.setDisable(true);
        imgOldPwd.setVisible(false);
    }

    private void initDefaultText(){
        ivUsername.setText(user.getUserName());
        txtOldPwd.setText(user.getPassword());
        changeDisplayPwdStyle();
    }

    private void initAvatar(){
        Image circleAvatar = ImageTools.getCircleImages(user.getAvatar());
        ivAvatar.setImage(circleAvatar);
    }

    private void changeDisplayPwdStyle(){
        if(showPassword){
            txtOldPwd.setText(user.getPassword());
        }else{
            String defaultTxt = user.getPassword();
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
                btnChangePwd.setDisable(false);
            }
        }
    }

    @FXML
    void changePasswordClicked() {
        user.setPassword(txtNewPwd.getText());
        boolean status = DataTools.resetPassword(user.getEmail(), txtNewPwd.getText());
        System.out.println(status);
        Platform.runLater(() -> {
            txtNewPwd.setText("");
            txtReEnterPwd.setText("");
            btnChangePwd.setDisable(true);
            String password = user.getPassword();
            for (int i = 0; i < password.length(); i++) {
                password = password.replace(password.charAt(i), '●');
            }
            txtOldPwd.setText(password);
        });

        Platform.runLater(this::showNotification);
    }

    @FXML
    void imgOldPwdClicked() {
//        if(showPassword){
//            imgOldPwd.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/view.png"));
//            showPassword = false;
//        }else{
//            imgOldPwd.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/hidden.png"));
//            showPassword = true;
//        }
//        changeDisplayPwdStyle();
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

        textField.getStyleClass().remove("txtFieldError");
        textField.getStyleClass().add("txtBoxFocus");
        textField.getStyleClass().remove("txtFieldWeak");
        textField.getStyleClass().remove("txtFieldStrong");

        if(!(newValue.length() > 0)){
            initNoticeVisible(false);
            passwordStatus = false;
            return;
        }
        initPasswordCount();
        textField.getStyleClass().add("txtFieldError");
       // textField.getStyleClass().remove("txtFieldWeak");

        if(newValue.length() < 8) {
            lblPwdNotice.setText("Password must be at least 8 characters");
            initNoticeVisible(true);
            //textField.getStyleClass().remove("txtBoxWeak");
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
            return;
        }

        if(!(newValue.matches(".*[a-z].*"))) {
            lblPwdNotice.setText("Password must contain at least one lowercase letter");
            initNoticeVisible(true);
            return;
        }

        if(!(newValue.matches(".*[0-9].*"))) {
            lblPwdNotice.setText("Password must contain at least one number");
            initNoticeVisible(true);
            return;
        }

        if(!(newValue.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*"))){
            lblPwdNotice.setText("Password must contain at least one special character");
            initNoticeVisible(true);
            return;
        }

        textField.getStyleClass().remove("txtFieldError");
        textField.getStyleClass().remove("txtFieldStrong");
        textField.getStyleClass().add("txtFieldWeak");

        initNoticeVisible(true);
        lblPwdNotice.setText("Password is weak");
        passwordStatus = true;

        if(newValue.length() > 11){
            lblPwdNotice.setVisible(true);
            lblPwdNotice.setText("Password is strong");
            textField.getStyleClass().remove("txtFieldWeak");
            textField.getStyleClass().add("txtFieldStrong");
        }

    }

    private void initTextEventListener(){
        txtNewPwd.textProperty().addListener((observable, oldValue, newValue) -> {
            initPasswordNotice(txtNewPwd, newValue, oldValue);
        });
        txtReEnterPwd.textProperty().addListener((observable, oldValue, newValue) -> {
            checkChangePassword();
        });
    }

    private void showNotification(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.SUCCESS, "Password Changed Successfully");
        messageFrame.show();
    }
}