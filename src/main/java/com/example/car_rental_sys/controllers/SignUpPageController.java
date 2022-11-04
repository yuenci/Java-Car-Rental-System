package com.example.car_rental_sys.controllers;


import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.ui_components.BrowserModal;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.function.Function;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;


public class SignUpPageController {

    @FXML
    void loginClicked(MouseEvent event) {
//        System.out.println("login clicked");
        Tools.changeScene("loginPage.fxml");
    }

    @FXML
    void btnCreateClicked(MouseEvent e) {
        String url = "http://127.0.0.1:8080/slideVerify/index.html";
        BrowserModal browserModal = new BrowserModal(375, 450, url) ;
        // modal
        browserModal.setModality();
        Function<String, Void> func =(message) -> {
            if (Objects.equals(message, "Verification succeeded")) {
                StatusContainer.ifVerify = true;
                System.out.println(message);
                Platform.runLater(() -> {
                    //code
                    try {
                        Thread.sleep(2 * 1000);
                        browserModal.stage.close();

                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            return null;
        };

        browserModal.setFunction(func);
        browserModal.show();
    }

    void btnBreateEvent() {
        System.out.println("create clicked");

        Stage stage = new Stage();

        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        Browser browser = engine.newBrowser();

        browser.navigation().loadUrl("http://127.0.0.1:8080/slideVerify/index.html");
        //browser.navigation().loadUrl("https://www.google.com/");

        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            //ConsoleMessageLevel level = consoleMessage.level();
            String message = consoleMessage.message();
            if (Objects.equals(message, "Verification succeeded")) {
                StatusContainer.ifVerify = true;
                System.out.println(message);
                Platform.runLater(() -> {
                    //code
                    try {
                        Thread.sleep(2 * 1000);
                        stage.close();

                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        });

        BrowserView view = BrowserView.newInstance(browser);

        Scene scene = new Scene(new BorderPane(view), 370, 450);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("JxBrowser");
        stage.setScene(scene);
        stage.show();
    }
}

