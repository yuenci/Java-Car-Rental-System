package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ui_components.BrowserModal;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Function;


public class MainPageController extends Controller{
    public static MainPageController mainPageController;
    @FXML
    public Pane mainPane;

    @FXML
    private ImageView carImage;


    @FXML
    private Button searchBtn;

    @FXML
    private Button openCatalogBtn;

    @FXML
    private Label pickUpDateLabel;

    @FXML
    private Label returnDateLabel;

    @FXML
    private Label introLabel;

    @FXML
    private  Label locationBtn;

    @FXML
    private  Label pickUpDateBtn;

    @FXML
    private  Label returnDateBtn;

    @FXML
    public  Label locationText;

    @FXML
    Pane operationBar;

    @FXML
    private String imagefolderRoot = "src/main/resources/com/example/car_rental_sys/image/UI/";

    @FXML
    private String[] carImageList = new String[]{"/600LT-Spider-rg1.png","/grearcar.png",
            "/Koenigsel.png","/Tuatara_Striker.png"};
    @FXML
    private int currentCarImageIndex = 0;

    @FXML
    public void initialize() {
            //System.out.println("init");
        addScrollEvent();
        addKeyPressEvent();
        initDateTimeLabel();
        initIntroLabel();
        mainPageController = this;
        StatusContainer.currentPageController = this;
    }
    private void initIntroLabel(){
        introLabel.setWrapText(true);
        String text =
                "We want you to have a stress-free\n"+
                        "rental experience, so we make it easy to\n"+
                        "hire a car - by providing simple car\n"+
                        "rental app with customer reviews and\n"+
                        "plenty of pick-up locations across the \ncities.\n";

        introLabel.setText(text);
    }

    @FXML
    void addScrollEvent() {
        mainPane.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            //System.out.println(deltaY);
            if (deltaY < 0) {
                changeCardImage(1);
            }else{
                changeCardImage(-1);
            }

        });
    }

    void addKeyPressEvent() {
        mainPane.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case DOWN:
                case LEFT:
                    changeCardImage(1); break;
                case UP:
                case RIGHT:
                    changeCardImage(-1); break;
            }
        });
    }

    void changeCardImage(int direction){
        int nextCarImageIndex;
        if (direction < 0) {
            nextCarImageIndex = currentCarImageIndex + 1;
            if(nextCarImageIndex >= carImageList.length){
                nextCarImageIndex = 0;
            }

//                System.out.println(carImageList[nextCarImageIndex]);
        }else{
            nextCarImageIndex = currentCarImageIndex - 1;
            if(nextCarImageIndex < 0){
                nextCarImageIndex = carImageList.length - 1;
            }
        }
        changeImage(imagefolderRoot +carImageList[nextCarImageIndex],carImage);
        currentCarImageIndex = nextCarImageIndex;
        changeStyleAccordingIndex(currentCarImageIndex);
    }

    @FXML
    void initDateTimeLabel(){
        Date currentDate = new Date();
        Date dateaAfterOneWeek = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);

        if(StatusContainer.pickDateTime == null){
            pickUpDateLabel.setText(farmatDateTime(currentDate));
            returnDateLabel.setText(farmatDateTime(dateaAfterOneWeek));
        }else{
            pickUpDateLabel.setText(StatusContainer.pickDateTime);
            returnDateLabel.setText(StatusContainer.returnDateTime);
        }

    }

    String farmatDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        //get month
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        Month month = localDate.getMonth();
        String shortMonth = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        //get return date

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String pickUpDate = dateFormat.format(date).substring(8,10);

        //get time
        String pickUpTime = dateFormat.format(date).substring(12,17);
        //System.out.println(pickUpTime);

        // get am pm
        String ampm = (Integer.parseInt(pickUpTime.substring(0,2)) >= 12) ? "PM" : "AM";

        // return date string
        return shortMonth + " " + pickUpDate + ", " + pickUpTime + " " + ampm;
    }

    @FXML
    void changeImage(String fileUrl,ImageView imageView){
        File file = new File(fileUrl);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
    void changeClass(Button button, String className){
        button.getStyleClass().clear();
        button.getStyleClass().add(className);
    }

    void changeClass(Label button, String className){
        button.getStyleClass().clear();
        button.getStyleClass().add(className);
    }


    @FXML
    public  void changeStyle(String styleType){
        if(styleType.equalsIgnoreCase("green")){
            NavigationBarController.navigationBarControllerInstance.changeLogoImage("green");
            changeClass(searchBtn,"searchBtnGreen");
            changeClass(openCatalogBtn,"openCatalogBtnGreen");
            changeClass(locationBtn,"operaBtnGreen");
            changeClass(pickUpDateBtn,"operaBtnGreen");
            changeClass(returnDateBtn,"operaBtnGreen");
        }else if(styleType.equalsIgnoreCase("blue")){
            NavigationBarController.navigationBarControllerInstance.changeLogoImage("blue");
            changeClass(searchBtn,"searchBtnBlue");
            changeClass(openCatalogBtn,"openCatalogBtnBlue");
            changeClass(locationBtn,"operaBtnBlue");
            changeClass(pickUpDateBtn,"operaBtnBlue");
            changeClass(returnDateBtn,"operaBtnBlue");
        }else {
            System.out.println("styleType error");
        }
    }

    @FXML
    void changeStyleAccordingIndex(int index) {
        if (index%2== 0) {
            changeStyle("green");
        } else if (index%2 == 1) {
            changeStyle("blue");
        } else {
            System.out.println("index error");
        }
    }


    @FXML
    void openCatalogBtnClick(ActionEvent actionEvent) {
        StatusContainer.carDetailsEntrance = "catalog";
        FXTools.changeScene("carsListPage.fxml");
    }

    @FXML
    void locationBtnClick() {
        String url =  "src/main/resources/com/example/car_rental_sys/html/googleMap.html";
        BrowserModal browserModal = new BrowserModal(800,600, url) ;
        browserModal.setModality();
        Function<String, Void> func =  (message) -> {
            if(!Objects.equals(message, "")){
                Platform.runLater(() -> locationText.setText(message));

            }
            return null;
        };

        browserModal.setFunction(func);
        browserModal.show();

    }


    @FXML
    void pickUpBtnClick() {
        showDatePicker();
    }


    @FXML
    void returnBtnClick() {

        //System.out.println("returnBtnClick");
        showDatePicker();
    }

    @FXML
    void searchBtnClick() {
        StatusContainer.carDetailsEntrance = "search";
        if(StatusContainer.pickUpTimeStamp == 0 || StatusContainer.returnTimeStamp == 0){
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING,"Please choose pick up date and return date first");
            messageFrame.show();
        }else if(DataTools.getAvailableCars().size() == 0){
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING,"No available cars");
            messageFrame.show();
        }
        else{
            FXTools.changeScene("carsListPage.fxml");
        }
    }

    public void showDatePicker(){
        String url = ConfigFile.backendPost +  "datePicker/index.html";
        BrowserModal browserModal = new BrowserModal(600, 455, url) ;
        browserModal.setModality();
        Function<String, Void> func = (message) -> {
            if(message.length() == 73){
                String[] messageArray = message.split(";");
                StatusContainer.pickDateTime = messageArray[0];
                StatusContainer.returnDateTime = messageArray[1];



                Platform.runLater(() -> {
                    if(DateTools.validateDate(messageArray[2],messageArray[3])){
                        pickUpDateLabel.setText(StatusContainer.pickDateTime);
                        returnDateLabel.setText(StatusContainer.returnDateTime);

                        StatusContainer.pickUpTimeStamp = DateTools.dateTimeToTimestamp(messageArray[2]);
                        StatusContainer.returnTimeStamp = DateTools.dateTimeToTimestamp(messageArray[3]);
                    }

                });
            }
            return null;
        };

        browserModal.setFunction(func);
        browserModal.show();
    }
}