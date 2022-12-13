package com.example.car_rental_sys.ui_components;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class FeedBackFrame extends MessageFrame {
    private Pane paneContainer = new Pane();
    public Pane mainBGPanel = new Pane();
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
        int width = 395;
        int height = 330;

        paneContainer.setPrefSize(width, height);
        paneContainer.setLayoutX((1280- width)/2);
        paneContainer.setLayoutY((832- height)/2 - 80);
        paneContainer.setStyle("-fx-background-color: #2a2a2b");

        UIRating rating = new UIRating();
        rating.setPrefSize(width, height);
        paneContainer.getChildren().add(rating);

        mainBGPanel.getChildren().add(paneContainer);
        mainPane.getChildren().add(mainBGPanel);

    }
}
