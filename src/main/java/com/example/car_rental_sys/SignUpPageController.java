package com.example.car_rental_sys;


import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;


public class SignUpPageController {

    @FXML
    void loginClicked(MouseEvent event) {
//        System.out.println("login clicked");
        Tools.changeScene("loginPage.fxml");
    }

    @FXML
    void btnCreateClicked(MouseEvent e) {
        System.out.println("create clicked");

        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        Browser browser = engine.newBrowser();

        browser.navigation().loadUrl("http://127.0.0.1:8080/slideVerify/index.html");
        //browser.navigation().loadUrl("https://www.google.com/");

        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            //ConsoleMessageLevel level = consoleMessage.level();
            String message = consoleMessage.message();
            System.out.println(message);

//            String[] messageArray = message.split(",");
//            StatusContainer.pickDateTime = messageArray[0];
//            StatusContainer.returnDateTime = messageArray[1];

        });

        BrowserView view = BrowserView.newInstance(browser);

        Scene scene = new Scene(new BorderPane(view),800,600);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("JxBrowser");
        stage.setScene(scene);
        stage.show();

//        updatePickAndReturnDateTime();
//
//        stage.setOnCloseRequest(event -> {
//            timerDatePciker.cancel();
//            engine.close();
//        });

    }
}

