package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class TrackingStatusController {
    @FXML
    private Pane panelStatus;
    @FXML
    private Label lblStatusText1, lblStatusText2, lblStatusText3, lblStatusText4;
    @FXML
    private Pane dotStatus1, dotStatus2, dotStatus3, dotStatus4;
    @FXML
    private Pane pane1, pane2, pane3, pane4;
    @FXML
    private ImageView imgView1, imgView2, imgView3, imgView4;

    private Order order = StatusContainer.currentOrder;


    @FXML
    private void initialize(){
        initTheme();
        initStyle();
    }

    private Pane[] dotsVisible;

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelStatus.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/trackingComponentDark.css")
                            .toURI().toString());
        }
    }

    private void initStyle(){
        int status = TrackOrderController.instance.getStatus();
        if(status == 1 || status == 0){
            dotsVisible = new Pane[]{dotStatus2, dotStatus3, dotStatus4};
            setDotVisible();
            setFocusStatus(pane1);
            setActivateImage(imgView1);
            updateTextStyle(lblStatusText1);
        }else if(status == 2){
            dotsVisible = new Pane[]{dotStatus3, dotStatus4};
            setDotVisible();
            setFocusStatus(pane2);
            setActivateImage(imgView2);
            updateTextStyle(lblStatusText2);
            setInactiveImage(new ImageView[]{imgView1});
            setInactivateStatus(new Pane[]{pane1});
        }else if(status == 3 || status == 4){
            dotsVisible = new Pane[]{dotStatus4};
            setDotVisible();
            setFocusStatus(pane3);
            setActivateImage(imgView3);
            updateTextStyle(lblStatusText3);
            setInactiveImage(new ImageView[]{imgView1, imgView2});
            setInactivateStatus(new Pane[]{pane1, pane2});
        }else if(status == 5){
            dotsVisible = new Pane[]{};
            setDotVisible();
            setFocusStatus(pane4);
            setActivateImage(imgView4);
            updateTextStyle(lblStatusText4);
            setInactiveImage(new ImageView[]{imgView1, imgView2, imgView3});
            setInactivateStatus(new Pane[]{pane1, pane2, pane3});
        }else{
            dotsVisible = new Pane[]{dotStatus1,dotStatus2, dotStatus3, dotStatus4};
            setDotVisible();
        }
    }

    private void updateTextStyle(Label labelStatus){
        Label[] labels = new Label[]{lblStatusText1, lblStatusText2, lblStatusText3, lblStatusText4};
        for (Label label : labels) {
            label.getStyleClass().remove("lblFocusText");
            label.setLayoutX(70);
        }

        labelStatus.getStyleClass().add("lblFocusText");
        labelStatus.setLayoutX(60);
        labelStatus.setLayoutY(labelStatus.getLayoutY() - 5);
    }

    private void setDotVisible(){
        Pane[] dotStatus = {dotStatus1, dotStatus2, dotStatus3, dotStatus4};
        for (Pane dots : dotStatus) {
            dots.setVisible(false);
        }
        if(dotsVisible != null){
            for(Pane pane : dotsVisible){
                pane.setVisible(true);
            }
        }
    }

    private void setFocusStatus(Pane pane){
        Pane[] panes = {pane1, pane2, pane3, pane4};
        for(Pane pane1 : panes){
            pane1.getStyleClass().remove("pane-active");
        }

        if(pane != null){
            pane.getStyleClass().add("pane-active");
        }
    }

    private void setInactivateStatus(Pane[] panes){
        for (Pane pane : panes) {
            pane.getStyleClass().remove("pane-active");
            pane.getStyleClass().add("pane-inactive");
        }
    }

    private void setActivateImage(ImageView imageView){
        if (imageView != null) {
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/checkWhite.png"));
        }
    }

    private void setInactiveImage(ImageView[] imageViews){
        for (ImageView imageView : imageViews) {
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/checkGrey.png"));
        }
    }
}
