package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.ui_components.OrderCard;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class TextController {
    @FXML
    Pane mainPane;

    BrowserView view;

    Browser browser;



    @FXML
    public void initialize() {
        OrderCard orderCard = new OrderCard(new Order(1));
        mainPane.getChildren().add(orderCard);
        addBrowser();
    }

    public  void addBrowser(){

        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        browser = engine.newBrowser();

        browser.navigation().loadUrl("https://html5test.com");
        view = BrowserView.newInstance(browser);
        view.setPrefSize(300, 300);
        mainPane.getChildren().add(view);

        //mainPane.getChildren().add(container);

//        Scene scene = new Scene(new BorderPane(view), 300, 300);
//        Stage primaryStage = new Stage();
//        primaryStage.setTitle("JxBrowser JavaFX");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        primaryStage.show();

    }

    @FXML
    private void buttonClick() {
        browser.resize(600, 600);
        //view.resize(500, 500);
    }
}
