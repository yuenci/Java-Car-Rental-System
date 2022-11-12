package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Date;

public class Car {
    private int carID;
    private String carModel;
    private String chassisNumber;
    private String carNumber;
    private Date manufacturingDate;
    private Date addedDate;
    private int status;

    public Car(int carID){
        this.carID = carID;
        initData();
    }

    private void  initData(){
        String sql = "SELECT * FROM carInfo WHERE carID = " + carID;
        ArrayList<String[]> data = SQL.query(sql);
        if(data.size() == 0){
            return;
        }
        String[] carInfo = data.get(0);
        carModel = carInfo[1];
        chassisNumber = carInfo[2];
        carNumber = carInfo[3];
        manufacturingDate = DateTools.stringToDateObje(carInfo[4]);
        addedDate = DateTools.stringToDateObje(carInfo[5]);
        status = Integer.parseInt(carInfo[6]);
    }

    public Car(int carID, String carModel, String chassisNumber, String carNumber, Date manufacturingDate, Date addedDate, int status) {
        this.carID = carID;
        this.carModel = carModel;
        this.chassisNumber = chassisNumber;
        this.carNumber = carNumber;
        this.manufacturingDate = manufacturingDate;
        this.addedDate = addedDate;
        this.status = status;
    }
}
