package com.example.car_rental_sys;

import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;


import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.js.JsObject;



public class MainPageController {
    public static MainPageController mainPageController;
    @FXML
    private Pane mainPane;

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

        pickUpDateLabel.setText(farmatDateTime(currentDate));
        returnDateLabel.setText(farmatDateTime(dateaAfterOneWeek));
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
        new Tools().reSetScence(actionEvent,"carsList.fxml");
    }

    @FXML
    void locationBtnClick() {
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

        Browser browser = engine.newBrowser();

        //URL url = this.getClass().getResource("/com/example/car_rental_sys/html/map.html");
        //URL url = this.getClass().getResource("/com/example/car_rental_sys/html/jxTest.html");
//        assert url != null;
//        browser.navigation().loadUrl(url.toString());

        browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/googleMap.html").getAbsolutePath());

        browser.set(InjectJsCallback.class, params -> {
            JsObject window = params.frame().executeJavaScript("window");
            assert window != null;
            window.putProperty("java", new JavaObject());

            return InjectJsCallback.Response.proceed();
        });


//        Frame frame = browser.frames().get(0);
//        frame.executeJavaScript("insert();");

        //browser.mainFrame.executeJavaScript("alert('hello world')");
//
//        String title =  browser.mainFrame().executeJavaScript("document.title");
//
//        browser.executeJavaScript("alert('hello world')");

        //browser.navigation().loadUrl("https://www.bejson.com/httputil/clientinfo/");
        //browser.navigation().loadUrl("http://127.0.0.1:8080/");

        BrowserView view = BrowserView.newInstance(browser);




        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            //ConsoleMessageLevel level = consoleMessage.level();
            String message = consoleMessage.message();
            System.out.println(message);
        });

        Scene scene = new Scene(new BorderPane(view),800,600);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("JxBrowser");
        stage.setScene(scene);
        stage.show();

        updateLocation();

        stage.setOnCloseRequest(event -> {
            timer.cancel();
            engine.close();
        });
    }

    private  Timer timer;

    private void updateLocation(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (StatusContainer.locationChose!=null){
                        locationText.setText(StatusContainer.locationChose);
                    }
                });

            }
        },0,200);
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
        //System.out.println("searchBtnClick");

    }

    void showDatePicker(){
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        Browser browser = engine.newBrowser();

        browser.navigation().loadUrl("http://127.0.0.1:8080/");

        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            //ConsoleMessageLevel level = consoleMessage.level();
            String message = consoleMessage.message();
            System.out.println(message);

            String[] messageArray = message.split(",");
            StatusContainer.pickDateTime = messageArray[0];
            StatusContainer.returnDateTime = messageArray[1];

        });

        BrowserView view = BrowserView.newInstance(browser);

        Scene scene = new Scene(new BorderPane(view),800,600);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("JxBrowser");
        stage.setScene(scene);
        stage.show();

        updatePickAndReturnDateTime();

        stage.setOnCloseRequest(event -> {
            timerDatePciker.cancel();
            engine.close();
        });

    }

    private Timer timerDatePciker ;

    private void updatePickAndReturnDateTime(){
        timerDatePciker = new Timer();
        timerDatePciker.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (StatusContainer.pickDateTime!=null){
                        pickUpDateLabel.setText(StatusContainer.pickDateTime);
                        returnDateLabel.setText(StatusContainer.returnDateTime);
                    }
                });

            }
        },0,200);
    }
}