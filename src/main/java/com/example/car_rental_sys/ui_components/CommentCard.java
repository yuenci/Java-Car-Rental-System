package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.sqlParser.SQL;
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
import java.util.ArrayList;
import java.util.Arrays;

public class CommentCard extends Pane {
    private String commentID;
    private int  cardHeight = 90;
    private int cardWidth = 770;
    private String[] commentData;
    private Button likeBtn, starBtn, replyBtn;
    private boolean liked = false;
    private boolean starred = false;
    public CommentCard(String commentID) {
        super();
        this.commentID = commentID;
        initialize();
    }

    private  void initialize(){
        initData();
        setSize();
        setProfileImage();
        initRightSide();
        //new File("").toURI().toString();
        //src/main/resources/com/example/car_rental_sys/style/carDetailsPage.css
        this.getStylesheets()
                .add(new File("src/main/resources/com/example/car_rental_sys/style/carDetailsPage.css")
                .toURI().toString());


        initEvent();
    }
    private void initData(){
        String sql = "select * from comments where commentID = " + commentID;
        commentData = SQL.query(sql).get(0);
    }

    private void setSize(){
        this.setPrefHeight(cardHeight);
        this.setPrefWidth(cardWidth);
    }

    private  void setProfileImage(){
        String profileImagePathRoot = "src/main/resources/com/example/car_rental_sys/image/avatar/";
        String userID = commentData[3];
        Image image = getImageObjFromPath(profileImagePathRoot + userID +".png");

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
        String userName = SQL.query("select userName from userInfo where userID = " + commentData[3]).get(0)[0];

        HBox hBox = new HBox();
        Label userNameLabel = new Label("  " +userName + "  ");
        Label commentTime = new Label(commentData[6].substring(0,10));

        userNameLabel.setPrefHeight(20);
        userNameLabel.setAlignment(Pos.TOP_CENTER);

        hBox.getChildren().addAll(userNameLabel,commentTime);

        return hBox;
    }

    private  Label getCommentContent(){
        Label commentContent = new Label("  " + commentData[4]);
        commentContent.setPrefHeight(20);
        commentContent.setFont(new Font(14));
        commentContent.setAlignment(Pos.TOP_CENTER);
        return commentContent;
    }


    private  HBox getToolbar(){
        String UIImageRoot = "src/main/resources/com/example/car_rental_sys/image/UI/";

        HBox hBox = new HBox();

        String[] imageNames = {"commentLike.png","commentStar.png","commentReply.png"};
        //System.out.println(Arrays.toString(commentData));
        String[] data = {commentData[7],commentData[8],"Reply"};

        for (int i = 0; i < imageNames.length; i++) {
            Button button = new Button(data[i]);
            ImageView imageView = new ImageView(getImageObjFromPath(UIImageRoot +imageNames[i]));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            button.setGraphic(imageView);
            button.setStyle("-fx-background-color: transparent;");
            button.getStyleClass().add("toolbarButton");
            hBox.getChildren().add(button);

            switch (i){
                case 0:
                    likeBtn = button;
                    break;
                case 1:
                    starBtn = button;
                    break;
                case 2:
                    replyBtn = button;
                    break;
            }
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


    private  void initEvent(){

        likeBtn.setOnAction(event -> {
            if (liked) return;
            int likeNumNew = Integer.parseInt(commentData[7]) + 1;
            String sql = "update comments set likes = "+ likeNumNew +" where commentID = " + commentID;
            System.out.println(sql);
            SQL.execute(sql);
            likeBtn.setText(Integer.parseInt(String.valueOf(likeNumNew))+ "");
            liked = true;
            setNewImage(likeBtn,"commentLiked.png");
        });

        starBtn.setOnAction(event -> {
            if (starred) return;
            int starNumNew = Integer.parseInt(commentData[8]) +  1;
            String sql = "update comments set stars = "+ starNumNew +" where commentID = " + commentID;
            SQL.execute(sql);
            System.out.println(sql);
            starBtn.setText(Integer.parseInt(String.valueOf(starNumNew)) + "");
            starred = true;
            setNewImage(starBtn,"commentStarred.png");
        });

        replyBtn.setOnAction(event -> {
            String sql = "update comments set replies = replies + 1 where commentID = " + commentID;
            SQL.execute(sql);
            //replyBtn.setText(Integer.parseInt(replyBtn.getText()) + 1 + "");
        });
    }

    private  void setNewImage(Button button, String imageName){
        String UIImageRoot = "src/main/resources/com/example/car_rental_sys/image/UI/";
        Image image = ImageTools.getImageObjFromPath(UIImageRoot +imageName );
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        button.setGraphic(imageView);
    }
}
