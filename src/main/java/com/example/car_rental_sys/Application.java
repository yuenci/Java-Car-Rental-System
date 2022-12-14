package com.example.car_rental_sys;

import com.example.car_rental_sys.ToolsLib.*;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.Test;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Driver;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.ui_components.*;
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
    public static Application instance ;

    @Override
    public void init() throws Exception {
        NetTools.registerJxBrowserLicence();
        //NetTools.StartHttpServer();
        //dataFilesDecrypt();
        DataTools.keepUserLoggedIn();

        Test.test();
        //FXTools.pandaHead();
        //System.out.println(DataTools.ifCarsAvailable("Mclaren_2018"));
    }

    @Override
    public void start(Stage stage) throws IOException {
        //String css = Objects.requireNonNull(this.getClass().getResource("pagination.css")).toExternalForm();
        //String fxmlName = "mainPage.fxml";
        //String fxmlName = "carsListPage.fxml";
        //String fxmlName = "signUpPage.fxml";
        //String fxmlName = "loginPage.fxml";
        //String fxmlName = "carDetailsPage.fxml";
//        String fxmlName = "paymentPage.fxml";
        //String fxmlName = "contactUsPage.fxml";
        //String fxmlName = "OrderDetailsComponent.fxml";
        //String fxmlName = "paySuccessPage.fxml";
        //String fxmlName = "aboutUsPage2.fxml";
        //String fxmlName = "driverServicePage.fxml";
        //String fxmlName = "test.fxml";
        //String fxmlName = "drivingModePage.fxml";
        //String fxmlName = "customerServicePage.fxml";
        //String fxmlName = "addBankCardPage.fxml";
        String fxmlName = "adminServicePage.fxml";
        //String fxmlName = "showOrderComponent.fxml";
//        String fxmlName = "aboutUsPage.fxml";
        //String fxmlName = "messagePage.fxml";
        //String fxmlName = "showCardDetails.fxml";
        //String fxmlName = "Invoice.fxml";
      //  String fxmlName = "customerServicePage.fxml";
        //String fxmlName = "BillingComponent.fxml";
        //String fxmlName = "AdminVehiclePage.fxml";
        //String fxmlName = "AccountSecurityPage.fxml";
        //String fxmlName = "PersonalInfoPage.fxml";
        //String fxmlName = "SettingPage.fxml";
        //String fxmlName = "MiddleBar.fxml";
        //String fxmlName = "AccountSecurityPage.fxml";
        //String fxmlName = "PersonalInfoPage.fxml";
        //String fxmlName = "PasswordPage.fxml";
        //String fxmlName = "UserSettingPage.fxml";
        //String fxmlName = "ViewCommentsPage.fxml";

        stageInstance = stage;

        //setUser("d");
        setUser("a");
        //setUser("c");
        startStage(fxmlName);
        //setUser();
        //setUser("c");

        // message page test

        //startApplication();

        //testComponent(stage,"css");

    }

    private static void testComponent(Stage stage, String css){
        UIRating rating = new UIRating();

        Pane pane = new Pane();
        pane.setPrefSize(500,500);
        pane.getChildren().addAll(rating);
        Scene scene = new Scene(pane,500,500);
        //scene.getStylesheets().add(css);
        stage.setTitle("Test Component");
        stage.setScene(scene);
        stage.show();

    }

    public static void startStage(String fxmlName) throws IOException {

        if (fxmlName.contains("Page")) {
            startPrimaryStage(fxmlName) ;
        } else {
            FXTools.componentTest(fxmlName,800,700);
        }

    }
    private static  void setUser(){
        StatusContainer.currentUser  = null;
    }
    private static  void setUser(String type){
        if(Objects.equals(type, "c")){
            //StatusContainer.currentUser  = new Customer("1575270674@qq.com");
            StatusContainer.currentUser  = new Customer("lbaker@hotmail.com");
//            StatusContainer.currentUser  = new Customer("15705156@qq.com");
        }else if(Objects.equals(type, "d")){
            StatusContainer.currentUser  = new Driver("cervantesmichael@yahoo.com");
        }else if(Objects.equals(type, "a")){
            StatusContainer.currentUser  = new Admin("david32@hotmail.com");
        }else{
            StatusContainer.currentUser  = null;
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




    private static void startApplication() throws IOException {
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
    FXTools.startLoadingStage();
    }

    public static void main(String[] args) {
        launch();
    }


}