package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.controllers.OrderListComponentController;
import com.example.car_rental_sys.controllers.SearchBarController;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.function.Function;

public class UIFilter extends Pane {

    private Button filCancel, filDone, filDateStart, filDateEnd;
    private RadioButton rbPriceRec, rbPriceDesc, rbPriceAsc;
    private static int selectedState = 1;

    public static UIFilter instance;

    public UIFilter() {
        instance = this;
        generateFilter();
    }

    public void generateFilter(){
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setPrefSize(240, 260);
        initStyle();
        initComponents();
        initEvent();
    }

    private void initStyle() {
        this.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #D9D9D9; -fx-border-width: 1px;");
    }

    private void initComponents() {
        Label lblDate = new Label("Date");
        lblDate.setStyle("-fx-font-size: 14px; -fx-text-fill: #86909C; -fx-font-weight: bold;");
        lblDate.setLayoutX(11);
        lblDate.setLayoutY(22);

        Label lblTo = new Label("To");
        lblTo.setStyle("-fx-font-size: 12px; -fx-text-fill: #86909C; -fx-font-weight: bold;");
        lblTo.setLayoutX(103);
        lblTo.setLayoutY(50);

        Label lblPricing = new Label("Pricing");
        lblPricing.setStyle("-fx-font-size: 14px; -fx-text-fill: #86909C; -fx-font-weight: bold;");
        lblPricing.setLayoutX(11);
        lblPricing.setLayoutY(91);

        Label lblRecommend = new Label("Recommended");
        lblRecommend.setStyle("-fx-font-size: 11px; -fx-text-fill: #4E5969; -fx-font-weight: bold;");
        lblRecommend.setLayoutX(45);
        lblRecommend.setLayoutY(118);

        Label lblPriceAsc = new Label("Lowest Price");
        lblPriceAsc.setStyle("-fx-font-size: 11px; -fx-text-fill: #4E5969; -fx-font-weight: bold;");
        lblPriceAsc.setLayoutX(45);
        lblPriceAsc.setLayoutY(141);

        Label lblPriceDesc = new Label("Highest Price");
        lblPriceDesc.setStyle("-fx-font-size: 11px; -fx-text-fill: #4E5969; -fx-font-weight: bold;");
        lblPriceDesc.setLayoutX(45);
        lblPriceDesc.setLayoutY(167);

        final ToggleGroup group = new ToggleGroup();
        rbPriceRec = new RadioButton();
        rbPriceRec.setToggleGroup(group);
        if(selectedState == 1){
            rbPriceRec.setSelected(true);
        }
        rbPriceRec.setPrefSize(12, 12);
        //rbPriceRec.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #165DFF; -fx-border-width: 2px; -fx-border-radius: 15px; -fx-border-insets: 0; -fx-background-image: null;");
        rbPriceRec.setLayoutX(13);
        rbPriceRec.setLayoutY(118);

        rbPriceDesc = new RadioButton();
        rbPriceDesc.setToggleGroup(group);
        if(selectedState == 2){
            rbPriceDesc.setSelected(true);
        }
        rbPriceDesc.setPrefSize(12, 12);
        //rbPriceDesc.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #165DFF; -fx-border-width: 2px; -fx-border-radius: 15px; -fx-border-insets: 0;");
        rbPriceDesc.setLayoutX(13);
        rbPriceDesc.setLayoutY(141);

        rbPriceAsc = new RadioButton();
        rbPriceAsc.setToggleGroup(group);
        if(selectedState == 3){
            rbPriceAsc.setSelected(true);
        }
        rbPriceAsc.setPrefSize(12, 12);
        //rbPriceAsc.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #165DFF; -fx-border-width: 2px; -fx-border-radius: 15px; -fx-border-insets: 0;");
        rbPriceAsc.setLayoutX(13);
        rbPriceAsc.setLayoutY(166);

        //ban the radio button
        rbPriceRec.setDisable(true);
        rbPriceDesc.setDisable(true);
        rbPriceAsc.setDisable(true);

        filCancel = new Button("Cancel");
        filCancel.setStyle("-fx-font-size: 12px; -fx-background-color: #E5E6EB; -fx-background-radius: 6px; -fx-background-insets: 0; -fx-border-width: 0px; -fx-text-fill: #4E5969; -fx-font-weight: bold;");
        filCancel.setLayoutX(28);
        filCancel.setLayoutY(215);
        filCancel.setPrefSize(80, 25);

        filDone = new Button("Done");
        filDone.setStyle("-fx-font-size: 12px; -fx-background-color: #4080FF; -fx-background-radius: 6px; -fx-background-insets: 0; -fx-border-width: 0px; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
        filDone.setLayoutX(128);
        filDone.setLayoutY(215);
        filDone.setPrefSize(80, 25);

//        ImageView imageView1 = new ImageView();
//        imageView1.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/calendar.png"));
//        imageView1.setFitHeight(20);
//        imageView1.setFitWidth(20);

        filDateStart = new Button("Nov 12, 2022");
        filDateStart.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 6px; -fx-background-insets: 0; -fx-border-width: 1px; -fx-border-color: #D9D9D9; -fx-border-insets: 0; -fx-border-radius: 6px; -fx-font-size: 11px; -fx-text-fill: #4E5969; -fx-font-weight: bold; -fx-padding: 0 0 0 8;");
        filDateStart.setLayoutX(11);
        filDateStart.setLayoutY(45);
        filDateStart.setPrefSize(90, 25);


        filDateEnd = new Button("Nov 13, 2022");
        filDateEnd.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 6px; -fx-background-insets: 0; -fx-border-width: 1px; -fx-border-color: #D9D9D9; -fx-border-insets: 0; -fx-border-radius: 6px; -fx-font-size: 11px; -fx-text-fill: #4E5969; -fx-font-weight: bold; -fx-padding: 0 0 0 8;");
        filDateEnd.setLayoutX(118);
        filDateEnd.setLayoutY(45);
        filDateEnd.setPrefSize(90, 25);

        this.getChildren().addAll(lblDate, lblTo, lblPricing, lblRecommend, lblPriceAsc, lblPriceDesc,
                rbPriceRec, rbPriceDesc, rbPriceAsc,
                filCancel, filDone, filDateStart, filDateEnd);
    }

    private void initEvent() {
        if (filCancel != null) {
            addHoverEvent();
            filCancel.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> OrderListComponentController.instance.removeFilterPane());
        }

        if (filDone != null) {
            addHoverEvent();
            filDone.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> OrderListComponentController.instance.removeFilterPane());
        }

        if (rbPriceRec != null) {
            rbPriceRec.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                selectedState = 1;
                System.out.println("rbPriceRec");
            });
        }
        if (rbPriceDesc != null) {
            rbPriceDesc.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                selectedState = 2;
                System.out.println("rbPriceDesc");
            });
        }

        if (rbPriceAsc != null) {
            rbPriceAsc.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                selectedState = 3;
                System.out.println("rbPriceAsc");
            });
        }

        if (filDateStart != null) {
            addHoverEvent();
            //on click then show date picker
            filDateStart.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> showDatePicker());
        }

        if (filDateEnd != null) {
            addHoverEvent();
            assert filDateStart != null;
            filDateStart.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> showDatePicker());
        }
    }

    private void addHoverEvent(){
        //onMouseEntered
        this.setOnMouseEntered(event -> setCursor(Cursor.HAND));
        //onMouseExited
        this.setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
    }

    private boolean ifExist = false;

    private void showDatePicker(){
        if(ifExist){
            return;
        }
        String url = ConfigFile.backendPost +  "datePicker/index_white.html";
        BrowserModal browserModal = new BrowserModal(600, 455, url) ;
        browserModal.setModality();
        Function<String, Void> func = (message) -> {
        if(message.length() == 25){
                //System.out.println("hiii");
                String[] messageArray = message.split(";");

                Platform.runLater(() -> {
                    filDateStart.setText( messageArray[0]);
                    filDateEnd.setText(messageArray[1]);
                });
            }
            return null;
        };
        ifExist = true;
        browserModal.setFunction(func);
        browserModal.show();
    }

    private void getValue(){

    }
}

