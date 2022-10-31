package com.example.car_rental_sys;

import com.example.car_rental_sys.sqlParser.SQL;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class HelloApplication extends Application {
    public static Stage stageInstance;

    @Override
    public void start(Stage stage) throws IOException {
        String fxmlName = "mainPage.fxml";
        //String fxmlName = "carsList.fxml";
        //String fxmlName = "signUp.fxml";
        //String fxmlName = "loginPage.fxml";
        //String fxmlName = "carDetailsPage.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/"+ fxmlName));

        double width = 1280;
        double height = 832;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        Scene scene = new Scene( fxmlLoader.load(), width, height);
        stageInstance = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/logoIcon.png"));
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        System.setProperty("jxbrowser.license.key",Config.jxBrowserLicense);
        StartHttpServer();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void StartHttpServer(){
//        String path = System.getProperty("user.dir") + Config.htmlComponents + "datePicker\\test.bat";
//        Thread thread = new Thread(() -> {
//            Tools.executeBatFile("\""+ path +"\"",false);
//            System.out.println("http server started");
//        });
//        thread.start();
//        Thread thread = new Thread(() -> {
//            Runtime ec=Runtime.getRuntime();
//            try {
//                String path = System.getProperty("user.dir") + Config.htmlComponents + "datePicker";
//                String cmd = "\"" + path + "\\test.bat\"";
//                ec.exec(cmd);
//                System.out.println(cmd);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        thread.start();


    }

    private static class Stirng {
    }
}