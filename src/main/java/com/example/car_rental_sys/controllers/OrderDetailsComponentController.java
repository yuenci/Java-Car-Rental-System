package com.example.car_rental_sys.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class OrderDetailsComponentController {

    public static OrderDetailsComponentController orderDetailsComponentController;



    @FXML
    private Label orderNum;


    @FXML
    private Label orderDate;
    @FXML
    private Label carName;
    @FXML
    private Label orderQty;
    @FXML
    private Label carPrice;
    @FXML
    private Label paymentType;

    @FXML
    private Label lblLocation;

    @FXML
    private Label orderTime;

    @FXML
    private Label approveTime;

    @FXML
    private Label paymentTime;
    @FXML
    private Label dueTime;
    @FXML
    private Label pickUpTime;
    @FXML
    private Label totalAmount;



    @FXML
    private ImageView paymentLogo;
    @FXML
    private ImageView carImg;

    @FXML
    public void initialize() {
        System.out.println("OrderDetailsComponentController");
    }

    private void setOrderNum(String orderNum) {
        this.orderNum.setText(orderNum);
        System.out.println("setOrd  erNum");
    }

    private void setOrderDate(String orderDate) {
        this.orderDate.setText(orderDate);
        System.out.println("setOrderDate");
    }
}

