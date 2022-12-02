package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PersonalPageController {

    private String newImageURL;

    @FXML
    private TextField ivTxtPhoneNumber;

    @FXML
    private TextField ivTxtBirthday;

    @FXML
    private Button ivDeleteBtn;

    @FXML
    private ImageView ivAvatar;

    @FXML
    private TextField ivTxtUsername;

    @FXML
    private Button ivCancelBtn;

    @FXML
    private TextArea ivTxtAbout;

    @FXML
    private Button ivSaveBtn;

    @FXML
    private TextField ivTxtEmail;

    @FXML
    private Button ivUploadBtn;

    @FXML
    private TextArea ivTxtAddress;

    @FXML
    private void initialize(){

    }

    private void initText(){

    }

    private void initAvatar(){
        //ivAvatar.setImage(User.instance.getAvatar());
    }

    @FXML
    void deleteClicked(MouseEvent event) {
        ivAvatar.setImage(null);
    }

    @FXML
    void uploadClicked(MouseEvent event) {
        newImageURL = DataTools.fileChooser();
        ivAvatar.setImage(new Image(newImageURL));
    }


    @FXML
    void saveChangesClicked(MouseEvent event) {
        //save all
    }

    @FXML
    void cancelClicked(MouseEvent event) {
        //if have changes then no save
    }

}

