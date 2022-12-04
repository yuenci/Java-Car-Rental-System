package com.example.car_rental_sys;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.NetTools;
import com.example.car_rental_sys.ToolsLib.SelfTestTools;
import com.example.car_rental_sys.controllers.UIPaginationController;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.Test;
import com.example.car_rental_sys.ui_components.PaymentCard;
import com.example.car_rental_sys.ui_components.UICusBillRow;
import com.example.car_rental_sys.ui_components.UIPagination;
import com.example.car_rental_sys.ui_components.VehicleCard;
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
        //String fxmlName = "driverServicePage.fxml";
        //String fxmlName = "test.fxml";
        //String fxmlName = "drivingModePage.fxml";
       // String fxmlName = "customerServicePage.fxml";
        //String fxmlName = "addBankCardPage.fxml";
        String fxmlName = "adminServicePage.fxml";
        //String fxmlName = "showOrderComponent.fxml";
//        String fxmlName = "aboutUsPage.fxml";
        //String fxmlName = "messagePage.fxml";
        //String fxmlName = "showCardDetails.fxml";
        //String fxmlName = "Invoice.fxml";
        //String fxmlName = "customerServicePage.fxml";
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

        stageInstance = stage;

        startStage(fxmlName);

        //startApplication();

        //testComponent(stage,"css");
    }

    private static void testComponent(Stage stage, String css){
        //UIPagination01 pagination = new UIPagination01();
        UIPagination pagination2 = new UIPagination();
        //set the resource style sheets
        UIPaginationController test = new UIPaginationController();
        PaymentCard empty = new PaymentCard("empty");
        //empty.setPrefSize(150,90);
        empty.setLayoutX(10);
        empty.setLayoutY(300);
        PaymentCard visa = new PaymentCard("visa", "123456789123", "Yuenci", "Bank of China", "1000");
        //visa.setPrefSize(150,90);
        visa.setLayoutX(10);
        visa.setLayoutY(200);
        PaymentCard master = new PaymentCard("master", "123456789123", "My Master:)", "Bank of APU", "1000");
        //master.setPrefSize(150,90);
        master.setLayoutX(10);
        master.setLayoutY(400);
        PaymentCard paymentCard = new PaymentCard("paypal", "123456789456", "My PayPal", "Bank of China", "1000");
        //paymentCard.setPrefSize(150,90);
        paymentCard.setLayoutX(10);
        paymentCard.setLayoutY(100);

        UICusBillRow row1 = new UICusBillRow("top-up", "2020-12-12", "2:09", "1000", "1");
        row1.setLayoutX(10);
        row1.setLayoutY(100);
        UICusBillRow row2 = new UICusBillRow("rental", "2020-12-12", "2:09", "1000", "2");
        row2.setLayoutX(10);
        row2.setLayoutY(150);

        String[] theme = {"#ffffff","#000000"};
        VehicleCard card = new VehicleCard("Lamborghini_Super","1000", theme[0], theme[1]);
        card.setLayoutX(10);
        card.setLayoutY(100);

        Pane pane = new Pane();
        pane.setPrefSize(500,500);
        //pane.getChildren().addAll(pagination);
        //pane.getChildren().addAll(row1,row2);
        //pane.getChildren().addAll(pagination2, paymentCard, empty, visa, master);
        pane.getChildren().addAll(card);
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