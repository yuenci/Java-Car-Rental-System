package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.orm.Customer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class NavigationBarControllerShort {
    @FXML
    private ImageView closeIconBtn,logoImage;

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

    private String iconPath = imagefolderRoot +"closeIconBlack.png";

    private void initIconShowEvent(){
        //navBarPanel.setVisible(false);
        if(StatusContainer.currentUser instanceof Customer){
            //System.out.println("yes is customer");
            iconPath = imagefolderRoot +"closeIconWhite.png";
            changeImage(iconPath,closeIconBtn);
        }

        navBarPanel.setOnMouseEntered(mouseEvent -> {
            closeIconBtn.setVisible(true);
            logoImage.setVisible(true);
        });

        navBarPanel.setOnMouseExited(mouseEvent -> {
            closeIconBtn.setVisible(false);
            logoImage.setVisible(false);
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
        changeImage(iconPath,closeIconBtn);
    }

    @FXML
    public void LogoClickEvent(){
        FXTools.changeScene("mainPage.fxml");

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
