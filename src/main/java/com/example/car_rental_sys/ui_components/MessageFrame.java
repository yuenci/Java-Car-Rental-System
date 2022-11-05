package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.util.Objects;
import java.util.function.Function;

public class MessageFrame {
    double transparent = 0.7;
    int titleSpace = 5;


    private String type;
    private String message = "";
    private String title = "";
    private Function<Integer, Void> callback = null;

    private final Pane mainPane =StatusContainer.currentPageController.mainPane;
    private final Pane mainBGCPane = new VBox();
    private final VBox messageVPane = new VBox();
    private String iconPath = null;

    public  MessageFrame(String type) {
        this(type,  "");
    }

    public MessageFrame(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageFrame(String type, String message, String title) {
        this.type = type;
        this.message = message;
        this.title = title;
    }

    public void show() {
        addBackgroundPane();
        addMessageBox();
        addIcon();
        addTitle();
        addMessage();
        addBtn();
        System.out.println("MessageFrame.show() called");
    }

    private void addBackgroundPane() {
        mainBGCPane.setPrefSize(1280, 832);
        mainBGCPane.setLayoutX(0);
        mainBGCPane.setLayoutY(0);
        mainBGCPane.getStylesheets()
                .add(new File("src/main/resources/com/example/car_rental_sys/style/messageFrame.css")
                .toURI().toString());
        mainBGCPane.setStyle("-fx-background-color: rgba(0,0,0,"+transparent+");");
        mainPane.getChildren().add(mainBGCPane);
    }

    private void addMessageBox(){
        Pane messageBoxContainer = new Pane();
        int width = 465;
        int height = 235;
        messageBoxContainer.setPrefSize(width, height);
        messageBoxContainer.setLayoutX((1280- width)/2);
        messageBoxContainer.setLayoutY((832- height)/2 - 50);

        messageBoxContainer.setStyle("-fx-background-color: transparent;");

        messageVPane.setPrefSize(width, height);
        messageVPane.setLayoutX((1280- width)/2);
        messageVPane.setLayoutY((832- height)/2 - 50);
        //messageVPane.setAlignment(Pos.CENTER);

        messageVPane.setStyle("-fx-background-color: #2a2a2b;");
        messageBoxContainer.getChildren().add(messageVPane);

        mainBGCPane.getChildren().add(messageBoxContainer);
    }

    private void addIcon(){
        String iconPathRoot = "file:src/main/resources/com/example/car_rental_sys/image/UI/";
        switch (type) {
            case MessageFrameType.SUCCESS:
                iconPath = iconPathRoot + "success.png";
                break;
            case MessageFrameType.ERROR:
                iconPath = iconPathRoot + "error.png";
                break;
            case MessageFrameType.WARNING:
            case MessageFrameType.CONFIRM:
                iconPath = iconPathRoot + "warning.png";
                break;
            case MessageFrameType.NOTIFICATION:
                iconPath = iconPathRoot + "notification.png";
                break;
        }
    }


    private void addTitle(){
        String titleText = title;
        if(Objects.equals(title, "")){
            StringBuilder titleSpaceStr = new StringBuilder();
            titleText = titleSpaceStr.append(" ".repeat(titleSpace)) + type;
        }else{
            titleText = type;
        }
        Label title = new Label(titleText);

        ImageView imageView = new ImageView(new Image(iconPath));
        title.setPrefHeight(80);
        title.setAlignment(Pos.CENTER_LEFT);
        title.setGraphicTextGap(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(title);

        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        title.getStyleClass().add("title");
        title.setGraphic(imageView);
        messageVPane.getChildren().add(borderPane);
    }

    private void  addMessage(){
        Label messageLabel = new Label(message);
        //messageLabel.setPrefHeight(100);

        messageLabel.getStyleClass().add("message");
        VBox.setMargin(messageLabel, new Insets(0, 40, 0, 40));
        messageLabel.wrapTextProperty().setValue(true);
        messageVPane.getChildren().add(messageLabel);
    }

    private void addBtn(){
       if(Objects.equals(type, MessageFrameType.CONFIRM)){
           addTwoBtn();
       }else{
           addSingleBtn();
       }
    }


    private void addSingleBtn(){
        Button btn = new Button("OK");
        btn.getStyleClass().add("btnBlue");
        btn.setPrefSize(100, 30);
        btn.setOnAction(e -> close());

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(btn);
        VBox.setMargin(borderPane, new Insets(40, 0, 0, 0));

        messageVPane.getChildren().add(borderPane);
    }

    private void addTwoBtn(){
        Button btn1 = new Button("OK");
        btn1.getStyleClass().add("btnBlue");
        btn1.setPrefSize(100, 30);
        btn1.setOnAction(e -> callbackfunc());
        messageVPane.getChildren().add(btn1);

        Button btn2 = new Button("Cancel");
        btn2.getStyleClass().add("btnRed");
        btn2.setPrefSize(100, 30);
        btn2.setOnAction(e -> close());

        HBox hBox = new HBox();
        HBox.setMargin(btn1, new Insets(40, 20, 0, 20));
        HBox.setMargin(btn2, new Insets(40, 20, 0, 20));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btn1, btn2);


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(hBox);

        messageVPane.getChildren().add(borderPane);
    }

    private void close(){
        mainPane.getChildren().remove(mainBGCPane);
    }

    private void callbackfunc(){
        if(this.callback != null){
            this.callback.apply(1);
        }else {
            System.out.println("callback is null");
        }

        System.out.println("callbackfunc() called");
    }

    public void setCallbackfunc(Function<Integer, Void> func) {
        this.callback = func;
    }
}
