package com.example.car_rental_sys.ui_components;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class UICusBillRow extends Pane {

    private String status;
    private String amount;
    private String time;
    private String date;
    private String type;

    private Label billStatus, billAmount, billTime, billDate, billType;

    public UICusBillRow(){
        //do nothing
        initStyle();
        initComponents();
    }

    public UICusBillRow(String type, String date, String time, String amount, String status){
        this.type = type;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.status = status;
        initStyle();
        initComponents();
    }

    private void initStyle(){
        this.setPrefHeight(35);
        this.setPrefWidth(470);
        this.setStyle("-fx-background-color: #202020;");
    }

    private void initComponents(){

        Pane imgPane = new Pane();
        imgPane.setPrefSize(24,24);
        imgPane.setLayoutX(15);
        imgPane.setLayoutY(6);
        imgPane.setStyle("-fx-border-radius: 4; -fx-border-color: GREY;");

        ImageView imageView = new ImageView();
        imageView.setFitHeight(18);
        imageView.setFitWidth(18);
        imageView.setLayoutX(3);
        imageView.setLayoutY(3);
        if(Objects.equals(type, "rental")){
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/downArrow.png"));
        }else{
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/upArrow.png"));
        }

        imgPane.getChildren().add(imageView);

        String typeText = "";
        String amountText = "";
        if(Objects.equals(type, "rental")){
            typeText = "Rental Fee";
            amountText = "-RM" + amount;
        }
        else if (Objects.equals(type, "top-up")){
            typeText = "Top-up wallet";
            amountText = "+RM" + amount;
        }

        billType = new Label(typeText);
        billType.setLayoutX(52);
        billType.setLayoutY(10);
        billType.setStyle("-fx-font-size: 12px; -fx-text-fill: #2971cf; -fx-font-weight: bold;");

        billDate = new Label(this.date);
        billDate.setLayoutX(151);
        billDate.setLayoutY(10);
        billDate.setStyle("-fx-font-size: 12px; -fx-text-fill: GREY; -fx-font-weight: bold;");

        billTime = new Label(this.time);
        billTime.setLayoutX(248);
        billTime.setLayoutY(10);
        billTime.setStyle("-fx-font-size: 12px; -fx-text-fill: GREY; -fx-font-weight: bold;");

        billAmount = new Label(amountText);
        billAmount.setLayoutX(321);
        billAmount.setLayoutY(10);
        billAmount.setStyle("-fx-font-size: 12px; -fx-text-fill: #e0e0e0; -fx-font-weight: bold;");

        String textColor;
        String textStatus;
        if(Objects.equals(status, "1")){
            textColor = "#1db440";
            textStatus = "Completed";
        }
        else if(Objects.equals(status, "2")){
            textColor = "#f54e4e";
            textStatus = "Canceled";
        }else{
            textColor = "#ff8d1f";
            textStatus = "Pending";
        }
        billStatus = new Label(textStatus);
        billStatus.setLayoutX(397);
        billStatus.setLayoutY(10);
        billStatus.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill:" + textColor + ";");

        this.getChildren().addAll(imgPane, billType, billDate, billTime, billAmount, billStatus);
    }

}
