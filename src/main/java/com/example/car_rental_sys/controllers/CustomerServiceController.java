package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CustomerServiceController {

    @FXML
    private Pane rightContainer;
    @FXML
    private Pane centerContainer;

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/OrderDetailsComponent.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("fxml/showOrderComponent.fxml"));
        rightContainer.getChildren().add(fxmlLoader.load());
        centerContainer.getChildren().add(fxmlLoader2.load());
    }
}
