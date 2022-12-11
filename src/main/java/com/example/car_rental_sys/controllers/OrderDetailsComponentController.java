package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.orm.CarModel;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.ui_components.InvoiceBox;
import com.example.car_rental_sys.ui_components.UIOrderRow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

/*

///                   _ooOoo_
///                  o8888888o
///                 88" .   "88
///                 (|  -_-  |)
///                  O\  =  /O
///               ____/`---'\____
///             .'  \\|     |//  `.
///            /  \\|||  :  |||//  \
///           /  _||||| -:- |||||-  \
///           |   | \\\  -  /// |   |
///           | \_|  ''\---/''  |   |
///           \  .-\__  `-`  ___/-. /
///         ___`. .'  /--.--\  `. . __
///      ."" '<  `.___\_<|>_/___.'  >'"".
///     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
///     \  \ `-.   \_ __\ /__ _/   .-` /  /
///======`-.____`-.___\_____/___.-`____.-'======
///                   `=---='
///^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

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

/// BOBI CODE NO ERROR    \|/
/// END INIT INFO
*/

public class OrderDetailsComponentController {

    public static OrderDetailsComponentController instance;

    @FXML
    private Pane panelOrderDetails;

    @FXML
    private Button invoiceBtn;

    @FXML
    private Label orderNum,orderDate,carName,orderQty,carPrice,paymentType,
            lblLocation,orderTime,approveTime,paymentTime,
            dueTime,pickUpTime,totalAmount;

    @FXML
    private ImageView paymentLogo,carImg,invoiceIcon;

    private CarModel carModel = StatusContainer.currentCarModel;
    private Order order = StatusContainer.currentOrder;


    @FXML
    public void initialize() {
        //checkShowingStatus();
        initComponentText();
        initBtnEvent();
        initTheme();
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
                InvoiceBox invoiceBox = new InvoiceBox();
                invoiceBox.showInvoiceStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setOrderNum(String orderNum) {
        this.orderNum.setText(orderNum);
        System.out.println("setOrderNum");
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