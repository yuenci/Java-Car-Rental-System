package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class PersonalPageController {

    private String newImageURL;

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
        ivTxtUsername.setText(StatusContainer.currentUser.getUserName());
        ivTxtEmail.setText(StatusContainer.currentUser.getEmail());
        ivTxtPhoneNumber.setText(StatusContainer.currentUser.getPhone());
        ivTxtBirthday.setText(StatusContainer.currentUser.getBirthday());
        ivTxtAddress.setText(StatusContainer.currentUser.getAddress());
        ivTxtAbout.setText(StatusContainer.currentUser.getAbout());
        initAboutWordCount();
        initNameWordCount();
    }

    private void initAvatar(){
        setCircleAvatar(StatusContainer.currentUser.getAvatar());
        ivTxtUsername.setText(StatusContainer.currentUser.getUserName());
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
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
            if(!newValue.equals(StatusContainer.currentUser.getUserName()) && !newValue.isBlank() && newValue.length() > 6){
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
            }else if(newValue.length() == 10 && !newValue.equals(StatusContainer.currentUser.getBirthday())){
                FXTools.validInputIsDate(ivTxtBirthday, "/", newValue);
                ivSaveBtn.setDisable(false);
            }
        });
        ivTxtAbout.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 4){
                ivSaveBtn.setDisable(true);
            }else if(newValue.length() > 10 && !newValue.equals(StatusContainer.currentUser.getAbout())){
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
                ivTxtAddress.getStyleClass().add("txtErrorFocusStyle");
            }else if(newValue.length() > 10 && !newValue.equals(StatusContainer.currentUser.getAddress())){
                ivSaveBtn.setDisable(false);
                if(newValue.length() > 90){
                    ivTxtAddress.setText(oldValue);
                }
                ivTxtAddress.getStyleClass().remove("txtErrorFocusStyle");
            }
        });
        ivTxtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 5){
                ivSaveBtn.setDisable(true);
                ivTxtEmail.getStyleClass().add("txtErrorFocusStyle");
            }else if(newValue.length() > 10 && !newValue.equals(StatusContainer.currentUser.getEmail())){
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
            }else if(newValue.length() > 9 && !newValue.equals(StatusContainer.currentUser.getPhone())){
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
        ivAvatar.setImage(null);
    }

    @FXML
    void uploadClicked() {
        newImageURL = DataTools.fileChooser();
        //remove the current image
        Image newAvatar =   ImageTools.getNewAvatar(newImageURL,StatusContainer.currentUser.getUserID());
        setCircleAvatar(newAvatar);
        //save new profile
    }

    private void setCircleAvatar(Image avatar){
        Image circleAvatar = ImageTools.getCircleImages(avatar);
        ivAvatar.setImage(circleAvatar);
    }

    @FXML
    void saveChangesClicked() {
        //save all
    }

    @FXML
    void cancelClicked() {
        Node[] nodes = {ivTxtUsername, ivTxtBirthday, ivTxtAddress, ivTxtAbout, ivTxtEmail, ivTxtPhoneNumber};
        for(Node node : nodes){
            node.getStyleClass().remove("txtErrorFocusStyle");
        }
        initText();
    }

}

