package com.example.car_rental_sys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stageInstance;

    @Override
    public void start(Stage stage) throws IOException {
        //String fxmlName = "mainPage.fxml";
        //String fxmlName = "carsList.fxml";
        String fxmlName = "signUp.fxml";
        //String fxmlName = "loginPage.fxml";


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
    }

    public static void main(String[] args) {
        launch();
    }


}