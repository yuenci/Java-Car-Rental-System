package com.example.car_rental_sys.ui_components;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class PaymentCardList extends Pane {

    public static PaymentCardList instance;

    public PaymentCardList(ArrayList<String[]> cardDetails){
        instance = this;

        //count the cardDetails size
        int size = cardDetails.size();

        this.setPrefWidth(size * 150 + 18);
        this.setPrefHeight(90);
        //this.setPrefWidth(300);
    }

    public void createCardList(){

    }

    public void addCard(PaymentCard card){
        this.getChildren().add(card);
    }

    public void removeCard(PaymentCard card){
        this.getChildren().remove(card);
    }

    public void clear(){
        this.getChildren().clear();
    }
}
