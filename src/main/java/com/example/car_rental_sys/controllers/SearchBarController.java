package com.example.car_rental_sys.controllers;

import javafx.scene.input.MouseEvent;

public class SearchBarController {

    public int filterState = 0;

    public static SearchBarController instance;

    public SearchBarController() {
        instance = this;
    }

    public void setFilterState(int state){
        filterState = state;
    }
    public void filterBtnClicked(MouseEvent mouseEvent) {
        System.out.println("hi");
        if (filterState == 0){
            filterState = 1;
            OrderListComponentController.instance.addFilterPane();
        }else{
            filterState = 0;
            OrderListComponentController.instance.removeFilterPane();
        }
    }
}
