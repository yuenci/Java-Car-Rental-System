package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Tools;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


import java.io.File;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class DrivingModeController {
    @FXML
    Pane sideIconLogo, sideIconHome, sideIconVolume, sideIconPhone, sideIconSetting, browserContainer, distanceDataPane;

    @FXML
    Label distanceLeftLabel, timeLeftLabel;

    @FXML
    private void initialize() {
        initSideIconsEvent();
        initBorwserEvent();
    }

    private void initSideIconsEvent() {
        sideIconLogo.setOnMouseClicked(event -> {
            setIconStyle(sideIconLogo);
            backToMainPage();
        });

        sideIconHome.setOnMouseClicked(event -> {
            setIconStyle(sideIconHome);
            backToDriverMainPage();
        });
        sideIconVolume.setOnMouseClicked(event -> {
            setIconStyle(sideIconVolume);
            setVolume();
        });
        sideIconPhone.setOnMouseClicked(event -> {
            phoneIconClicked();
            setIconStyle(sideIconPhone);
        });
        sideIconSetting.setOnMouseClicked(event -> {
            setIconStyle(sideIconSetting);
            backToSettingPage() ;
        });
    }

    private void setIconStyle(Pane pane) {
        clearAllSideIconsStyle();
        pane.getStyleClass().add("sidebarIconActive");
    }

    private void clearAllSideIconsStyle() {
        Pane[] sideIcons = new Pane[]{sideIconLogo, sideIconHome, sideIconVolume, sideIconPhone, sideIconSetting};
        for (Pane sideIcon : sideIcons) {
            sideIcon.getStyleClass().remove("sidebarIconActive");
        }
    }

    private void initBorwserEvent() {
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

        Browser browser = engine.newBrowser();
        //browser.navigation().loadUrl("https://www.google.com/maps");
        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/directionsDark.html").getAbsolutePath());

        BrowserView view = BrowserView.newInstance(browser);
        view.setPrefSize(300, 490);
        //view.toBack();

        browserContainer.getChildren().add(view);

        //distanceDataPane.toFront();

        //execute JavaScript code in the context of the loaded web page
        String origin = "'Asia Pacific University of Technology & Innovation'";
        String destination = "'endah regal condo'";

        String jsArgs = "getDirections(" + origin + "," + destination + ")";
        System.out.println(jsArgs);
        Frame frame = browser.frames().get(0);
        frame.executeJavaScript(jsArgs);

        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            String message = consoleMessage.message();
            System.out.println(message);
            Platform.runLater(() -> updateDistanceData(message));
        });
    }

    private void updateDistanceData(String distanceAndTime) {
        String[] distanceAndTimeArray = distanceAndTime.split(",");
        String distance = distanceAndTimeArray[0];
        String time = distanceAndTimeArray[1];

        distanceLeftLabel.setText(distance);
        timeLeftLabel.setText(time);

    }

    @FXML
    private void userAvatarClicked() {
        System.out.println("show user profile");
    }

    @FXML
    private void backToDriverMainPage() {
        System.out.println("backToDriverMainPage");
        Tools.changeScene("driverMainPage.fxml");
    }

    @FXML
    private void backToMainPage() {
        System.out.println("backToMainPage");
        Tools.changeScene("mainPage.fxml");
    }

    @FXML
    private void backToSettingPage() {
        System.out.println("sbackToSettingPage");
        System.out.println("back to setting page");
    }

    @FXML
    private void phoneIconClicked() {
        System.out.println("phoneIconClicked");
        Tools.callWhatsApp("601110715226");
    }

    private void setVolume(){
        System.out.println("setVolume");
    }
}

// TODO: close the browser engine when leave the page