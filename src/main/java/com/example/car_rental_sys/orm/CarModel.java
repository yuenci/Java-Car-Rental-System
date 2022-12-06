package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.sqlParser.SQL;

import java.awt.*;
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
    private double star;
    private int speed;
    private double power;
    private String imageURL;

    public CarModel(){

    }

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
            sql = "SELECT * FROM carModels WHERE " + type + " = " + modelID;
        }else if (Objects.equals(type, "carModel")){
            sql = "SELECT * FROM carModels WHERE " + type + " = " +"'" + carModel +"'";
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
        star = Double.parseDouble(carModelInfo[8]);
        speed = Integer.parseInt(carModelInfo[9]);
        power = Double.parseDouble(carModelInfo[10]);
        imageURL = "file:src/main/resources/com/example/car_rental_sys/image/cars/"+ carModel + ".png";

    }

    public CarModel(String carModel, int seats, int price, String gearType, String darkColor, String lightColor, String carBrand, String imageURL) {
        this.carModel = carModel;
        this.seats = seats;
        this.price = price;
        this.gearType = gearType;
        this.darkColor = darkColor;
        this.lightColor = lightColor;
        this.carBrand = carBrand;
        this.imageURL = imageURL;
    }

    public CarModel(int modelID, String carModel, int seats, int price, String gearType, String darkColor,
                    String lightColor, String carBrand, double star, int speed, double power) {
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

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void updateCarModel(String carModel, int seats, int price, String gearType, String darkColor, String lightColor){
        this.carModel = carModel;
        this.seats = seats;
        this.price = price;
        this.gearType = gearType;
        this.darkColor = darkColor;
        this.lightColor = lightColor;
        update();
    }

    public void addCarModel(){
        String values = DataTools.getID("carModels") + ",'" + carModel + "'," + seats + "," + price + ",'" + gearType + "','" + darkColor +
                "','" + lightColor + "','" + carBrand + "'," + DataTools.getCarStar() + "," + DataTools.getCarSpeed() + "," + DataTools.getCarPower();
        String sql = "INSERT INTO carModels VALUES (" + values + ")";
        System.out.println(sql);
        SQL.execute(sql);
        System.out.println(imageURL);
        ImageTools.renameCarImage(imageURL, carModel);
    }

    public void update(){
        String sql = "UPDATE carModels SET carModel = '" + carModel + "', seats = " + seats + ", price = " + price +
                ", gearType = '" + gearType + "', darkColor = '" + darkColor + "', lightColor = '" + lightColor +
                "' WHERE modelID = " + modelID;
        System.out.println(sql);
        boolean process = SQL.execute(sql);
        System.out.println(process);
    }

    public void delete(){
        String sql = "DELETE FROM carModels WHERE modelID = " + modelID;
        SQL.execute(sql);
        System.out.println("done");
        ImageTools.deleteFile(imageURL);
    }
}
