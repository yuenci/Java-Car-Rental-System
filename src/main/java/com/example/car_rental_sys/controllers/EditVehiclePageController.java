package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;

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
        imageURL = DataTools.fileChooser();
        System.out.println(imageURL);
        ArrayList<int[]> colorList = ImageTools.getColorSetsFromImage(imageURL);
        showImgPane();
        Pane[] colorPane = {colorOne,colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight};
        for(int i = 0; i < Objects.requireNonNull(colorList).size(); i++){
            colorPane[i].setStyle(colorPane[i].getStyle() + "-fx-background-color: rgb("+colorList.get(i)[0]+","
                    +colorList.get(i)[1]+","+colorList.get(i)[2]+");");
        }
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
        showDragPane();
        clearColorPlateColor();
        imageURL = null;
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

    private void clearColorPlateColor(){
        Pane[] colorPane = {colorOne,colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight};
        for(Pane pane : colorPane){
            pane.setStyle(pane.getStyle() + "-fx-background-color: white;");
        }
    }

}