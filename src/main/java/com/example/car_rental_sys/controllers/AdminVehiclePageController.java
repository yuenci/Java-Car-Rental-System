package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class AdminVehiclePageController{

    public static AdminVehiclePageController instance;

    public static String defaultDisplay = "overview";

    @FXML
    private Button overviewBtn;
    @FXML
    private Button addVehicleBtn;
    @FXML
    private Button editVehicleBtn;

    @FXML
    private Pane AdVehicleMainPane;

    public AdminVehiclePageController(){
        instance = this;
    }

    @FXML
    private void initialize(){
        overviewBtn.getStyleClass().add("btnFocusStyle");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/OverviewVehiclePage.fxml"));
            AdVehicleMainPane.getChildren().add(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void overviewPageClicked(MouseEvent event) {
        System.out.println(System.currentTimeMillis());
        if(!Objects.equals(defaultDisplay, "editVehicle")){
            headerButtonClickEvent(event);
            defaultDisplay = "overview";
            System.out.println("In: " +System.currentTimeMillis());
            refreshDisplayPane();
        }
    }

    @FXML
    void addVehiclePageClicked(MouseEvent event) {
        //System.out.println(System.currentTimeMillis());
        System.out.println(defaultDisplay);
        if(!Objects.equals(defaultDisplay, "addVehicle")&&!Objects.equals(defaultDisplay, "editVehicle")){
            System.out.println("addVehiclePageClicked");
            addVehicleBtn.setText("+ Add Vehicle");
            headerButtonClickEvent(event);
            defaultDisplay = "addVehicle";
            refreshDisplayPane();
        }
    }

    @FXML
    private void headerButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("btnFocusStyle");
        btn.getStyleClass().remove("controlBtn");
    }

    private void clearFocusStyle(){
        Button[] btns = {overviewBtn,addVehicleBtn,editVehicleBtn};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnFocusStyle");
            btn.getStyleClass().add("controlBtn");
        }
    }

    public void setDefaultFocus(){
        clearFocusStyle();
        overviewBtn.getStyleClass().add("btnFocusStyle");
    }

    public void showEditVehiclePage(){
        defaultDisplay = "editVehicle";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource
                    ("fxml/EditVehiclePage.fxml"));
            AdVehicleMainPane.getChildren().clear();
            clearFocusStyle();
            editVehicleBtn.setVisible(true);
            editVehicleBtn.getStyleClass().add("btnFocusStyle");
            AdVehicleMainPane.getChildren().add(fxmlLoader.load());
            System.out.println(defaultDisplay);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void removeDisplayPane(){
        AdVehicleMainPane.getChildren().clear();
    }

    public void setDefaultDisplay(String defaultDisplay) {
        AdminVehiclePageController.defaultDisplay = defaultDisplay;
    }

    public void refreshDisplayPane() {
        removeDisplayPane();
        editVehicleBtn.setVisible(false);
        Thread thread = new Thread(() -> Platform.runLater(() -> {
            String fxmlName;
            if(defaultDisplay.equals("overview")){
                addVehicleBtn.setText("Add Vehicle");
                fxmlName = "OverviewVehiclePage.fxml";
            }else{
                fxmlName = "EditVehiclePage.fxml";
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxmlName));
                AdVehicleMainPane.getChildren().add(fxmlLoader.load());
                System.out.println("inside: " +System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        thread.start();
    }
}

