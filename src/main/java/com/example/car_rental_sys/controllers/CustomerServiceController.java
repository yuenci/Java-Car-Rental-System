package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CustomerServiceController {

    public static CustomerServiceController instance;

    // History Page
    private final String orderMain = "showOrderComponent.fxml";
    private final String orderSide = "OrderDetailsComponent.fxml";
    private final String trackPane = "TrackOrderComponent.fxml";

    //Wallet Page
    private final String walletMain = "BillingComponent.fxml";
    private final String walletSide = "BillingSideBar.fxml";

    // Setting Page
    private final String settingMain = "SettingPage.fxml";
    private final String accountSecurity = "AccountSecurityPage.fxml";
    private final String passwordPage = "PasswordPage.fxml";
    private final String personalInfo = "PersonalInfoPage.fxml";
    private final String middleBar = "MiddleBar.fxml";

    @FXML
    private Pane rightContainer;
    @FXML
    private Pane centerContainer;

    public CustomerServiceController() {
        instance = this;
    }

    public void initialize() throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/OrderDetailsComponent.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/BillingSideBar.fxml"));

        //FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("fxml/showOrderComponent.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("fxml/BillingComponent.fxml"));
        rightContainer.getChildren().add(fxmlLoader.load());
        centerContainer.getChildren().add(fxmlLoader2.load());
    }

    private void renewCenterPane(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxml));
        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(fxmlLoader.load());
    }

    private void renewRightPane(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxml));
        rightContainer.getChildren().clear();
        rightContainer.getChildren().add(fxmlLoader.load());
    }

    public void showTrackOrder(){
        try {
            renewRightPane(trackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeTrackOrder(){
        try {
            rightContainer.getChildren().clear();
            renewRightPane(orderSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderPage() {
        try {
            renewCenterPane(orderMain);
            renewRightPane(orderSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWalletPage() {
        try {
            renewCenterPane(walletMain);
            renewRightPane(walletSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
