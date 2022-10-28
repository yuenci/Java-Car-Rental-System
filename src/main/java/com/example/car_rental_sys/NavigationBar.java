package com.example.car_rental_sys;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class NavigationBar {
    @FXML
    private ImageView closeIconBtn;

    @FXML
    private Pane navBarPanel;

    @FXML
    private void initialize() {
//        System.out.println("Hello World!");
        dragAndDrop();
    }

    @FXML
    private String imagefolderRoot = "src/main/resources/com/example/car_rental_sys/image/";

    @FXML
    private void contactBtnClick() {
        System.out.println("contactBtnClick");
    }

    @FXML
    private void carsBtnClick() {
        System.out.println("carsBtnClick");
    }

    @FXML
    private void homeBtnClick() {
        System.out.println("homeBtnClick");
    }

    @FXML
    private void serviceBtnClick() {
        System.out.println("serviceBtnClick");
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
            locationX = HelloApplication.stageInstance.getX() - event.getScreenX();
            locationY = HelloApplication.stageInstance.getY() - event.getScreenY();
        });

        navBarPanel.setOnMouseDragged((MouseEvent event) -> {
            HelloApplication.stageInstance.setX(event.getScreenX() + locationX);
            HelloApplication.stageInstance.setY(event.getScreenY() + locationY);
        });
    }
}
