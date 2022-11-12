package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.SelfTestTools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DiagnosticDataController {
    @FXML
    Button okBtn;

    @FXML
    Pane dropPane;

    @FXML
    ScrollPane errorCardsScrollPane;

    Stage currentStage = null;

    @FXML
    public void initialize(){
        dragAndDrop();
        getTestObjs();
        //System.out.println("DiagnosticDataController initialize");
    }

    @FXML
    public void okBtnClick(){
        // close the window
        FXTools.ifShowDiagnosticDataPageOpen = false;
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    private double locationX,locationY;
    @FXML
    void dragAndDrop() {

        dropPane.setOnMousePressed((MouseEvent event) -> {
            if(currentStage == null){
                currentStage = (Stage) dropPane.getScene().getWindow();
            }
            locationX = currentStage.getX() - event.getScreenX();
            locationY = currentStage.getY() - event.getScreenY();
        });

        dropPane.setOnMouseDragged((MouseEvent event) -> {
            currentStage.setX(event.getScreenX() + locationX);
            currentStage.setY(event.getScreenY() + locationY);
        });
    }

    private void getTestObjs(){
        ArrayList<SelfTestTools.Test> testObjsList = SelfTestTools.testList;
        System.out.println("testObjsList size: " + testObjsList.size());
        VBox vBox = new VBox();
        for (SelfTestTools.Test testObj : testObjsList) {
            VBox.setMargin(testObj.vBox, new javafx.geometry.Insets(10, 0, 0, 10));
            if(testObj.isPass){
                testObj.vBox.getStyleClass().add("testSuccessPane");
            }else{
                testObj.vBox.getStyleClass().add("testFailPane");
            }

            vBox.getChildren().add(testObj.vBox);
        }
        // hide scroll bar
        errorCardsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        errorCardsScrollPane.setContent(vBox);
    }
}

// TODO: get user device info
