package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.orm.Order;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.util.Date;

public class OrderCard extends Pane {
    String carModel = "";
    String color = "";
    int status = -1;
    int orderID = -1;
    String darkColor = "#000000";
    String lightColor = "#ffffff";
    Date pickUpDateTime = null;
    Date returnDateTime = null;


    public OrderCard(Order order) {
        initData(order);
        initUI();
    }

    private  void initData(Order order){
        this.carModel = Tools.getCarModelFromCarID(order.carID);
        this.color = Tools.getCarColorFromCarID(order.carID);
        this.status = order.getStatus();
        this.orderID = order.getOrderID();

        String gradientColor = Tools.getGradientColorFromCarID(order.carID);
        assert gradientColor != null;
        this.darkColor = gradientColor.split(",")[0];
        this.lightColor = gradientColor.split(",")[1];

        this.pickUpDateTime = order.getPickUpTime();
        this.returnDateTime = order.getReturnTime();
    }

    private void initUI(){
        initSize();
        initLabels();
        initImageViews();
    }

    private void initSize(){
        this.setPrefHeight(150);
        this.setPrefWidth(270);
        this.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; ");
    }

    private void initLabels(){
        Label carModelLabel = new Label(this.carModel);
        Font font = Font.font("System", FontWeight.BOLD, 20);
        setLabelsStyle(carModelLabel,7,15,22,140,font);


        Label colorLabel = new Label(color);
        setLabelsStyle(colorLabel,14,48);


        Label startDateLabel = new Label(Tools.dateToString(pickUpDateTime,"yyyy-MM-dd"));
        setLabelsStyle(startDateLabel,174,72);


        Label to = new Label("To");
        setLabelsStyle(to,203,94);



        Label endDateLabel = new Label(Tools.dateToString(returnDateTime,"yyyy-MM-dd"));
        setLabelsStyle(endDateLabel,174,113);


        this.getChildren().addAll(carModelLabel,colorLabel,startDateLabel,to,endDateLabel);

    }

    private void setLabelsStyle(Label label, double x, double y){
        label.setLayoutX(x);
        label.setLayoutY(y);
    }

    private void setLabelsStyle(Label label,double x, double y, Font font){
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFont(font);
    }

    private void setLabelsStyle(Label label,double x, double y, double prefHeight, double prefWidth, Font font){
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setPrefHeight(prefHeight);
        label.setPrefWidth(prefWidth);
        label.setFont(font);
    }

    private void initImageViews(){
        String cardImageRoot ="src/main/resources/com/example/car_rental_sys/image/cars/";
        String UIImageRoot ="src/main/resources/com/example/car_rental_sys/image/UI/";
        String carImageAddress = cardImageRoot + this.carModel + ".png";
        ImageView carImageView = getImageView(carImageAddress,22,3,80,60);
        Pane carImagePane = new Pane(carImageView);
        String carStyle = "-fx-background-radius: 10;"+
                "-fx-background-color: " +
                "linear-gradient(to left," + this.darkColor +"," + this.lightColor + ");";
        setPane(carImagePane,7,71,123,60,carStyle);

        String hideImageAddress = UIImageRoot + "hide.png";
        ImageView hideImageView = getImageView(hideImageAddress,7,3,35,35);
        Pane hideImagePane = new Pane(hideImageView);
        String hideStyle = "-fx-background-color: #79809c; -fx-background-radius: 10;";
        setPane(hideImagePane,149,15,50,40,hideStyle);

        String chooseImageAddress = UIImageRoot + "choose.png";
        ImageView chooseImageView = getImageView(chooseImageAddress,10,5,30,30);
        Pane chooseImagePane = new Pane(chooseImageView);
        String chooseStyle = "-fx-background-color: #24a853; -fx-background-radius: 10;";
        setPane(chooseImagePane,206,15,50,40,chooseStyle);

        this.getChildren().addAll(carImagePane,hideImagePane,chooseImagePane);
    }

    private ImageView getImageView(String path,double x,double y,double width, double height){
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        ImageView carImageView = new ImageView(image);
        carImageView.setLayoutX(x);
        carImageView.setLayoutY(y);
        carImageView.setFitWidth(width);
        carImageView.setFitHeight(height);
        return carImageView;
    }

    private void setPane(Pane pane, double x,double y,double width, double height,String style){
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setStyle(style);
    }

    @Override
    public String toString() {
        return  "OrderCard{" +
                "carModel='" + carModel + '\'' +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", orderID=" + orderID +
                ", darkColor='" + darkColor + '\'' +
                ", lightColor='" + lightColor + '\'' +
                ", pickUpDateTime=" + pickUpDateTime +
                ", returnDateTime=" + returnDateTime +
                '}';
    }
}
