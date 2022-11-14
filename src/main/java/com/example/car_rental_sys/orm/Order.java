package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int orderID;
    private int carID;
    private Date orderTime = null;
    private Date pickUpTime = null;
    private Date returnTime = null;
    private String pickUpLocation = "";
    private String parkingLocation = "";


    private int userID = 0;
    private int price = 0;
    private String paymentMethod = "";
    private String account = "";
    private int status = -1;
    private int star = 0;

    private String invoiceNo = "";

    public Order() {
    }

    /*
    public static void getNewOrder(){
        Order newOrder = new Order();
        newOrder.setOrderID(getID("orders"));
        newOrder.setCarID();
        newOrder.setOrderTime(DateTools.stringToDateObje(DateTools.getNow()));
        newOrder.setPickUpTime();
        newOrder.setReturnTime();
        newOrder.setParkingLocation();
        newOrder.setPickUpLocation();
        newOrder.setUserID();
        newOrder.setPrice();
        newOrder.setPaymentMethod();
        newOrder.setAccount();
        newOrder.setStatus();
        newOrder.setStar();
    }
    * */

    public Order(int orderID) {
        this.orderID = orderID;
        initOrder();
    }

    private void initOrder() {
        String sql = "select * from orders where orderID = " + orderID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            System.out.println("Order: " + this.orderID +  "not found");
        }
        else{
            String[] orderInfo = result.get(0);
            this.carID = Integer.parseInt(orderInfo[1]);
            this.orderTime = DateTools.stringToDateObje(orderInfo[2]);
            this.pickUpTime = DateTools.stringToDateObje(orderInfo[3]);
            this.returnTime = DateTools.stringToDateObje(orderInfo[4]);
            this.pickUpLocation = orderInfo[5];
            this.parkingLocation = orderInfo[6];
            this.userID = Integer.parseInt(orderInfo[7]);
            this.price = Integer.parseInt(orderInfo[8]);
            this.paymentMethod = orderInfo[9];
            this.account = orderInfo[10];
            this.status = Integer.parseInt(orderInfo[11]);
            this.star = Integer.parseInt(orderInfo[12]);
            this.invoiceNo = DataTools.generateRandomInvoiceNo(this.orderID);
        }
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCarID() {
        return carID;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public int getUserID() {
        return userID;
    }

    public int getPrice() {
        return price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getAccount() {
        return account;
    }

    public int getStatus() {
        return status;
    }

    public int getStar() {
        return star;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", carID=" + carID +
                ", orderTime=" + orderTime +
                ", pickUpTime=" + pickUpTime +
                ", returnTime=" + returnTime +
                ", userID=" + userID +
                ", price=" + price +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", account='" + account + '\'' +
                ", status=" + status +
                ", star=" + star +
                ", invoiceNo='" + invoiceNo + '\'' +
                '}';
    }
}
