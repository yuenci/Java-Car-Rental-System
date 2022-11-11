package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class BankCard extends Pane {
    private String cardNumber;
    private String cardType;
    private String cardExpireDate;

    private String cardTypeAndNumberText;
    private String cardExpireDateText;

    private  ImageView choseDone;
    public BankCard(String cardNumber, String cardType, String cardExpireDate) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cardExpireDate = cardExpireDate;
        initialize();
    }

    private void initialize() {
        initSize();
        initStyle();
        initCardEvent();
        initComponents();
        initChoseDoneVisible();
    }

    private void initSize() {
        this.setPrefSize(720, 84);
    }

    private void initStyle(){
        this.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-padding: 10px;");
    }

    private void initCardEvent(){
        this.setOnMouseClicked(event -> {
            //System.out.println("Card Clicked");
            StatusContainer.currentPaymentMethod = this.cardType;
            ArrayList<BankCard> bankCards = StatusContainer.paymentControllerInstance.bankCards;
            for (BankCard bankCard : bankCards) {
                bankCard.setChoseDoneInvisible();
            }
            choseDone.setVisible(true);
            StatusContainer.paymentControllerInstance.updateInfo(this.cardTypeAndNumberText, this.cardExpireDateText,this.cardType);
            StatusContainer.radioBtn2.setSelected(true);
        });

        this.setOnMouseEntered(event -> this.setStyle("-fx-background-color: #f3f3f5; -fx-background-radius: 10px; -fx-padding: 10px;"));

        this.setOnMouseExited(event -> this.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-padding: 10px;"));
    }

    private  void  initComponents(){
        StatusContainer.currentChooseBankCardNum = this.cardNumber;
        this.cardTypeAndNumberText = cardType + " " + "****" +  cardNumber.substring(cardNumber.length() - 4);
        Label cardTypeAndNumber = new Label(cardTypeAndNumberText);
        cardTypeAndNumber.setLayoutX(140);
        cardTypeAndNumber.setLayoutY(20);
        cardTypeAndNumber.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        this.cardExpireDateText = "Expires on " +  this.cardExpireDate;
        Label cardExpireDate = new Label(cardExpireDateText);
        cardExpireDate.setLayoutX(140);
        cardExpireDate.setLayoutY(45);
        cardExpireDate.setStyle("-fx-font-size: 14px; -fx-text-fill: #838383; -fx-background-radius: 5px;");

        ImageView cardTypeImage = new ImageView();
        Image image = null;
        if(this.cardType.equalsIgnoreCase("visa")){
            image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/visa.png");
        }else if(this.cardType.equalsIgnoreCase("mastercard")){
            image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/mastercard.png");
        }
        cardTypeImage.setImage(image);
        cardTypeImage.setFitHeight(44);
        cardTypeImage.setFitWidth(56);
        cardTypeImage.setLayoutX(40);
        cardTypeImage.setLayoutY(20);

        choseDone = new ImageView();
        choseDone.setImage(ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/chose.png"));
        choseDone.setFitHeight(25);
        choseDone.setFitWidth(25);
        choseDone.setLayoutX(650);
        choseDone.setLayoutY(30);

        this.getChildren().addAll(cardTypeAndNumber, cardExpireDate, cardTypeImage,choseDone);

    }

    private void initChoseDoneVisible(){
        choseDone.setVisible(false);
    }


    private void setChoseDoneInvisible(){
        choseDone.setVisible(false);
    }
}
