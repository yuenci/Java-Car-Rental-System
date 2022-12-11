package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ui_components.CarCard;
import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.funtions.FileOperate;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Objects;

public class CarsListController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(1270);
        
        ArrayList<Integer> availableCarIds = DataTools.getAvailableCars();
        // print all available cars
//        for (int carId : availableCarIds) {
//            System.out.println(carId);
//        }


        ArrayList<String[]> carsData = FileOperate.readFileToArray(ConfigFile.carsDataPath);
        if(Objects.equals(StatusContainer.carDetailsEntrance, "search")){
            for (int i = 1; i < carsData.size(); i++) {
                // if carData id in availableCarIds
                if (availableCarIds.contains(Integer.parseInt(carsData.get(i)[0]))) {
                    String[] carData = carsData.get(i);
                    CarCard carCard = new CarCard(carData[1],carData[2],carData[3],carData[4],carData[5],carData[6]);
                    flowPane.getChildren().add(carCard);
                }
            }
        }else if(Objects.equals(StatusContainer.carDetailsEntrance, "catalog")){
            for (int i = 1; i < carsData.size(); i++) {
                String[] carData = carsData.get(i);
                CarCard carCard = new CarCard(carData[1],carData[2],carData[3],carData[4],carData[5],carData[6]);
                flowPane.getChildren().add(carCard);
            }
        }else{
            System.out.println("Error: CarsListController initialize");
        }



        scrollPane.setFitToWidth(true);
        scrollPane.setContent(flowPane);

    }
}
