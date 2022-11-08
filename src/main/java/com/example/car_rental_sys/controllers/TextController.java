package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.ui_components.OrderCard;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class TextController {
    @FXML
    Pane mainPane;

    @FXML
    public void initialize() {
        OrderCard orderCard = new OrderCard(new Order(1));
        mainPane.getChildren().add(orderCard);
    }
}
