package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class UserSettingPageController {

    // Setting Page
    private final String settingMain = "SettingPage.fxml";
    private final String accountSecurity = "AccountSecurityPage.fxml";
    private final String passwordPage = "PasswordPage.fxml";
    private final String personalInfo = "PersonalInfoPage.fxml";

    @FXML
    public Pane settingMainPanel;

    public static UserSettingPageController instance;

    public UserSettingPageController() {
        instance = this;
    }

    @FXML
    private void initialize() throws IOException {
        initPane(settingMain);
    }

    public void showAccSecurity() throws IOException {
        initPane(accountSecurity);
    }

    public void showSetting() throws IOException {
        initPane(settingMain);
    }

    public void showPassword() throws IOException {
        initPane(passwordPage);
    }

    public void showPersonalInfo() throws IOException {
        initPane(personalInfo);
    }

    private void initPane(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxml));
        settingMainPanel.getChildren().clear();
        settingMainPanel.getChildren().add(fxmlLoader.load());
    }
}
