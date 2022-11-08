package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.ui_components.Loading;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class NavigationBarControllerShort {
    @FXML
    private ImageView closeIconBtn;

    @FXML
    private Pane navBarPanel;

    @FXML
    public static NavigationBarControllerShort navigationBarControllerInstance;

    @FXML
    private void initialize() {
//        System.out.println("Hello World!");
        initIconShowEvent();
        dragAndDrop();
        navigationBarControllerInstance = this;
    }

    @FXML
    private final String imagefolderRoot = "src/main/resources/com/example/car_rental_sys/image/UI/";

    private void initIconShowEvent(){
        closeIconBtn.setVisible(false);
        navBarPanel.setOnMouseEntered(mouseEvent -> closeIconBtn.setVisible(true));

        navBarPanel.setOnMouseExited(mouseEvent -> {
            closeIconBtn.setVisible(false);
//            try {
//                Thread.sleep(1000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });
    }



    @FXML
    void changeImage(String fileUrl, ImageView imageView){
        File file = new File(fileUrl);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
    void closeIconBtnClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void changeImageToHover(MouseEvent mouseDragEvent) {
        changeImage(imagefolderRoot +"/closeIconHover.png",closeIconBtn);
    }

    //changeImageToClose
    @FXML
    public void changeImageToClose(MouseEvent mouseDragEvent) {
        changeImage(imagefolderRoot +"/closeIcon.png",closeIconBtn);
    }

    private double locationX,locationY;

    @FXML
    void dragAndDrop() {
        navBarPanel.setOnMousePressed((MouseEvent event) -> {
            locationX = Application.stageInstance.getX() - event.getScreenX();
            locationY = Application.stageInstance.getY() - event.getScreenY();
        });

        navBarPanel.setOnMouseDragged((MouseEvent event) -> {
            Application.stageInstance.setX(event.getScreenX() + locationX);
            Application.stageInstance.setY(event.getScreenY() + locationY);
        });
    }
}
