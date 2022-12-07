package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
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
    ImageView imgIcon1, imgIcon2, imgIcon3, imgIcon4, imgIcon5, imgIcon6, imgIcon7;
    @FXML
    Pane item1, item2, item3, item4, item5, item6, item7;

    @FXML
    Label nameTextLabel,emailTextLabel,postLabel;

    @FXML
    ImageView avatarImageView;

    private Admin admin = (Admin) StatusContainer.currentUser;
    public static AdminSideBarController instance;

    public AdminSideBarController(){
        instance = this;
    }

    @FXML
    private void initialize() {
        initUserData();
        initAvatar();
        initMenuEvent();
        initIconEvent();
        initLabelEvent();
        item3.getStyleClass().add("menuItemActive");
    }
    public void initUserData() {
        nameTextLabel.setText(admin.getUserName());
        emailTextLabel.setText(admin.getEmail());
        postLabel.setText(admin.getPost());
    }

    public void initAvatar(){
        Image circleImage = ImageTools.getCircleImages(admin.getAvatar());
        avatarImageView.setImage(circleImage);
    }


    private void initMenuEvent() {
        item1.setOnMouseClicked(event -> {
            AdminServiceController.instance.showProfilePage();
            changeMenuStyle(item1);
        });
        item2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
            FXTools.changeScene("messagePage.fxml");
        });
        item3.setOnMouseClicked(event -> {
            AdminServiceController.instance.showDashboardPage();
            changeMenuStyle(item3);
        });
        item4.setOnMouseClicked(event -> {
            AdminServiceController.instance.showVehiclePage();
            changeMenuStyle(item4);
        });
        item5.setOnMouseClicked(event -> {
            AdminServiceController.instance.showOrderPage();
            changeMenuStyle(item5);
        });
        item6.setOnMouseClicked(event -> {
            AdminServiceController.instance.showSettingPage();
            changeMenuStyle(item6);
        });
        item7.setOnMouseClicked(event -> {
            changeMenuStyle(item6);
            StatusContainer.currentUser = null;
            DataTools.logOut();
            FXTools.changeScene("mainPage.fxml");
        });

    }

    private void initIconEvent(){
        imgIcon1.setOnMouseClicked(event -> {
            AdminServiceController.instance.showProfilePage();
            changeMenuStyle(item1);
        });
        imgIcon2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
            FXTools.changeScene("messagePage.fxml");
        });
        imgIcon3.setOnMouseClicked(event -> {
            AdminServiceController.instance.showDashboardPage();
            changeMenuStyle(item3);
        });
        imgIcon4.setOnMouseClicked(event -> {
            AdminServiceController.instance.showVehiclePage();
            changeMenuStyle(item4);
        });
        imgIcon5.setOnMouseClicked(event -> {
            AdminServiceController.instance.showOrderPage();
            changeMenuStyle(item5);
        });
        imgIcon6.setOnMouseClicked(event -> {
            AdminServiceController.instance.showSettingPage();
            changeMenuStyle(item6);
        });
        imgIcon7.setOnMouseClicked(event -> {
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

    private void  initLabelEvent(){
        nameTextLabel.setAlignment(Pos.CENTER);
        emailTextLabel.setAlignment(Pos.CENTER);
        postLabel.setAlignment(Pos.CENTER);
    }

}
