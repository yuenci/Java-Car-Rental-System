package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.ToolsLib.StringTools;
import com.example.car_rental_sys.orm.Driver;
import com.example.car_rental_sys.sqlParser.SQL;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class DriverSideController {
    public Label nameTextLabel;
    private Driver driver = (Driver) StatusContainer.currentUser;
    public static DriverSideController instance;

    @FXML
    Label postLabel,nameLabel,carNumberLabel;

    @FXML
    Label rating,exp,speed;

    @FXML
    ImageView avatarImageView;

    @FXML
    Pane item1, item2, item3, item4, item5;

    @FXML
    ImageView imgIcon1, imgIcon2, imgIcon3, imgIcon4, imgIcon5;

    public DriverSideController(){
        instance = this;
    }

    @FXML
    private void initialize() {
//        System.out.println("DriverSideController");
        initSideBar();
        initMenuEvent();
        initIconEvent();
        InitDriverInfo();
        item3.getStyleClass().add("menuItemActive");
    }

    private void initSideBar() {
        // label set text align center
        postLabel.setText(StringTools.capitalizeFirstLetter(driver.getPost()));
        postLabel.setAlignment(Pos.CENTER);

        nameLabel.setText(StringTools.capitalizeFirstLetter(driver.getUserName()));
        nameLabel.setAlignment(Pos.CENTER);

        avatarImageView.setImage(ImageTools.getCircleImages(driver.getAvatar()));
        ImageTools.setImageShapeToCircle(avatarImageView);

        avatarImageView.setOnMouseClicked(event -> {
            FXTools.changeScene("driverServicePage.fxml");
        });

        // avatarImageView hover
        avatarImageView.setOnMouseEntered(event -> {
            avatarImageView.getScene().setCursor(javafx.scene.Cursor.HAND);
        });
        avatarImageView.setOnMouseExited(event -> {
            avatarImageView.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
        });

    }


    private void initMenuEvent() {
        item1.setOnMouseClicked(event -> {
            changeMenuStyle(item1);
            DriverMainPageController.driverMainPageInstance.showProfilePage();
        });
        item2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
            FXTools.changeScene("messagePage.fxml");
        });
        item3.setOnMouseClicked(event -> {
            changeMenuStyle(item3);
            DriverMainPageController.driverMainPageInstance.showWorkPlace();
        });
        item4.setOnMouseClicked(event -> {
            changeMenuStyle(item4);
            DriverMainPageController.driverMainPageInstance.showSettingPage();
        });
        item5.setOnMouseClicked(event -> {
            changeMenuStyle(item5);
            StatusContainer.currentUser = null;
            DataTools.logOut();
            FXTools.changeScene("mainPage.fxml");
        });
    }

    private void initIconEvent(){
        imgIcon1.setOnMouseClicked(event -> {
            changeMenuStyle(item1);
            DriverMainPageController.driverMainPageInstance.showProfilePage();
        });
        imgIcon2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
            FXTools.changeScene("messagePage.fxml");
        });
        imgIcon3.setOnMouseClicked(event -> {
            changeMenuStyle(item3);
            DriverMainPageController.driverMainPageInstance.showWorkPlace();
        });
        imgIcon4.setOnMouseClicked(event -> {
            changeMenuStyle(item4);
            DriverMainPageController.driverMainPageInstance.showSettingPage();
        });
        imgIcon5.setOnMouseClicked(event -> {
            changeMenuStyle(item5);
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

        activePane.getStyleClass().add("menuItemActive");
    }

    private void InitDriverInfo() {
        rating.setAlignment(Pos.CENTER);
        exp.setAlignment(Pos.CENTER);
        speed.setAlignment(Pos.CENTER);

        rating.setText("3.0");
        exp.setText("70");
        speed.setText("5.0");

        String sql = "select * from driverInfo where driverID = " + StatusContainer.currentUser.getUserID();
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() != 0) {
            String[] info = result.get(0);
            rating.setText(info[1]);
            exp.setText(info[2]);
            speed.setText(info[3]);
        }

    }
}



