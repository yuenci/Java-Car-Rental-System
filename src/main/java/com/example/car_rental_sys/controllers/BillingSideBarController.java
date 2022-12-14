package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class BillingSideBarController {

    @FXML
    private Label billSideText;
    @FXML
    private Pane radioChart,lineChart;


    @FXML
    public void initialize() {
        billSideText.setText("RM "+DataTools.getTotalSpending(StatusContainer.currentUser.getUserID())+ "0");
        initWebView();
    }


    private void initWebView(){
//        WebEngine webViewMiddleEngine = webViewMiddle.getEngine();
//        webViewMiddleEngine.load("file:src/main/resources/com/example/car_rental_sys/html/bar.html");
//
//        WebEngine webViewBottomEngine = webViewBottom.getEngine();
//        webViewBottomEngine.load("file:src/main/resources/com/example/car_rental_sys/html/radio.html");

        Engine engine = Engine.newInstance(RenderingMode.OFF_SCREEN);
        Browser browser = engine.newBrowser();
        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/wallet/radio.html").getAbsolutePath());
        BrowserView view = BrowserView.newInstance(browser);
        view.setPrefSize(250, 200);
        radioChart.getChildren().add(view);

        Engine engine1 = Engine.newInstance(RenderingMode.OFF_SCREEN);
        Browser browser1 = engine1.newBrowser();
        browser1.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/wallet/bar.html").getAbsolutePath());
        BrowserView view1 = BrowserView.newInstance(browser1);
        view1.setPrefSize(310, 200);
        lineChart.getChildren().add(view1);
    }
}
