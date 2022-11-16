package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ui_components.SwitchButton;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class SettingPageController {

    @FXML
    private Pane switchPaneOne;

    @FXML
    private Pane switchPaneTwo;

    @FXML
    private Pane switchPaneThree;

    @FXML
    private void initialize(){
        Pane[] panes = {switchPaneOne, switchPaneTwo, switchPaneThree};
        for (Pane pane : panes) {
            pane.getChildren().add(new SwitchButton());
        }
    }
}
