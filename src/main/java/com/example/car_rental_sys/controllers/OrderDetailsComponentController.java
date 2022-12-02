package com.example.car_rental_sys.controllers;
import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.ui_components.InvoiceBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class OrderDetailsComponentController {

    public static OrderDetailsComponentController instance;

    @FXML
    private Button invoiceBtn;

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
    Pane mainPage;

    @FXML
    public void initialize() {
        initBtnEvent();
        initTheme();
    }

    private void  initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            //System.out.println("init theme");
            mainPage.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/orderDetailsDark.css")
                            .toURI().toString());
        }
    }

    private void initBtnEvent() {
        invoiceBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                InvoiceBox invoiceBox = new InvoiceBox();
                invoiceBox.showInvoiceStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setOrderNum(String orderNum) {
        this.orderNum.setText(orderNum);
        System.out.println("setOrd  erNum");
    }

    private void setOrderDate(String orderDate) {
        this.orderDate.setText(orderDate);
        System.out.println("setOrderDate");
    }

    private void setCarName(String carName) {
        this.carName.setText(carName);
        System.out.println("setCarName");
    }


}

