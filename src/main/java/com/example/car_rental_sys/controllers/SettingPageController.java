package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.ui_components.SwitchButton;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.File;

public class SettingPageController {

    @FXML
    private Pane settingPane;
    @FXML
    private Pane switchPaneOne;

    @FXML
    private Pane switchPaneTwo;

    @FXML
    private Pane switchPaneThree;

    @FXML
    private void initialize(){
        initTheme();
        Pane[] panes = {switchPaneOne, switchPaneTwo, switchPaneThree};
        for (Pane pane : panes) {
            pane.getChildren().add(new SwitchButton());
        }
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            settingPane.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/settingComponentDark.css")
                            .toURI().toString());
        }
    }
}
