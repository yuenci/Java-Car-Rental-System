package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Admin;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class AdminSideBarController {
    @FXML
    Pane item1, item2, item3, item4, item5, item6, item7;

    @FXML
    Label nameTextLabel,emailTextLabel;

    @FXML
    ImageView avatarImageView;

    private Admin admin = (Admin) StatusContainer.currentUser;

    @FXML
    private void initialize() {
        initUserData();
        initMenuEvent();
        initLabelEvent();
    }
    private void initUserData() {
        nameTextLabel.setText(admin.getUserName());
        emailTextLabel.setText(admin.getEmail());

        Image circleImage = ImageTools.getCircleImages(admin.getAvatar());
        avatarImageView.setImage(circleImage);
    }


    private void initMenuEvent() {
        item1.setOnMouseClicked(event -> changeMenuStyle(item1));
        item2.setOnMouseClicked(event -> changeMenuStyle(item2));
        item3.setOnMouseClicked(event -> changeMenuStyle(item3));
        item4.setOnMouseClicked(event -> changeMenuStyle(item4));
        item5.setOnMouseClicked(event -> changeMenuStyle(item5));
        item6.setOnMouseClicked(event -> changeMenuStyle(item6));
        item7.setOnMouseClicked(event -> {
            changeMenuStyle(item6);
            FXTools.changeScene("mainPage.fxml");
        });

    }

    private void changeMenuStyle(Pane activePane) {
        System.out.println(activePane.toString() + "changeMenuStyle-line229");
        item1.getStyleClass().remove("menuItemActive");
        item2.getStyleClass().remove("menuItemActive");
        item3.getStyleClass().remove("menuItemActive");
        item4.getStyleClass().remove("menuItemActive");
        item5.getStyleClass().remove("menuItemActive");
        item6.getStyleClass().remove("menuItemActive");

        activePane.getStyleClass().add("menuItemActive");
    }

    private  void  initLabelEvent(){
        nameTextLabel.setAlignment(Pos.CENTER);
        emailTextLabel.setAlignment(Pos.CENTER);
    }

}
