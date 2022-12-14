package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Driver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class NavigationBaMsgrController {
    @FXML
    private ImageView closeIconBtn;

    @FXML
    private Pane navBarPanel,transparentBtn;

    @FXML
    public static NavigationBaMsgrController navigationBarControllerInstance;


    @FXML
    private void initialize() {
//        System.out.println("Hello World!");
        initIconShowEvent();
        dragAndDrop();
        intiBackEvent();
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

        closeIconBtn.setVisible(true);
    }

    private void  intiBackEvent(){
        transparentBtn.setOnMouseClicked(event -> {
            backToMainPage();
        });
    }

    private void backToMainPage(){
        Platform.runLater(() -> {
            try {
                if(StatusContainer.currentUser instanceof Admin){
                    FXTools.changeScene("adminServicePage.fxml");
                }else if (StatusContainer.currentUser instanceof Driver){
                    FXTools.changeScene("driverServicePage.fxml");
                }else if (StatusContainer.currentUser instanceof Customer){
                    FXTools.changeScene("customerServicePage.fxml");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
