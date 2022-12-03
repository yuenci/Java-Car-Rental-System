package com.example.car_rental_sys.controllers;
import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.ui_components.InvoiceBox;
import com.example.car_rental_sys.ui_components.UIOrderRow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class OrderDetailsComponentController {

    public static OrderDetailsComponentController instance;

    @FXML
    private Pane panelOrderDetails;

    @FXML
    private Button invoiceBtn;

    @FXML
    private Label orderNum;

    @FXML
    private Label orderDate;
    @FXML
    private Label carName;
    @FXML
    private Label orderQty;
    @FXML
    private Label carPrice;
    @FXML
    private Label paymentType;

    @FXML
    private Label lblLocation;

    @FXML
    private Label orderTime;

    @FXML
    private Label approveTime;

    @FXML
    private Label paymentTime;
    @FXML
    private Label dueTime;
    @FXML
    private Label pickUpTime;
    @FXML
    private Label totalAmount;



    @FXML
    private ImageView paymentLogo;
    @FXML
    private ImageView carImg;
    @FXML
    private ImageView invoiceIcon;

    private Pane pane = null;

    private int showingStatus = 0;


    @FXML
    public void initialize() {
        initBtnEvent();
        initTheme();
        instance = this;
        checkShowingStatus();
    }



    private void checkShowingStatus(){
        if(showingStatus == 0){
            showUpperPane("Select any order to view more details");
        }else if(showingStatus == 1) {
            if(pane != null){
                panelOrderDetails.getChildren().remove(pane);
            }
        }else if(showingStatus == 2) {
            if(pane != null){
                panelOrderDetails.getChildren().remove(pane);
            }
            showUpperPane("No order details can be viewed");
        }
    }

    public void showUpperPane(String text){
        pane = new Pane();
        pane.setPrefSize(320, 670);
        pane.setLayoutX(20);
        pane.setLayoutY(28);
        pane.getStyleClass().add("descPane");

        Label label = new Label(text);
        label.setLayoutX(32);
        label.setLayoutY(304);
        label.getStyleClass().add("descText");

        pane.getChildren().add(label);
        panelOrderDetails.getChildren().add(pane);
    }

    public void updateStatus(int status){
        showingStatus = status;
        checkShowingStatus();
    }


    public void initComponentText(Object[] detailsList){
        orderNum.setText(detailsList[0].toString());
        orderDate.setText(detailsList[1].toString());
        carName.setText(detailsList[2].toString());
        orderQty.setText(detailsList[3].toString());
        carPrice.setText(detailsList[4].toString());
        paymentType.setText(detailsList[5].toString());
        lblLocation.setText(detailsList[6].toString());
        orderTime.setText(detailsList[7].toString());
        approveTime.setText(detailsList[8].toString());
        paymentTime.setText(detailsList[9].toString());
        pickUpTime.setText(detailsList[10].toString());
        dueTime.setText(detailsList[11].toString());
        totalAmount.setText(detailsList[12].toString());
        carImg.setImage(new javafx.scene.image.Image(new File("src/main/resources/images/"+detailsList[13].toString()).toURI().toString()));
        paymentLogo.setImage(new javafx.scene.image.Image(new File("src/main/resources/images/"+detailsList[14].toString()).toURI().toString()));
    }


    private void initBtnEvent() {
        invoiceBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                InvoiceBox invoiceBox = new InvoiceBox();
                invoiceBox.showInvoiceStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setOrderNum(String orderNum) {
        this.orderNum.setText(orderNum);
        System.out.println("setOrd  erNum");
    }

    private void setOrderDate(String orderDate) {
        this.orderDate.setText(orderDate);
        System.out.println("setOrderDate");
    }

    private void setCarName(String carName) {
        this.carName.setText(carName);
        System.out.println("setCarName");
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelOrderDetails.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/orderDetailsComponentDark.css")
                            .toURI().toString());
            invoiceIcon.setImage(new javafx.scene.image.Image("file:src/main/resources/com/example/car_rental_sys/image/UI/invoice-light.png"));
        }
    }

    public void updateOrderDetails(UIOrderRow uiOrderRow){
        carImg.setImage(uiOrderRow.imgCar);
    }


    public void btnTrackOrderClicked(MouseEvent mouseEvent) throws IOException {
        Pane trackOrderPane = new Pane();
        trackOrderPane.setPrefSize(350, 750);
        trackOrderPane.setLayoutX(0);
        trackOrderPane.setLayoutY(0);
        trackOrderPane.getStyleClass().add("trackOrderPane");

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/TrackOrderComponent.fxml"));
        trackOrderPane.getChildren().add(fxmlLoader.load());

        panelOrderDetails.getChildren().add(trackOrderPane);
    }
}

