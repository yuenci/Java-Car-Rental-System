package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class PersonalPageController {

    @FXML
    private Pane panelSetting;
    private String newImageURL;

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
//        ivTxtAbout.setFocusTraversable(false);
//        ivTxtAddress.setFocusTraversable(false);
//        ivTxtAbout.setMouseTransparent(true);
//        ivTxtAddress.setMouseTransparent(true);
    }

    private void initText(){

    }

    private void initAvatar(){
        //ivAvatar.setImage(customer.getAvatar());
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelSetting.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }

    @FXML
    void deleteClicked() {
        ivAvatar.setImage(null);
    }

    @FXML
    void uploadClicked() {
        newImageURL = DataTools.fileChooser();
        //ImageTools.getCircleImages(new javafx.scene.image.Image("file:///"+newImageURL));
        ivAvatar.setImage(ImageTools.getCircleImages(new javafx.scene.image.Image("file:///"+newImageURL)));
        //save new profile
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

