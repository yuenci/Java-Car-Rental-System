package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    public int orderID;
    public int carID;
    public Date orderTime = null;
    public Date pickUpTime = null;
    public Date returnTime = null;
    public String pickUpLocation = "";
    public String parkingLocation = "";

    public int userID = 0;
    public int price = 0;
    public String paymentMethod = "";
    public String account = "";
    public int status = -1;
    public int star = 0;

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
            this.orderTime = Tools.stringToDateObje(orderInfo[2]);
            this.pickUpTime = Tools.stringToDateObje(orderInfo[3]);
            this.returnTime = Tools.stringToDateObje(orderInfo[4]);
            this.pickUpLocation = orderInfo[5];
            this.parkingLocation = orderInfo[6];
            this.userID = Integer.parseInt(orderInfo[7]);
            this.price = Integer.parseInt(orderInfo[8]);
            this.paymentMethod = orderInfo[9];
            this.account = orderInfo[10];
            this.status = Integer.parseInt(orderInfo[11]);
            this.star = Integer.parseInt(orderInfo[12]);
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
                '}';
    }
}
