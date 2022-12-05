package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CarModel {
    private int modelID;
    private String carModel;
    private int seats;
    private int price;
    private String gearType;
    private String darkColor;
    private String lightColor;
    private String carBrand;
    private int star;
    private int speed;
    private int power;

    private String chassisNumber;
    private String plateNumber;
    private String manufacturingDate;

    public CarModel(int modelID) {
        this.modelID = modelID;
        initData("modelID");
    }

    public CarModel(String carModel) {
        this.carModel = carModel;
        initData("carModel");
    }

    private void initData(String type){
        String sql ;
        if(Objects.equals(type, "modelID")){
            sql = "SELECT * FROM carModel WHERE " + type + " = " + modelID;
        }else if (Objects.equals(type, "carModel")){
            sql = "SELECT * FROM carModel WHERE " + type + " = " +"'" + carModel +"'";
        }else{
            throw  new IllegalArgumentException("type must be modelID or carModel");
        }
        ArrayList<String[]> data = SQL.query(sql);
        if(data.size() == 0){
            return;
        }
        String[] carModelInfo = data.get(0);
        modelID = Integer.parseInt(carModelInfo[0]);
        carModel = carModelInfo[1];
        seats = Integer.parseInt(carModelInfo[2]);
        price = Integer.parseInt(carModelInfo[3]);
        gearType = carModelInfo[4];
        darkColor = carModelInfo[5];
        lightColor = carModelInfo[6];
        carBrand = carModelInfo[7];
        star = Integer.parseInt(carModelInfo[8]);
        speed = Integer.parseInt(carModelInfo[9]);
        power = Integer.parseInt(carModelInfo[10]);


    }

    public CarModel(int modelID, String carModel, int seats, int price, String gearType, String darkColor,
                    String lightColor, String carBrand, int star, int speed, int power) {
        this.modelID = modelID;
        this.carModel = carModel;
        this.seats = seats;
        this.price = price;
        this.gearType = gearType;
        this.darkColor = darkColor;
        this.lightColor = lightColor;
        this.carBrand = carBrand;
        this.star = star;
        this.speed = speed;
        this.power = power;
    }

    public void setCarInfo(String chassisNumber, String plateNumber, String manufacturingDate){
        this.chassisNumber = chassisNumber;
        this.plateNumber = plateNumber;
        this.manufacturingDate = manufacturingDate;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getDarkColor() {
        return darkColor;
    }

    public void setDarkColor(String darkColor) {
        this.darkColor = darkColor;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public void saveCarModel(){

    }
}
