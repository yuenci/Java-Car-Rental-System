package com.example.car_rental_sys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class NavigationBarController {
    @FXML
    private ImageView closeIconBtn;

    @FXML
    private ImageView logoImage;

    @FXML
    private Pane navBarPanel;

    @FXML
    public static NavigationBarController navigationBarControllerInstance;

    @FXML
    private void initialize() {
//        System.out.println("Hello World!");
        dragAndDrop();
        navigationBarControllerInstance = this;
    }

    @FXML
    private final String imagefolderRoot = "src/main/resources/com/example/car_rental_sys/image/UI/";

    @FXML
    private void contactBtnClick() {
        System.out.println("contactBtnClick");
    }

    @FXML
    private void aboutBtnClick() {
        System.out.println("aboutBtnClick");
    }

    @FXML
    private void homeBtnClick(ActionEvent actionEvent) {
        new Tools().reSetScene(actionEvent,"mainPage.fxml");
    }

    @FXML
    private void serviceBtnClick(ActionEvent actionEvent) {
        new Tools().reSetScene(actionEvent,"signUp.fxml");
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

    @FXML
    public void changeLogoImage(String type){
//        System.out.println(type + "lallala");
        String fileUrl = null;
        if (type.equalsIgnoreCase("green")){
            fileUrl = imagefolderRoot + "/logo_dark.png" ;
        }else if (type.equalsIgnoreCase("blue")){
            fileUrl = imagefolderRoot +"/logo_dark_blue.png";
        }
        assert fileUrl != null;
        File file = new File(fileUrl);
        Image image = new Image(file.toURI().toString());
        logoImage.setImage(image);
    }
}
