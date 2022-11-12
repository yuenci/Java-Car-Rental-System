package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.ui_components.CommentCard;
import com.example.car_rental_sys.sqlParser.SQL;
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

public class CarDetailsPageController {
    @FXML
    ImageView carImageView;

    @FXML
    ImageView logImageView;

    @FXML
    ImageView backBtnImageView;

    @FXML
    ImageView keyTipsImageView;

    @FXML
    WebView webview;

    @FXML
    ScrollPane commentScrollPane;

    @FXML
    Label starText;

    @FXML
    Label modelText;

    @FXML
    Label speedText;

    @FXML
    Label seatsText;

    @FXML
    Label powerText;

    @FXML
    Label priceText;

    @FXML
    Label commentsNum;

    @FXML
    Pane containPane;

    @FXML
    Pane cardDetailsPageContainer;


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
        String sql = "SELECT commentID FROM comments WHERE payload = '"+model+"'" + " and type='modelComment'";
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
        cardDetailsPageContainer.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
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
        if (StatusContainer.isLogin) {
            new Tools().reSetScene("paymentPage.fxml");
        } else {
            //System.out.println("Please login first");
            new Tools().reSetScene("loginPage.fxml");
        }
    }

}
