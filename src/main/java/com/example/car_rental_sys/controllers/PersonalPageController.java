package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class PersonalPageController {

    private Image avatar;
    private String newImageURL;

    private User user = StatusContainer.currentUser;


    @FXML
    private Label aboutWordCount, nameWordCount;
    @FXML
    private Pane panelSetting;

    @FXML
    private TextField ivTxtPhoneNumber, ivTxtBirthday, ivTxtUsername, ivTxtEmail;

    @FXML
    private Button ivDeleteBtn,ivCancelBtn, ivSaveBtn, ivUploadBtn;

    @FXML
    private ImageView ivAvatar;

    @FXML
    private TextArea ivTxtAbout, ivTxtAddress;


    @FXML
    private void initialize(){
        initTheme();
        initAvatar();
        initText();
        initTextEventListener();
        ivSaveBtn.setDisable(true);
    }

    private void initText(){
        ivTxtUsername.setText(user.getUserName());
        ivTxtEmail.setText(user.getEmail());
        ivTxtPhoneNumber.setText(user.getPhone());
        ivTxtBirthday.setText(user.getBirthday());
        ivTxtAddress.setText(user.getAddress());
        ivTxtAbout.setText(user.getAbout());
        initAboutWordCount();
        initNameWordCount();
    }

    private void initAvatar(){
        setCircleAvatar(user.getAvatar());
        ivTxtUsername.setText(user.getUserName());
    }

    private void initTheme(){
        if(user instanceof Customer){
            panelSetting.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }

    private void initAboutWordCount(){
        aboutWordCount.setText(String.valueOf(ivTxtAbout.getText().length()));
    }

    private void initNameWordCount(){
        nameWordCount.setText(String.valueOf(ivTxtUsername.getText().length()));
    }

    private void initTextEventListener(){
        System.out.println("initTextEventListener");
        ivTxtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(user.getUserName()) && !newValue.isBlank() && newValue.length() > 6){
                ivSaveBtn.setDisable(false);
                if(newValue.length() > 20){
                    ivTxtUsername.setText(oldValue);
                }
                ivTxtUsername.getStyleClass().remove("txtErrorFocusStyle");
            }else if(newValue.isBlank()){
                ivTxtUsername.getStyleClass().add("txtErrorFocusStyle");
                ivSaveBtn.setDisable(true);
            }else{ivSaveBtn.setDisable(true);}
            initNameWordCount();
        });
        ivTxtBirthday.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 10){
                ivSaveBtn.setDisable(true);
                ivTxtBirthday.getStyleClass().add("txtErrorFocusStyle");
            }else if(newValue.length() == 10 && !newValue.equals(user.getBirthday())){
                FXTools.validInputIsDate(ivTxtBirthday, "/", newValue);
                ivSaveBtn.setDisable(false);
            }
        });
        ivTxtAbout.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 4){
                ivSaveBtn.setDisable(true);
            }else if(newValue.length() > 10 && !newValue.equals(user.getAbout())){
                ivSaveBtn.setDisable(false);
                if(newValue.length() > 90){
                    ivTxtAbout.setText(oldValue);
                }
            }
            initAboutWordCount();
        });
        ivTxtAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 5){
                ivSaveBtn.setDisable(true);
                ivTxtAddress.getStyleClass().add("txtAreaErrorStyle");
            }else if(newValue.length() > 10 && !newValue.equals(user.getAddress())){
                ivSaveBtn.setDisable(false);
                if(newValue.length() > 90){
                    ivTxtAddress.setText(oldValue);
                }
                ivTxtAddress.getStyleClass().remove("txtAreaErrorStyle");
            }
        });
        ivTxtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 5){
                ivSaveBtn.setDisable(true);
                ivTxtEmail.getStyleClass().add("txtErrorFocusStyle");
            }else if(newValue.length() > 10 && !newValue.equals(user.getEmail())){
                if(FXTools.validInputIsEmail(ivTxtEmail, newValue)){
                    ivTxtEmail.getStyleClass().remove("txtErrorFocusStyle");
                    ivSaveBtn.setDisable(false);
                }else{
                    ivTxtEmail.getStyleClass().add("txtErrorFocusStyle");
                }
            }
        });
        ivTxtPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 5){
                ivTxtPhoneNumber.getStyleClass().add("txtErrorFocusStyle");
                ivSaveBtn.setDisable(true);
            }else if(newValue.length() > 9 && !newValue.equals(user.getPhone())){
                if(FXTools.validInputIsPhone(ivTxtPhoneNumber, newValue)){
                    ivTxtPhoneNumber.getStyleClass().remove("txtErrorFocusStyle");
                    ivSaveBtn.setDisable(false);
                }else{
                    ivTxtPhoneNumber.getStyleClass().add("txtErrorFocusStyle");
                }
            }
        });
    }

    @FXML
    void deleteClicked() {
        System.out.println(user.getGender());
        avatar = ImageTools.getDefaultProfile(user.getGender());
        System.out.println("hi: " + avatar);
        Platform.runLater(() -> {
            ivAvatar.setImage(null);
            setCircleAvatar(avatar);
        });
        user.setAvatar(ImageTools.getDefaultProfile(user.getGender()));
        refreshAvatar();
    }

    @FXML
    void uploadClicked() {
        newImageURL = DataTools.fileChooser();
        if(newImageURL != null){
            Image newAvatar = ImageTools.getNewAvatar(newImageURL,user.getUserID());
            setCircleAvatar(newAvatar);
            avatar = newAvatar;
            user.setAvatar(avatar);
            refreshAvatar();
        }
    }

    private void refreshAvatar(){
        Platform.runLater(() -> {
            if(user instanceof Admin){
                AdminSideBarController.instance.initAvatar();
            }else if(user instanceof Customer){
                CustomerSideBarController.instance.initAvatar();
            }
        });
    }

    private void setCircleAvatar(Image avatar){
        Image circleAvatar = ImageTools.getCircleImages(avatar);
        ivAvatar.setImage(circleAvatar);
    }

    @FXML
    void saveChangesClicked() {
        //save all
        getValue();

        Platform.runLater(() -> {
            if(user instanceof Admin){
                AdminSideBarController.instance.initUserData();
            }else if(user instanceof Customer){
                CustomerSideBarController.instance.initUserInfo();
            }
        });

        Platform.runLater(() -> {
            user.updateUserInfo();
            showNotification();
        });
    }

    @FXML
    void cancelClicked() {
        Node[] nodes = {ivTxtUsername, ivTxtBirthday, ivTxtEmail, ivTxtPhoneNumber};
        for(Node node : nodes){
            node.getStyleClass().remove("txtErrorFocusStyle");
        }
        Node[] nodes2 = {ivTxtAddress};
        for(Node node : nodes2){
            node.getStyleClass().remove("txtAreaErrorStyle");
        }
        initText();
    }

    private void getValue(){
        user.setUserName(ivTxtUsername.getText());
        user.setBirthday(ivTxtBirthday.getText());
        user.setAddress(ivTxtAddress.getText());
        user.setAbout(ivTxtAbout.getText());
        user.setEmail(ivTxtEmail.getText());
        user.setPhone(ivTxtPhoneNumber.getText());
    }

    private void showNotification(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.SUCCESS, "Modify Changes Successfully");
        messageFrame.show();
    }

}

