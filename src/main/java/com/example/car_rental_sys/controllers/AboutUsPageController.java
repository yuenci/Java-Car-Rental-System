package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class AboutUsPageController {
    //configuration
    int imageNumMax = 20;



    @FXML
    Pane paneContainer,wordsContainer;

    //private double[] mouseCurrentCoordinate = new double[2];

    @FXML
    private void initialize() {
        //logMouseCoordinate();
        //startDetect();
        //addImage();
    }

//    private void logMouseCoordinate() {
//        paneContainer.setOnMouseMoved(mouseEvent -> {
//            //System.out.println("mouse Entered");
//            // print the mouse coordinates
//            mouseCurrentCoordinate[0] = mouseEvent.getSceneX();
//            mouseCurrentCoordinate[1] = mouseEvent.getSceneY();
//
//            //System.out.println("Mouse X: " + mouseCurrentCoordinate[0] + " Mouse Y: " + mouseCurrentCoordinate[1]);
//        });
//    }
    
    private void startDetect(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            System.out.println(DateTools.getNow());
            addImage();
        }));
        timeline.setCycleCount(20);
        timeline.play();
    }

    private void addImage(){
        // get mouse coordinate
        ImageView imageView= getRandomImage();
        paneContainer.getChildren().add(imageView);
        wordsContainer.toFront();
    }

    private ImageView getRandomImage(){
        double[] mouseCurrentCoordinate = FXTools.getMousePosition();
        int randomNum = DataTools.getRandomInt(1,imageNumMax);
        String imageSrc = "src/main/resources/com/example/car_rental_sys/image/others/r" + randomNum + ".jpg";
        //System.out.println(imageSrc);
        Image image =ImageTools.getImageObjFromPath(imageSrc);
        ImageView imageView = new ImageView(image);

        int[] size = ImageTools.getImageSize(imageSrc);

        if (size[0] > size[1]){
            imageView.setFitHeight(230);
            imageView.setFitWidth(230 * size[0] / size[1]);
        }else {
            imageView.setFitWidth(280);
            imageView.setFitHeight(280 * size[1] / size[0]);
        }

        double layoutX = mouseCurrentCoordinate[0] -imageView.getFitWidth()/2 >0? mouseCurrentCoordinate[0] -imageView.getFitWidth()/2 : 0;
        double layoutY = mouseCurrentCoordinate[1] -imageView.getFitHeight()/2 >0? mouseCurrentCoordinate[1] -imageView.getFitHeight()/2 : 0;

        imageView.setLayoutX(layoutX);
        imageView.setLayoutY(layoutY);


        return imageView;
    }

}
