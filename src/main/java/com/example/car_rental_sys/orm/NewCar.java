package com.example.car_rental_sys.orm;

import javafx.scene.image.Image;

import java.lang.reflect.Field;

public class NewCar {
    private String carModel;
    private String chassisNumber;
    private String carNumber;    //plateNumber
    private String manufacturingDate;
    private String price;
    private String seatNumber;
    private String gearType;  //category Type
    private String carBrand;   //car brand
    private String gradientDark;
    private String gradientLight;
    private String imageUrl;
    private Image newCarImage;


    public NewCar(String carModel, String chassisNumber, String carNumber, String manufacturingDate, String price,
                  String seatNumber, String gearType, String carBrand, String gradientDark, String gradientLight, String imageUrl, Image newCarImage) {
        this.carModel = carModel;
        this.chassisNumber = chassisNumber;
        this.carNumber = carNumber;
        this.manufacturingDate = manufacturingDate;
        this.price = price;
        this.seatNumber = seatNumber;
        this.gearType = gearType;
        this.carBrand = carBrand;
        this.gradientDark = gradientDark;
        this.gradientLight = gradientLight;
        this.imageUrl = imageUrl;
        this.newCarImage = newCarImage;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getGradientDark() {
        return gradientDark;
    }

    public void setGradientDark(String gradientDark) {
        this.gradientDark = gradientDark;
    }

    public String getGradientLight() {
        return gradientLight;
    }

    public void setGradientLight(String gradientLight) {
        this.gradientLight = gradientLight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image getNewCarImage() {
        return newCarImage;
    }

    public void setNewCarImage(Image newCarImage) {
        this.newCarImage = newCarImage;
    }

    public void clearProperty() {
        this.carModel = null;
        this.chassisNumber = null;
        this.carNumber = null;
        this.manufacturingDate = null;
        this.price = null;
        this.seatNumber = null;
        this.gearType = null;
        this.carBrand = null;
        this.gradientDark = null;
        this.gradientLight = null;
        this.imageUrl = null;
        this.newCarImage = null;

        System.out.println("draft deleted");
    }

    public boolean checkAllNull() throws IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(this) != null) {
                if (field.getName().equals("gradientDark") || field.getName().equals("gradientLight")) {
                    continue;
                }
                return false;
            }
        }
        return true;

    }
}
