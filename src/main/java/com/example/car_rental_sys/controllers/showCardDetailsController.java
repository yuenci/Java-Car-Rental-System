package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.orm.Card;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class showCardDetailsController {

    @FXML
    private Button cardEditBtn;
    @FXML
    private TextField txtCardName;
    @FXML
    private Pane themeBlue;
    @FXML
    private Button cardSaveBtn;
    @FXML
    private Label cardName;
    @FXML
    private TextArea txtBillingAddress;
    @FXML
    private Label cardBankName;
    @FXML
    private Label cardHolderName;
    @FXML
    private Pane themeWhite;
    @FXML
    private Pane themeRed;
    @FXML
    private Label cardBillingAddress;
    @FXML
    private Button cardDeleteBtn;
    @FXML
    private Pane themeGreen;
    @FXML
    private Pane themeOrange;
    @FXML
    private Label cardValidDate;
    @FXML
    private Label cardNumber;

    public void themeWhiteClicked(MouseEvent mouseEvent) {
    }

    public void themeRedClicked(MouseEvent mouseEvent) {
    }

    public void themeBlueClicked(MouseEvent mouseEvent) {
    }

    public void themeGreenClicked(MouseEvent mouseEvent) {
    }

    public void themeOrangeClicked(MouseEvent mouseEvent) {
    }

    public void cardEditClicked(MouseEvent mouseEvent) {
        setEditableTrue();
    }

    public void cardSaveClicked(MouseEvent mouseEvent) {
        setEditableFalse();
        getChangesText();
    }

    public void cardDeleteClicked(MouseEvent mouseEvent) {
        setEditableFalse();
    }

    @FXML
    private void initialize(){

    }

    private void showSelectedCardDetails(){
        String[] cardDetails = Card.instance.getCardDetails(1, 123456789);

    }

    private void getChangesText(){
        String name = txtCardName.getText();
        String address = txtBillingAddress.getText();
        setChangesText(name, address);
        //update the changes into database
    }

    private void setChangesText(String name, String address){
        //print cardName and cardBillingAddress
        System.out.println(name);
        txtCardName.setText(name);
        cardName.setText(name);
        txtBillingAddress.setText(address);
        cardBillingAddress.setText(address);
    }

    private void setEditableTrue(){
        cardEditBtn.setVisible(false);
        cardSaveBtn.setVisible(true);
        cardDeleteBtn.setVisible(true);
        txtCardName.setVisible(true);
        txtBillingAddress.setVisible(true);
    }

    private void setEditableFalse(){
        cardEditBtn.setVisible(true);
        cardSaveBtn.setVisible(false);
        cardDeleteBtn.setVisible(false);
        txtCardName.setVisible(false);
        txtBillingAddress.setVisible(false);
    }
}
