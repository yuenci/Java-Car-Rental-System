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

    public CarModel(int modelID, String carModel, int seats, int price, String gearType, String darkColor, String lightColor, String carBrand, int star, int speed, int power) {
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
}
