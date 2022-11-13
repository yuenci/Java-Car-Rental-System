package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Driver;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.Objects;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class MessagePageController {
    @FXML
    Pane mainPane;

    @FXML
    public void initialize() {
        initBrowser();
        System.out.println("MessagePageController initialize");
    }

    private void initBrowser() {
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

        Browser browser = engine.newBrowser();

        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/contactUs/index.html").getAbsolutePath());

        BrowserView view = BrowserView.newInstance(browser);
        view.setPrefSize(1280,832);
        mainPane.getChildren().add(view);


        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            String message = consoleMessage.message();
            System.out.println(message);
            if(Objects.equals(message, "back to service")){
                backToService();
            }
        });
    }

    private void backToService(){
        Platform.runLater(() -> {
            try {
                if(StatusContainer.currentUser instanceof Admin){
                    FXTools.changeScene("adminServicePage.fxml");
                }else if (StatusContainer.currentUser instanceof Driver){
                    FXTools.changeScene("driverMainPage.fxml");
                }else if (StatusContainer.currentUser instanceof Customer){
                    FXTools.changeScene("customerServicePage.fxml");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
