package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UIEmptyOrderPane extends Pane {

    private String text;
    private Label label;

    public UIEmptyOrderPane(String text){
        this.text = text;
        init();
        initTheme();
    }

    private void init(){
        this.setPrefSize(320, 670);
        this.setLayoutX(18);
        this.setLayoutY(28);

        label = new Label(text);
        label.setLayoutX(24);
        label.setLayoutY(304);

        this.getChildren().add(label);
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Admin){
            label.setStyle("-fx-text-fill: #4E5969; -fx-font-size: 16px; -fx-font-weight: bold;");
            this.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px;");
        }else{
            label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");
            this.setStyle("-fx-background-color: #111111; -fx-background-radius: 10px;");
        }
    }
}
