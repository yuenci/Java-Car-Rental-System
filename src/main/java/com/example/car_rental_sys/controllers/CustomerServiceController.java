package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ui_components.UIEmptyOrderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CustomerServiceController extends Controller{

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
    private final String settingMiddleBar = "MiddleBar.fxml";
    private final String infoMiddleBar = "InfoMiddleBar.fxml";

    @FXML
    Pane mainPane;
    @FXML
    private Pane middlePanel;
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

        FXMLLoader fxmlLoader3 = new FXMLLoader(Application.class.getResource("fxml/infoMiddleBar.fxml"));
        FXMLLoader fxmlLoader4 = new FXMLLoader(Application.class.getResource("fxml/PersonalInfoPage.fxml"));
        //hugeContainer.setVisible(true);
        //middlePanel.getChildren().add(fxmlLoader3.load());
        //settingMainPanel.getChildren().add(fxmlLoader4.load());

        //showWalletPage();
        StatusContainer.currentPageController = this;

        showProfilePage();
        //showOrderPage();

        DataTools.generateWalletLineData();
    }

    private void initHugeContainer(){
        hugeContainer.getChildren().clear();
        hugeContainer.setVisible(true);
        hugeContainer.getChildren().addAll(middlePanel, settingMainPanel);
    }

    public void showTrackOrder(){
        try {
            FXTools.initFXML(rightContainer, trackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeTrackOrder(){
        try {
            FXTools.initFXML(rightContainer, orderSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderPage() {
        try {
            StatusContainer.paginationUsage = "order";
            rightContainer.getChildren().clear();
            FXTools.initFXML(centerContainer, orderMain);
            rightContainer.getChildren().add(new UIEmptyOrderPane("Select any order to view more details"));
            hugeContainer.getChildren().clear();
            hugeContainer.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderDetails(){
        try {
            FXTools.initFXML(rightContainer, orderSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWalletPage() {
        try {
            StatusContainer.paginationUsage = "wallet";
            FXTools.initFXML(centerContainer, walletMain);
            FXTools.initFXML(rightContainer, walletSide);
            hugeContainer.getChildren().clear();
            hugeContainer.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSettingPage(){
        try {
            initHugeContainer();
            FXTools.initFXML(middlePanel, settingMiddleBar);
            FXTools.initFXML(settingMainPanel, settingMain);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProfilePage(){
        try {
            initHugeContainer();
            FXTools.initFXML(middlePanel, infoMiddleBar);
            FXTools.initFXML(settingMainPanel, personalInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAccSecurity(){
        try {
            FXTools.initFXML(settingMainPanel, accountSecurity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPasswordPage(){
        try {
            FXTools.initFXML(settingMainPanel, passwordPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
