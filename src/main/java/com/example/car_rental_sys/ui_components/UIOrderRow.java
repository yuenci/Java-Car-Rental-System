package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.controllers.OrderDetailsComponentController;
import com.example.car_rental_sys.controllers.OrderListComponentController;
import com.example.car_rental_sys.orm.Customer;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class UIOrderRow extends Pane {

    private Label lblOrderID, lblName, lblOrderDate, lblOrderAmount, lblOrderStatus;
    private String orderID, name, orderDate, orderAmount;
    private int orderStatus;

    public UIOrderRow(){

    }

    public UIOrderRow(String orderID, String name, String orderDate, int orderAmount, int orderStatus){
        this.orderID = orderID;
        this.name = name;
        this.orderDate = orderDate;
        this.orderAmount = "RM" + orderAmount;
        this.orderStatus = orderStatus;
        init();
    }

    private void init(){
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setPrefSize(460,30);
        initComponent();
        initTheme();
        initEvent();
    }

    private void initComponent(){
        lblOrderID = new Label();
        lblName = new Label();
        lblOrderDate = new Label();
        lblOrderAmount = new Label();
        lblOrderStatus = new Label();

        lblOrderID.setText(orderID);
        lblName.setText(name);
        lblOrderDate.setText(orderDate);
        lblOrderAmount.setText(orderAmount);
        String status,color;
        if(orderStatus == 0){
            status = "Cancelled";
            color = "#F76560";
        }else if(orderStatus == 1){
            status = "In Progress";
            color = "#6AA1FF";
        }else if(orderStatus == 2) {
            status = "Continuing";
            color = "#FF9A2E";
        }else if(orderStatus == 3){
            status = "Completed";
            color = "#4CD263";
        }else{
            status = "Unknown";
            color = "#86909C";
        }
        lblOrderStatus.setText(status);
        lblOrderStatus.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");

        lblOrderID.setLayoutX(11);
        lblName.setLayoutX(105);
        lblOrderDate.setLayoutX(215);
        lblOrderAmount.setLayoutX(305);
        lblOrderStatus.setLayoutX(395);

        lblOrderID.setLayoutY(6);
        lblName.setLayoutY(6);
        lblOrderDate.setLayoutY(6);
        lblOrderAmount.setLayoutY(6);
        lblOrderStatus.setLayoutY(6);

        this.getChildren().addAll(lblOrderID, lblName,lblOrderDate,lblOrderAmount,lblOrderStatus);
    }


    private void initDarkStyle(){
        Label[] labels = {lblOrderID, lblName, lblOrderDate, lblOrderAmount};
        for(Label label : labels){
            label.setStyle("-fx-text-fill: #ababac; -fx-font-weight: bold;");
        }
    }

    private void initLightStyle(){
        Label[] labels = {lblOrderID, lblName,lblOrderDate,lblOrderAmount};
        for(Label label : labels){
            label.setStyle("-fx-text-fill: #86909C; -fx-font-weight: bold;");
        }
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            initDarkStyle();
        }
        else{
            initLightStyle();
        }
    }

    private void initEvent(){
        this.setCursor(Cursor.HAND);
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> OrderDetailsComponentController.instance.updateStatus(1));
    }
}