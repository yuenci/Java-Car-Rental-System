package com.example.car_rental_sys.ui_components;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class FeedBackFrame extends MessageFrame {
    private Pane paneContainer = new Pane();
    private Pane mainBGPanel = new Pane();
    public static FeedBackFrame instance;


    public FeedBackFrame(String type) {
        super(type);
        instance = this;
        init();
    }

    private void init(){
        addNewPaneToContainer();
        addMainBGPanel();
    }
    private  void addMainBGPanel(){
        mainBGPanel.setPrefSize(1280, 832);
        mainBGPanel.setLayoutX(0);
        mainBGPanel.setLayoutY(0);
        mainBGPanel.setStyle("-fx-background-color: rgba(0,0,0,"+transparent+");");
    }
    private void addNewPaneToContainer() {
        int width = 450;
        int height = 520;

        paneContainer.setPrefSize(width, height);
        paneContainer.setLayoutX((1280- width)/2);
        paneContainer.setLayoutY((832- height)/2 - 50);
        paneContainer.setStyle("-fx-background-color: #2a2a2b");
        mainBGPanel.getChildren().add(paneContainer);

        mainPane.getChildren().add(mainBGPanel);
    }
}
