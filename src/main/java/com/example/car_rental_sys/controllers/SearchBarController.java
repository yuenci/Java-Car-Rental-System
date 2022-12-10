package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SearchBarController {

    public int filterState = 0;

    public static SearchBarController instance;

    @FXML
    private Button filterBtn;
    @FXML
    private TextField searchBar;

    public SearchBarController() {
        instance = this;
    }

    public void setFilterState(int state){
        filterState = state;
    }

    @FXML
    private void initialize(){
        searchBar.setEditable(false);
        filterBtn.setDisable(true);
    }

    @FXML
    private void filterBtnClicked() {
        //System.out.println("hi");
        if (filterState == 0){
            filterState = 1;
            OrderListComponentController.instance.addFilterPane();
        }else{
            filterState = 0;
            OrderListComponentController.instance.removeFilterPane();
        }
    }
}
