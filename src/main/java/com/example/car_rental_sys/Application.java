package com.example.car_rental_sys;

import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.ui_components.OrderCard;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

// https://github.com/users/yuenci/projects/3

public class Application extends javafx.application.Application {
    public static Stage stageInstance;

    @Override
    public void init() throws Exception {
        registerJxBrowserLicence();
        //StartHttpServer();
        //ormObjTest(new OrderCard(new Order(1)));
    }

    @Override
    public void start(Stage stage) throws IOException {
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
        String fxmlName = "test.fxml";

        stageInstance = stage;

        startStage(fxmlName);

    }

    private static void startStage(String fxmlName) throws IOException {

        if (fxmlName.contains("Page")) {
            startPrimaryStage(fxmlName) ;
        } else {
            Tools.componentTest(fxmlName,800,700);
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
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void StartHttpServer() {
        Tools.StartHttpServer();
    }

    private static void registerJxBrowserLicence(){
        System.setProperty("jxbrowser.license.key", ConfigFile.jxBrowserLicense);
    }

    private void ormObjTest(Object obj){
        System.out.println(obj.toString());
    }
}

// # drive license id