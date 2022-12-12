package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Card;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class showCardDetailsController {

    public static showCardDetailsController instance;

    @FXML
    private Button cardEditBtn,cardSaveBtn,cardDeleteBtn;
    @FXML
    private TextField txtCardName;
    @FXML
    private TextArea txtBillingAddress;
    @FXML
    private Label cardName, cardBankName, cardHolderName, cardBillingAddress, cardValidDate, cardNumber,lblTheme;
    @FXML
    private Pane themeWhite, themeRed, themeBlue, themeGreen, themeOrange;

    public showCardDetailsController(){
        instance = this;
    }

    @FXML
    private void initialize(){
        hideColorPlate();
        clearText();
    }

    public void initText(){
        Card card = StatusContainer.currentCard;
        cardName.setText(card.getCardName());
        cardBankName.setText(card.getCardBankName());
        cardHolderName.setText(card.getCardHolderName());
        cardBillingAddress.setText(card.getCardBillingAddress());
        cardValidDate.setText(card.getCardValidDate());
        String number = card.getCardNumber();
        cardNumber.setText("**** **** **** "+number.substring(number.length()-4));
        txtCardName.setText(card.getCardName());
        txtBillingAddress.setText(card.getCardBillingAddress());
    }

    @FXML
    private void cardEditClicked() {
        setComponentVisible(true);
    }

    @FXML
    private void cardSaveClicked() {
        setComponentVisible(false);
        BillingComponentController.instance.refreshCardList();
        getChangesText();
    }

    @FXML
    private void cardDeleteClicked() {
        setComponentVisible(false);
        //delete the card
        clearText();
        StatusContainer.currentCard.delete();
        BillingComponentController.instance.refreshCardList();
    }

    private void getChangesText(){
        String name = txtCardName.getText();
        String address = txtBillingAddress.getText();
        setChangesText(name, address);
        //update the changes into database
        StatusContainer.currentCard.setCardName(name);
        StatusContainer.currentCard.setCardBillingAddress(address);
        StatusContainer.currentCard.update();
    }

    private void setChangesText(String name, String address){
        //print cardName and cardBillingAddress
        System.out.println(name);
        txtCardName.setText(name);
        cardName.setText(name);
        txtBillingAddress.setText(address);
        cardBillingAddress.setText(address);
    }

    private void setComponentVisible(boolean edit){
        cardEditBtn.setVisible(!edit);
        cardSaveBtn.setVisible(edit);
        cardDeleteBtn.setVisible(edit);
        txtCardName.setVisible(edit);
        txtBillingAddress.setVisible(edit);
    }

    public void setColorPlateVisible(boolean visible){
        themeWhite.setVisible(visible);
        themeRed.setVisible(visible);
        themeBlue.setVisible(visible);
        themeGreen.setVisible(visible);
        themeOrange.setVisible(visible);
    }

    private void clearText(){
        txtCardName.setText("");
        txtBillingAddress.setText("");
        cardName.setText("");
        cardBankName.setText("");
        cardHolderName.setText("");
        cardBillingAddress.setText("");
        cardValidDate.setText("");
        cardNumber.setText("");
    }

    @FXML
    private void themeWhiteClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void themeRedClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void themeBlueClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void themeGreenClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void themeOrangeClicked(MouseEvent mouseEvent) {
    }

    private void hideColorPlate(){
        Pane[] colorPlates = {themeWhite, themeRed, themeBlue, themeGreen, themeOrange};
        for (Pane colorPlate : colorPlates) {
            colorPlate.setVisible(false);
        }
        lblTheme.setVisible(false);
    }
}
