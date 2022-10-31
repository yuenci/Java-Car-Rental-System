package com.example.car_rental_sys;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
        addClickEvent();
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
        addImage("passage.png",80,cardHeight-80,20,20,"UI");
        addImage("manual.png",140,cardHeight-80,20,20,"UI");
        Label passagesNum = addLabel(passgerNum,110,cardHeight-80);
        Label manualOrAuto = addLabel(carType,170,cardHeight-80);
        Label price = addLabel("RM"+ priceNum +"/D",280,cardHeight-80);
        price.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(passagesNum,manualOrAuto,price);

    }

    private void addImage(String imageName, double x, double y, double width, double height,String type){
        ImageView imageview = new ImageView();
        imageview.setLayoutX(x);
        imageview.setLayoutY(y);
        imageview.setFitHeight(height);
        imageview.setFitWidth(width);
        if (type.equalsIgnoreCase("car")){
            imageview.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/"+imageName));
        }
        else if (type.equalsIgnoreCase("UI")){
            imageview.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/"+imageName));
        }
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
        addImage("cars/" + imageName +".png",50,50,320,216,"car");
        this.setStyle(this.getStyle() +
                "-fx-background-color: " +
                "linear-gradient(to left," + this.gradientDark +"," + this.gradientLight+");");
    }

    private void addClickEvent(){
        this.setOnMouseClicked(event -> {
            System.out.println("clicked on " + brandModel);
            StatusContainer.currentCarChose = this.brandModel;
            if (StatusContainer.isLogin) {
                goToDetailsPage();

            } else {
                //System.out.println("Please login first");
                new Tools().reSetScence("loginPage.fxml");
            }
        });
    }

    private void goToDetailsPage(){
        new Tools().reSetScence("carDetailsPage.fxml");
    }
}

