package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Loading {
    private static final Pane mainBGCPane = new Pane();
    private static final Pane mainPane = StatusContainer.currentPageController.mainPane;
    private static final int width = 1280;
    private static final int height = 832;
    private static final double opacity = 0.3;



    private static void addBackgroundPane() {
        mainBGCPane.setPrefSize(width, height);
        mainBGCPane.setLayoutX(0);
        mainBGCPane.setLayoutY( 100);
        //mainBGCPane.getStylesheets()
        //        .add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/Loading.css")).toExternalForm());
        //mainBGCPane.getStyleClass().add("mainBGCPane");
        mainBGCPane.setStyle("-fx-background-color: #000000; -fx-opacity: "+opacity+";");
        mainPane.getChildren().add(mainBGCPane);
    }

    private static void addLodingImage(){
        //String path = "file:src/main/resources/com/example/car_rental_sys/image/UI/loading-green.gif";
        String path = "file:src/main/resources/com/example/car_rental_sys/image/UI/loading-blue.gif";
        ImageView imageView = new ImageView(new Image(path));
        int imageXY = 150;

        imageView.setFitWidth(imageXY);
        imageView.setFitHeight(imageXY);
        imageView.setLayoutX((width - imageXY) / 2);
        imageView.setLayoutY((height - imageXY) / 2 );
        mainPane.getChildren().add(imageView);
    }

    public static void show() {
        addBackgroundPane();
        addLodingImage();
    }

    public static void hide() {
        mainPane.getChildren().remove(mainBGCPane);
    }
}
