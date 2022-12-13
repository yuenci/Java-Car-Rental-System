package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
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

    private Pane emojiPane;
    Pane emojiPane1 = new Pane();
    Pane emojiPane2 = new Pane();
    Pane emojiPane3 = new Pane();
    Pane emojiPane4 = new Pane();
    Pane emojiPane5 = new Pane();

    Pane greyPane1 = new Pane();
    Pane greyPane2 = new Pane();
    Pane greyPane3 = new Pane();
    Pane greyPane4 = new Pane();
    Pane greyPane5 = new Pane();

    private HBox emojiHBox;
    private ImageView emoji1, emoji2, emoji3, emoji4, emoji5;

    private Button btnPost, btnCancel;

    private Pane txtAreaPane = new Pane();
    private TextArea txtArea = new TextArea();

    private int status = 0;
    private int rating = 0;
    private boolean firstClick = false;


    public UIRating(){
        initialize();
        initBasicComponent();
        initEmojiBox();
        initChildren();
        initButtonEvent();
        initEmojiClickedEvent();
        initEmojiHoverEvent();

        this.getStylesheets()
                .add(new File("src/main/resources/com/example/car_rental_sys/style/messageFrame.css")
                        .toURI().toString());
        initTextArea();
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
        emojiPane.setLayoutX(20);
        emojiPane.setLayoutY(120);
        emojiPane.setPrefSize(350, 90);
        emojiPane.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px;");

        btnPost = new Button("COMMENT");
        btnPost.setPrefSize(120, 45);
        btnPost.getStyleClass().add("btnBlue");
        btnPost.setDisable(true);

        btnCancel = new Button("CANCEL");
        btnCancel.setPrefSize(120, 45);
        btnCancel.getStyleClass().add("btnRed");

        resetButtonDefaultPosition();
    }

    private void initEmojiBox(){
        String path = "file:src/main/resources/com/example/car_rental_sys/image/UI/";
        emojiHBox = new HBox();
        emojiHBox.setStyle("-fx-background-radius: 10px;");
        emojiHBox.setLayoutY(10);

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
            ImageTools.centerImage(emoji);
            //emojiHBox.getChildren().add(emoji);
        }

        Pane[] greyPanes = {greyPane1, greyPane2, greyPane3, greyPane4, greyPane5};
        //use a for loop to set the style of greyPanes
        for (Pane greyPane : greyPanes) {
            greyPane.setLayoutX(0);
            greyPane.setLayoutY(0);
            greyPane.setPrefSize(70, 70);
            greyPane.setStyle("-fx-background-color: #000000; -fx-background-radius: 10px; -fx-opacity: 0.5;");
        }

        Pane[] emojiPanes = {emojiPane1, emojiPane2, emojiPane3, emojiPane4, emojiPane5};
        //add into emojiHBox
        for (int i = 0; i < emojiPanes.length; i++) {
            emojiPanes[i].setPrefSize(70, 70);
            emojiPanes[i].getChildren().addAll(emojis[i], greyPanes[i]);
        }

        //add into emojiHBox
        for (Pane emojiPane : emojiPanes) {
            emojiHBox.getChildren().add(emojiPane);
        }

        emojiPane.getChildren().add(emojiHBox);

    }

    private void initEmojiHoverEvent(){
        emojiPane1.setOnMouseEntered(event -> greyPane1.setVisible(false));
        emojiPane1.setOnMouseExited(event -> mouseExitedEvent(greyPane1));

        emojiPane2.setOnMouseEntered(event -> greyPane2.setVisible(false));
        emojiPane2.setOnMouseExited(event -> mouseExitedEvent(greyPane2));

        emojiPane3.setOnMouseEntered(event -> greyPane3.setVisible(false));
        emojiPane3.setOnMouseExited(event -> mouseExitedEvent(greyPane3));

        emojiPane4.setOnMouseEntered(event -> greyPane4.setVisible(false));
        emojiPane4.setOnMouseExited(event -> mouseExitedEvent(greyPane4));

        emojiPane5.setOnMouseEntered(event -> greyPane5.setVisible(false));
        emojiPane5.setOnMouseExited(event -> mouseExitedEvent(greyPane5));
    }

    private void mouseExitedEvent(Pane pane){
        Pane[] greyPanes = {greyPane1, greyPane2, greyPane3, greyPane4, greyPane5};
        for (Pane greyPane : greyPanes) {
            greyPane.setVisible(true);
        }

        // show currentRating false
        switch (this.rating){
            case 1:
                greyPane1.setVisible(false);
                break;
            case 2:
                greyPane2.setVisible(false);
                break;
            case 3:
                greyPane3.setVisible(false);
                break;
            case 4:
                greyPane4.setVisible(false);
                break;
            case 5:
                greyPane5.setVisible(false);
                break;
        }

    }


    private void initEmojiClickedEvent(){
        emojiPane1.setOnMouseClicked((MouseEvent event) -> {
            if(!firstClick){
                firstClick = true;
                status = 1;
            }
            rating = 1;
            btnPost.setDisable(false);
            greyPane1.setVisible(false);
        });

        emojiPane2.setOnMouseClicked((MouseEvent event) -> {
            if(!firstClick){
                firstClick = true;
                status = 1;
            }
            rating = 2;
            btnPost.setDisable(false);
            greyPane2.setVisible(false);
        });

        emojiPane3.setOnMouseClicked((MouseEvent event) -> {
            if(!firstClick){
                firstClick = true;
                status = 1;
            }
            rating = 3;
            btnPost.setDisable(false);
            greyPane3.setVisible(false);
        });

        emojiPane4.setOnMouseClicked((MouseEvent event) -> {
            if(!firstClick){
                firstClick = true;
                status = 1;
            }
            rating = 4;
            btnPost.setDisable(false);
            greyPane4.setVisible(false);
        });

        emojiPane5.setOnMouseClicked((MouseEvent event) -> {
            if(!firstClick){
                firstClick = true;
                status = 1;
            }
            rating = 5;
            btnPost.setDisable(false);
            greyPane5.setVisible(false);
        });
    }


    private void initChildren(){
        this.getChildren().addAll(lblFeedback, lblText, emojiPane, btnPost, btnCancel);
    }


    private void initButtonEvent(){
        btnPost.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(status == 1){
                //event.consume();
                initActivateState();
                System.out.println("Activate");
                status = 0;
            } else{
                //event.consume();
                initDeactivateState();
                System.out.println("Deactivate");
                String text = txtArea.getText();
                System.out.println("Rating: " + rating + " text: " + text);

                status = 1;
                boolean res = DataTools.storeComment(rating , text, StatusContainer.currentOrder.getOrderID());
                if (res) closeFeedback();
            }
        });
        btnCancel.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> closeFeedback());
    }

    private void closeFeedback(){
        FeedBackFrame.instance.mainPane.getChildren().remove(FeedBackFrame.instance.mainBGPanel);
        rating = 0;
        status = 0;
        firstClick = false;
    }

    private void initActivateState(){
        this.setPrefSize(395, 500);
        setupTextArea();
        resetButtonPosition();
        btnPost.setDisable(true);
    }

    private void initTextArea(){
        txtAreaPane.setLayoutX(25);
        txtAreaPane.setLayoutY(240);
        txtAreaPane.setPrefSize(345, 170);
        txtAreaPane.setStyle("-fx-background-color: #313132; -fx-background-radius: 10px");

        txtArea.setPromptText("leave your comment here");
        txtArea.setLayoutX(0);
        txtArea.setLayoutY(0);
        txtArea.setPrefSize(345, 170);
        txtArea.getStyleClass().add("text-area");
    }

    private void setupTextArea(){
        initTextAreaEvent();
        txtAreaPane.getChildren().add(txtArea);
        this.getChildren().add(txtAreaPane);
    }

    private void initTextAreaEvent(){
        txtArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isBlank() || newValue.length() < 5){
                btnPost.setDisable(true);
            }else{
                btnPost.setDisable(false);
                if (newValue.length() > 50) {
                    txtArea.setText(oldValue);
                }
            }
        });
    }

    private void resetButtonPosition(){
        btnPost.setLayoutX(btnPost.getLayoutX());
        btnPost.setLayoutY(btnPost.getLayoutY() + 170 + 10);

        btnCancel.setLayoutX(btnCancel.getLayoutX());
        btnCancel.setLayoutY(btnCancel.getLayoutY() + 170 + 10);
    }

    private void initDeactivateState(){
        this.setPrefSize(395, 330);
        this.getChildren().remove(txtAreaPane);
        resetButtonDefaultPosition();
        btnPost.setDisable(true);
    }

    private void resetButtonDefaultPosition(){
        btnPost.setLayoutX(25);
        btnPost.setLayoutY(250);

        btnCancel.setLayoutX(250);
        btnCancel.setLayoutY(250);
    }

}
