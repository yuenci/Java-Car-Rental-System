package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.ui_components.BankCard;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.sqlParser.SQL;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class PaymentController {
    @FXML
    ImageView carImageView,bankcardImage,avatarImageView;

    @FXML
    Label modelText,priceText,carChooseText,balanceText,cardTypeAndNum,expiresDate,totalPrice,customerName;

    @FXML
    Label price,pickUpTime,returnTime,duration,discount,tax;

    @FXML
    Button carChooseBtn,downIconBtn;

    @FXML
    RadioButton radioBtn1,radioBtn2;

    @FXML
    ScrollPane bankCardsContainer;


    public ArrayList<BankCard> bankCards = new ArrayList<>();

    boolean isBankCardSelecterShow = false;


    final String imageFileRoot = "src/main/resources/com/example/car_rental_sys/image/cars/";

    @FXML
    public void initialize() {
        initAvatar();
        initCarImage();
        initPrice();
        initBalance();
        initCarChooseHoverEvent();
        initRadioEvent();
        textAlignment();
        initBankCardChooser();
        initOrderInfo();

        StatusContainer.paymentControllerInstance = this;
        StatusContainer.radioBtn2 = radioBtn2;
    }
    private void initAvatar(){
        Image avatar = ImageTools.getImageObjFromUserID(StatusContainer.currentUser.getUserID());
        Image avatarCircle = ImageTools.getCircleImages(avatar);
        avatarImageView.setImage(avatarCircle);
        customerName.setText(StatusContainer.currentUser.getUserName());
    }

    private void initOrderInfo(){
        Label[] labels = {price, pickUpTime,returnTime,duration,discount,tax};
        for (Label label : labels) {
            label.setAlignment(Pos.CENTER_LEFT);
        }
        int priceNum = DataTools.getCarUnitPriceFromCarModel(StatusContainer.currentCarChose);
        price.setText("RM" + priceNum);

        String pickUpTimeStr = DateTools.timeStampToDateTime(StatusContainer.pickUpTimeStamp);
        String returnTimeStr = DateTools.timeStampToDateTime(StatusContainer.returnTimeStamp);
        int durationNum = DateTools.getHourDiff(StatusContainer.pickUpTimeStamp,StatusContainer.returnTimeStamp);
        int discountNum = DataTools.getDiscountNum(priceNum,durationNum);

        pickUpTime.setText(pickUpTimeStr.substring(0,16));
        returnTime.setText(returnTimeStr.substring(0,16));
        duration.setText(durationNum + " Hours");
        discount.setText(discountNum + "RM");
        tax.setText(DataTools.getTax());

        double totalPriceNum = (priceNum * durationNum - discountNum) * (1+ ConfigFile.tax);
        int totalPriceInt = (int) totalPriceNum;
        totalPrice.setText("RM" + totalPriceInt);
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
        int priceNum = DataTools.getCarUnitPriceFromCarModel(StatusContainer.currentCarChose);
        priceText.setText("RM" + priceNum + "/Hour");
    }

    private void initBalance(){

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
        selectRadioBtn1();
        radioBtn2.setToggleGroup(group);
    }

    @FXML
    private void viewCarDetailBtnClickEvent(){
       FXTools.changeScene("carDetailsPage.fxml");
    }

    private void textAlignment(){
        balanceText.setAlignment(Pos.CENTER_RIGHT);
    }

    @FXML
    private void goToCarListPage(){
        FXTools.changeScene("carsListPage.fxml");
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
            image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/visa.png");
        }else if(cardType.equalsIgnoreCase("mastercard")){
            image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/mastercard.png");
        }
        bankcardImage.setImage(image);
    }


    @FXML
    private  void makePaymentBtnClickEvent(){
        String payMethod = StatusContainer.currentPaymentMethod;
        //System.out.println(payMethod);
        if(Objects.equals(payMethod, "paypal")){
            showPayPalCheckoutPage();
        }else if (Objects.equals(payMethod, "visa") || Objects.equals(payMethod, "mastercard")){
            showBankCardCheckoutPage();
        }
    }

    @FXML
    private void selectRadioBtn1(){
        radioBtn1.setSelected(true);
        StatusContainer.currentPaymentMethod = "paypal";
    }

    @FXML
    private void selectRadioBtn2(){
        radioBtn2.setSelected(true);
        String cardTypeAndCardNum = cardTypeAndNum.getText();
        System.out.println(cardTypeAndCardNum);
        if(cardTypeAndCardNum.contains("Visa")){
            StatusContainer.currentPaymentMethod = "visa";
        }else if (cardTypeAndCardNum.contains("Mastercard")){
            StatusContainer.currentPaymentMethod = "mastercard";
        }
        //System.out.println( StatusContainer.currentPaymentMethod);
    }

    private void showPayPalCheckoutPage(){
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        Browser browser = engine.newBrowser();
        Stage primaryStage = new Stage();


//        browser.navigation().loadUrl("https://html5test.com");
        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/payMethods/new/loginToPaypal.html").getAbsolutePath());
        //browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/payMethods/PayPalCheckout.html").getAbsolutePath());

        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            String message = consoleMessage.message();
            System.out.println(message);
            if(message.equalsIgnoreCase("consentButton clicked")){
                Platform.runLater(() -> {
                    primaryStage.close();
                    FXTools.changeScene("paySuccessPage.fxml");
                });
            }else if(message.equals("login success")){
                Platform.runLater(() -> primaryStage.setTitle("PayPal Checkout"));
            }
        });

        BrowserView view = BrowserView.newInstance(browser);
        Scene scene = new Scene(new BorderPane(view), 800, 700);

        primaryStage.setTitle("Login in to your PayPal account");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            browser.close();
            engine.close();
        });
    }

    private void showBankCardCheckoutPage() {
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        Browser browser = engine.newBrowser();
        Stage primaryStage = new Stage();

        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/payMethods/checkout.html").getAbsolutePath());
        //addData(100, "1575270674@qq.com", "1234567890123456");
        String price = "'" + totalPrice.getText() + "'";
        String email = StatusContainer.currentUser.getEmail() ;
        String cardNum = StatusContainer.currentChooseBankCardNum;


        String jsArgs = String.format("addData(%s, '%s', '%s')", price,email,cardNum);
        // jxBrowser execute JavaScript
        Frame frame = browser.frames().get(0);
        frame.executeJavaScript(jsArgs);


        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            String message = consoleMessage.message();
            System.out.println(message);
            if(message.equalsIgnoreCase("payBtn click")){
                Platform.runLater(() -> {
                    primaryStage.close();
                    FXTools.changeScene("paySuccessPage.fxml");
                });
            }else if(message.equals("backBtn click")){
                Platform.runLater(primaryStage::close);
            }
        });

        BrowserView view = BrowserView.newInstance(browser);
        Scene scene = new Scene(new BorderPane(view), 1100, 700);

        primaryStage.setTitle("Checkout");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            browser.close();
            engine.close();
        });

    }
}
