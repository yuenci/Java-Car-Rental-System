package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.funtions.JavaObject;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.InjectJsCallback;
import com.teamdev.jxbrowser.browser.event.ConsoleMessageReceived;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.js.ConsoleMessage;
import com.teamdev.jxbrowser.js.JsObject;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.function.Function;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class BrowserFrame {
    private int width;
    private int height;
    private String url;
    private double locationX,locationY;
    protected  Stage stage =  new Stage();
    protected  Browser browser = null;
    protected  Function<String,Void> callBackFunc = null;

    // style
    private String captionBarBgc = "#323844";

    public BrowserFrame(int width, int height, String url) {
        this.width = width;
        this.height = height;
        this.url = url;
    }


    private void initBrowser() {
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
        browser = engine.newBrowser();
        //browser.devTools().show();

        if(url.startsWith("http")){
            browser.navigation().loadUrl(this.url);
        }else{
            browser.navigation().loadUrl(new File(this.url).getAbsolutePath());
        }
        // browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/googleMap.html").getAbsolutePath());
        //browser.navigation().loadUrl("https://www.google.com");
        //System.out.println("initBrowser");
        browser.on(ConsoleMessageReceived.class, event -> {
            ConsoleMessage consoleMessage = event.consoleMessage();
            String message = consoleMessage.message();
            callBack(message);
        });
    }

    private void initStage(){


        BrowserView view = BrowserView.newInstance(this.browser);
        BorderPane browserContainer = new BorderPane(view);
        browserContainer.setPrefSize(width,height);
        browserContainer.setLayoutX(0);
        browserContainer.setLayoutY(30);

        Pane root = new Pane();
        root.getChildren().addAll(browserContainer,getCaptionBar(),getCloseButton());

        Scene scene = new Scene(root,this.width,this.height +30);

        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
    }

    public Label  getCaptionBar(){
        Label captionBar = new Label();
        captionBar.setPrefSize(this.width,30);
        captionBar.setStyle("-fx-background-color: " + captionBarBgc + ";");
//        captionBar.setStyle("-fx-background-color: #818181");
        captionBar.setLayoutX(0);
        captionBar.setLayoutY(0);

        captionBar.setOnMousePressed((MouseEvent event) -> {
            locationX = stage.getX() - event.getScreenX();
            locationY = stage.getY() - event.getScreenY();
        });

        captionBar.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() + locationX);
            stage.setY(event.getScreenY() + locationY);
        });
        return  captionBar;
    }


    public ImageView getCloseButton(){
        ImageView closeIcon = new ImageView();
        closeIcon.setX(this.width - 30);
        closeIcon.setY(5);
        closeIcon.setFitWidth(20);
        closeIcon.setFitHeight(20);

        Image image = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/closeIconFrame.png");
        Image imageHover = ImageTools.getImageObjFromPath("src/main/resources/com/example/car_rental_sys/image/UI/closeIconHoverFrame.png");

        closeIcon.setImage(image);

        closeIcon.setOnMouseClicked(e -> stage.close());

        closeIcon.setOnMouseEntered(e -> {
            closeIcon.setImage(imageHover);
            stage.getScene().setCursor(Cursor.HAND);
        });

        closeIcon.setOnMouseExited(e -> {
            closeIcon.setImage(image);
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
        return  closeIcon;
    }


    private  void callBack( String message){
        System.out.println("call back message: [" + message + "] from BrowserFrame");
        callBackFunc.apply(message);
    }

    public void show(){
        initBrowser();
        initStage();
        setJavaScriptFunc();
        stage.show();
    }

    private void closeCallBack(){
        callBackFunc.apply("close");
    }

    public void setCloseCallBack(Function<String,Void> callBackFunc){
        stage.setOnCloseRequest(e -> callBackFunc.apply("close"));
    }

    public void setJavaScriptFunc(){
        browser.set(InjectJsCallback .class, params -> {
            JsObject window = params.frame().executeJavaScript("window");
            assert window != null;
            window.putProperty("java", new JavaObject());
            return InjectJsCallback.Response.proceed();
        });
    }


}
