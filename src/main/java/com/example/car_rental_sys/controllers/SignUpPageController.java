package com.example.car_rental_sys.controllers;


import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.StringTools;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.SendEmail;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.BrowserModal;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;


public class SignUpPageController extends Controller {
    @FXML
    Pane textFieldContainer;

    @FXML
    Button createAccountBtn;

    @FXML
    public Pane mainPane;

    @FXML TextField firstName, lastName, emailInput;

    @FXML PasswordField password;

    private final Customer newCustomer = new Customer();
    private String firstNameStr, lastNameStr, emailStr, passwordStr;



    private  TextField verifyCodeTextField;

    @FXML
    void loginClicked(MouseEvent event) {
//        System.out.println("login clicked");
        FXTools.changeScene("loginPage.fxml");
    }

    @FXML
    private  void initialize(){
        StatusContainer.currentPageController = this;

//        textFieldContainer.getChildren().clear();
//        createAccountBtn.setText("Create Account");
//        addVerifyCodePage();

    }

    @FXML
    void btnCreateClicked(MouseEvent e) {
        String text= createAccountBtn.getText();

        if(Objects.equals(text, "Next")){
            getAllTextFieldStr();
            if(checkTextField().equals("YYYY")){
                if(!StringTools.ifStringContainsNumberAndSpecialCharacter(firstNameStr+lastNameStr)){
                    if(checkEmailAndPwd()){
                        textFieldContainer.getChildren().clear();
                        createAccountBtn.setText("Create Account");
                        addVerifyCodePage();
                        setNewCustomerInfo();
                        showValidEmail();
                    }
                }else{
                    String msg = "First name and last name can only contain letters";
                    MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, msg);
                    messageFrame.show();
                }
            }else{
                alarmMessage(checkTextField());
            }

        }
        else if(Objects.equals(text, "Create Account")){
            //add create account page
            if(Objects.equals(verifyCodeTextField.getText(), StatusContainer.currentPinCode)){
                showSuccessMessage();
                storeNewCustomerInfo();
                System.out.println( "create account success + go to loginPage.fxml" );
            }
            else{
                System.out.println("verify failed");
                MessageFrame messageFrame = new MessageFrame(MessageFrameType.ERROR, "Verify code is incorrect");
                messageFrame.show();
            }
        }


    }
    private void showValidEmail(){
        try {
            SendEmail.sendVerificationEmail(emailInput.getText(),firstNameStr + lastNameStr,"Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllTextFieldStr(){
        firstNameStr = firstName.getText();
        lastNameStr = lastName.getText();
        emailStr = emailInput.getText();
        passwordStr = password.getText();
    }


    private void addVerifyCodePage() {
        addTextField();
        addLabel();
        addLabel2();
    }

    private void addTextField() {
        // add a text field
        TextField textField = new TextField();
        textField.setPromptText("Validation code");
        textField.setPrefHeight(50);
        textField.setPrefWidth(335);
        textField.setPrefColumnCount(1);
        textField.setLayoutX( 14 );
        textField.setLayoutY( 92 );
        textField.getStyleClass().add("txtField");
        verifyCodeTextField = textField;

        textFieldContainer.getChildren().add(textField);
    }

    private void addLabel() {
        // add a label
        Label label = new Label();
        label.setText("We send a validation email to you, please \nenter the validation code");
        label.setPrefHeight(70);
        label.setPrefWidth(335);
        label.setLayoutX( 14 );
        label.setLayoutY(10);
        //label.setWrapText(true);
        label.getStyleClass().add("tipText");

        textFieldContainer.getChildren().add(label);
    }

    private void  addLabel2(){
        // add a label
        Label label = new Label();
        label.setText("Didn't receive the email?");
        label.setPrefHeight(30);
        label.setPrefWidth(335);
        label.setLayoutX( 14 );
        label.setLayoutY(168);
        //label.setWrapText(true);
        // 右对齐
        label.setAlignment(Pos.CENTER_RIGHT);
        label.getStyleClass().add("tipText2");

        textFieldContainer.getChildren().add(label);

        // set click event
        label.setOnMouseClicked(e -> slideVerify());
    }

    @FXML
    private void slideVerify() {
        String url = ConfigFile.backendPost +  "slideVerify/index.html";
        BrowserModal browserModal = new BrowserModal(375, 450, url) ;
        // modal
        browserModal.setModality();
        Function<String, Void> func =(message) -> {
            if (Objects.equals(message, "Verification succeeded")) {
                StatusContainer.ifVerify = true;
                System.out.println(message);
                Platform.runLater(() -> {
                    //code
                    try {
                        Thread.sleep(2 * 1000);
                        storeNewCustomerInfo();
                        browserModal.stage.close();
                        showSuccessMessage();

                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            return null;
        };

        browserModal.setFunction(func);
        browserModal.show();
    }

    private void showSuccessMessage(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.SUCCESS, "Account created successfully");
        messageFrame.setSuccessCallbackFunc((i) -> {
            FXTools.changeScene("loginPage.fxml");
            return null;
        });
        messageFrame.show();
    }

    private String checkTextField(){
        // check if the text field is empty
        StringBuilder res = new StringBuilder();

        TextField[] textFields = {firstName, lastName, password, emailInput};

        for ( TextField textField : textFields ) {
            res.append(checkTextFieldEmpty(textField));
        }
        return res.toString();
    }

    private String checkTextFieldEmpty(TextField textField){
        if(textField.getText().isEmpty()){
            return "N";
        }else{
            return "Y";
        }
    }

    private void alarmMessage(String message){
        String msg = "Please fill your ";

        ArrayList<String> list = new ArrayList<>();

        if(message.charAt(0) == 'N'){
            list.add("first name");
        }
        if(message.charAt(1) == 'N'){
            list.add("last name");
        }
        if(message.charAt(2) == 'N'){
            list.add("password");
        }
        if(message.charAt(3) == 'N'){
            list.add("email");
        }
        String res = String.join(", ", list);
        msg = msg + res + ".";

        MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, msg);
        messageFrame.show();
    }

    private boolean checkEmailAndPwd(){
        // check if the email is valid
        if(!checkEmail()){
            return false;
        }
        // check if the password is valid
        return checkPassword();
    }

    // password strong check
    private boolean checkPassword(){
         return DataTools.checkPassword(passwordStr);
    }

    // check if the email is valid
    private boolean checkEmail(){
        String email = emailStr;
        String sql = "SELECT email FROM userInfo WHERE email = " + "'" + email + "'";
        ArrayList<String[]> res = SQL.query(sql);


        if(res.size() >0) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Email already exists");
            messageFrame.show();
            return false;
        }

        return  DataTools.checkEmail(email);

    }

    private void setNewCustomerInfo(){
        // set new customer info in new thread
        new Thread(() -> {
            try {
                newCustomer.setUserFirstName(firstNameStr);
                newCustomer.setUserLastName(lastNameStr);
                newCustomer.setPassword(Encryption.med5Encrypt(passwordStr));
                newCustomer.setEmail(emailStr);
                newCustomer.setUserID(DataTools.getNewUserID());
                newCustomer.setRegTime(DateTools.getFormatDateTime());
                //random save user gender {Male,Female}
                newCustomer.setGender(DataTools.getRandomGender());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void storeNewCustomerInfo(){
        // store new customer info
        // use new thread to store data
        new Thread(() -> {
            try {
                newCustomer.storeCustomerInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

// #TODO use slideverification in out of net environment
// #TODO use slideverification can't verify email, should verify email again
