package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Driver;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.BrowserModal;
import com.example.car_rental_sys.ui_components.CommentCard;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class CarDetailsPageController extends  Controller{
    @FXML
    ImageView carImageView ,logImageView,backBtnImageView,keyTipsImageView;

    @FXML
    WebView webview;

    @FXML
    ScrollPane commentScrollPane;

    @FXML
    Label starText,modelText,speedText,seatsText,powerText,priceText,commentsNum,viewMore;

    @FXML
    Pane containPane,mainPane;

    private String[] carDetailsData = null;

    @FXML
    private void initialize(){
        initData();
        initContainPaneEvent();
        initImage();
        initStar();
        initSpeedSeatsPowerPrise();
        initLogo();
        initComments();
        backBtnClickEvent();
        initKeyTips();
        initWebView();
        StatusContainer.currentPageController = this;
    }
    private void initData(){
        String sql = "SELECT * FROM carModels WHERE carModel = '"+ StatusContainer.currentCarChose +"'";
        ArrayList<String[]> result = SQL.query(sql);
        carDetailsData = result.get(0);
        //System.out.println(Arrays.toString(carDetailsdata));
    }

    private  void initImage(){
        modelText.setText(StatusContainer.currentCarChose.replace("_"," "));

        String imageFileRoot = "src/main/resources/com/example/car_rental_sys/image/cars/";
        File file = new File(imageFileRoot + StatusContainer.currentCarChose + ".png");

        Image image = new Image(file.toURI().toString());
        carImageView.setImage(image);

        //verticalsImage
        Translate flipTranslation = new Translate(carImageView.getImage().getWidth(),0);
        Rotate flipRotation = new Rotate(180,Rotate.Y_AXIS);
        carImageView.getTransforms().addAll(flipTranslation,flipRotation);
    }

    private  void initLogo(){
        Thread thread = new Thread(() -> {
            String logoFileRoot = "src/main/resources/com/example/car_rental_sys/image/cars/logo/";
            String sql = "select carBrand from carModels where carModel = '" + StatusContainer.currentCarChose + "'";
            String brand = SQL.query(sql).get(0)[0];
            //System.out.println(brand);

            Image image = ImageTools.getImageObjFromPath(logoFileRoot + brand + "-logo.png");
            if(image != null){
                logImageView.setImage(image);
            }else{
                logImageView.setImage(ImageTools.getImageObjFromPath(logoFileRoot + "default-logo.png"));
            }
        });
        thread.start();


    }

    private  void initStar(){
        Thread thread = new Thread(() -> {
            if(carDetailsData != null && carDetailsData.length > 8){
                starText.setText(carDetailsData[8]);
            }
        });
        thread.start();

    }

    private void initSpeedSeatsPowerPrise(){
        Thread thread = new Thread(() -> {
            if(carDetailsData != null && carDetailsData.length > 8){
                speedText.setText(carDetailsData[9] + " km/h");
                seatsText.setText(carDetailsData[2]);
                powerText.setText(carDetailsData[10] + " L");
                priceText.setText("RM" + carDetailsData[3] + "/D");
            }
        });
        thread.start();

    }

    private void initComments(){
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(780);

        String model = StatusContainer.currentCarChose;
        int modelID = DataTools.getModelIDFromCarModel(model);
        String sql = "SELECT commentID FROM comments WHERE payload = '"+modelID+"'" + " and type='modelComment'";
        ArrayList<String[]> result = SQL.query(sql);

        commentsNum.setText(result.size() + " comments");

        for (String[] strings : result) {
            //System.out.println(strings[0]);
            CommentCard commentCard = new CommentCard(strings[0]);
            flowPane.getChildren().add(commentCard);
        }


        commentScrollPane.setFitToWidth(true);
        commentScrollPane.setContent(flowPane);
    }

    private void backBtnClickEvent(){
        backBtnImageView.setOnMouseEntered(event -> {
            Image image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/back-blue.png");
            backBtnImageView.setImage(image);
            backBtnImageView.getScene().setCursor(javafx.scene.Cursor.HAND);
        });

        backBtnImageView.setOnMouseExited(event -> {
            Image image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/back-white.png");
            backBtnImageView.setImage(image);
            backBtnImageView.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
        });

        backBtnImageView.setOnMouseClicked(event -> FXTools.changeScene("carsListPage.fxml"));

        viewMore.setOnMouseClicked(event -> FXTools.changeScene("ViewCommentsPage.fxml"));
    }

    private  void initKeyTips(){
        Thread thread = new Thread(() -> {
            for (int i = 0; i <1; i++) {
                try {
                    Thread.sleep(6 * 1000); //set pause time
                    keyTipsImageView.setImage(null);
                    StatusContainer.isFirstViewCar = false;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        if(StatusContainer.isFirstViewCar){
            thread.start();
        }else{
            keyTipsImageView.setImage(null);
        }
    }

    private void initWebView(){
        String sql = "select power,comfort,handing,space,allocation,interior"
                +" from carsRadarData where carModel = '"
                +StatusContainer.currentCarChose +"'";
        //System.out.println(sql);
        String[] radarData = SQL.query(sql).get(0);
        String radarDataStr = Arrays.toString(radarData);
        //System.out.println(radarDataStr);

        WebEngine engine = webview.getEngine();
        URL url = this.getClass().getResource("/com/example/car_rental_sys/html/radar.html");
        Platform.runLater(() -> {
            engine.setJavaScriptEnabled(true);
            engine.getLoadWorker().stateProperty().addListener(
                    (ov, oldState, newState) -> {
                        if (newState == Worker.State.SUCCEEDED) {
                            engine.executeScript("initRadar("+radarDataStr+");");
                        }
                    });
            assert url != null;
        });
        assert url != null;
        engine.load(url.toString());


    }

    private void initContainPaneEvent(){
        mainPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT){
                changeCar(-1);
            }else if(keyEvent.getCode() == KeyCode.RIGHT){
                changeCar(1);
            }
            keyEvent.consume();
        });
    }

    private void changeCar(int index){
        //System.out.println("changeCar:" + index);
        String model = StatusContainer.currentCarChose;
        String sql = "select modelID from carModels where carModel='" + model + "'";
        //System.out.println(sql);
        int modelID = Integer.parseInt(SQL.query(sql).get(0)[0]);

        String sqlFirstModel = "select carModel from carModels where modelID = 1 " ;
        String firstCarModel = SQL.query(sqlFirstModel).get(0)[0];

        if(modelID + index ==0){
            StatusContainer.currentCarChose = firstCarModel;
        }else{
            String sqlNewModel = "select carModel from carModels where modelID = " + (modelID + index);
            try{
                StatusContainer.currentCarChose = SQL.query(sqlNewModel).get(0)[0];
            }catch (Exception e){
                StatusContainer.currentCarChose = firstCarModel;
            }
        }

        FXTools.changeScene("carDetailsPage.fxml");
    }

    @FXML
    private void rentNowBtnClickEvent(){
        // check if user is admin or driver
        if (StatusContainer.currentUser != null){
            if (StatusContainer.currentUser instanceof Admin || StatusContainer.currentUser instanceof Driver) {
                MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM,"You are not allowed to rent a car, please login as a customer");
                messageFrame.setSuccessCallbackFunc((i) -> {
                    FXTools.changeScene("loginPage.fxml");
                    return null;
                });
                messageFrame.setFailedCallbackFunc((i) -> {
                    messageFrame.close();
                    return null;
                });
                messageFrame.show();
                return;
            }
        }


        //check if user choose date
        if(StatusContainer.pickUpTimeStamp == 0 || StatusContainer.returnTimeStamp == 0){
            showDatePicker();
            return;
        }

        //check if user choose location
        if(StatusContainer.pickUpLocation == null){
            showLocationPicker();
            return;
        }


        //check if user is login
        //System.out.println(StatusContainer.currentUser);
        if (StatusContainer.currentUser == null) {
            StatusContainer.loginEntrance = "carDetails";
            FXTools.changeScene("loginPage.fxml");
            return;
        }


        //check if this car is available
        String currentCarChose = StatusContainer.currentCarChose;
        if(!DataTools.ifCarsAvailable(currentCarChose)){
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING,"This car is not available now!");
            messageFrame.show();
            return;
        }


        //check if user has a Driving License
        if(!DataTools.ifCurrentCustomerHasDLNumber()){
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM,"You have not upload your driving license number, do you want to upload it now?");
            messageFrame.setSuccessCallbackFunc((i) -> {
                FXTools.goToSettingPage();
               // System.out.println("go to setting page");
                return null;
            });

            messageFrame.setFailedCallbackFunc((i) -> {
                System.out.println("don't go to setting page upload dl number");
                messageFrame.close();
                return null;
            });
            messageFrame.show();
            return;
        }

        //check if user has phone number
        if(Objects.equals(StatusContainer.currentUser.getPhone(), "null")){
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM,"You have not upload your phone number, do you want to upload it now?");
            messageFrame.setSuccessCallbackFunc((i) -> {
                FXTools.changeScene("customerServicePage.fxml");
                return null;
            });

            messageFrame.setFailedCallbackFunc((i) -> {
                messageFrame.close();
                return null;
            });
            messageFrame.show();
            return;
        }

        makeAPayment();
        sendSystemMsg();
        FXTools.changeScene("paymentPage.fxml");


    }
    private void sendSystemMsg(){
        String pickUpTime = DateTools.timeStampToDateTime(StatusContainer.pickUpTimeStamp);
        String returnTime = DateTools.timeStampToDateTime(StatusContainer.returnTimeStamp);
        String message = "You have made a payment for " + StatusContainer.currentCarChose + " at " + pickUpTime + " from " + returnTime + " to " + returnTime + " successfully!";
        DataTools.sendSystemMessage(StatusContainer.currentUser.getUserID(),message);
    }


    private void makeAPayment(){
        // carStatus - for date
        ArrayList<Integer>  carsAvailable = DataTools.carsAvailable(StatusContainer.currentCarChose);
        String carID = carsAvailable.get(0).toString();
        String modelID = DataTools.getModelIDFromCarModel(StatusContainer.currentCarChose) + "";
        String pickUpTimeStamps = (StatusContainer.pickUpTimeStamp /1000) + "";
        String returnTimeStamps = (StatusContainer.returnTimeStamp /1000) + "";
        String sql1 = "update carStatus set pickUpTime = " + pickUpTimeStamps + ", returnTime = " + returnTimeStamps + " where carID = " + carID + "AND modelID = " + modelID;
        //System.out.println(sql1);

        // orders - for info
        String orderID = DataTools.getNewID("orders") + "";
        String orderTime = DateTools.getNow();
        String pickUpTime = DateTools.timeStampToDateTime(StatusContainer.pickUpTimeStamp);
        String returnTime = DateTools.timeStampToDateTime(StatusContainer.returnTimeStamp);
        String parkingLocation = ConfigFile.parkingLocation;
        String pickUpLocation = StatusContainer.pickUpLocation;
        int userID = StatusContainer.currentUser.getUserID();
        int  price = 0 ; // need to update
        String paymentMethod = "null"; // need to update
        String account = "null"; // need to update
        int status = 0; // need to update
        int star = 0; // need to update
        String sql2 = "insert into orders values (" + orderID + ",'" + orderTime + "','" + pickUpTime + "','" + returnTime + "','" + parkingLocation + "','" + pickUpLocation + "'," + userID + "," + price + ",'" + paymentMethod + "','" + account + "'," + status + "," + star + ")";
        //System.out.println(sql2);

        // schedule - for date
        int scheduleID = DataTools.getNewID("schedule");
        String relate = "null";
        String sql3 = "insert into schedule values (" + scheduleID + "," + orderID + "," + status + "," + relate + ",'" + orderTime + "')";
        //System.out.println(sql3);

        StatusContainer.currentOrderID = Integer.parseInt(orderID);


//        SQL.execute(sql1);
//        SQL.execute(sql2);
//        SQL.execute(sql3);
    }

    private void showDatePicker(){
        String url = ConfigFile.backendPost +  "datePicker/index.html";
        BrowserModal browserModal = new BrowserModal(600, 455, url) ;
        browserModal.setModality();
        Function<String, Void> func = (message) -> {
            if(message.length() == 73 || message.length() == 74){
                String[] messageArray = message.split(";");
                StatusContainer.pickDateTime = messageArray[0];
                StatusContainer.returnDateTime = messageArray[1];

                Platform.runLater(() -> {
                    if(DateTools.validateDate(messageArray[2],messageArray[3])){
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

    private void showLocationPicker(){
        String url =  "src/main/resources/com/example/car_rental_sys/html/googleMap.html";
        BrowserModal browserModal = new BrowserModal(800,600, url) ;
        browserModal.setModality();
        Function<String, Void> func =  (message) -> {
            if(!Objects.equals(message, "")){
                StatusContainer.pickUpLocation = message;
            }
            return null;
        };

        browserModal.setFunction(func);
        browserModal.show();
    }

}
