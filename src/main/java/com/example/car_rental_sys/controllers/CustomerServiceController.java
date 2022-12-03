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
    private Pane settingMainPanel;
    @FXML
    private Pane hugeContainer;
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
        //rightContainer.getChildren().add(fxmlLoader.load());
        //centerContainer.getChildren().add(fxmlLoader2.load());

        FXMLLoader fxmlLoader3 = new FXMLLoader(Application.class.getResource("fxml/userSettingPage.fxml"));
        hugeContainer.getChildren().add(fxmlLoader3.load());

    }

    private void initFXML(Pane pane, String fxml)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxml));
        pane.getChildren().clear();
        pane.getChildren().add(fxmlLoader.load());
    }

    public void showTrackOrder(){
        try {
            initFXML(rightContainer, trackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeTrackOrder(){
        try {
            rightContainer.getChildren().clear();
            initFXML(rightContainer, orderSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderPage() {
        try {
            initFXML(centerContainer, orderMain);
            initFXML(rightContainer, orderSide);
            hugeContainer.getChildren().clear();
            hugeContainer.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWalletPage() {
        try {
            initFXML(centerContainer, walletMain);
            initFXML(rightContainer, walletSide);
            hugeContainer.getChildren().clear();
            hugeContainer.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSettingPage(){
        try {
            hugeContainer.setVisible(true);
            hugeContainer.getChildren().clear();
            initFXML(hugeContainer, settingMain);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
