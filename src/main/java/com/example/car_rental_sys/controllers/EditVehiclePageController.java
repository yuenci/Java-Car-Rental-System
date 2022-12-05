package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Car;
import com.example.car_rental_sys.orm.CarModel;
import com.example.car_rental_sys.orm.NewCar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EditVehiclePageController {

    public static EditVehiclePageController instance;

    private static String vehicleNameValue;
    private static String plateNumberValue;
    private static String chassisNumberValue;
    private static String manufacturingValue;
    private static String priceValue;
    private static String categoryValue;
    private static String seatNumberValue;
    private static String brandValue;
    private static String imageURL;
    private static int carStatus;
    private static boolean isSaved = false;

    private static int bgcStatus = 0;

    private static String gradientLight = "#FFFFFF";
    private static String gradientDark = "#FFFFFF";

    public Label ivLabelBackground, lblEditVeh,btnDeleteVehicle;

    @FXML
    private Button btnOperation, btnSaveOption;
    @FXML
    private ComboBox<String> cmbCategory,cmbSeatNumber, cmbCarBrand;
    @FXML
    private Pane imgPane, dragPane;
    @FXML
    private TextField txtManufacturing,txtPlateNumber,txtPrice,txtVehicleName,txtChassisNumber;
    @FXML
    private ImageView imgVehicle;
    @FXML
    private Pane colorOne, colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight;

    private final String[] seatNum = {"2","4"};
    private final String[] category = {"Manual","Automatic"};
    private final String[] brand = {"Bugatti","Chevrolet","Ferrari","Ford","Honda","Koenigsegg","lamborghini","Maserati","McLaren","Myvi","Porsche","Wuling"};
    private final HashMap<Integer,String> statusMap = new HashMap<>() {{
        put(0, "Not Exist");
        put(1, "Ready");
        put(2, "In Rent");
        put(3, "Reserved");
        put(4, "Repairing");
    }};

    public EditVehiclePageController(){
        instance = this;
    }

    @FXML
    private void initialize(){
        if(isSaved){
            setBackAllValue();
            //System.out.println("isSaved");
        }
        initComboBox();
        initComponent();
        initColorPlateEvent();
        //System.out.println("init");
    }

    private void initComboBox(){
        //add item into combo box
        cmbSeatNumber.getItems().addAll(seatNum);
        cmbCategory.getItems().addAll(category);
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            cmbCarBrand.getItems().addAll(brand);
        }else{
            cmbCarBrand.getItems().addAll(statusMap.values());
        }
    }

    private void initComponent(){
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            btnOperation.setText("Add Vehicle");
            btnOperation.setStyle(btnOperation.getStyle() + "-fx-background-color: #165dff;");
            btnSaveOption.setText("Save Modify");
        }else{
            btnDeleteVehicle.setVisible(true);
            btnOperation.setText("Cancel");
            btnOperation.setStyle(btnOperation.getStyle() +
                    "-fx-background-color: transparent; -fx-border-color: #165dff; " +
                    "-fx-border-radius: 6px; -fx-border-insets: 0; -fx-text-fill: #165dff;");
            btnSaveOption.setStyle(btnSaveOption.getStyle() + "-fx-background-color: #165dff; -fx-text-fill: white; -fx-background-radius: 6px");
            lblEditVeh.setText("Vehicle Status");
            txtChassisNumber.setEditable(false);
            txtManufacturing.setEditable(false);
            initComboEvent();
        }
    }

    private void initColorPlateEvent(){
        colorOne.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorOne);
            setCarBgColor(color);
        });
        colorTwo.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorTwo);
            setCarBgColor(color);
        });
        colorThree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorThree);
            setCarBgColor(color);
        });
        colorFour.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorFour);
            setCarBgColor(color);
        });
        colorFive.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorFive);
            setCarBgColor(color);
        });
        colorSix.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorSix);
            setCarBgColor(color);
        });
        colorSeven.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorSeven);
            setCarBgColor(color);
        });
        colorEight.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = getNodeColor(colorEight);
            setCarBgColor(color);
        });
    }

    private void initComboEvent(){
        cmbCarBrand.valueProperty().addListener((observable, oldValue, newValue) -> {
            //if new value is equal to 2 or 3, then ignore the selection of cmbCarBrand
            if(Objects.equals(oldValue, "Not Exist") || Objects.equals(oldValue, "Ready") || Objects.equals(oldValue, "Repairing")){
                if(Objects.equals(newValue, "In Rent") || Objects.equals(newValue, "Reserved")){
                    cmbCarBrand.setValue(oldValue);
                }
            }
        });
    }

    private String getNodeColor(Pane pane){
        //get the style from pane and turn into string
        String style = pane.getStyle();
        //get the last 8 character from the string
        String lastStr = style.substring(style.length() - 8);
        //get the first 7 character from the string

        return lastStr.substring(0,7);
    }

    private void initTextField(){
        txtVehicleName.setText(vehicleNameValue);
        txtPlateNumber.setText(plateNumberValue);
        txtChassisNumber.setText(chassisNumberValue);
        txtManufacturing.setText(manufacturingValue);
        txtPrice.setText(priceValue);
        cmbCategory.setValue(categoryValue);
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            cmbCarBrand.setValue(brandValue);
        }else{
            cmbCarBrand.setValue(statusMap.get(carStatus));
            if(carStatus == 2 || carStatus == 3){
                cmbCarBrand.setDisable(true);
            }
        }
        cmbSeatNumber.setValue(seatNumberValue);
    }

    private void initVehiclePane(){
        imgPane.setVisible(true);
        dragPane.setVisible(false);
        imgPane.setStyle(imgPane.getStyle() + "-fx-background-color: " + "linear-gradient(to left, " + gradientDark + ", " + gradientLight + ");");
        System.out.println("->" + imageURL);
        imgVehicle.setImage(ImageTools.getImageObjFromPath(imageURL));
    }

    private void initColorPlate(String imageURL){
        //showImgPane();
        ArrayList<int[]> colorList = ImageTools.getColorSetsFromImage(imageURL);
        Pane[] colorPane = {colorOne,colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight};
        for(int i = 0; i < Objects.requireNonNull(colorList).size(); i++){
            String hex = String.format("#%02x%02x%02x", colorList.get(i)[0], colorList.get(i)[1], colorList.get(i)[2]);
            colorPane[i].setVisible(true);
            colorPane[i].setStyle(colorPane[i].getStyle() + "-fx-cursor: hand; -fx-background-color: " + hex + ";");
        }
    }

    @FXML
    private void btnBrowseClicked() {
        //open the browse window
        imageURL = DataTools.fileChooser();
        //System.out.println(imageURL);
        showImgPane();
        initColorPlate(imageURL);
    }

    @FXML
    private void addVehicleBtnClicked() {
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            getValue();
            clearValue();
            isSaved = false;
        }
        refreshPane();
        clearProperty();
    }

    @FXML
    private void saveVehicleBtnClicked() {
        //System.out.println(System.currentTimeMillis());
        if(AdminVehiclePageController.defaultDisplay.equals("editVehicle")){
            getValue();
            System.out.println(vehicleNameValue + " " + seatNumberValue + " " + priceValue + " " + categoryValue + " " + gradientDark + " " + gradientLight);
            //here
            StatusContainer.currentCarModel.updateCarModel(vehicleNameValue, Integer.parseInt(seatNumberValue), Integer.parseInt(priceValue),
                    categoryValue, gradientDark, gradientLight);

            clearProperty();
            System.out.println(vehicleNameValue + " " + seatNumberValue + " " + priceValue + " " + categoryValue + " " + gradientDark + " " + gradientLight);
        }else{
            //getValue();
            //get value draft
        }
        //go to overview page
        //set isSaved to true
        isSaved = true;
        refreshPane();
    }

    @FXML
    private void btnDeleteVehicleClicked() {
        //delete the vehicle & go to overview page
        isSaved = false;
        StatusContainer.currentCarModel.delete();
        StatusContainer.currentCarInfo.delete();
        refreshPane();
    }

    private void refreshPane(){
        AdminVehiclePageController.defaultDisplay = "overview";
        AdminVehiclePageController.instance.refreshDisplayPane();
        AdminVehiclePageController.instance.setDefaultFocus();
    }

    @FXML
    void clearBtnClicked() {
        //clear the imageURL
        showDragPane();
        clearColorPlateColor();
        imageURL = null;
        Pane[] colorPane = {colorOne,colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight};
        for(Pane pane : colorPane){
            pane.setVisible(false);
        }
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
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            StatusContainer.currentNewCarInfo = new NewCar(vehicleNameValue, chassisNumberValue, plateNumberValue, manufacturingValue,
                    priceValue, seatNumberValue, categoryValue, brandValue, gradientDark, gradientLight);
        }
    }

    //clear the value
    private void clearValue(){
        TextField[] textFields = {txtVehicleName,txtPlateNumber,txtChassisNumber,txtManufacturing,txtPrice};
        for(TextField textField : textFields){
            textField.setText("");
        }
        ComboBox[] comboBoxes = {cmbCategory,cmbSeatNumber,cmbCarBrand};
        for(ComboBox comboBox : comboBoxes){
            comboBox.setValue(null);
        }
        showDragPane();
    }

    private void clearProperty(){
        vehicleNameValue = null;
        plateNumberValue = null;
        chassisNumberValue = null;
        manufacturingValue = null;
        priceValue = null;
        categoryValue = null;
        seatNumberValue = null;
        brandValue = null;
        imageURL = null;
        gradientDark = "#FFFFFF";
        gradientLight = "#FFFFFF";
    }

    //set all the text field value
    public void setBackAllValue(){
        txtVehicleName.setText(StatusContainer.currentNewCarInfo.getCarModel());
        txtPlateNumber.setText(StatusContainer.currentNewCarInfo.getCarNumber());
        txtChassisNumber.setText(StatusContainer.currentNewCarInfo.getChassisNumber());
        txtManufacturing.setText(StatusContainer.currentNewCarInfo.getManufacturingDate());
        txtPrice.setText(StatusContainer.currentNewCarInfo.getPrice());
        cmbCategory.setValue(StatusContainer.currentNewCarInfo.getGearType());
        cmbSeatNumber.setValue(StatusContainer.currentNewCarInfo.getSeatNumber());
        cmbCarBrand.setValue(StatusContainer.currentNewCarInfo.getCarBrand());
        if(imageURL != null){
            showImgPane();
            initColorPlate(imageURL);
        }
        gradientDark = StatusContainer.currentNewCarInfo.getGradientDark();
        gradientLight = StatusContainer.currentNewCarInfo.getGradientLight();
        showImgPane();
    }


    private void showImgPane(){
        if(imageURL != null){
            imgVehicle.setImage(new javafx.scene.image.Image("file:///"+imageURL));
            imgPane.setVisible(true);
            dragPane.setVisible(false);
        }else{
            imgPane.setVisible(false);
            dragPane.setVisible(true);
        }
    }

    private void showDragPane(){
        imgVehicle.setImage(null);
        imgPane.setVisible(false);
        dragPane.setVisible(true);
    }

    private void clearColorPlateColor(){
        Pane[] colorPane = {colorOne,colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight};
        for(Pane pane : colorPane){
            pane.setStyle(pane.getStyle() + "-fx-background-color: white;");
        }
    }

    public void setCarData(int carID){
        StatusContainer.currentCarInfo = new Car(carID);
        StatusContainer.currentCarModel = new CarModel(carID);

        vehicleNameValue = StatusContainer.currentCarModel.getCarModel();
        plateNumberValue = StatusContainer.currentCarInfo.getCarNumber();
        chassisNumberValue = StatusContainer.currentCarInfo.getChassisNumber();
        manufacturingValue = StatusContainer.currentCarInfo.getManufacturingDate();
        priceValue = String.valueOf(StatusContainer.currentCarModel.getPrice());
        categoryValue = StatusContainer.currentCarModel.getGearType();
        seatNumberValue = String.valueOf(StatusContainer.currentCarModel.getSeats());
        brandValue = StatusContainer.currentCarModel.getCarBrand();
        carStatus = StatusContainer.currentCarInfo.getStatus();

        imageURL = StatusContainer.currentCarModel.getImageURL();
        gradientLight = StatusContainer.currentCarModel.getLightColor();
        gradientDark = StatusContainer.currentCarModel.getDarkColor();
        initTextField();
        initVehiclePane();
        System.out.println(imageURL);
        initColorPlate(imageURL);
    }

    private void setCarBgColor(String color){
        if(bgcStatus == 0){
            gradientDark = color;
            bgcStatus = 1;
        }else if(bgcStatus == 1){
            gradientLight = color;
            bgcStatus = 0;
        }
        //System.out.println("now dark: " + gradientDark + ", now light:" + gradientLight);
        imgPane.setStyle("-fx-background-color: linear-gradient(to left, "+gradientDark+", "+gradientLight+");");
    }
}
