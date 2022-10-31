package com.example.car_rental_sys;

import com.example.car_rental_sys.sqlParser.SQL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CarDetailsPage {
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


    private String imageFileRoot = "src/main/resources/com/example/car_rental_sys/image/cars/";
    private String[] carDetailsdata = null;

    @FXML
    private void initialize(){
        initData();
        initImage();
        initStar();
        initSpeedSeatsPowerPrise();
        initLogo();
        initComments();
        backBtnClickEvent();
        initKeyTips();
        initWebView();


        //engine.load("https://www.google.com/");
        //engine.load( getClass().getResource("radar.html").toString() );
    }
    private void initData(){
        String sql = "SELECT * FROM cars WHERE carModel = '"+StatusContainer.currentCarChoosed+"'";
        ArrayList<String[]> result = SQL.query(sql);
        carDetailsdata = result.get(0);
        //System.out.println(Arrays.toString(carDetailsdata));
    }

    private  void initImage(){
        modelText.setText(StatusContainer.currentCarChoosed.replace("_"," "));

        File file = new File(imageFileRoot + StatusContainer.currentCarChoosed + ".png");

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
            String sql = "select carBrand from cars where carModel = '" + StatusContainer.currentCarChoosed + "'";
            String brand = SQL.query(sql).get(0)[0];
            //System.out.println(brand);

            Image image = Tools.getImageObjFromPath(logoFileRoot + brand + "-logo.png");
            if(image != null){
                logImageView.setImage(image);
            }else{
                logImageView.setImage(Tools.getImageObjFromPath(logoFileRoot + "default-logo.png"));
            }
        });
        thread.start();


    }

    private  void initStar(){
        Thread thread = new Thread(() -> {
            if(carDetailsdata != null && carDetailsdata.length > 8){
                starText.setText(carDetailsdata[8]);
            }
        });
        thread.start();

    }

    private void initSpeedSeatsPowerPrise(){
        Thread thread = new Thread(() -> {
            if(carDetailsdata != null && carDetailsdata.length > 8){
                speedText.setText(carDetailsdata[9] + " km/h");
                seatsText.setText(carDetailsdata[2]);
                powerText.setText(carDetailsdata[10] + " L");
                priceText.setText("RM" + carDetailsdata[3] + "/D");
            }
        });
        thread.start();

    }

    private void initComments(){
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(780);

        String model = StatusContainer.currentCarChoosed;
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
            Image image = Tools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/back-blue.png");
            backBtnImageView.setImage(image);
            backBtnImageView.getScene().setCursor(javafx.scene.Cursor.HAND);
        });

        backBtnImageView.setOnMouseExited(event -> {
            Image image = Tools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/back-white.png");
            backBtnImageView.setImage(image);
            backBtnImageView.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
        });

        backBtnImageView.setOnMouseClicked(event -> Tools.changeScence("carsList.fxml"));


    }

    private  void initKeyTips(){
        Thread thread = new Thread(() -> {
            for (int i = 0; i <1; i++) {
                try {
                    Thread.sleep(6 * 1000); //set pause time
                    keyTipsImageView.setImage(null);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        if(StatusContainer.isFirstViewCar){
            thread.start();
        }else{
            keyTipsImageView.setImage(null);
            StatusContainer.isFirstViewCar = false;
        }


    }

    private void initWebView(){
        String sql = "select power,comfort,handing,space,allocation,interior"
                +" from carsRadarData where carModel = '"
                +StatusContainer.currentCarChoosed+"'";
        String[] radarData = SQL.query(sql).get(0);
        String radarDataStr = Arrays.toString(radarData);
        //System.out.println(radarDataStr);

        WebEngine engine = webview.getEngine();
        URL url = this.getClass().getResource("/com/example/car_rental_sys/html/radar.html");
        engine.setJavaScriptEnabled(true);
        engine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        engine.executeScript("initRadar("+radarDataStr+");");
                    }
                });
        assert url != null;
        engine.load(url.toString());

        //engine.executeScript("initRadar([1.0,2.0,3.0,4.0,5.0,6.0]);");

//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl("file:///android_asset/test.html");
//        webview.setWebViewClient(new WebViewClient(){
//            public void onPageFinished(WebView view, String url){
//                webview.loadUrl("javascript:init('" + theArgumentYouWantToPass + "')");
//            }
//        });
    }

}
