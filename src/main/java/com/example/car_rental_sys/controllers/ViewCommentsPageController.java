package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.CommentCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class ViewCommentsPageController {
    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox vBoxContainer;

    @FXML
    ImageView backBtn,sendIcon;

    @FXML
    Label titleLabel;

    @FXML
    public TextArea textArea;

    public static ViewCommentsPageController instance;

    private final String carModel = StatusContainer.currentCarChose;
    private final int carModelID = DataTools.getModelIDFromCarModel(carModel);


    @FXML
    private void initialize(){
        StatusContainer.ifInCommentPage = true;
        initTitle();
        initScrollPane();
        initBackEvent();
        addCommentCards();
        InitSendIconEvent();
        instance = this;
    }
    private void initTitle(){
        String cardModel = carModel.replace("_", " ");
        titleLabel.setText("Comments On " +cardModel);
    }

    private void initScrollPane(){
        // hide scroll bar
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void initBackEvent(){
        backBtn.setOnMouseClicked(event -> {
            StatusContainer.ifInCommentPage = false;
            FXTools.changeScene("carDetailsPage.fxml");
        });
    }

    private void InitSendIconEvent(){
        sendIcon.setOnMouseClicked(event -> sendIconEvent());
        // enter key event
        textArea.setOnKeyPressed(event -> {
            if(event.getCode().toString().equals("ENTER")) sendIconEvent();
        });
    }

    private void sendIconEvent(){
        int commentID = storeComment();
        System.out.println(commentID);
        addCommentCards(commentID);
        textArea.setText("");
        scrollPane.setVvalue(1.0);
    }

    private int  storeComment(){
        int commentID = DataTools.getNewID("comments");
        String type = "modelComment";
        String payload = carModelID + "";
        int posterID = StatusContainer.currentUser.getUserID();
        String comment = textArea.getText();
        comment = comment.replace(",", "，");
        comment = comment.replace("\n", " ");
        int relevantCommentID = StatusContainer.relevantCommentID;
        String commentDateTime = DateTools.getNow();
        int likes = 0;
        int stars = 0;
        int rating = 0;
        int orderID = 0;

        DataTools.storeCommentToDB(commentID, type, payload, posterID, comment, relevantCommentID, commentDateTime, likes, stars, rating, orderID);
        return  commentID;
    }

    private void addCommentCards(){
        String sql = "SELECT commentID FROM comments WHERE payload = '"+carModelID+"'" + " and type='modelComment'";
        ArrayList<String[]> result = SQL.query(sql);

        for (String[] strings : result) {
            //System.out.println(strings[0]);
            CommentCard commentCard = new CommentCard(strings[0]);
            vBoxContainer.getChildren().add(commentCard);

        }

    }

    private void addCommentCards(int commentID){
        String commentIDStr = String.valueOf(commentID);
        CommentCard commentCard = new CommentCard(commentIDStr);
        vBoxContainer.getChildren().add(commentCard);
    }
}
