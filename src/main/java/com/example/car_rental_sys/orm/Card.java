package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;

public class Card {
    private int userID;
    private String cardType;
    private String cardName;
    private String cardBankName;
    private String cardHolderName;
    private String cardValidDate;
    private String cardNumber;
    private String cardBillingAddress;
    private String cardBalance;
    private String[] cardTheme;


    public static Card instance;

    public Card() {
        instance = this;
    }

    public String[] getCardDetails(int userID, String cardNumber){
        // get card details from database
        String query = "SELECT * FROM bankCardInfo WHERE userID = " + userID + " AND cardNumber = "+cardNumber +";";
        ArrayList<String[]> cardDetails = SQL.query(query);
        //get the first row of the result
        return cardDetails.get(0);
    }

    public Card(int userID, String cardNumber){
        String[] cardDetails = getCardDetails(userID, cardNumber);
        this.userID = Integer.parseInt(cardDetails[0]);
        this.cardType = cardDetails[1];
        this.cardName = cardDetails[2];
        this.cardBankName = cardDetails[3];
        this.cardHolderName = cardDetails[4];
        this.cardValidDate = cardDetails[5];
        this.cardNumber = cardDetails[6];
        this.cardBillingAddress = cardDetails[7];
        this.cardTheme = cardDetails[8].split(",");
        this.cardBalance = cardDetails[9];
    }

    public Card(int userID, String cardType, String cardName, String cardBankName, String cardHolderName, String cardValidDate, String cardNumber, String cardBillingAddress, String[] cardTheme) {
        this.userID = userID;
        this.cardType = cardType;
        this.cardName = cardName;
        this.cardBankName = cardBankName;
        this.cardHolderName = cardHolderName;
        this.cardValidDate = cardValidDate;
        this.cardNumber = cardNumber;
        this.cardBillingAddress = cardBillingAddress;
        this.cardTheme = cardTheme;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardBankName() {
        return cardBankName;
    }

    public void setCardBankName(String cardBankName) {
        this.cardBankName = cardBankName;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardValidDate() {
        return cardValidDate;
    }

    public void setCardValidDate(String cardValidDate) {
        this.cardValidDate = cardValidDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardBillingAddress() {
        return cardBillingAddress;
    }

    public void setCardBillingAddress(String cardBillingAddress) {
        this.cardBillingAddress = cardBillingAddress;
    }

    public String[] getCardTheme() {
        return cardTheme;
    }

    public void setCardTheme(String[] cardTheme) {
        this.cardTheme = cardTheme;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    public void update(){
        String query = "UPDATE bankCardInfo SET cardName = '"+this.cardName+"', billingAddress = '"+this.cardBillingAddress+
                "' WHERE userID = "+this.userID+" AND cardNumber = "+this.cardNumber+";";
        //System.out.println(query);
        SQL.execute(query);
    }

    public void delete(){
        String query = "DELETE FROM bankCardInfo WHERE userID = "+this.userID+" AND cardNumber = "+this.cardNumber+";";
        //System.out.println(query);
        SQL.execute(query);
    }
}
