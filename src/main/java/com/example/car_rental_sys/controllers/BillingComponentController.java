package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.PaymentCard;
import com.example.car_rental_sys.ui_components.UICusBillRow;
import com.example.car_rental_sys.ui_components.UIPagination;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class BillingComponentController {

    public static BillingComponentController instance;

    private int totalBills = 0;
    private ArrayList<String[]> data = null;
    private ArrayList<String[]> cacheData = null;
    private ArrayList<String[]> pendingData = new ArrayList<>();
    private ArrayList<String[]> completedData = new ArrayList<>();
    private ArrayList<String[]> cancelledData = new ArrayList<>();

    private User user = StatusContainer.currentUser;

    @FXML
    private Button billAllBtn, billCancelBtn, billCompleteBtn,billPendingBtn;
    @FXML
    private Pane billCardDetailBox,cusBillTable,pagContainer;
    @FXML
    private ScrollPane billCardList;

    @FXML
    private void initialize(){
        instance = this;
        billAllBtn.getStyleClass().add("focusCusBillButton");
        initTotalOrder();
        addPaymentCardToPane();
        initPagination();
        initTable();
    }

    private void initPagination(){
        pagContainer.getChildren().add(new UIPagination(totalBills,6));
    }

    private void initTotalOrder(){
        totalBills = DataTools.getTotalTransactionNum(user.getUserID());
    }

    private void initTable(){
        String sql = "SELECT * FROM transactionRecord WHERE userID = " + user.getUserID();
        data = SQL.query(sql);
        initUsageArray();

        Platform.runLater(() -> refreshTable(1));

        Thread thread = new Thread(() -> Platform.runLater(() -> {
            for (String[] row : data) {
                if(Integer.parseInt(row[7]) == -1){
                    cancelledData.add(row);
                }
                else if(Integer.parseInt(row[7]) == 1 || Integer.parseInt(row[7]) == 2 || Integer.parseInt(row[7]) == 3 ||
                        Integer.parseInt(row[7]) == 4 || Integer.parseInt(row[7]) == 5){
                    completedData.add(row);
                }
                else if(Integer.parseInt(row[7]) == 0){
                    pendingData.add(row);
                }
            }
        }));
        thread.start();
    }

    private void initUsageArray(){
        ArrayList<String[]> newData = new ArrayList<>();
        for (String[] row : data) {
            String[] newRow = new String[row.length + 1];
            System.arraycopy(row, 0, newRow, 0, row.length);
            row = newRow;
            row[row.length - 1] = String.valueOf(DataTools.getOrderStatusWithID(Integer.parseInt(row[2])));
            newData.add(row);
        }
        data = newData;
        cacheData = data;
    }

    public void refreshTable(int page){
        cusBillTable.getChildren().clear();
        ArrayList<String[]> billData = DataTools.getTableData(data,page,6);
        for (int i = 0; i < billData.size(); i++) {
            String[] row = billData.get(i);
            UICusBillRow billRow = new UICusBillRow(row[3], DateTools.dateToString(row[6],"yyy-MM-dd"), DateTools.dateToString(row[6],"HH:mm:ss"),
                    Integer.parseInt(row[5]), Integer.parseInt(row[7]));
            cusBillTable.getChildren().add(billRow);
            billRow.setLayoutY(i*35);
        }
        initEmptyText();
    }

    private void addPaymentCardToPane(){
        FlowPane pane = new FlowPane();

        PaymentCard visa = new PaymentCard("visa", "123456789123", "Yuenci", "Bank of China", "1000");
        PaymentCard master = new PaymentCard("master", "123456789123", "My Master:)", "Bank of APU", "1000");
        PaymentCard paymentCard = new PaymentCard("paypal", "123456789456", "My PayPal", "Bank of China", "1000");
        PaymentCard payment = new PaymentCard("paypal", "123456789456", "My PayPal", "Bank of China", "1000");
        PaymentCard paymentC = new PaymentCard("paypal", "123456789456", "My PayPal", "Bank of China", "1000");
        PaymentCard[] cards = {visa, master, paymentCard, payment, paymentC};
        for (int i = 0; i < cards.length; i++) {
            PaymentCard card = cards[i];
            if(i == 0){
                card.setLayoutX(0);
            }else{
                card.setLayoutX(i * 150 + i*18);
            }
            card.setLayoutY(0);
            pane.getChildren().add(card);
        }

        pane.setPrefWidth(cards.length * 180);
        pane.setHgap(20);
        pane.setVgap(18);
        pane.setPadding(new Insets(5,5,5,5));
        pane.setStyle("-fx-background-color: transparent;");
        //pane.getChildren().addAll(visa, master, paymentCard);
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

        billCardList.setFitToHeight(true);
        billCardList.setContent(pane);
        billCardList.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    @FXML
    private void billAllBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        data = cacheData;
        recountTotalBills();
        UIPagination.refreshPaginationState();
        refreshTable(1);
    }

    @FXML
    private void billCompleteBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        data = completedData;
        recountTotalBills();
        UIPagination.refreshPaginationState();
        refreshTable(1);
    }

    @FXML
    private void billPendingBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        data = pendingData;
        recountTotalBills();
        UIPagination.refreshPaginationState();
        refreshTable(1);
    }

    @FXML
    private void billCancelBtnClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        data = cancelledData;
        recountTotalBills();
        UIPagination.refreshPaginationState();
        refreshTable(1);
    }

    @FXML
    private void headerButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("focusCusBillButton");
        btn.getStyleClass().remove("cusBillButton");

        data = cacheData;
    }

    private void clearFocusStyle(){
        Button[] btns = {billCompleteBtn,billPendingBtn, billCancelBtn, billAllBtn};
        for (Button btn : btns) {
            btn.getStyleClass().remove("focusCusBillButton");
            btn.getStyleClass().add("cusBillButton");
        }
    }

    private void recountTotalBills(){
        totalBills = data.size();
        pagContainer.getChildren().clear();
        if(totalBills > 0){
            initPagination();
        }
    }

    private void initEmptyText(){
        if (totalBills == 0){
           Label label = new Label("No Bill Records Found");
           label.setStyle("-fx-font-size: 22px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
           label.setLayoutX(120);
           label.setLayoutY(80);
           cusBillTable.getChildren().add(label);
        }
    }
}
