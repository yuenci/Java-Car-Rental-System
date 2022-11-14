package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;



public class AddBankCardPageController {

    @FXML
    Pane mainPane,tngType, visaType, paypalType ,typeMastercard;

    @FXML
    RadioButton mastercardRadio, tngRadio, paypalRadio, visaRadio;

    @FXML
    ImageView cardImageMain, cardImage1, cardImage2, cardImage3;

    @FXML
    Image mastercardImage, tngImage, paypalImage, visaImage;

    @FXML
    ScrollPane testScrollPane;

    @FXML
    ComboBox<String> banksComboBox;

    private boolean ifReach = false;

    @FXML
    private  void initialize(){
        initRadioGroup();
        setCardImages();
        initMainPaneEvent();
        initBanksChoiceBox();
    }

    private void initRadioGroup(){
        ToggleGroup group = new ToggleGroup();
        mastercardRadio.setToggleGroup(group);
        tngRadio.setToggleGroup(group);
        paypalRadio.setToggleGroup(group);
        visaRadio.setToggleGroup(group);
    }

    private void setCardImages(){
        mastercardImage = ImageTools.getUIImageObjFromName("MasterCardBlack.png");
        tngImage = ImageTools.getUIImageObjFromName("tng.png");
        paypalImage = ImageTools.getUIImageObjFromName("paypal.png");
        visaImage = ImageTools.getUIImageObjFromName("visaWhite.png");
    }


    @FXML
    public void cardRadioClick(MouseEvent e) {
        cardImageMain.setVisible(true);
        cardImage1.setVisible(false);
        cardImage2.setVisible(false);
        cardImage3.setVisible(false);

        Object target = e.getSource();
        if(target == mastercardRadio){
            cardImageMain.setImage(mastercardImage);
            System.out.println("mastercard");
        }else if(target == tngRadio){
            cardImageMain.setImage(tngImage);
            System.out.println("tng");
        }
        else if(target == paypalRadio){
            cardImageMain.setImage(paypalImage);
            System.out.println("paypal");
        }
        else if(target == visaRadio){
            cardImageMain.setImage(visaImage);
            System.out.println("visa");
        }
    }

    private void initMainPaneEvent(){
        mainPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double currentY = testScrollPane.getLayoutY();
            if(currentY <=262 &&  event.getDeltaY() < 0){
                ifReach = true;
            }else if(currentY > 610){
                return;
            }

            if(ifReach){
                return;
            }
            event.consume();
            double delta = event.getDeltaY();
            //System.out.println(delta);
            if(delta > 0){
                // go down
                testScrollPane.setLayoutY(testScrollPane.getLayoutY() + 10);
            }else{
                // go up
                testScrollPane.setLayoutY(testScrollPane.getLayoutY()- 10);
            }

        });
    }

    private void initBanksChoiceBox(){
        String[] bankList = ConfigFile.banks;

        banksComboBox.setPromptText("Choose the bank");
        banksComboBox.setStyle("-fx-prompt-text-fill: #e4e4e4");
        for(String bank : bankList){
            banksComboBox.getItems().add(bank);
        }
    }
}
