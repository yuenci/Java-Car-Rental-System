package com.example.car_rental_sys.ui_components;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.controllers.AdminServiceController;
import com.example.car_rental_sys.controllers.CustomerServiceController;
import com.example.car_rental_sys.controllers.OrderDetailsComponentController;
import com.example.car_rental_sys.orm.CarModel;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Order;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


/*
 *                                |~~~~~~~|
 *                                |       |
 *                                |       |
 *                                |       |
 *                                |       |
 *                                |       |
 *     |~.\\\_\~~~~~~~~~~~~~~xx~~~         ~~~~~~~~~~~~~~~~~~~~~/_//;~|
 *     |  \  o \_         ,XXXXX),                         _..-~ o /  |
 *     |    ~~\  ~-.     XXXXX`)))),                 _.--~~   .-~~~   |
 *      ~~~~~~~`\   ~\~~~XXX' _/ ';))     |~~~~~~..-~     _.-~ ~~~~~~~
 *               `\   ~~--`_\~\, ;;;\)__.---.~~~      _.-~
 *                 ~-.       `:;;/;; \          _..-~~
 *                    ~-._      `''        /-~-~
 *                        `\              /  /
 *                          |         ,   | |
 *                           |  '        /  |
 *                            \/;          |
 *                             ;;          |
 *                             `;   .       |
 *                             |~~~-----.....|
 *                            | \             \
 *                           | /\~~--...__    |
 *                           (|  `\       __-\|
 *                           ||    \_   /~    |
 *                           |)     \~-'      |
 *                            |      | \      '
 *                            |      |  \    :
 *                             \     |  |    |
 *                              |    )  (    )
 *                               \  /;  /\  |
 *                               |    |/   |
 *                               |    |   |
 *                                \  .'  ||
 *                                |  |  | |
 *                                (  | |  |
 *                                |   \ \ |
 *                                || o `.)|
 *                                |`\\) |
 *                                |       |
 *                                |       |
 */



public class  UIOrderRow extends Pane {

    private Label lblOrderID, lblName, lblOrderDate, lblOrderAmount, lblOrderStatus;
    private String name, orderDate, orderAmount;
    private int carID, orderID;
    private int orderStatus;
    public Image imgCar;

    public UIOrderRow(){

    }

    public UIOrderRow(int orderID, String name, String orderDate, int orderAmount, int orderStatus){
        this.orderID = orderID;
        this.name = name;
        this.orderDate = DateTools.getSpecFormatDate(orderDate);
        this.orderAmount = "RM" + orderAmount;
        this.orderStatus = orderStatus;
        init();
    }

    private void init(){
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setPrefSize(460,30);
        initData();
        initComponent();
        initTheme();
        initEvent();
    }
    private void initData(){
        this.carID = Integer.parseInt(DataTools.getOrderInfoFromOrderID(String.valueOf(orderID))[1]);
    }

    private void initComponent(){
        lblOrderID = new Label();
        lblName = new Label();
        lblOrderDate = new Label();
        lblOrderAmount = new Label();
        lblOrderStatus = new Label();

        lblOrderID.setText(DataTools.getOrderIDStr(orderID));
        lblName.setText(name);
        lblOrderDate.setText(orderDate);
        lblOrderAmount.setText(orderAmount);
        String status,color;
        if(orderStatus == -1){
            status = "Cancelled";
            color = "#F76560";
        }else if(orderStatus == 0 || orderStatus == 2){
            status = "In Progress";
            color = "#6AA1FF";
        }else if(orderStatus == 1 || orderStatus == 3 || orderStatus == 4) {
            status = "Continuing";
            color = "#FF9A2E";
        }else if(orderStatus == 5){
            status = "Completed";
            color = "#4CD263";
        }else{
            status = "Unknown";
            color = "#86909C";
        }
        lblOrderStatus.setText(status);
        lblOrderStatus.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");

        lblOrderID.setLayoutX(9);
        lblName.setLayoutX(90);
        lblOrderDate.setLayoutX(215);
        lblOrderAmount.setLayoutX(305);
        lblOrderStatus.setLayoutX(395);

        lblOrderID.setLayoutY(6);
        lblName.setLayoutY(6);
        lblOrderDate.setLayoutY(6);
        lblOrderAmount.setLayoutY(6);
        lblOrderStatus.setLayoutY(6);

        this.getChildren().addAll(lblOrderID, lblName,lblOrderDate,lblOrderAmount,lblOrderStatus);
    }


    private void initDarkStyle(){
        Label[] labels = {lblOrderID, lblName, lblOrderDate, lblOrderAmount};
        for(Label label : labels){
            label.setStyle("-fx-text-fill: #ababac; -fx-font-weight: bold;");
        }
    }

    private void initLightStyle(){
        Label[] labels = {lblOrderID, lblName,lblOrderDate,lblOrderAmount};
        for(Label label : labels){
            label.setStyle("-fx-text-fill: #86909C; -fx-font-weight: bold;");
        }
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            initDarkStyle();
        }
        else{
            initLightStyle();
        }
    }

    private void initEvent(){
        this.setCursor(Cursor.HAND);
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            StatusContainer.currentCarModel = new CarModel(carID);
            StatusContainer.currentOrder = new Order(orderID);

            if(StatusContainer.currentUser instanceof Customer){
                CustomerServiceController.instance.closeTrackOrder();
            }else{
                AdminServiceController.instance.closeTrackOrder();
            }

            //OrderDetailsComponentController.instance.updateStatus(1);
            loadImage();
            OrderDetailsComponentController.instance.updateOrderDetails(this);
        });
    }

    private void loadImage(){
        imgCar= ImageTools.getCarImageObjFromCarID(this.carID);
    }
}
