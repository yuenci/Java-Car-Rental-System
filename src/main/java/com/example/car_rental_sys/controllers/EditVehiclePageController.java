package com.example.car_rental_sys.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class EditVehiclePageController {
    private static String vehicleNameValue;
    private static String plateNumberValue;
    private static String chassisNumberValue;
    private static String manufacturingValue;
    private static String priceValue;
    private static String categoryValue;
    private static String seatNumberValue;
    private static String brandValue;
    private static String imageURL;
    private static boolean isSaved = false;

    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ComboBox<String> cmbSeatNumber;
    @FXML
    private ComboBox<String> cmbCarBrand;
    @FXML
    private Pane imgPane;
    @FXML
    private Pane dragPane;
    @FXML
    private TextField txtManufacturing;
    @FXML
    private TextField txtPlateNumber;
    @FXML
    private TextField txtPrice;
    @FXML
    private ImageView imgVehicle;
    @FXML
    private TextField txtVehicleName;
    @FXML
    private TextField txtChassisNumber;

    @FXML
    private Pane colorOne;
    @FXML
    private Pane colorTwo;
    @FXML
    private Pane colorThree;
    @FXML
    private Pane colorFour;
    @FXML
    private Pane colorFive;
    @FXML
    private Pane colorSix;
    @FXML
    private Pane colorSeven;
    @FXML
    private Pane colorEight;

    private final String[] seatNum = {"2","4"};
    private final String[] category = {"Manual","Automatic"};
    private final String[] brand = {"Bugatti","Chevrolet","Ferrari","Ford","Honda","Koenigsegg","lamborghini","Maserati","McLaren","Myvi","Porsche","Wuling"};


    @FXML
    private void initialize(){
        if(isSaved){
            setBackAllValue();
            //System.out.println("isSaved");
        }
        initComboBox();
    }

    private void initComboBox(){
        //add item into combo box
        cmbSeatNumber.getItems().addAll(seatNum);
        cmbCategory.getItems().addAll(category);
        cmbCarBrand.getItems().addAll(brand);
    }

    @FXML
    void btnBrowseClicked(MouseEvent event) {
        //open the browse window
    }

    @FXML
    void addVehicleBtnClicked(MouseEvent event) {
        getValue();
        clearValue();
        isSaved = false;
    }

    @FXML
    void saveVehicleBtnClicked(MouseEvent event) {
        //System.out.println(System.currentTimeMillis());
        getValue();
        if(AdminVehiclePageController.defaultDisplay.equals("editVehicle")){
            AdminVehiclePageController.defaultDisplay = "overview";
            AdminVehiclePageController.instance.refreshDisplayPane();
            AdminVehiclePageController.instance.setDefaultFocus();
        }
        //go to overview page
        //set isSaved to true
        isSaved = true;
    }

    @FXML
    void clearBtnClicked(MouseEvent event) {
        //clear the imageURL
        imageURL = null;
        showDragPane();
    }

    @FXML
    void imgVehicleDrop(DragEvent event) {
        //get the drag image path
        System.out.println("get!");
        imageURL = event.getDragboard().getFiles().get(0).getAbsolutePath();
        System.out.println(imageURL);
        showImgPane();
    }

    private void getValue(){
        vehicleNameValue = txtVehicleName.getText();
        plateNumberValue = txtPlateNumber.getText();
        chassisNumberValue = txtChassisNumber.getText();
        manufacturingValue = txtManufacturing.getText();
        priceValue = txtPrice.getText();
        categoryValue = cmbCategory.getValue();
        seatNumberValue = cmbSeatNumber.getValue();
        brandValue = cmbCarBrand.getValue();
    }

    //clear the value
    private void clearValue(){
        txtVehicleName.setText("");
        txtPlateNumber.setText("");
        txtChassisNumber.setText("");
        txtManufacturing.setText("");
        txtPrice.setText("");
        cmbCategory.setValue("");
        cmbSeatNumber.setValue("");
        cmbCarBrand.setValue("");
        showDragPane();
    }

    //set all the text field value
    public void setBackAllValue(){
        txtVehicleName.setText(vehicleNameValue);
        txtPlateNumber.setText(plateNumberValue);
        txtChassisNumber.setText(chassisNumberValue);
        txtManufacturing.setText(manufacturingValue);
        txtPrice.setText(priceValue);
        cmbCategory.setValue(categoryValue);
        cmbSeatNumber.setValue(seatNumberValue);
        cmbCarBrand.setValue(brandValue);
        showImgPane();
    }


    private void showImgPane(){
        //set the image view
        imgVehicle.setImage(new javafx.scene.image.Image("file:///"+imageURL));
        imgPane.setVisible(true);
        dragPane.setVisible(false);
    }

    private void showDragPane(){
        //clear the image
        imgVehicle.setImage(null);
        //imgPane visible false
        imgPane.setVisible(false);
        //dragPane visible true
        dragPane.setVisible(true);
    }

    private void initColorPlate(){
        colorOne.setStyle(colorOne.getStyle() + "-fx-background-color: #FF0000");
        colorTwo.setStyle(colorTwo.getStyle() + "-fx-background-color: #FF7F00");
        colorThree.setStyle(colorThree.getStyle() + "-fx-background-color: #FFFF00");
        colorFour.setStyle(colorFour.getStyle() + "-fx-background-color: #00FF00");
        colorFive.setStyle(colorFive.getStyle() + "-fx-background-color: #0000FF");
        colorSix.setStyle(colorSix.getStyle() + "-fx-background-color: #4B0082");
        colorSeven.setStyle(colorSeven.getStyle() + "-fx-background-color: #9400D3");
        colorEight.setStyle(colorEight.getStyle() + "-fx-background-color: #FFFFFF");
    }

}
