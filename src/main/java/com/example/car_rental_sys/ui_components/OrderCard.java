package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.controllers.DriverMainPageController;
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
    public String carModel = "";
    public String carNumber = "";
    int status = -1;
    public int orderID = -1;
    public int userID = -1;
    String darkColor = "#000000";
    String lightColor = "#ffffff";
    Date pickUpDateTime = null;
    Date returnDateTime = null;

    public String pickUpLocation = "";
    public String parkingLocation = "";


    int offsetX = 5;

    Pane hideImagePane = null;
    Pane chooseImagePane = null;
    Pane carImagePaneC = null;
    Label carModelLabelC = null;

    public Order order;

    DriverMainPageController driverMainIns = DriverMainPageController.driverMainPageInstance;

    public OrderCard(Order order) {
        initData(order);
        initUI();
        initEvent();
        this.order = order;
    }

    private  void initData(Order order){
        this.carModel = DataTools.getCarModelFromCarID(order.getCarID());
        this.carNumber = DataTools.getCarNumberFromCarID(order.getCarID());
        this.status = order.getStatus();
        this.orderID = order.getOrderID();
        this.userID = order.getUserID();

        String gradientColor = DataTools.getGradientColorFromCarID(order.getCarID());
        assert gradientColor != null;
        this.darkColor = gradientColor.split(",")[0];
        this.lightColor = gradientColor.split(",")[1];

        this.pickUpDateTime = order.getPickUpTime();
        this.returnDateTime = order.getReturnTime();

        pickUpLocation = order.getPickUpLocation();
        parkingLocation = order.getParkingLocation();
    }

    private void initUI(){
        initSize();
        initLabels();
        initImageViews();
    }

    private void initSize(){
        this.setPrefHeight(150);
        this.setPrefWidth(270);
        this.getStyleClass().add("mainPane");
        this.getStylesheets()
                .add(new File("src/main/resources/com/example/car_rental_sys/style/orderCard.css")
                        .toURI().toString());
    }

    private void initLabels(){
        Label carModelLabel = new Label(this.carModel);
        Font font = Font.font("System", FontWeight.BOLD, 20);
        setLabelsStyle(carModelLabel,7 + offsetX,15,22,140,font);
        carModelLabel.getStyleClass().add("carModelLabel");
        carModelLabelC = carModelLabel;


        Label colorLabel = new Label(carNumber);
        setLabelsStyle(colorLabel,14 +offsetX,48);


        Label startDateLabel = new Label(DateTools.dateToString(pickUpDateTime,"yyyy-MM-dd"));
        setLabelsStyle(startDateLabel,174 +offsetX,72);


        Label to = new Label("To");
        setLabelsStyle(to,203 +offsetX,94);



        Label endDateLabel = new Label(DateTools.dateToString(returnDateTime,"yyyy-MM-dd"));
        setLabelsStyle(endDateLabel,174 +offsetX,113);


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
        ImageView carImageView = getImageView(carImageAddress,22 ,3,80,60);
        Pane carImagePane = new Pane(carImageView);
        carImagePaneC = carImagePane;
        carImagePane.getStyleClass().add("carImagePane");
        String carStyle =
                "-fx-background-color: " +
                "linear-gradient(to left," + this.darkColor +"," + this.lightColor + ");";
        ImageTools.yAxisFlip(carImageView,590,180);
        setPane(carImagePane,7 +offsetX,71,123,60,carStyle);

        String hideImageAddress = UIImageRoot + "hide.png";
        ImageView hideImageView = getImageView(hideImageAddress,5,0,20,20);
        hideImagePane = new Pane(hideImageView);
        hideImagePane.getStyleClass().add("buttonHidePane");
        setPane(hideImagePane,165 +offsetX,18,30,20);

        String chooseImageAddress = UIImageRoot + "choose.png";
        ImageView chooseImageView = getImageView(chooseImageAddress,7,2,17,15);
        chooseImagePane = new Pane(chooseImageView);
        chooseImagePane.getStyleClass().add("buttonDonePane");
        setPane(chooseImagePane,210 +offsetX,18,30,20);

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

    private void setPane(Pane pane, double x,double y,double width, double height){
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
    }

    private void setPane(Pane pane, double x,double y,double width, double height, String style){
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setStyle(style);
    }

    private  void  initEvent(){
        carImagePaneC.setOnMouseClicked(mouseEvent -> changeInfo());
        carModelLabelC.setOnMouseClicked((mouseEvent -> changeInfo()));

        hideImagePane.setOnMouseClicked(mouseEvent ->hideCard());
        chooseImagePane.setOnMouseClicked(mouseEvent ->chooseCard());
    }

    private void changeInfo(){
        driverMainIns.currentOrderCard = this;
        driverMainIns.setRentInfo();
    }

    private void hideCard(){
        driverMainIns.hideCard(this.orderID);
    }

    private void chooseCard(){
        driverMainIns.chooseCard(this.orderID);
        StatusContainer.currentOrderCard = this;
    }

    public void saveEventToSchedule(){
        String driverID = String.valueOf(StatusContainer.currentUser.getUserID());
        DataTools.setOrderStatus(orderID,2, driverID);
    }


    @Override
    public String toString() {
        return  "OrderCard{" +
                "carModel='" + carModel + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", status=" + status +
                ", orderID=" + orderID +
                ", darkColor='" + darkColor + '\'' +
                ", lightColor='" + lightColor + '\'' +
                ", pickUpDateTime=" + pickUpDateTime +
                ", returnDateTime=" + returnDateTime +
                '}';
    }
}
