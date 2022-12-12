package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.orm.*;
import com.example.car_rental_sys.ui_components.InvoiceBox;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import com.example.car_rental_sys.ui_components.UIOrderRow;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

/*

//          ⣠⣤⣤⣤⡀⠀⠀⢀⣀⣀⣤⣤⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
//        ⠀⣾⣻⣻⣻⣻⡿⠚⠉⠉⠀⠀⠀⠀⠀⠀⠈⠙⠲⣴⣻⣻⣻⣷⣄⠀
//        ⢸⣻⣻⣻⣻⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣻⣻⣻⣻⡆
//        ⠀⠻⣻⣻⠏⠀⠀⠀⠀⣶⠿⠿⢿⣷⣄⢠⡿⠿⠶⠦⠀⢹⣻⣻⣻⠃
//        ⠀⠀⢈⡏⠀⠀⠀⠀⠈⠑⠺⣻⠟⠉⠁⠈⠛⢿⠆⠀⠀⠀⢻⠋⠁⠀
//        ⠀⠀⡼⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⠂⠀⢀⠀⢠⡄⠀⠀⠀⢸⡀⠀⠀
//        ⢀⣴⡇⠀⠀⠀⠀⠀⠀⠶⢿⣻⣉⣛⣻⣻⣛⣉⡻⣦⠀⠀⠀⣇⠀⠀
//        ⣾⣻⡇⠀⠀⠀⠀⠀⠀⠄⢸⡟⢿⣯⣭⣭⣽⣻⠃⠈⠀⠀⠀⣻⣧⠀
//        ⣻⣻⣻⣦⡀⠀⠀⠀⠀⠈⠈⠿⠶⣭⣭⣬⡭⠁⠀⠀⠀⢀⣼⣻⣻⣧
//        ⣻⣻⣻⣻⣻⣶⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣴⣻⣻⣻⣻⣻
//        ⣻⣻⣻⣻⣻⣻⣻⣻⣻⣷⣶⣶⣶⣶⣶⣶⣾⣻⣻⣻⣻⣻⣻⣻⣻⣻

/// CODE NO ERROR    \|/
/// END INIT INFO
*/

public class OrderDetailsComponentController {

    public static OrderDetailsComponentController instance;
    private User user = StatusContainer.currentUser;

    @FXML
    private Pane panelOrderDetails;

    @FXML
    private Button invoiceBtn, btnTrackOrder;

    @FXML
    private Label orderNum,orderDate,carName,orderQty,carPrice,paymentType,
            lblLocation,orderTime,approveTime,paymentTime,
            dueTime,pickUpTime,totalAmount;

    @FXML
    private ImageView paymentLogo,carImg,invoiceIcon,trackIcon;

    private CarModel carModel = StatusContainer.currentCarModel;
    private Order order = StatusContainer.currentOrder;


    @FXML
    public void initialize() {
        initComponentText();
        initBtnEvent();
        initTheme();
        checkOrderStatus();
        instance = this;
    }

    public void initComponentText(){
        orderNum.setText(DataTools.getOrderIDStr(order.getOrderID()));

        orderDate.setText(order.getOrderTimeStr().substring(0,order.getOrderTimeStr().length()-5));
        carName.setText(carModel.getCarModel());

        orderQty.setText("Qty: " + order.getPrice()/carModel.getPrice());

        carPrice.setText("RM" + carModel.getPrice());

        String paymentTypeText = order.getPaymentMethod();
        //change the first letter to uppercase
        paymentTypeText = paymentTypeText.substring(0, 1).toUpperCase() + paymentTypeText.substring(1);
        paymentType.setText(paymentTypeText + " **" + DataTools.getRandomInt(10,99));

        lblLocation.setText(order.getPickUpLocation());
        orderTime.setText(order.getOrderTimeStr());
        approveTime.setText("Oct 12, 2022 14:00");
        paymentTime.setText("Oct 12, 2022 14:02");
        pickUpTime.setText(order.getPickUpTimeStr());
        dueTime.setText(order.getReturnTimeStr());
        totalAmount.setText("RM" + order.getPrice());
        carImg.setImage(new Image("file:src/main/resources/com/example/car_rental_sys/image/cars/"+carModel.getCarModel()+".png"));
        //paymentLogo.setImage(new javafx.scene.image.Image(new File("src/main/resources/images/"+detailsList[14].toString()).toURI().toString()));
    }


    private void initBtnEvent() {
        invoiceBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                if(order.getStatus() != 0){
                    InvoiceBox invoiceBox = new InvoiceBox();
                    invoiceBox.showInvoiceStage();
                }else{
                    showNotification();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void showNotification(){
        MessageFrame messageFrame = new MessageFrame(MessageFrameType.CONFIRM, "The order will be canceled");
        messageFrame.setSuccessCallbackFunc((i) -> {
            //System.out.println(" accept order");
            DataTools.updateOrderStatusWithID(order.getOrderID(), -1);
            hideButton();
            OrderListComponentController.instance.getDataArray();
            OrderListComponentController.instance.refreshTable(1);
            return null;
        });

        messageFrame.setFailedCallbackFunc((i) -> {
            //System.out.println(" reject order");
            messageFrame.close();
            return null;
        });
        messageFrame.show();
    }

    private void initTheme(){
        if(user instanceof Customer){
            panelOrderDetails.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/orderDetailsComponentDark.css")
                            .toURI().toString());
            invoiceIcon.setImage(new javafx.scene.image.Image("file:src/main/resources/com/example/car_rental_sys/image/UI/invoice-light.png"));
        }
    }

    public void updateOrderDetails(UIOrderRow uiOrderRow){
        carImg.setImage(uiOrderRow.imgCar);
    }

    private void checkOrderStatus(){
        if(order.getStatus() == 0 && user instanceof Customer){
            invoiceBtn.setText("Cancel Order");
            invoiceIcon.setImage(null);
            //set insets
            invoiceBtn.setPadding(new Insets(0,0,0,0));
            invoiceBtn.setStyle(invoiceBtn.getStyle() + "-fx-border-color: #f54e4e !important; -fx-text-fill: #f54e4e !important;");
        }
        else if(order.getStatus() == 0 && user instanceof Admin){
            hideButton();
        }
        else if(order.getStatus() == -1){
            hideButton();
        }

    }

    private void hideButton(){
        invoiceBtn.setVisible(false);
        invoiceIcon.setVisible(false);
        btnTrackOrder.setVisible(false);
        trackIcon.setVisible(false);
    }

    @FXML
    private void btnTrackOrderClicked(){
        if (StatusContainer.currentUser instanceof Customer){
            CustomerServiceController.instance.showTrackOrder();

            //System.out.println(CustomerServiceController.instance);
        }else{
            AdminServiceController.instance.showTrackOrder();
            //System.out.println("btnTrackOrderClicked@!!");
        }
    }
}