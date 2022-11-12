package com.example.car_rental_sys.controllers;

import javafx.scene.input.MouseEvent;

public class SearchBarController {
    public void filterBtnClicked(MouseEvent mouseEvent) {
        //OrderListComponentController.add();
        //OrderListComponentController.addFilterPane();
        System.out.println("hi");
        OrderListComponentController.instance.addFilterPane();
    }
}
