package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

public class BillingSideBarController {

    @FXML
    private Label billSideText;
    @FXML
    private WebView webViewMiddle;
    @FXML
    private WebView webViewBottom;

    @FXML
    public void initialize() {
        billSideText.setText("Billing Side");  // monthly speeding amount
    }
}
