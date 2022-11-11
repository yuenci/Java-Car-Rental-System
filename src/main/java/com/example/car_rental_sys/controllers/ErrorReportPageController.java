package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.PlatformTools;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ErrorReportPageController {
    @FXML
    Button cancelBtn,okBtn;

    @FXML
    Label tipLabel,diagnosticLabel;

    @FXML
    ImageView screenShotImage;

    @FXML
    Pane dropPane,mainPane;

    @FXML
    RadioButton radioBtn1, radioBtn2;

    @FXML
    private void  initialize(){
        Image screenShot = PlatformTools.getScreenShotImageObj();
        screenShotImage.setImage(screenShot);
        dragAndDrop();
        initRadioButton();
    }

    @FXML
    public void cancelBtnClick(){

        System.exit(0);
    }

    @FXML
    public void okBtnClick(){
        tipLabel.setText("Thank your for sharing!");
        tipLabel.setStyle("-fx-side: 20px");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> System.exit(0));
        pause.play();

    }

    @FXML
    public void screenShotImageClick(){
        new Thread(PlatformTools::openScreenShotWithMsPaint).start();
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

    private void initRadioButton(){
        ToggleGroup group = new ToggleGroup();
        radioBtn1.setToggleGroup(group);
        radioBtn2.setToggleGroup(group);
    }

    @FXML
    private void diagnosticLabelClick(){
        //
        System.out.println( "diagnosticLabelClick");
    }
}
