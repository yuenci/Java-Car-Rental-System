package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.ToolsLib.StringTools;
import com.example.car_rental_sys.orm.Driver;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DriverSideController {
    private Driver driver = (Driver) StatusContainer.currentUser;

    @FXML
    Label postLabel,nameLabel,carNumberLabel;

    @FXML
    ImageView avatarImageView;

    @FXML
    Pane item1, item2, item3, item4, item5, item6;

    @FXML
    private void initialize() {
//        System.out.println("DriverSideController");
        initSideBar();
        initMenuEvent();
    }

    private void initSideBar() {
        // label set text align center
        postLabel.setText(StringTools.capitalizeFirstLetter(driver.getPost()));
        postLabel.setAlignment(Pos.CENTER);

        nameLabel.setText(StringTools.capitalizeFirstLetter(driver.getUserName()));
        nameLabel.setAlignment(Pos.CENTER);

        avatarImageView.setImage(ImageTools.getCircleImages(driver.getAvatar()));
        ImageTools.setImageShapeToCircle(avatarImageView);

    }


    private void initMenuEvent() {
        item1.setOnMouseClicked(event -> {
            changeMenuStyle(item1);
        });
        item2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
            FXTools.changeScene("messagePage.fxml");
        });
        item3.setOnMouseClicked(event -> {
            changeMenuStyle(item3);
        });
        item4.setOnMouseClicked(event -> {
            changeMenuStyle(item4);
        });
        item5.setOnMouseClicked(event -> {
            changeMenuStyle(item5);
        });
        item6.setOnMouseClicked(event -> {
            changeMenuStyle(item6);
            StatusContainer.currentUser = null;
            DataTools.logOut();
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
}



