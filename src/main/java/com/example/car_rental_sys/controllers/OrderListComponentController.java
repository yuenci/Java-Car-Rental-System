package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ui_components.UIFilter;
import com.example.car_rental_sys.ui_components.UIPagination;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.function.Function;

public class OrderListComponentController {

    public Label numOrder;
    public Button btnAllOrder,btnComplete,btnContinue,btnCancel;
    public Pane paneFilterBox;
    @FXML
    private Pane pagContainer;
    @FXML
    private Pane tableContainer;

    private static String initQuery = "SELECT * FROM orders WHERE userID = 1";

    @FXML
    public void initialize() {
        // TODO
        initPagination();
        initButtonStyle();
        //addFilterPane();
    }

    //Function<> is a functional interface, which means it can be used as a lambda expression
    private Function<String, String> queryBuilder = (String query) -> query;

    public void addFilterPane(){
        //paneFilterBox = new Pane();
        UIFilter filter = new UIFilter();
        filter.setLayoutX(0);
        filter.setLayoutY(0);
        paneFilterBox.getChildren().add(filter);
        System.out.println("here");
    }

//    public static void removeFilterPane(){
//        paneFilterBox.getChildren().clear();
//    }

    private void initPagination(){
        pagContainer.getChildren().add(new UIPagination());
    }

    private void initButtonStyle(){
        btnAllOrder.getStyleClass().add("btnFocusStyle");
        btnAllOrder.getStyleClass().remove("controlBtn");
    }

    private void initTableData(){
        // TODO
        //get ArrayList<Order> from database
        //init table
        //use a for loop to add each order to the tableContainer
    }

    @FXML
    private void headerButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocuStyle();
        btn.getStyleClass().add("btnFocusStyle");
        btn.getStyleClass().remove("controlBtn");
    }


    private void clearFocuStyle(){
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


}
