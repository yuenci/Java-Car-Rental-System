package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;


public class AddBankCardPageController extends Controller {

    public ImageView backBtn;
    public TextArea billTextArea;
    public CheckBox sendMeCheckBox;
    public Button addCardButton;
    public TextField txtCardHolder;
    public TextField txtCardNumber;
    public TextField txtValidMonth;
    public TextField txtValidDate;
    public TextField txtCardName;
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
    private boolean billingAddressFormat = false;
    private boolean cardHolderFormat = false;
    private boolean cardNumberFormat = false;
    private boolean validMonthFormat = false;
    private boolean validDateFormat = false;
    private boolean cardNameFormat = false;

    private String cardType = "";


    @FXML
    private void initialize(){
        initRadioGroup();
        setCardImages();
        initMainPaneEvent();
        initBanksChoiceBox();
        initComponentEvent();
    }

    private void initComponentEvent() {
        backBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            backToPreviousPage();
        });

        txtCardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            cardNumberFormat = FXTools.validInputCardNumber(txtCardNumber, newValue);
            if (newValue.length() > 16) {
                txtCardNumber.setText(newValue.substring(0, 16));
            }
        });
        txtValidMonth.textProperty().addListener((observable, oldValue, newValue) -> {
            validMonthFormat = FXTools.validInputCardValid(txtValidMonth, newValue);
            if (newValue.length() > 2) {
                txtValidMonth.setText(newValue.substring(0, 2));
            }
        });
        txtValidDate.textProperty().addListener((observable, oldValue, newValue) -> {
            validDateFormat = FXTools.validInputCardValid(txtValidDate, newValue);
            if (newValue.length() > 2) {
                txtValidDate.setText(newValue.substring(0, 2));
            }
        });
        txtCardHolder.textProperty().addListener((observable, oldValue, newValue) -> {
            cardHolderFormat = FXTools.validInputCardValueLength(txtCardHolder, newValue);
            if (newValue.length() > 25) {
                txtCardHolder.setText(oldValue);
            }
        });
        txtCardName.textProperty().addListener((observable, oldValue, newValue) -> {
            cardNameFormat = FXTools.validInputCardValueLength(txtCardName, newValue);
            if (newValue.length() > 25) {
                txtCardName.setText(oldValue);
            }
        });
        billTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            billingAddressFormat = FXTools.validInputBillingAddress(billTextArea, oldValue, newValue);
            if (newValue.length() > 50) {
                billTextArea.setText(oldValue);
            }
        });

        addCardButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (cardHolderFormat && cardNumberFormat && validMonthFormat && validDateFormat && cardNameFormat && billingAddressFormat){
                if(!Objects.equals(cardType, "") && sendMeCheckBox.isSelected()){
                    getValueAndSave();
                    backToPreviousPage();
                    showNotification();
                }
                else{
                    showWarning();
                }
            }else{
                showWarning();
            }
        });
    }

    private void backToPreviousPage(){
        FXTools.changeScene("customerServicePage.fxml");
        CustomerServiceController.instance.showWalletPage();
    }

    private void getValueAndSave(){
        int userID = StatusContainer.currentUser.getUserID();
        String cardType = this.cardType;
        String cardName = txtCardName.getText();
        String bankName = banksComboBox.getValue();
        String cardHolder = txtCardHolder.getText();
        String validDate = txtValidMonth.getText() + "/" + txtValidDate.getText();
        String cardNumber = txtCardNumber.getText();
        String billingAddress = billTextArea.getText();

        String sql = "INSERT INTO bankCardInfo VALUES ('" +
                userID + "', '" + cardType + "', '" + cardName + "', '" + bankName + "', '" + cardHolder + "', '" +
                validDate + "', '" + cardNumber + "', '" + billingAddress + "', " + 0 + "," + 0 + ");";
        System.out.println(sql);
        System.out.println(SQL.execute(sql));
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
            //System.out.println("mastercard");
            cardType = "mastercard";
        }else if(target == tngRadio){
            cardImageMain.setImage(tngImage);
            //System.out.println("tng");
            cardType = "tng";
        }
        else if(target == paypalRadio){
            cardImageMain.setImage(paypalImage);
            //System.out.println("paypal");
            cardType = "paypal";
        }
        else if(target == visaRadio){
            cardImageMain.setImage(visaImage);
            //System.out.println("visa");
            cardType = "visa";
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

    private void showNotification(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.SUCCESS, "Card added successfully");
        messageFrame.show();
    }

    private void showWarning(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Please fill in all the blanks");
        messageFrame.show();
    }
}
