package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
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
    private Button ivSaveButton;
    @FXML
    private Pane panelSetting;

    @FXML
    private TextField txtSecurityAnswer,txtDrivingLicense;
    @FXML
    private ComboBox<String> comboQuestion;

    @FXML
    private ImageView ivAvatar;

    @FXML
    private Label ivUsername;

    private User user = StatusContainer.currentUser;

    @FXML
    private void initialize(){
        initTheme();
        initDefaultText();
        initAvatar();
        initComboBox();
        initTextEventListener();
        ivSaveButton.setDisable(true);
    }

    private void initDefaultText(){
        ivUsername.setText(user.getUserName());    /// change to username
        txtDrivingLicense.setText(user.getDLNumber());
        comboQuestion.setValue(user.getSecurityProblem());
        txtSecurityAnswer.setText(user.getSecurityAnswer());
    }

    private void initComboBox(){
        comboQuestion.getItems().addAll("What is your favorite color?","What is your favorite food?","What is your favorite movie?");
    }

    private void initAvatar(){
        Image circleAvatar = ImageTools.getCircleImages(user.getAvatar());
        ivAvatar.setImage(circleAvatar);
    }

    @FXML
    public void saveBtnClicked() {
        user.updateSecurityProblem(comboQuestion.getValue(),txtSecurityAnswer.getText());
        ivSaveButton.setDisable(true);
        Platform.runLater(this::showNotification);
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelSetting.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }

    private void initTextEventListener(){
        txtSecurityAnswer.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtSecurityAnswer.getText().isBlank()){
                ivSaveButton.setDisable(true);
                return;
            }

            if(!newValue.equals(user.getSecurityAnswer())){
                ivSaveButton.setDisable(false);
            }else{
                ivSaveButton.setDisable(true);
            }
        });
    }

    private void showNotification(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.SUCCESS, "Modify Changes Successfully");
        messageFrame.show();
    }
}
