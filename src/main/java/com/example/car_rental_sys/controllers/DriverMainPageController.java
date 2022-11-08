package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.BrowserModal;
import com.example.car_rental_sys.ui_components.OrderCard;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class DriverMainPageController {
    @FXML
    private WebView webview;

    @FXML
    private ScrollPane scrollPane;

    private int[] orderIDs;


    @FXML
    public void initialize() {
        initData();
        initOrderCards();
        initWebview();
        initScrollPaneEvent();
    }
    private void initWebview(){
        WebEngine engine = webview.getEngine();
        String path = "/com/example/car_rental_sys/html/directions.html";
        engine.load( Objects.requireNonNull(getClass().getResource(path)).toString());
    }

    private void initScrollPane(){
        //scrollPane.setFitToWidth(true);
    }

    private void initData(){
        String sql = "select orderID from orders where status = 1 " ;
        ArrayList<String[]> result = SQL.query(sql);
        if(result.size() == 0){
            System.out.println("no order");
        }
        else{
            orderIDs = new int[result.size()];
            for (int i = 0; i < result.size(); i++) {
                orderIDs[i] = Integer.parseInt(result.get(i)[0]);
            }
        }
    }

    private void initOrderCards(){
        Pane pane = new Pane();
        double width = orderIDs.length * 300 ;
        pane.setPrefWidth(width);
        pane.setPrefHeight(180);
        pane.setStyle("-fx-background-color: transparent");
        for (int i = 0; i < orderIDs.length; i++) {
            OrderCard orderCard = new OrderCard(new Order(orderIDs[i]));
            orderCard.setLayoutX(i * 300+15);
            pane.getChildren().add(orderCard);
        }
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefViewportWidth(width);
        scrollPane.setContent(pane);
    }

    @FXML
    public void phoneClick() {
        String num = "601110715226";
        String url ="https://api.whatsapp.com/send/?phone=" + num;
        BrowserModal browserModal = new BrowserModal(500, 450, url) ;
        Function<String, Void> func =(message) -> {
            //System.out.println(message);
            return null;
        };
        browserModal.setFunction(func);
        browserModal.show();
    }

    private void initScrollPaneEvent(){
        //scrollPane.setVvalue(0.5);
        scrollPane.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 0.1;
            double width = scrollPane.getContent().getBoundsInLocal().getWidth();
            double vvalue = scrollPane.getVvalue();
            System.out.println(vvalue + -deltaY/width);
            scrollPane.setVvalue(vvalue + -deltaY/width);
        });
    }

}
