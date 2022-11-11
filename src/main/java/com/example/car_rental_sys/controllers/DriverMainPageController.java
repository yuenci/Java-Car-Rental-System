package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.*;
import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import com.example.car_rental_sys.ui_components.OrderCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DriverMainPageController extends  Controller{
    @FXML
    private WebView webview;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public Label startLabel, destinationLabel, speedLabel, powerLabel, seatsLabel,postLabel,nameLabel,
            renterName,renterPost,processTipLabel,carNumberLabel;

    @FXML
    public ImageView carImageView,renterAvatar;

    @FXML
    Pane mainPane, closeSideBarPane, phonePane, messagePane, sidePane ,processTipPane,processImagePane;

    @FXML
    Pane item1, item2, item3, item4, item5, item6;

    private String renterNameCache;


    private int[] orderIDs;

    public static DriverMainPageController driverMainPageInstance;

    public OrderCard currentOrderCard = null;

    private HashMap<Integer, OrderCard> orderCardHashMap = new HashMap<>();

    @FXML
    public void initialize() {
        driverMainPageInstance = this;
        initData();
        initSideBar();
        initOrderCards();
        initWebview();
        initScrollPaneEvent();
        initMenuEvent();
        initFirstOrderCard();
        initProcessTip();
        StatusContainer.currentPageController = this;
    }

    private void initWebview() {
        WebEngine engine = webview.getEngine();
        String path = "/com/example/car_rental_sys/html/directions.html";
        engine.load(Objects.requireNonNull(getClass().getResource(path)).toString());
    }

    private void initScrollPane() {
        //scrollPane.setFitToWidth(true);
    }

    private void initData() {
        String sql = "select orderID from orders where status = 1 ";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            System.out.println("no order");
        } else {
            orderIDs = new int[result.size()];
            for (int i = 0; i < result.size(); i++) {
                orderIDs[i] = Integer.parseInt(result.get(i)[0]);
            }
        }
    }

    private void initOrderCards() {
//        Pane pane = new Pane();
//        double width = orderIDs.length * 300;
//        pane.setPrefWidth(width);
//        pane.setPrefHeight(180);
//        pane.setStyle("-fx-background-color: transparent");

        // get map values



//        currentOrderCard = new OrderCard(new Order(orderIDs[0]));
//
//        for (int i = 0; i < orderIDs.length; i++) {
//            OrderCard orderCard = new OrderCard(new Order(orderIDs[i]));
//            orderCard.setLayoutX(i * 300 + 15);
//            pane.getChildren().add(orderCard);
//        }
//        scrollPane.setFitToHeight(true);
//        //scrollPane.setPrefViewportWidth(width);
//        scrollPane.setContent(pane);
        setOrderCardMapData();
        addOrderCardToPane();

    }

    private void setOrderCardMapData(){
        for (int orderID : orderIDs) {
            OrderCard orderCard = new OrderCard(new Order(orderID));
            orderCardHashMap.put(orderID, orderCard);
        }
    }

    private void addOrderCardToPane(){
        Pane pane = new Pane();
        // get map length
        double width = orderCardHashMap.size() * 300;
        pane.setPrefWidth(width);
        pane.setPrefHeight(180);
        pane.setStyle("-fx-background-color: transparent");

        int i = 0;
        Collection<OrderCard> orderCards = orderCardHashMap.values();
        for (OrderCard orderCard : orderCards) {
            orderCard.setLayoutX(i * 300 +15);
            pane.getChildren().add(orderCard);
            i++;
        }
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(pane);
    }

    private void removeOrderCardFromMap(int orderID){
        double width = orderCardHashMap.size() * 300;
        if(width>3){
            orderCardHashMap.remove(orderID);
        }
    }

    @FXML
    public void phoneClick() {
        PlatformTools.callWhatsApp("601110715226");
    }

    private void initScrollPaneEvent() {
//        scrollPane.setVvalue(0.5);
//        scrollPane.setOnScroll(event -> {
//            double deltaY = event.getDeltaY() * 0.1;
//            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
//            double vvalue = scrollPane.getVvalue();
//            System.out.println(vvalue + -deltaY / width);
//            scrollPane.setVvalue(vvalue + -deltaY / width);
//        });
    }

    @FXML
    private void closeSideBarPaneClick() {
////        System.out.println( "closeSideBarPaneClick");
//        //sidePane.setVisible(false);
////        mainPane.getChildren().remove(sidePane);
////        mainPane.getChildren().remove(webview);
////
////        WebView newWebview = new WebView();
////        WebEngine engine = newWebview.getEngine();
////        String path = "/com/example/car_rental_sys/html/directions.html";
////        engine.load(Objects.requireNonNull(getClass().getResource(path)).toString());
////        newWebview.resize(1000, 500);
////        newWebview.setLayoutX(280);
////        newWebview.setLayoutY(0);
////        mainPane.getChildren().add(newWebview);
//        StatusContainer.isHideDriverSideBar = true;
//        Tools.changeScene("driverMainPage.fxml");

    }

    private void initSideBar() {
       // label set text align center
        postLabel.setAlignment(Pos.CENTER);
        nameLabel.setAlignment(Pos.CENTER);
        carNumberLabel.setAlignment(Pos.CENTER);
    }

    private void initMenuEvent() {
        item1.setOnMouseClicked(event -> {
            changeMenuStyle(item1);
        });
        item2.setOnMouseClicked(event -> {
            changeMenuStyle(item2);
        });
        item3.setOnMouseClicked(event -> {
            changeMenuStyle(item3);
        });
        item4.setOnMouseClicked(event -> {
            changeMenuStyle(item4);
        });
        item5.setOnMouseClicked(event -> {
            changeMenuStyle(item5);
        });
        item6.setOnMouseClicked(event -> {
            changeMenuStyle(item6);
            FXTools.changeScene("mainPage.fxml");
        });

    }

    private void changeMenuStyle(Pane activePane) {
        System.out.println(activePane.toString() + "changeMenuStyle-line229");
        item1.getStyleClass().remove("menuItemActive");
        item2.getStyleClass().remove("menuItemActive");
        item3.getStyleClass().remove("menuItemActive");
        item4.getStyleClass().remove("menuItemActive");
        item5.getStyleClass().remove("menuItemActive");
        item6.getStyleClass().remove("menuItemActive");

        activePane.getStyleClass().add("menuItemActive");
    }

    private void initFirstOrderCard(){
        currentOrderCard = new OrderCard(new Order(orderIDs[0]));
        setCarInfo();
        setRenterInfo();
        setLocationInfo();
        Platform.runLater(this::setMapinfo);
    }

    public void setRentInfo(){
        setMapinfo();
        setCarInfo();
        setRenterInfo();
        setLocationInfo();
    }
    private void setMapinfo(){
        String start = "'" + StringTools.replaceSpacialChar(currentOrderCard.order.parkingLocation) + "'";
        String end = "'" + StringTools.replaceSpacialChar(currentOrderCard.order.pickUpLocation) + "'";

        String jsFunc = "changeDirections(" + start+","+  end +")";
        //System.out.println(jsFunc);
        webview.getEngine().executeScript(jsFunc);
    }


    private void setCarInfo(){
        String[] data = DataTools.getCarSeatsSpeedPowerFromCarModel(currentOrderCard.carModel);
        assert data != null;
        this.speedLabel.setText(data[1] + " km/h");
        this.powerLabel.setText(data[2] + " L");
        this.seatsLabel.setText(data[0] );
        this.carNumberLabel.setText(currentOrderCard.carNumber);

        String url= "src/main/resources/com/example/car_rental_sys/image/cars/" + currentOrderCard.carModel + ".png";
        this.carImageView.setImage(ImageTools.getImageObjFromPath(url));
    }

    private void setRenterInfo(){
        this.renterAvatar.setImage(ImageTools.getImageObjFromUserID(currentOrderCard.userID));

        String[] nameAndPost = DataTools.getRenterNameAndPostFromUserID(currentOrderCard.userID);
        assert nameAndPost != null;
        renterNameCache = nameAndPost[0].replace("-"," ");
        this.renterName.setText(renterNameCache);
        this.renterPost.setText(nameAndPost[1]);
    }

    private void setLocationInfo(){
        this.startLabel.setText(currentOrderCard.pickUpLocation);
        this.destinationLabel.setText(currentOrderCard.parkingLocation);
    }

    public void hideCard(int orderID){
        removeOrderCardFromMap(orderID);
        addOrderCardToPane();
    }

    public void chooseCard(int orderID){
        // show confirm dialog
        String message = "Do you want to accept "+ renterNameCache +"'s order?";
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM, message);
        messageFrame.setSuccessCallbackFunc((i) -> {
            this.scrollPane.setVisible(false);
            showProcessTip();
            showSuccessMessage();
            return null;
        });

        messageFrame.setFailedCallbackFunc((i) -> {
            messageFrame.close();
            return null;
        });
        messageFrame.show();
    }

    private  void   showSuccessMessage(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.SUCCESS, "Order accepted successfully!");
        messageFrame.show();
    }

    private void initProcessTip(){
        processTipPane.setVisible(false);
        processImagePane.setOnMouseEntered(event -> {
            HoverProcessTip();
        });

        processImagePane.setOnMouseExited(event -> {
            showProcessTip();
        });

        processImagePane.setOnMouseClicked(event -> {
            changeToDriveMode();
        });
    }

    private void showProcessTip(){
        processTipPane.setVisible(true);
        processTipLabel.setVisible(false);
        processTipPane.setStyle("-fx-background-color:transparent");
    }

    private  void HoverProcessTip(){
        processTipLabel.setVisible(true);
        processTipPane.setStyle("-fx-background-color:#ffffff");
    }

    private void changeToDriveMode(){
        FXTools.changeScene("drivingModePage.fxml");
    }

}

// TODO: add animations to the order cards
// TODO: add hide side Bar function
// TODO: remove google map api key from directions.html