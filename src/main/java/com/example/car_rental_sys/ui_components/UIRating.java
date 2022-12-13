package com.example.car_rental_sys.ui_components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;

public class UIRating extends Pane {

    private Label lblFeedback, lblText;

    private Pane emojiPane, txtAreaPane;
    private HBox emojiHBox;
    private ImageView emoji1, emoji2, emoji3, emoji4, emoji5;

    private Button btnPost, btnCancel;

    private TextArea txtArea;

    private int status = 1;

    public UIRating(){
        initialize();
        initBasicComponent();
        initEmojiBox();
        initChildren();
        checkButtonStatus();
        initButtonEvent();

        this.getStylesheets()
                .add(new File("src/main/resources/com/example/car_rental_sys/style/messageFrame.css")
                        .toURI().toString());
    }

    private void initialize(){
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setPrefSize(395,330);
        this.setStyle("-fx-background-color: #313132;");
    }

    private void initBasicComponent(){
        lblFeedback = new Label("Feedback");
        lblFeedback.setLayoutX(120);
        lblFeedback.setLayoutY(34);
        lblFeedback.setStyle("-fx-text-fill: #e8e8e9; -fx-font-size: 30px; -fx-font-weight: 700;");

        lblText = new Label("Your feedback is our motivation");
        lblText.setLayoutX(65);
        lblText.setLayoutY(76);
        lblText.setStyle("-fx-text-fill: #BABABB; -fx-font-size: 18px; -fx-font-weight: 700;");

        emojiPane = new Pane();
        emojiPane.setLayoutX(25);
        emojiPane.setLayoutY(120);
        emojiPane.setPrefSize(355, 90);
        emojiPane.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px;");

        btnPost = new Button("COMMENT");
        btnPost.setPrefSize(120, 45);
        btnPost.getStyleClass().add("btnBlue");

        btnCancel = new Button("CANCEL");
        btnCancel.setPrefSize(120, 45);
        btnCancel.getStyleClass().add("btnRed");

        resetButtonDefaultPosition();

    }

    private void initEmojiBox(){
        String path = "file:src/main/resources/com/example/car_rental_sys/image/UI/";
        emojiHBox = new HBox();
        emojiHBox.setStyle("-fx-background-radius: 10px;");
        emoji1 = new ImageView();
        emoji1.setImage(new Image(path+"e1.gif"));

        emoji2 = new ImageView();
        emoji2.setImage(new Image(path+"e2.gif"));

        emoji3 = new ImageView();
        emoji3.setImage(new Image(path+"e3.gif"));

        emoji4 = new ImageView();
        emoji4.setImage(new Image(path+"e4.gif"));

        emoji5 = new ImageView();
        emoji5.setImage(new Image(path+"e5.gif"));

        ImageView[] emojis = {emoji1, emoji2, emoji3, emoji4, emoji5};
        for (ImageView emoji : emojis) {
            emoji.setFitHeight(70);
            emoji.setFitWidth(70);
            emoji.setPreserveRatio(true);
            emoji.setSmooth(true);
            emoji.setCache(true);
            emojiHBox.getChildren().add(emoji);
        }

        emojiPane.getChildren().add(emojiHBox);
        //emojiHBox.setSpacing(10);
    }

    private void initChildren(){
        this.getChildren().addAll(lblFeedback, lblText, emojiPane, btnPost, btnCancel);
    }

    private void checkButtonStatus(){
        if(status == 0){
            btnPost.setDisable(true);
        }else{
            btnPost.setDisable(false);
        }
    }

    private void initButtonEvent(){
        btnPost.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(status == 1){
                //event.consume();
                initActivateState();
                System.out.println("Activate");
                status = 0;
            }else{
                //event.consume();
                initDeactivateState();
                System.out.println("Deactivate");
                status = -1;
            }
        });
    }

    private void initActivateState(){
        this.setPrefSize(395, 500);
        initTextArea();
        resetButtonPosition();
    }

    private void initTextArea(){
        txtAreaPane = new Pane();
        txtAreaPane.setLayoutX(25);
        txtAreaPane.setLayoutY(240);
        txtAreaPane.setPrefSize(345, 170);
        txtAreaPane.setStyle("-fx-background-color: #313132; -fx-background-radius: 10px");

        txtArea = new TextArea();
        txtArea.setPromptText("leave your comment here");
        txtArea.setLayoutX(0);
        txtArea.setLayoutY(0);
        txtArea.setPrefSize(345, 170);
        txtArea.getStyleClass().add("text-area");

        txtAreaPane.getChildren().add(txtArea);

        this.getChildren().add(txtAreaPane);
    }

    private void resetButtonPosition(){
        btnPost.setLayoutX(btnPost.getLayoutX());
        btnPost.setLayoutY(btnPost.getLayoutY() + 170 + 10);

        btnCancel.setLayoutX(btnCancel.getLayoutX());
        btnCancel.setLayoutY(btnCancel.getLayoutY() + 170 + 10);
    }

    private void initDeactivateState(){
        this.setPrefSize(395, 330);
        this.getChildren().remove(txtArea);
        resetButtonDefaultPosition();
    }

    private void resetButtonDefaultPosition(){
        btnPost.setLayoutX(25);
        btnPost.setLayoutY(250);

        btnCancel.setLayoutX(250);
        btnCancel.setLayoutY(250);
    }

}
