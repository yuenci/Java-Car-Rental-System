package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.funtions.FileOperate;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.CarCard;
import com.example.car_rental_sys.ui_components.VehicleCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class OverviewVehiclePageController {

    @FXML
    private ScrollPane scrollPaneVehicle;

    @FXML
    public void initialize() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(871);
        flowPane.setHgap(28);
        flowPane.setVgap(28);
        flowPane.setPadding(new Insets(20, 20, 20, 20));
        flowPane.setStyle("-fx-background-color: transparent");


        ArrayList<String[]> carsData = FileOperate.readFileToArray(ConfigFile.carsDataPath);
        for (int i = 1; i < carsData.size(); i++) {
            String[] carData = carsData.get(i);
            //VehicleCard carCard = new VehicleCard(carData[1],carData[3],carData[5],carData[6]);
            VehicleCard carCard = new VehicleCard(carData[1],carData[2],carData[3],carData[4],carData[5],carData[6],carData[7]);
            ArrayList<String[]> carsInfo = SQL.query("SELECT * FROM carInfo WHERE carID = " + carData[0]);
            if (carsInfo.size() != 0) {
                String[] carInfo = carsInfo.get(0);
                carCard.setCarInfo(carInfo[2],carInfo[3],carInfo[4]);
            }
            //String[] carInfo = carsInfo.get(0);
            //carCard.setCarInfo(carsInfo.get(0)[2],carsInfo.get(0)[3],carsInfo.get(0)[4]);
            flowPane.getChildren().add(carCard);
        }


        scrollPaneVehicle.setFitToWidth(true);
        scrollPaneVehicle.setContent(flowPane);
        scrollPaneVehicle.setStyle("-fx-background: white; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-border-color: #e0e0e0; -fx-border-width: 1px; \n" +
                " -fx-vbar-policy : never;");

    }
}
