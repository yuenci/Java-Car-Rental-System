package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.controllers.showCardDetailsController;
import com.example.car_rental_sys.orm.Card;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;


public class PaymentCard extends Pane {

    private String cardNumber;
    private String cardName;
    private String bankName;
    private String cardBalance;
    private String[] themeColor;
    private String cardType;

    public PaymentCard(String type) {
        this.setPrefSize(150,90);
        this.cardType = type;
        selectCardType(type);
        initEvent();
    }

    public PaymentCard(String type, String number, String cardName, String bankName, String cardBalance) {
        this.setPrefSize(150,90);
        this.cardNumber = number;
        this.cardName = cardName;
        this.bankName = bankName;
        this.cardBalance = cardBalance;
        this.cardType = type;
        selectCardType(type);
        initEvent();
    }

    private void selectCardType(String type){
        switch (type){
            case "visa":
                initBankCard("visa");
                break;
            case "mastercard":
                initBankCard("master");
                break;
            case "tng":
                initWalletCard("tng");
                break;
            case "paypal":
                initWalletCard("paypal");
                break;
            default:
                initAddNewCard();
                break;
        }
    }

    private void initWalletCard(String type){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setLayoutX(10);
        imageView.setLayoutY(6);
        if(Objects.equals(type, "tng")){
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/cardTng.png"));
            imageView.setStyle("-fx-background-color: #ffffff;");
        }else{
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/cardPaypal.png"));
        }

        Label cardName = new Label(this.cardName);
        cardName.setStyle("-fx-text-fill: #86909C; -fx-font-size: 11px; -fx-font-weight: bold;");
        cardName.setLayoutX(44);
        cardName.setLayoutY(10);

        Label cardBalance = new Label("RM "+ this.cardBalance + ".00");
        cardBalance.setStyle("-fx-text-fill: #0E42D2; -fx-font-size: 16px; -fx-font-weight: bold;");
        cardBalance.setLayoutX(16);
        cardBalance.setLayoutY(40);

        this.getChildren().addAll(imageView,cardName,cardBalance);
        this.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-border-color: #C9CDD4; -fx-border-width: 1px;");
    }

    private void initBankCard(String type){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(20);
        imageView.setLayoutX(10);
        imageView.setLayoutY(6);
        if(Objects.equals(type, "visa")){
            imageView.setFitWidth(20);
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/cardVisa.png"));
        }else{
            imageView.setFitWidth(32);
            imageView.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/cardMasterCard.png"));
        }

        //get the last 4 digits of card number
        String last4Digits = this.cardNumber.substring(this.cardNumber.length() - 4);


        Label cardNumber = new Label("**" + last4Digits);
        //Label cardNumber = new Label("**" + last4Digits);
        cardNumber.setStyle("-fx-text-fill: #000000; -fx-font-size: 11px; -fx-font-weight: bold;");
        cardNumber.setLayoutX(10);
        cardNumber.setLayoutY(70);

        //Label bankName = new Label("Au Center Bank");
        Label bankName = new Label(this.bankName);
        bankName.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-font-weight: bold;");
        bankName.setLayoutX(26);
        bankName.setLayoutY(44);

        ImageView wirelessIcon = new ImageView();
        wirelessIcon.setFitHeight(24);
        wirelessIcon.setFitWidth(24);
        wirelessIcon.setLayoutX(118);
        wirelessIcon.setLayoutY(20);
        wirelessIcon.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/wireless.png"));

        this.getChildren().addAll(imageView,cardNumber,bankName, wirelessIcon);
        this.setStyle("-fx-background-color: linear-gradient(to bottom,#200303,#B80000); -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-border-color: #C9CDD4; -fx-border-width: 1px;");

    }

    private void initAddNewCard(){
        ImageView addNewCard = new ImageView();
        addNewCard.setFitHeight(24);
        addNewCard.setFitWidth(24);
        addNewCard.setLayoutX(63);
        addNewCard.setLayoutY(28);
        addNewCard.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/addNewCard.png"));

        Label newCard = new Label("New Card");
        newCard.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: Arial");
        newCard.setLayoutX(47);
        newCard.setLayoutY(61);
        this.setStyle(this.getStyle()+ "-fx-background-color: #ffffff; -fx-border-color: #C9CDD4; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-cursor: hand;");
        this.getChildren().addAll(addNewCard,newCard);
    }

    private void initEvent(){
        this.setOnMouseEntered(event -> this.setCursor(Cursor.HAND));
        this.setOnMouseExited(event -> this.setCursor(Cursor.DEFAULT));

        this.setOnMouseClicked(event -> {
            System.out.println(this.cardType);
            if(cardType.equals("addNewCard")){
                System.out.println("add new card");
                FXTools.changeScene("addBankCardPage.fxml");
            }else{
                StatusContainer.currentCard = new Card(StatusContainer.currentUser.getUserID(),this.cardNumber);
                Platform.runLater(() -> showCardDetailsController.instance.initText());
            }

        });

    }
}
