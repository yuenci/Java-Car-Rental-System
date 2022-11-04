package com.example.car_rental_sys;

import com.example.car_rental_sys.sqlParser.SQL;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class PaymentController {
    @FXML
    ImageView carImageView;

    @FXML
    Label modelText;

    @FXML
    Label priceText;

    @FXML
    Label carChooseText;

    @FXML
    Button carChooseBtn;

    @FXML
    RadioButton radioBtn1;

    @FXML
    RadioButton radioBtn2;

    @FXML
    Label balanceText;

    @FXML
    ScrollPane bankCardsContainer;

    @FXML
    Label cardTypeAndNum;

    @FXML
    Label expiresDate;

    @FXML
    Button downIconBtn;

    @FXML
    ImageView bankcardImage;

    ArrayList<BankCard> bankCards = new ArrayList<>();

    boolean isBankCardSelecterShow = false;

    public  static  PaymentController paymentControllerInstance;


    final String imageFileRoot = "src/main/resources/com/example/car_rental_sys/image/cars/";

    @FXML
    public void initialize() {
        initCarImage();
        initPrice();
        initCarChooseHoverEvent();
        initRadioEvent();
        textAlignment();
        initBankCardChooser();
        paymentControllerInstance = this;
    }


    private  void   initCarImage(){
        modelText.setText(StatusContainer.currentCarChose.replace("_"," "));

        File file = new File(imageFileRoot + StatusContainer.currentCarChose + ".png");
        Image image = new Image(file.toURI().toString());
        carImageView.setImage(image);

        Translate flipTranslation = new Translate(carImageView.getImage().getWidth() - 520,0);
        Rotate flipRotation = new Rotate(180,Rotate.Y_AXIS);
        carImageView.getTransforms().addAll(flipTranslation,flipRotation);
    }

    private  void initPrice(){
        String sql = "SELECT price FROM cars WHERE carModel = '"+StatusContainer.currentCarChose +"'";
        ArrayList<String[]> result = SQL.query(sql);
        priceText.setText("RM" + result.get(0)[0]);
    }

    private void  initCarChooseHoverEvent(){
        carChooseBtn.setOnMouseEntered(event -> changeCarChooseStyle("hover"));
        carChooseBtn.setOnMouseExited(event -> changeCarChooseStyle("default"));
        carChooseText.setOnMouseEntered(event -> changeCarChooseStyle("hover"));
        carChooseText.setOnMouseExited(event -> changeCarChooseStyle("default"));
    }

    private void  changeCarChooseStyle(String style){
        carChooseBtn.getStyleClass().remove("chooseCarBtnDefault");
        carChooseText.getStyleClass().remove("chooseCarTextDefault");
        carChooseBtn.getStyleClass().remove("chooseCarBtnHover");
        carChooseText.getStyleClass().remove("chooseCarHover");

        //carChooseText.setPrefSize();
        if(Objects.equals(style, "default")){
            carChooseBtn.getStyleClass().add("chooseCarBtnDefault");
            carChooseText.getStyleClass().add("chooseCarTextDefault");
        }else if(Objects.equals(style, "hover")){
            carChooseBtn.getStyleClass().add("chooseCarBtnHover");
            carChooseText.getStyleClass().add("chooseCarHover");
        }
    }

    private void initRadioEvent(){
        ToggleGroup group = new ToggleGroup();
        radioBtn1.setToggleGroup(group);
        radioBtn1.setSelected(true);
        radioBtn2.setToggleGroup(group);
    }

    @FXML
    private  void methodCardClickEvent(MouseEvent event){
        String target = event.getTarget().toString();
        //System.out.println(target);
        if(Objects.equals(target, "Pane[id=methodPaypal]") || Objects.equals(target, "ImageView[id=paypalImage, styleClass=image-view]")){
            radioBtn1.setSelected(true);
        }
        else  if(Objects.equals(target, "Pane[id=methodBankcard]") || Objects.equals(target, "ImageView[id=bankcardImage, styleClass=image-view]")){
            radioBtn2.setSelected(true);
        }
    }

    @FXML void paypalMethodClickEvent( ) {
        radioBtn1.setSelected(true);
    }

    @FXML void bankcardMethodClickEvent() {
        radioBtn2.setSelected(true);
    }

    @FXML
    private void viewCarDetailBtnClickEvent(){
       Tools.changeScence("carDetailsPage.fxml");
    }

    private void textAlignment(){
        balanceText.setAlignment(Pos.CENTER_RIGHT);
    }

    @FXML
    private void goToCarListPage(){
        Tools.changeScence("carsList.fxml");
    }

    @FXML
    private void showBankCardChooser(){
        if(!isBankCardSelecterShow){
            bankCardsContainer.setVisible(true);
            isBankCardSelecterShow = true;
        }else {
            bankCardsContainer.setVisible(false);
            isBankCardSelecterShow = false;
        }
       //System.out.println("showBankCardChooser");
    }

    private void initBankCardChooser(){
        bankCardsContainer.setVisible(false);
        bankCardsContainer.setFitToWidth(true);

        VBox vBox = new VBox();
        BankCard bankCard1 = new BankCard("123121122121","visa","12/2020");
        BankCard bankCard2 = new BankCard("123121122121","mastercard","12/2021");
        BankCard bankCard3 = new BankCard("123121122121","mastercard","12/2022");

        vBox.getChildren().addAll(bankCard1,bankCard2,bankCard3);

        bankCards.add(bankCard1);
        bankCards.add(bankCard2);
        bankCards.add(bankCard3);

        bankCardsContainer.setContent(vBox);
    }

    public void updateInfo(String cardTypeAndCardNum, String expiresDate, String cardType){
        this.cardTypeAndNum.setText(cardTypeAndCardNum);
        this.expiresDate.setText(expiresDate);

        Image image = null;
        if(cardType.equalsIgnoreCase("visa")){
            image = Tools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/visa.png");
        }else if(cardType.equalsIgnoreCase("mastercard")){
            image = Tools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/mastercard.png");
        }
        bankcardImage.setImage(image);
    }

}
