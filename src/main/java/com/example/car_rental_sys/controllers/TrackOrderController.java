package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import java.io.File;

public class TrackOrderController {

    public static TrackOrderController instance;
    private Order order = StatusContainer.currentOrder;
    private int status = order.getStatus();

    @FXML
    private ImageView leftIcon;

    @FXML
    private Pane panelTracking;

    @FXML
    private ImageView locationIcon;
    @FXML
    private Pane mapTracking;
    @FXML
    private Button btnReceive;
    @FXML
    private Label lblOrderID, lblLocation, lblPickupTime, lblPickupDate;

    @FXML
    private void initialize() {
        initTheme();
        initLabel();
        initMap();
    }

    @FXML
    private void leftIconClicked() {
        if(StatusContainer.currentUser instanceof Customer){
            CustomerServiceController.instance.showOrderPage();
        }else{
            AdminServiceController.instance.showOrderPage();
        }
    }


    public TrackOrderController() {
        instance = this;
    }

    public void setStatus(int status){
        this.status = status;
        if(status == 5 && StatusContainer.currentUser instanceof Customer){
            btnReceive.setDisable(true);
            btnReceive.setStyle(btnReceive.getStyle() + "-fx-background-color: #165dff; -fx-cursor: hand;");
            //add action to button
            btnReceive.setOnMouseClicked(mouseEvent -> {
                //message box congrats
                CustomerServiceController.instance.closeTrackOrder();
            });
        }
    }

    public int getStatus(){
        return status;
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelTracking.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/trackingComponentDark.css")
                            .toURI().toString());
            locationIcon.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/locationWhite.png"));
            leftIcon.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/leftWhite.png"));
        }
    }

    //get from ORM's Order
    private void initLabel(){
        lblOrderID.setText("ID " + DataTools.getOrderIDStr(order.getOrderID()));
        lblLocation.setText(order.getPickUpLocation());
        lblPickupDate.setText(DateTools.dateToString(order.getPickUpTime(),"dd/MM/yyyy"));
        lblPickupTime.setText(DateTools.dateToString(order.getOrderTime(),"HH:mm") + " - " +
                DateTools.dateToString(order.getReturnTime(),"HH:mm"));
    }

    private  void initMap(){
        mapTracking.getChildren().add(FXTools.getMapComponent());
    }
}