package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class InvoiceController {

    //for the invoice, need to add a stage to put it on

    public static InvoiceController instance;
    private String billingLineOne;
    private String billingLineTwo;
    private String carName;
    private int orderID;
    private String paymentDate;
    private String totalPrice;
    private String quantity;


    @FXML
    private Label ivAddressTwo;

    @FXML
    private Label ivQuantity;

    @FXML
    private Label ivSubTotal;

    @FXML
    private Label ivPaymentDate;

    @FXML
    private Label ivUnitPrice;

    @FXML
    private Label ivAddressOne;

    @FXML
    private Pane ivQrCodePane;

    @FXML
    private Label ivPrice;

    @FXML
    private Label ivTotalPrice;

    @FXML
    private Label ivUserName;

    @FXML
    private Label ivOrderID;

    @FXML
    private Label ivCarName;

    public InvoiceController() {
        instance = this;
    }

    private void initialize(){
        setLabelText();
    }

    private void getLabelText(){
        //String query = "SELECT * FROM orders WHERE order_id = " + orderID + " AND user_id = " + LoginController.instance.getUserID();
    }

    private void setLabelText(){
        ivAddressOne.setText("Address One");
        ivAddressTwo.setText("Address Two");
        ivCarName.setText("Car Name");
        ivOrderID.setText("Order ID");
        ivPaymentDate.setText("Payment Date");
        ivPrice.setText("Price");
        ivQuantity.setText("Quantity");
        ivSubTotal.setText("Sub Total");
        ivTotalPrice.setText("Total Price");
        ivUnitPrice.setText("Unit Price");
        ivUserName.setText("User Name");
    }

    private void initQrCode() {

    }

    public void ivDownloadClicked(MouseEvent mouseEvent) {
        //download function
    }
}
