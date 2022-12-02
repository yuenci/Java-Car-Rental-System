package com.example.car_rental_sys.ui_components;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SwitchButton extends Pane {

    private Pane circle;
    private boolean isOn = true;

    public SwitchButton() {
        initStyle();
        initEvent();
        initComponent();
    }

    private void initStyle(){
        this.setPrefSize(30,16);
        if(isOn){
            this.setStyle("-fx-background-radius: 20; -fx-background-color: #4080ff;");
        }else{
            this.setStyle("-fx-background-radius: 20; -fx-background-color: #D9D9D9;");
        }
    }

    private void initComponent(){
        circle = new Pane();
        circle.setPrefSize(8,8);
        circle.setStyle("-fx-background-radius: 4; -fx-background-color: #ffffff;");
        circle.setLayoutX(18);
        circle.setLayoutY(4);

        this.getChildren().add(circle);
    }

    private void initEvent(){
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!isOn){
                isOn = true;
                circle.setLayoutX(18);
                this.setStyle("-fx-background-radius: 20; -fx-background-color: #4080ff;");
            }else {
                isOn = false;
                circle.setLayoutX(4);
                this.setStyle("-fx-background-radius: 20; -fx-background-color: #D9D9D9;");
            }
        });
    }

}
