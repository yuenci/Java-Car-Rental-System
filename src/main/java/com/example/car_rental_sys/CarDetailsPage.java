package com.example.car_rental_sys;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class CarDetailsPage {
    @FXML
    ImageView carImageView;

    @FXML
    WebView webview;

    @FXML
    ScrollPane commentScrollPane;

    private String imageFileRoot = "src/main/resources/com/example/car_rental_sys/image/cars/";

    @FXML
    private void initialize(){
        initImage();
        initComments();
        WebEngine engine = webview.getEngine();

        URL url = this.getClass().getResource("/com/example/car_rental_sys/html/radar.html");
        assert url != null;
        engine.load(url.toString());


        //engine.load("https://www.google.com/");
        //engine.load( getClass().getResource("radar.html").toString() );
    }

    private  void initImage(){
        File file = new File(imageFileRoot + StatusContainer.currentCarChoosed + ".png");
        Image image = new Image(file.toURI().toString());
        carImageView.setImage(image);

        //verticalsImage
        Translate flipTranslation = new Translate(carImageView.getImage().getWidth(),0);
        Rotate flipRotation = new Rotate(180,Rotate.Y_AXIS);
        carImageView.getTransforms().addAll(flipTranslation,flipRotation);
    }

    private void initComments(){
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(780);


        //ArrayList<String[]> carsData = FileOperate.readFileToArray(Config.carsDataPath);
//        for (String[] commentData :commentsData
//        ) {
//            CommentCard commentCard = new CommentCard("comment1");
//            flowPane.getChildren().add(commentCard);
//        }

        for (int i = 0; i < 4; i++) {
            CommentCard commentCard = new CommentCard("comment1");
            flowPane.getChildren().add(commentCard);
        }


        commentScrollPane.setFitToWidth(true);
        commentScrollPane.setContent(flowPane);
    }

}
