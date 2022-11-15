package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Order;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class InvoiceController {

    //for the invoice, need to add a stage to put it on

    public static InvoiceController instance;
//    private String billingLineOne;
//    private String billingLineTwo;
//    private String carName;
//    private int orderID;
//    private String paymentDate;
//    private String totalPrice;
//    private String quantity;

    String carName;
    String invoiceNo;
    String paymentData;
    String price;
    String hour;
    String userName;
    String unitPrice;


    @FXML
    private Label ivAddressTwo;

    @FXML
    private Label ivQuantity;

    @FXML
    private Label ivSubTotal;

    @FXML
    private Label ivPaymentDate;

    @FXML
    private Label ivUnitPrice;

    @FXML
    private Label ivAddressOne;

    @FXML
    private Pane ivQrCodePane,printAreaPane;

    @FXML
    private Label ivPrice;

    @FXML
    private Label ivTotalPrice;

    @FXML
    private Label ivUserName;

    @FXML
    private Label ivOrderID;

    @FXML
    private Label ivCarName;

    @FXML
    private Label taxLabel;

    @FXML
    private ImageView qrCodeImageView;



    public InvoiceController() {
        instance = this;
    }

    @FXML
    private void initialize(){
        setLabelLayout();
        setLabelText();
        initQrCode();
    }

    private void getLabelText(){
        //String query = "SELECT * FROM orders WHERE order_id = " + orderID + " AND user_id = " + LoginController.instance.getUserID();
    }

    private void setLabelLayout(){
        ivPrice.setAlignment(Pos.CENTER_RIGHT);
        ivSubTotal.setAlignment(Pos.CENTER_RIGHT);
        ivTotalPrice.setAlignment(Pos.CENTER_RIGHT);
        taxLabel.setAlignment(Pos.CENTER_RIGHT);
        ivUnitPrice.setAlignment(Pos.CENTER_RIGHT);
        ivPaymentDate.setAlignment(Pos.CENTER_RIGHT);
    }

    private void setLabelText(){
        Order order = StatusContainer.currentOrder;

        ivAddressOne.setText("Address One");
        ivAddressTwo.setText("Address Two");

        carName = DataTools.getCarModelFromCarID(order.getCarID()).replace("_"," ");
        ivCarName.setText(carName);

        invoiceNo = DataTools.generateRandomInvoiceNo(order.getCarID());
        ivOrderID.setText(invoiceNo);

        paymentData = DateTools.dateToString(order.getOrderTime(), "MM dd,yyyy");
        ivPaymentDate.setText(paymentData);

        price = "RM " + order.getPrice();

        ivPrice.setText(price);

        hour = DateTools.getHourDiff(order.getPickUpTime(), order.getReturnTime())  + " hours";
        ivQuantity.setText(hour);


        ivSubTotal.setText(price);

        ivTotalPrice.setText(price);

        unitPrice = "RM " + DataTools.getCarUnitPriceFromCarID(order.getCarID()) + " /h";
        ivUnitPrice.setText(unitPrice);

        userName =DataTools.getUserNameFromUserID(order.getUserID()).replace("-"," ");
        ivUserName.setText(userName);
    }

    private void initQrCode() {
        String invoiceNoContent ="Invoice NO: " + invoiceNo ;
        String carNameContent ="Car Name: " + carName ;
        String userNameContent = "User Name: " +userName;
        String unitPriceContent = "Unit Price: " +unitPrice;
        String hourContent = "Duration: " +hour;
        String priceContent = "Total Price: " +price;
        String paymentDataContent ="Payment Date time:" +paymentData ;

        String qrContent = invoiceNoContent + "\n" + carNameContent + "\n" + userNameContent
                + "\n" + unitPriceContent + "\n" + hourContent + "\n" + priceContent
                + "\n" + paymentDataContent;





        ImageTools.generateQRCode(qrCodeImageView,qrContent,100,100);
    }

    public void ivDownloadClicked(MouseEvent mouseEvent) {
        //download function
    }

    public void downloadBtnClick(MouseEvent mouseEvent) {
        //printAreaPane
        FXTools.printJavaFXNode(printAreaPane);

    }

    public void cancelBtnClick(MouseEvent mouseEvent) {
    }
}
