package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
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
    private int status = 5;

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
    private Label lblOrderID;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblPickupTime;
    @FXML
    private Label lblPickupDate;

    @FXML
    private void initialize() {
        initTheme();
        initLabel();
    }

    @FXML
    private void leftIconClicked() {
        CustomerServiceController.instance.closeTrackOrder();
    }


    public TrackOrderController() {
        instance = this;
    }

    public void setStatus(int status){
        this.status = status;
        if(status == 5){
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
        lblOrderID.setText("");
        lblLocation.setText("");
        lblPickupDate.setText("");
        lblPickupTime.setText("");
    }
}