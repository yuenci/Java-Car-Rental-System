package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class AboutUsPageController {
    //configuration
    int imageNumMax = 20;

    @FXML
    Label per,use,eng,des,bra;


    @FXML
    Pane paneContainer,wordsContainer,mainPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    WebView webView;

    //private double[] mouseCurrentCoordinate = new double[2];

    @FXML
    private void initialize() {
        //logMouseCoordinate();
        //startDetect();
        //addImage();
        initWeb();
        preventBrowserScrollingSvents();
        setOriginPosition();
        setAnimation();
    }

    private void  initWeb(){
        // load web page webview
        WebEngine engine = webView.getEngine();
        URL url = this.getClass().getResource("/com/example/car_rental_sys/html/aboutUs/index.html");
        engine.load(url.toString());
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


    private void  preventBrowserScrollingSvents(){
        mainPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();
                scrollPane.setVvalue(scrollPane.getVvalue() - event.getDeltaY()/1000 );
            }
        });
    }

    private void setOriginPosition(){
        per.setLayoutX(1280);
        use.setLayoutX(1280);
        eng.setLayoutX(1280);
        des.setLayoutX(1280);
        bra.setLayoutX(1280);
    }

    private void setAnimation1(){
        Translate translate1 = new Translate();
        translate1.setX(1800);
//        TranslateTransition translate = new TranslateTransition();
//        translate.setDuration(Duration.seconds(10));
//        translate.setByX(-1600);
//        translate.setNode(per);
//        translate.play();
//
//        // use time line to do another animation
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
//            translate.setNode(use);
//            translate.play();
//            use.setLayoutX(1280);
//        }));
//        timeline.setCycleCount(50);
//        timeline.play();

        // use time lime do animation in 5 elements, they start at different time
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            per.getTransforms().addAll(translate1);
            TranslateTransition translate = new TranslateTransition();
            translate.setDuration(Duration.seconds(5));
            translate.setByX(-1600);
            translate.setNode(per);
            translate.play();

        }));

        timeline.setCycleCount(50);
        timeline.play();

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(2.1), event -> {
            TranslateTransition translate = new TranslateTransition();
            use.getTransforms().addAll(translate1);
            translate.setDuration(Duration.seconds(5));
            translate.setByX(-1600);
            translate.setNode(use);
            translate.play();

        }));
        timeline2.setCycleCount(50);
        timeline2.play();

        Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(4.1), event -> {
            TranslateTransition translate = new TranslateTransition();
            eng.getTransforms().addAll(translate1);
            translate.setDuration(Duration.seconds(5));
            translate.setByX(-1600);
            translate.setNode(eng);
            translate.play();

        }));
        timeline3.setCycleCount(50);
        timeline3.play();


    }

    private void setAnimation(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            setAnimationForNode(per);
        }));
        timeline.play();

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            setAnimationForNode(use);
        }));
        timeline2.play();

        Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(8), event -> {
            setAnimationForNode(eng);
        }));
        timeline3.play();

        Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            setAnimationForNode(des);
        }));
        timeline4.play();

        Timeline timeline5 = new Timeline(new KeyFrame(Duration.seconds(6), event -> {
            setAnimationForNode(bra);
        }));
        timeline5.play();


    }

    private void  setAnimationForNode(Label node){
        TranslateTransition translate = new TranslateTransition();
        translate.setByX(-1600);
        translate.setDuration(Duration.seconds(12));
        translate.setCycleCount(500);
        translate.setNode(node);
        translate.play();
    }
}
