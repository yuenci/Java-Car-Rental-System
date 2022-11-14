package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.funtions.SendEmail;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Driver;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;

public class loginPageController extends Controller{
    @FXML
    Button signUpBtn,loginBtn,resetBtn;

    @FXML
    public Pane mainPane;

    @FXML
    TextField passwordInputText ,emailInput;

    @FXML
    PasswordField passwordInputPass;

    @FXML
    Label emailLabel, passwordLabel,tipLabel,rememberMeLabel,recoveryLabel;

    @FXML
    CheckBox checkBox;

    @FXML
    ImageView emailImageView;

    private String passwordUserInput;

    @FXML
    private  void initialize(){
        StatusContainer.currentPageController = this;
        passwordInputText.setVisible(false);
        resetBtn.setVisible(false);
        initEnterEvent();
    }

    @FXML
    private void loginBtnClick() {
        boolean rememberMe = checkBox.isSelected();
        //System.out.println( "loginBtnClick" );
        if(checkEmailAndPassword()){
            String emailValue = emailInput.getText();
            String userRole = DataTools.getUserRoleFromUserEmail(emailValue);
            if(Objects.equals(userRole, "customer")){
                System.out.println( "customer login" );
                StatusContainer.currentUser = new Customer(emailValue);
                FXTools.changeScene("customerServicePage.fxml");}
            else if(Objects.equals(userRole, "driver")){
                System.out.println( "driver login" );
                StatusContainer.currentUser = new Driver(emailValue);
                FXTools.changeScene("driverServicePage.fxml");
            }else if(Objects.equals(userRole, "admin")){
                System.out.println( "admin login" );
                StatusContainer.currentUser = new Admin(emailValue);
                FXTools.changeScene("adminServicePage.fxml");
            }
            DataTools.logLogin(rememberMe);
        }else{
            System.out.println( "login fail" );
        }
    }

    @FXML
    private void signUpBtnClick(){
        new Tools().reSetScene( "signUpPage.fxml");
    }

    @FXML
    private void showPasswordIconClick(){
        passwordUserInput = getPasswordInput();

        if(passwordInputPass.isVisible()){
            passwordInputPass.setVisible(false);
            passwordInputText.setVisible(true);
            passwordInputText.setText(passwordUserInput);
            passwordInputText.requestFocus();
        }else{
            passwordInputPass.setVisible(true);
            passwordInputText.setVisible(false);
            passwordInputPass.setText(passwordUserInput);
            passwordInputPass.requestFocus();
        }
    }
    private void initEnterEvent(){
        mainPane.setOnKeyPressed(event -> {
            if(event.getCode().toString().equals("ENTER")){
                //System.out.println("yes");
                loginBtnClick();
            }
        });
    }

    private boolean  checkEmailAndPassword(){
        String emailValue = emailInput.getText();

        String passwordValue = getPasswordInput();

        if(emailValue.length() == 0 || passwordValue.length() == 0){
            new MessageFrame(MessageFrameType.ERROR,"Email or password can not be empty" ).show();
            return false;
        }
        if(!DataTools.checkEmail(emailValue))  return false;

        int validCode = Tools.loginValidation(emailValue,passwordValue);

        // return code
        // 200: success
        // 100: email not found
        // 300: password not match
        // 400: unknown error
        if(validCode == 100){
            new MessageFrame(MessageFrameType.WARNING,"Email doesn't exist" ).show();
            return false;

        }else if(validCode == 300){
            new MessageFrame(MessageFrameType.WARNING,"Password is incorrect" ).show();
            return false;

        }else if(validCode == 400){
            new MessageFrame(MessageFrameType.ERROR,"Unknown error occurred." ).show();
            new Tools().reSetScene( "mainPage.fxml");
            return false;
        }



        return true;
    }

    @FXML
    private boolean recoveryPassword(){
        // check if email  is empty
        String emailValue = emailInput.getText();
        if(emailValue.length() == 0){
            new MessageFrame(MessageFrameType.ERROR,"Email can not be empty" ).show();
            return false;
        }

        // check if email exist
        String sql = "SELECT userName FROM userInfo WHERE email = '"+emailValue+"'";
        ArrayList<String[]> result = SQL.query(sql);
        if(result.size() == 0){
            new MessageFrame(MessageFrameType.WARNING,"Email doesn't exist" ).show();
            return false;
        }
        String userName = result.get(0)[0].replace("-","");

        StatusContainer.currentResetPassWordEmail = emailValue;

        // send email
        new Thread(() -> {
            try {
                SendEmail.sendVerificationEmail(emailValue,userName,"reset password");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // switch UI
        loginToReset(true);

        return  true;

    }

    private void loginToReset(boolean isReset){
        boolean boolValue1 = !isReset;
        String emailText = boolValue1 ? "Email" : "Verification code";
        String passwordLabelValue = boolValue1 ? "Password" : "New password";
        String tipLabelValue = boolValue1 ? "To keep connect with please login with your personal info" :
                "Please check your email for verification code";
        String imageNameValue = boolValue1 ? "mailIcon.png" : "verifyCode.png";

        if(isReset){
            emailInput.setText("");
            emailInput.setPromptText("");
            passwordInputPass.setText("");
            passwordInputText.setText("");
        }else{
            emailInput.setText(StatusContainer.currentResetPassWordEmail);
            emailInput.setPromptText("Sample@anywhere.com");
        }

        emailLabel.setText(emailText);
        passwordLabel.setText(passwordLabelValue);
        tipLabel.setText(tipLabelValue);


        String path = "src/main/resources/com/example/car_rental_sys/image/UI/"+imageNameValue;
        emailImageView.setImage(ImageTools.getImageObjFromPath(path));
        checkBox.setVisible(boolValue1);
        rememberMeLabel.setVisible(boolValue1);
        recoveryLabel.setVisible(boolValue1);
        signUpBtn.setVisible(boolValue1);
        loginBtn.setVisible(boolValue1);
        resetBtn.setVisible(isReset);
    }

    @FXML
    private void resetBtnClick(MouseEvent mouseEvent) {
        String verifyCode = emailInput.getText();
        String newPassword = getPasswordInput();
        if(!DataTools.checkPassword(newPassword)){
            return;
        }

        if(Objects.equals(verifyCode, StatusContainer.currentPinCode) ){
            loginToReset(false);
            DataTools.resetPassword(StatusContainer.currentResetPassWordEmail,getPasswordInput());
        }else {
            new MessageFrame(MessageFrameType.WARNING,"Verification code is incorrect" ).show();
        }
    }

    private String getPasswordInput(){
        String passwordTextFieldValue = passwordInputPass.getText();
        String passwordTextValue = passwordInputText.getText();
        return passwordTextFieldValue.length() >passwordTextValue.length() ?passwordTextFieldValue : passwordTextValue;
    }
}
// DONE: back to login page, input email automatically
// TODO : can't receiver email, use security question instead
