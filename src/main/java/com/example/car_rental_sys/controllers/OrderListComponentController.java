package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.ui_components.UIFilter;
import com.example.car_rental_sys.ui_components.UIOrderRow;
import com.example.car_rental_sys.ui_components.UIPagination;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.function.Function;

public class OrderListComponentController {

    @FXML
    private Pane panelOrderList;
    private int totalOrders = 0;
    public Label numOrder;
    public Button btnAllOrder,btnComplete,btnContinue,btnCancel;
    public Pane paneFilterBox;
    @FXML
    private Pane pagContainer;
    @FXML
    private Pane tableContainer,mainPane;

    @FXML
    private ImageView carImg;

    //private static String initQuery = "SELECT * FROM orders WHERE userID = " + User.instance.getUserID();

    public static OrderListComponentController instance;

    @FXML
    public void initialize() {
        instance = this;
        initTotalOrder();
        initPagination();
        initButtonStyle();
        initTable();
        initTheme();
        //System.out.println(User.instance.getUserID());
    }

    public void addFilterPane(){
        UIFilter filter = new UIFilter();
        paneFilterBox.getChildren().add(filter);
        System.out.println("here");
    }

    public void removeFilterPane(){
        SearchBarController.instance.setFilterState(0);
        Thread thread = new Thread(() -> Platform.runLater(() -> paneFilterBox.getChildren().clear()));
        thread.start();
    }

    private void initPagination(){
        pagContainer.getChildren().add(new UIPagination());
    }

    private void initButtonStyle(){
        btnAllOrder.getStyleClass().add("btnFocusStyle");
        btnAllOrder.getStyleClass().remove("controlBtn");
    }

    private void initTotalOrder(){
        numOrder.setText(totalOrders + " orders");
    }

    private void initTable(){
        for (int i = 0; i < 15; i++) {
            UIOrderRow orderRow = new UIOrderRow("1", "Myvi Pro 2021", "Dec 3, 2022", 999, 1);
            orderRow.setLayoutY(i * 30);
            tableContainer.getChildren().add(orderRow);
        }
    }

    @FXML
    private void headerButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("btnFocusStyle");
        btn.getStyleClass().remove("controlBtn");
    }


    private void clearFocusStyle(){
        Button[] btns = {btnAllOrder,btnComplete,btnContinue,btnCancel};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnFocusStyle");
            btn.getStyleClass().add("controlBtn");
        }
    }

    @FXML
    void btnAllOrderClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        //replaceButtonStyle();
    }

    @FXML
    void btnCompletedClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        //replaceButtonStyle();
    }

    @FXML
    void btnContinueClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        //replaceButtonStyle();
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        //replaceButtonStyle();
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelOrderList.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/orderListComponentDark.css")
                            .toURI().toString());
        }
    }


}
