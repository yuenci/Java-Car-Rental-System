package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdminServiceController {

    public static AdminServiceController instance;

    //Setting Page
    private final String settingMain = "SettingPage.fxml";
    private final String accountSecurity = "AccountSecurityPage.fxml";
    private final String passwordPage = "PasswordPage.fxml";
    private final String personalInfo = "PersonalInfoPage.fxml";
    private final String settingMiddleBar = "MiddleBar.fxml";
    private final String infoMiddleBar = "InfoMiddleBar.fxml";

    //Dashboard Page

    //Car Page
    private final String adminVehicle = "AdminVehiclePage.fxml";

    //History Page
    private final String orderMain = "showOrderComponent.fxml";
    private final String orderSide = "OrderDetailsComponent.fxml";
    private final String trackPane = "TrackOrderComponent.fxml";

    @FXML
    private Pane centerContainer,rightContainer,hugeContainer,middlePanel,settingMainPanel, dashboardContainer,toRightPane;

    @FXML
    ImageView toRightBtn;

    private boolean ifDashboard = true;

    @FXML
    public void initialize() {
        //System.out.println("AdminServiceController");
        initToRightBtnEvent();
    }

    private void initToRightBtnEvent(){



        toRightBtn.setOnMouseClicked(event -> {
            if(ifDashboard){
                dashboardController.instance.changePage("income");
                ifDashboard = false;
            }else {
                dashboardController.instance.changePage("dashboard");
                ifDashboard = true;
            }
        });

        // set hover effect
        toRightBtn.setOnMouseEntered(event -> {
            toRightPane.setStyle("-fx-background-color: #e7e7e7;-fx-background-radius: 5px;");
            //hand
            toRightPane.setCursor(Cursor.HAND);
        });

        toRightBtn.setOnMouseExited(event -> {
            toRightPane.setStyle("-fx-background-color: #fafafa;");
            toRightPane.setCursor(Cursor.DEFAULT);
        });
    }

    public AdminServiceController() {
        instance = this;
    }

    private void initFXML(Pane pane, String fxml)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxml));
        pane.getChildren().clear();
        pane.getChildren().add(fxmlLoader.load());
    }

    private void initHugeContainer(){
        hugeContainer.getChildren().clear();
        hugeContainer.setVisible(true);
        hugeContainer.getChildren().addAll(middlePanel, settingMainPanel);
    }

    public void showSettingPage(){
        try {
            initHugeContainer();
            dashboardContainer.setVisible(false);
            System.out.println("showSettingPage");
            initFXML(middlePanel, settingMiddleBar);
            initFXML(settingMainPanel, personalInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showVehiclePage(){
        try {
            dashboardContainer.setVisible(false);
            hugeContainer.getChildren().clear();
            hugeContainer.setVisible(true);
            initFXML(hugeContainer, adminVehicle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderPage() {
        try {
            dashboardContainer.setVisible(false);
            initFXML(centerContainer, orderMain);
            initFXML(rightContainer, orderSide);
            hugeContainer.getChildren().clear();
            hugeContainer.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTrackOrder(){
        try {
            initFXML(rightContainer, trackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboardPage(){
       dashboardContainer.setVisible(true);
    }

    public void showProfilePage(){
        try {
            initHugeContainer();
            dashboardContainer.setVisible(false);
            middlePanel.getChildren().clear();
            settingMainPanel.getChildren().clear();
            initFXML(middlePanel, infoMiddleBar);
            initFXML(settingMainPanel, personalInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAccSecurity(){
        try {
            settingMainPanel.getChildren().clear();
            initFXML(settingMainPanel, accountSecurity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPasswordPage(){
        try {
            settingMainPanel.getChildren().clear();
            initFXML(settingMainPanel, passwordPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeTrackOrder() {
        try {
            rightContainer.getChildren().clear();
            initFXML(rightContainer, orderSide);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeIcon(String type){
        if(type.equals("left")){
            toRightBtn.setImage(ImageTools.getUIImageObjFromName("left.png"));
        }else if (type.equals("right")){
            toRightBtn.setImage(ImageTools.getUIImageObjFromName("right.png"));
        }

    }
}
