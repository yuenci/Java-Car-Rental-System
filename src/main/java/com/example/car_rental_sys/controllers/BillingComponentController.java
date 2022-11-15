package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.PaymentCard;
import com.example.car_rental_sys.ui_components.UICusBillRow;
import com.example.car_rental_sys.ui_components.UIPagination;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class BillingComponentController {

    @FXML
    private Button billYearBtn;

    @FXML
    private Button billWeekBtn;

    @FXML
    private Pane billCardDetailBox;

    @FXML
    private Pane cusBillTable;

    @FXML
    private ScrollPane billCardList;

    @FXML
    private Pane pagContainer;

    @FXML
    private Button billMonthBtn;

    @FXML
    private void initialize(){
        billYearBtn.getStyleClass().add("focusCusBillButton");
        addPaymentCardToPane();
        initPagination();
        initTableData();
    }

    private void initPagination(){
        pagContainer.getChildren().add(new UIPagination());
    }

    private void initTableData(){
        //use a thread to load the table data
        Thread thread = new Thread(() -> Platform.runLater(() -> {
            for (int i = 0; i < 4; i++) {
                UICusBillRow row = new UICusBillRow();
                row.setLayoutX(0);
                row.setLayoutY(i * 35);
                cusBillTable.getChildren().add(row);
            }
        }));
        thread.start();
    }

    private void addPaymentCardToPane(){
//        Pane pane = new Pane();
//        String query = "";
//        //String query = "SELECT * FROM payment_card WHERE user_id = ";  //Customer.getID()
//        ArrayList<String[]> cardDetails = SQL.query(query);
//        double width = cardDetails.size() * 150;
//        pane.setPrefWidth(width);
//        pane.setPrefHeight(90);
//        pane.setStyle("-fx-background-color: transparent");
//
//        int i = 0;
//        for (String[] cardDetail : cardDetails) {
//            //assumption: cardDetail[0] = card_type, cardDetail[1] = card_number, cardDetail[2] = card_name, cardDetail[3] = bank_name, cardDetail[4] = card_balance
//            PaymentCard card = new PaymentCard(cardDetail[0], cardDetail[1], cardDetail[2], cardDetail[3], cardDetail[4]);
//            if(i == 0){
//                card.setLayoutX(0);
//            }else{
//                card.setLayoutX(i * 150 + 18);
//            }
//            card.setLayoutY(0);
//            pane.getChildren().add(card);
//            i++;
//        }
//
//        billCardList.setFitToHeight(true);
//        billCardList.setContent(pane);
    }

    @FXML
    private void billYearBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
    }

    @FXML
    private void billMonthBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
    }

    @FXML
    private void billWeekBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
    }

    @FXML
    private void headerButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("focusCusBillButton");
        btn.getStyleClass().remove("cusBillButton");
    }

    private void clearFocusStyle(){
        Button[] btns = {billMonthBtn, billWeekBtn, billYearBtn};
        for (Button btn : btns) {
            btn.getStyleClass().remove("focusCusBillButton");
            btn.getStyleClass().add("cusBillButton");
        }
    }

}
