package com.example.car_rental_sys.controllers;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.engine.internal.AcceleratedLightweight;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.io.File;
import java.net.URL;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
// import BrowserType



public class dashboardController {
    @FXML
    WebView webview;

    @FXML
    Pane browserPane;



    @FXML
    public void initialize() {
        System.out.println("dashboardController");
//        initWebview();
        initJxBrowser();
    }


    private void  initWebview(){
        WebEngine engine = webview.getEngine();
        URL url = this.getClass().getResource("/com/example/car_rental_sys/html/dashboard/index.html");
        assert url != null;
        engine.load(url.toString());
    }

    private void  initJxBrowser(){


        Engine engine = Engine.newInstance(RenderingMode.OFF_SCREEN);



        Browser browser = engine.newBrowser();

        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/dashboard/index.html").getAbsolutePath());
        BrowserView view = BrowserView.newInstance(browser);
        view.setPrefSize(1000,750);
        browserPane.getChildren().add(view);
    }



}
