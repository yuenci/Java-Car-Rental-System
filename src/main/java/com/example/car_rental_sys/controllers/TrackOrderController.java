package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
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
    private User user = StatusContainer.currentUser;
    private int status = order.getStatus();

    @FXML
    private ImageView leftIcon, locationIcon;
    @FXML
    private Pane mapTracking, panelTracking;
    @FXML
    private Button btnReceive;
    @FXML
    private Label lblOrderID, lblLocation, lblPickupTime, lblPickupDate;

    @FXML
    private void initialize() {
        initTheme();
        initStatus();
        initLabel();
        initMap();

        if(user instanceof Admin){
            btnReceive.setVisible(false);
        }
    }

    @FXML
    private void leftIconClicked() {
        if(user instanceof Customer){
            CustomerServiceController.instance.showOrderPage();
        }else{
            AdminServiceController.instance.showOrderPage();
        }
    }


    public TrackOrderController() {
        instance = this;
    }

    private void initStatus(){
        this.status = order.getStatus();
        if(status == 1){
            btnReceive.setVisible(false);
        }
        if(status == 3 || status == 4){
            btnReceive.setText("Complete My Order");
            btnReceive.setStyle(btnReceive.getStyle() + "-fx-background-color: #3c7eff; -fx-cursor: hand;");
            DataTools.updateOrderStatusWithID(order.getOrderID(), 3);
            order.setStatus(3);
            btnReceive.setOnMouseClicked(mouseEvent -> {
                showNotification();
                Platform.runLater(() -> CustomerServiceController.instance.closeTrackOrder());
            });
        }else if(status == 5){
            completeOder();
        }
    }

    private void completeOder(){
        btnReceive.setText("Order Completed");
        btnReceive.setStyle(btnReceive.getStyle() + "-fx-background-color: #27c346;");
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

    private void showNotification(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM, "Do you want to complete this order?", "Complete Order");
        messageFrame.setSuccessCallbackFunc((i) -> {
            DataTools.updateOrderStatusWithID(order.getOrderID(), 5);
            OrderListComponentController.instance.getDataArray();
            OrderListComponentController.instance.refreshTable(1);
            return null;
        });

        messageFrame.setFailedCallbackFunc((i) -> {
            messageFrame.close();
            return null;
        });
        messageFrame.show();
    }
}