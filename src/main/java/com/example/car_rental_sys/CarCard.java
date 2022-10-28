package com.example.car_rental_sys;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class CarCard extends Pane {
    private int cardHeight = 310;
    private int cardWidth = 420;
    private String brandModel;
    private String pasengers;
    private String price;
    private String carType;
    private String gradientDark;
    private String gradientLight;

    public CarCard(String brandModel,String pasengers,String price, String carType,String gradientDark,String gradientLight){
        super();
        this.brandModel = brandModel;
        this.pasengers = pasengers;
        this.price = price;
        this.carType = carType;
        this.gradientDark = gradientDark;
        this.gradientLight = gradientLight;
        initialize();
    }

    private void  initialize() {
        initSize();
        initStyle();
        initCarBrand(this.brandModel);//car brand args
        initParameters(this.pasengers,this.price,this.carType);//car parameters args
        addCarImage(this.brandModel);//car image args
        initHoverEvent();
    }

    private void initSize(){
        this.setPrefHeight(cardHeight);
        this.setPrefWidth(cardWidth);
    }

    private void initStyle(){
        this.setStyle("-fx-background-color: #000; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
//                "-fx-border-color: #E5E5E5; " +
                "-fx-border-width: 1px;" +
                "-fx-padding: 50px;" +
                "-fx-border-insets: 50px;" +
                "-fx-background-insets: 50px;"
        );
    }

    private void initHoverEvent(){
        Pane pane = new Pane();
        pane.setPrefHeight(215);
        pane.setPrefWidth(320);
        pane.setLayoutX(50);
        pane.setLayoutY(48);
        pane.setStyle("-fx-background-color: transparent; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; "
        );


        pane.setOnMouseEntered( event -> setCursor(Cursor.HAND));

        pane.setOnMouseExited( event -> setCursor(Cursor.DEFAULT));
        this.getChildren().add(pane);
    }

    private void initCarBrand(String brand){
//        Label carBrand = new Label("Car Brand XXXXXXXXX");
        Label carBrand = addLabel(brand.replace("_"," "),80,70);
        this.getChildren().add(carBrand);
    }

    private void initParameters(String passgerNum, String priceNum, String carType){
        addImage("passage.png",80,cardHeight-80,20,20);
        addImage("manual.png",140,cardHeight-80,20,20);
        Label passagesNum = addLabel(passgerNum,110,cardHeight-80);
        Label manualOrAuto = addLabel(carType,170,cardHeight-80);
        Label price = addLabel("RM"+ priceNum +"/D",280,cardHeight-80);
        price.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(passagesNum,manualOrAuto,price);

    }

    private void addImage(String imageName, double x, double y, double width, double height){
        ImageView imageview = new ImageView();
        imageview.setLayoutX(x);
        imageview.setLayoutY(y);
        imageview.setFitHeight(height);
        imageview.setFitWidth(width);
        Image image = new Image("file:src/main/resources/com/example/car_rental_sys/image/" +imageName);
        imageview.setImage(image);
        this.getChildren().add(imageview);
    }

    private Label addLabel(String text, double x, double y){
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setStyle("-fx-text-fill: #E5E5E5; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: normal ; ");
        return  label;
    }

    private void addCarImage(String imageName){
        addImage("cars/" + imageName +".png",50,50,320,216);
        this.setStyle(this.getStyle() +
                "-fx-background-color: " +
                "linear-gradient(to left," + this.gradientDark +"," + this.gradientLight+");");
    }

}

