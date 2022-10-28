package com.example.car_rental_sys;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class Tools {
    public  void reSetScence(ActionEvent actionEvent,String fxmlName) {
        Pane pane  = null;
        try {
            URL url = getClass().getResource("fxml/" + fxmlName);
            assert url != null;
            pane = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Button)actionEvent.getSource()).getScene().setRoot(pane);
    }
}
