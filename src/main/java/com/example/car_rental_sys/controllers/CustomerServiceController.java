package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CustomerServiceController {

    public static CustomerServiceController instance;

    @FXML
    private Pane rightContainer;
    @FXML
    private Pane centerContainer;

    // history page
    private String orderMain = "showOrderComponent.fxml";
    private String orderSecondary = "OrderDetailsComponent.fxml";

    // wallet page
    private String billMain = "BillingComponent.fxml";

    // setting page

    public CustomerServiceController() {
        instance = this;
    }

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/OrderDetailsComponent.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("fxml/showOrderComponent.fxml"));
        rightContainer.getChildren().add(fxmlLoader.load());
        centerContainer.getChildren().add(fxmlLoader2.load());
    }

    public void showOrderPage() throws IOException {
        changePage(new String[]{"fxml/showOrderComponent.fxml"});
    }

    private void changePage(String[] fxmlList) throws IOException {
        centerContainer.getChildren().clear();
        rightContainer.getChildren().clear();

    }
}
