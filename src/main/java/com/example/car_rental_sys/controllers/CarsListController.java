package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ui_components.CarCard;
import com.example.car_rental_sys.Config;
import com.example.car_rental_sys.funtions.FileOperate;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class CarsListController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(1270);

        ArrayList<String[]> carsData = FileOperate.readFileToArray(Config.carsDataPath);
        for (int i = 1; i < carsData.size(); i++) {
            String[] carData = carsData.get(i);
            CarCard carCard = new CarCard(carData[1],carData[2],carData[3],carData[4],carData[5],carData[6]);
            flowPane.getChildren().add(carCard);
        }


        scrollPane.setFitToWidth(true);
        scrollPane.setContent(flowPane);

    }
}