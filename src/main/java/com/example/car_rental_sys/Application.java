package com.example.car_rental_sys;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.NetTools;
import com.example.car_rental_sys.ToolsLib.SelfTestTools;
import com.example.car_rental_sys.controllers.UIPaginationController;
import com.example.car_rental_sys.funtions.Encryption;
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
        NetTools.registerJxBrowserLicence();
        //NetTools.StartHttpServer();
        //dataFilesDecrypt();
        Test.test();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //String css = Objects.requireNonNull(this.getClass().getResource("pagination.css")).toExternalForm();
        //String fxmlName = "mainPage.fxml";
        //String fxmlName = "carsListPage.fxml";
        //String fxmlName = "signUpPage.fxml";
        //String fxmlName = "loginPage.fxml";
        //String fxmlName = "carDetailsPage.fxml";
        //String fxmlName = "paymentPage.fxml";
        //String fxmlName = "contactUsPage.fxml";
        //String fxmlName = "OrderDetailsComponent.fxml";
        //String fxmlName = "paySuccessPage.fxml";
        //String fxmlName = "aboutUsPage2.fxml";
//        String fxmlName = "driverMainPage.fxml";
        //String fxmlName = "test.fxml";
//        String fxmlName = "drivingModePage.fxml";
        //String fxmlName = "customerServicePage.fxml";
//        String fxmlName = "addBankCardPagSe.fxml";
//        String fxmlName = "adminServicePage.fxml";
        //String fxmlName = "showOrderComponent.fxml";
        String fxmlName = "aboutUsPage.fxml";
        stageInstance = stage;

        //startStage(fxmlName);

        //startApplication();

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


    /*
    star loading page

    init data
    - start http server
    - decrypt data files
    - Register JxBrowser Licence


    check
    - backend working status - danger
    - encrypt data files status - danger
    - JxBrowser Licence status - danger
    - network connection - warning
    - data files integrity - danger

    render to login page
     */

    private static void startApplication() throws IOException {
        FXTools.startLoadingStage();
        SelfTestTools.executeSelfTest();
        //FXTools.showNetworkErrorPage();
        FXTools.showErrorsPage();


        //FXTools.showDiagnosticDataPage();

        // init program
        //// start the http server
//        try {
//            //NetTools.StartHttpServer();
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        DataTools.decryptDataFiles();
//        NetTools.registerJxBrowserLicence();
//        // self checking
//
//        for (int i = 0; i <1 ; i++) {
//            SelfTestTools.executeSelfTest();
//        }


    }

    public static void main(String[] args) {
        launch();
    }


}