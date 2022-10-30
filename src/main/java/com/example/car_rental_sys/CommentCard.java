package com.example.car_rental_sys;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;

public class CommentCard extends Pane {
    private String commentID;
    private int  cardHeight = 90;
    private int cardWidth = 770;
    public CommentCard(String commentID) {
        super();
        this.commentID = commentID;
        initialize();
    }

    private  void initialize(){
        setSize();
        setProfileImage();
        initRightSide();
        //new File("").toURI().toString();
        //src/main/resources/com/example/car_rental_sys/style/carDetailsPage.css
        this.getStylesheets()
                .add(new File("src/main/resources/com/example/car_rental_sys/style/carDetailsPage.css")
                .toURI().toString());


        //System.out.println("commentID: " + commentID);
    }

    private void setSize(){
        this.setPrefHeight(cardHeight);
        this.setPrefWidth(cardWidth);
    }

    private  void setProfileImage(){
        String profileImagePathRoot = "src/main/resources/com/example/car_rental_sys/image/avatar/";
        Image image = getImageObjFromPath(profileImagePathRoot + "innis.png");

        ImageView imageView =  new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setImage(image);

        imageView.setLayoutX(20);
        imageView.setLayoutY(10);

        this.getChildren().add(imageView);

    }

    private Image getImageObjFromPath(String path){
        File file = new File(path);
        return new Image(file.toURI().toString());
    }

    private  HBox getUserNameAndCommentTime(){
        HBox hBox = new HBox();
        Label userName = new Label("  " +"Innis" + "  ");
        Label commentTime = new Label("2020-12-12");

        userName.setPrefHeight(20);
        userName.setAlignment(Pos.TOP_CENTER);

        hBox.getChildren().addAll(userName,commentTime);

        return hBox;
    }

    private  Label getCommentContent(){
        Label commentContent = new Label("  " +"This is a comment");
        commentContent.setPrefHeight(20);
        commentContent.setFont(new Font(14));
        commentContent.setAlignment(Pos.TOP_CENTER);
        return commentContent;
    }


    private  HBox getToolbar(){
        String UIImageRoot = "src/main/resources/com/example/car_rental_sys/image/UI/";

        HBox hBox = new HBox();

        String[] imageNames = {"commentLike.png","commentStar.png","commentReply.png"};
        String[] data = {"520","333","Reply"};

        for (int i = 0; i < imageNames.length; i++) {
            Button button = new Button(data[i]);
            ImageView imageView = new ImageView(getImageObjFromPath(UIImageRoot +imageNames[i]));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            button.setGraphic(imageView);
            button.setStyle("-fx-background-color: transparent;");
            button.getStyleClass().add("toolbarButton");
            hBox.getChildren().add(button);
        }

        return hBox;

//        vBoxRightSide.getChildren().add(hBox);
    }

    private void initRightSide(){
        VBox vBoxRightSide = new VBox();
        vBoxRightSide.setLayoutX(70);
        vBoxRightSide.setLayoutY(10);


        vBoxRightSide.getChildren().addAll(getUserNameAndCommentTime(),getCommentContent(),getToolbar());

        this.getChildren().add(vBoxRightSide);

        this.setStyle("-fx-border-color: white;-fx-border-style: solid none none none;" );
    }



}
