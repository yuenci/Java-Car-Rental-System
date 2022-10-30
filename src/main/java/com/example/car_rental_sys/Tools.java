package com.example.car_rental_sys;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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


    public  void reSetScence(String fxmlName) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/"+ fxmlName));
        try {
            Scene scene = new Scene( fxmlLoader.load());
            HelloApplication.stageInstance.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
