package com.example.car_rental_sys;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class carsListController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane navBarPanelContainer;

    @FXML
    public void initialize() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(1270);

        ArrayList<String[]> carsData = FileOperate.readFileToArray(config.carsDataPath);
        for (String[] carData :carsData
                ) {
            CarCard carCard = new CarCard(carData[0],carData[1],carData[2],carData[3],carData[4],carData[5]);
            flowPane.getChildren().add(carCard);
        }


        scrollPane.setFitToWidth(true);
        scrollPane.setContent(flowPane);

    }
}
