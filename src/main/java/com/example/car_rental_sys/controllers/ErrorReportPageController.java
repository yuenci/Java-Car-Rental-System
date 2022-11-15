package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
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

import java.io.IOException;


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

    private Stage currentStage = null;

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

    private void initRadioButton(){
        ToggleGroup group = new ToggleGroup();
        radioBtn1.setToggleGroup(group);
        radioBtn2.setToggleGroup(group);
        // set default selected
        radioBtn1.setSelected(true);
    }

    @FXML
    private void diagnosticLabelClick(){
        //
        //System.out.println( "diagnosticLabelClick");
        try {
            FXTools.showDiagnosticDataPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
