package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.controllers.AdminVehiclePageController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class VehicleCard extends Pane {

    private String vehicleName;
    private String vehiclePrice;
    private String gradientLight;
    private String gradientDark;

    private Label lblEdit;


    public VehicleCard() {

    }

    public VehicleCard(String vehicleName, String vehiclePrice, String gradientLight, String gradientDark) {
        this.vehicleName = vehicleName;
        this.vehiclePrice = vehiclePrice;
        this.gradientLight = gradientLight;
        this.gradientDark = gradientDark;
        initVehicleCard();
    }

    private void initVehicleCard(){
        initStyle();
        initComponents();
        initEvent();
    }

    private void initStyle(){
        this.setPrefSize(233,221);
        this.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-border-color: #E5E5E5; -fx-border-width: 1px;");
    }

    private void initComponents(){
        ImageView logo = new ImageView();
        logo.setFitHeight(16);
        logo.setFitWidth(60);
        logo.setLayoutX(12);
        logo.setLayoutY(6);
        logo.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/logo_light_green.png"));

        lblEdit = new Label("Edit");
        lblEdit.setLayoutX(202);
        lblEdit.setLayoutY(6);
        lblEdit.setStyle("-fx-text-fill: #4080ff; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;");

        Pane pane = new Pane();
        pane.setPrefSize(233, 151);
        pane.setLayoutX(0);
        pane.setLayoutY(25);
        pane.setStyle("-fx-background-color: " +
                "linear-gradient(to left," + this.gradientDark +"," + this.gradientLight+");");

        ImageView carImage = new ImageView();
        carImage.setFitHeight(151);
        carImage.setFitWidth(233);
        carImage.setLayoutX(0);
        carImage.setLayoutY(0);
        carImage.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/cars/"+ vehicleName + ".png"));

        pane.getChildren().add(carImage);


        Label lblCarName = new Label(vehicleName);
        lblCarName.setLayoutX(12);
        lblCarName.setLayoutY(182);
        lblCarName.setStyle("-fx-text-fill: #4e5969; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label lblCarPrice = new Label("RM" + vehiclePrice + "/Day");
        lblCarPrice.setLayoutX(12);
        lblCarPrice.setLayoutY(200);
        lblCarPrice.setStyle("-fx-text-fill: #86909c; -fx-font-size: 10px; -fx-font-weight: bold;");

        this.getChildren().addAll(logo, lblEdit, pane, lblCarName, lblCarPrice);
    }

    private void initEvent(){
        if(lblEdit != null){
            lblEdit.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                System.out.println("Edit");
                Thread thread = new Thread(() -> Platform.runLater(
                        () -> AdminVehiclePageController.instance.showEditVehiclePage()
                ));

                thread.start();
                //AdminVehiclePageController.instance.showEditVehicleBtn();
            });
        }
    }
}
