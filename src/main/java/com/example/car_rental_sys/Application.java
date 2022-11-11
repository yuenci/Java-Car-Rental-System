package com.example.car_rental_sys;

import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.NetTools;
import com.example.car_rental_sys.controllers.UIPaginationController;
import com.example.car_rental_sys.funtions.Test;
import com.example.car_rental_sys.ui_components.UIPagination;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.Objects;

// https://github.com/users/yuenci/projects/3

public class Application extends javafx.application.Application {
    public static Stage stageInstance;

    @Override
    public void init() throws Exception {
        //registerJxBrowserLicence();
        //StartHttpServer();
        //dataFilesDecrypt();
        Test.test();
    }

    @Override
    public void start(Stage stage) throws IOException {
        String css = Objects.requireNonNull(this.getClass().getResource("pagination.css")).toExternalForm();
        //String fxmlName = "mainPage.fxml";
        //String fxmlName = "carsListPage.fxml";
        //String fxmlName = "signUpPage.fxml";
        //String fxmlName = "loginPage.fxml";
        //String fxmlName = "carDetailsPage.fxml";
        //String fxmlName = "paymentPage.fxml";
        //String fxmlName = "contactUsPage.fxml";
        //String fxmlName = "OrderDetailsComponent.fxml";
        //String fxmlName = "paySuccessPage.fxml";
        //String fxmlName = "aboutUsPage.fxml";
        //String fxmlName = "driverMainPage.fxml";
        //String fxmlName = "test.fxml";
        //String fxmlName = "drivingModePage.fxml";
        //String fxmlName = "customerServicePage.fxml";

        stageInstance = stage;

        //startStage(fxmlName);

        //startLoadingStage();

        //testComponent(stage,css);
    }

    private static void testComponent(Stage stage, String css){
        //UIPagination01 pagination = new UIPagination01();
        UIPagination pagination2 = new UIPagination();
        //set the resource style sheets
        UIPaginationController test = new UIPaginationController();

        Pane pane = new Pane();
        pane.setPrefSize(500,500);
        //pane.getChildren().addAll(pagination);
        pane.getChildren().addAll(pagination2);
        Scene scene = new Scene(pane,500,500);
        scene.getStylesheets().add(css);
        stage.setTitle("Test Component");
        stage.setScene(scene);
        stage.show();

    }

    private static void startStage(String fxmlName) throws IOException {

        if (fxmlName.contains("Page")) {
            startPrimaryStage(fxmlName) ;
        } else {
            FXTools.componentTest(fxmlName,800,700);
        }

    }
    private static void startPrimaryStage(String fxmlName) throws IOException {
        double width = 1280;
        double height = 832;

        Stage stage = stageInstance;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxmlName));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
        Scene scene = new Scene(fxmlLoader.load(), width, height);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/logoIcon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static void startLoadingStage() throws IOException {
        double width = 640;
        double height = 416;

        Stage stage = new Stage();
        stage.setWidth(width);
        stage.setHeight(height);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/loadingPage.fxml" ));
        FXTools.setStageShowCenterOfScreen(stage);
        Scene scene = new Scene(fxmlLoader.load(), width, height);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/logoIcon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public static void StartHttpServer() {
        NetTools.StartHttpServer();
    }

    private static void registerJxBrowserLicence(){
        System.setProperty("jxbrowser.license.key", ConfigFile.jxBrowserLicense);
    }

}