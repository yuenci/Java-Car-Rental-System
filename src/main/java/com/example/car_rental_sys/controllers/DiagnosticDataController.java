package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
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

    @FXML
    public void initialize(){
        dragAndDrop();
        getTestObjs();
        //System.out.println("DiagnosticDataController initialize");
    }

    @FXML
    public void okBtnClick(){
        System.out.println("okBtnClick");
    }

    private double locationX,locationY;
    @FXML
    void dragAndDrop() {
        Stage stage = StatusContainer.currentStage;
        dropPane.setOnMousePressed((MouseEvent event) -> {
            locationX = stage.getX() - event.getScreenX();
            locationY = stage.getY() - event.getScreenY();
        });

        dropPane.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() + locationX);
            stage.setY(event.getScreenY() + locationY);
        });
    }

    private void getTestObjs(){
        ArrayList<SelfTestTools.Test> testObjsList = SelfTestTools.testList;
        System.out.println("testObjsList size: " + testObjsList.size());
        VBox vBox = new VBox();
        for (SelfTestTools.Test testObj : testObjsList) {
            VBox.setMargin(testObj.pane, new javafx.geometry.Insets(10, 0, 0, 10));
            if(testObj.isPass){
                testObj.pane.getStyleClass().add("testSuccessPane");
            }else{
                testObj.pane.getStyleClass().add("testFailPane");
            }

            vBox.getChildren().add(testObj.pane);
        }
        errorCardsScrollPane.setContent(vBox);
    }
}
