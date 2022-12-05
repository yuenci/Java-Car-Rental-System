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

    private void initData(){
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

    public Car(int carID, String carModel, String chassisNumber, String carNumber, Date manufacturingDate, int status) {
        this.carID = carID;
        this.carModel = carModel;
        this.chassisNumber = chassisNumber;
        this.carNumber = carNumber;
        this.manufacturingDate = manufacturingDate;
        this.status = status;
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

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setManufacturingDate(Date manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCarID() {
        return carID;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getManufacturingDate() {
        System.out.println(manufacturingDate);
        return DateTools.dateToString(manufacturingDate,"yyyy-MM-dd");
    }

    public String getAddedDate() {
        return  DateTools.dateToString(addedDate,"yyyy-MM-dd");
    }

    public int getStatus() {
        return status;
    }

    public void updateCarInfo(String carModel, String chassisNumber, String carNumber, String manufacturingDate){
        this.carModel = carModel;
        this.chassisNumber = chassisNumber;
        this.carNumber = carNumber;
        this.manufacturingDate = DateTools.stringToDateObje(manufacturingDate);
        update();
    }

    public void addCarInfo(){
        String sql = "INSERT INTO carInfo VALUES (" + DataTools.getID("carInfo") + ", '" + carModel + "', '" + chassisNumber + "', '" + carNumber + "', '"
                + DateTools.stringToDateObje(getManufacturingDate()) + "', '" + DateTools.stringToDateObje(DateTools.getNow()) + "', " + status + ")";
        SQL.execute(sql);
    }

    public void update(){
        String sql = "UPDATE carInfo SET carModel = '" + carModel + "', chassisNumber = '" + chassisNumber + "', carNumber = '" + carNumber + "', manufacturingDate = '"
                + DateTools.stringToDateObje(getManufacturingDate()) + "', status = " + status + " WHERE carID = " + carID;
        SQL.execute(sql);
    }

    public void delete(){
        String sql = "DELETE FROM carInfo WHERE modelID = " + carID;
        SQL.execute(sql);
        System.out.println("done");
    }

}
