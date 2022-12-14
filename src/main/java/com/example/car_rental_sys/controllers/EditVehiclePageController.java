package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.FXTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.orm.Car;
import com.example.car_rental_sys.orm.CarModel;
import com.example.car_rental_sys.orm.NewCar;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.application.Platform;
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
import java.util.Map;
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

    private static Image newCarImage;

    @FXML
    private Label lblEditVeh,btnDeleteVehicle,lblClearImage;

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

    private Car carInfo = StatusContainer.currentCarInfo;
    private CarModel carModel= StatusContainer.currentCarModel;

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
        }else{
            gradientLight = "#FFFFFF";
            gradientDark = "#FFFFFF";
        }
        initComboBox();
        initComponent();
        initComponentEventListeners();
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
            lblClearImage.setVisible(isSaved && StatusContainer.currentNewCarInfo.getNewCarImage() != null);
            btnSaveOption.setDisable(true);
            btnOperation.setDisable(true);
            if(isSaved){
                btnDeleteVehicle.setText("Delete Draft");
                btnDeleteVehicle.setVisible(true);
            }
            btnOperation.setText("Add Vehicle");
            btnOperation.setStyle(btnOperation.getStyle() + "-fx-background-color: #165dff;");
            btnSaveOption.setText("Save Modify");
            btnOperation.setDisable(isNull());
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
            String color = FXTools.getNodeBgColor(colorOne);
            setCarBgColor(color);
        });
        colorTwo.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorTwo);
            setCarBgColor(color);
        });
        colorThree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorThree);
            setCarBgColor(color);
        });
        colorFour.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorFour);
            setCarBgColor(color);
        });
        colorFive.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorFive);
            setCarBgColor(color);
        });
        colorSix.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorSix);
            setCarBgColor(color);
        });
        colorSeven.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorSeven);
            setCarBgColor(color);
        });
        colorEight.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            String color = FXTools.getNodeBgColor(colorEight);
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
        //System.out.println("->" + imageURL);
        imgVehicle.setImage(ImageTools.getImageObjFromPath(imageURL));
        ImageTools.centerImage(imgVehicle);
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

    private void initComponentEventListeners(){
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            txtVehicleName.textProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue.length() <= 8);
                FXTools.validInputLength(txtVehicleName, "carName",oldValue, newValue);
                btnOperation.setDisable(isNull());
            });
            txtPlateNumber.textProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue.length() <= 5);
                FXTools.validInputLength(txtPlateNumber, "carNumber",oldValue, newValue);
                btnOperation.setDisable(isNull());
            });
            txtChassisNumber.textProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue.length() <= 5);
                FXTools.validInputNumber(txtChassisNumber, "chassis", oldValue, newValue);
                btnOperation.setDisable(isNull());
            });
            txtManufacturing.textProperty().addListener((observable, oldValue, newValue) -> {
                FXTools.validInputIsDate(txtManufacturing,"-",newValue);
                btnSaveOption.setDisable(newValue.length() <= 0);
                btnOperation.setDisable(isNull());
            });
            txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue.length() <= 0);
                FXTools.validInputNumber(txtPrice, "price", oldValue, newValue);
                btnOperation.setDisable(isNull());
            });
            cmbCategory.valueProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue == null);
                btnOperation.setDisable(isNull());
            });
            cmbCarBrand.valueProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue == null);
                btnOperation.setDisable(isNull());
            });
            cmbSeatNumber.valueProperty().addListener((observable, oldValue, newValue) -> {
                btnSaveOption.setDisable(newValue == null);
                btnOperation.setDisable(isNull());
            });
        }else{
            //set the btnSaveOption disable when the new value is empty or same as the old value
            txtVehicleName.textProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            txtPlateNumber.textProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            txtChassisNumber.textProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            txtManufacturing.textProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            txtPrice.textProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            cmbCategory.valueProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            cmbCarBrand.valueProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
            cmbSeatNumber.valueProperty().addListener((observable, oldValue, newValue) -> btnSaveOption.setDisable(newValue.length() <= 0 || newValue.equals(oldValue)));
        }
    }

    @FXML
    private void btnBrowseClicked() {
        //open the browse window
        imageURL = DataTools.fileChooser();
        if(imageURL != null){
            showImgPane("image");
            initColorPlate(imageURL);
            btnSaveOption.setDisable(false);
            btnOperation.setDisable(isNull());
            lblClearImage.setVisible(true);
        }
    }

    @FXML
    private void addVehicleBtnClicked() {
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            getValue();
            isSaved = false;
            String cacheUrl = "src/main/resources/com/example/car_rental_sys/image/others/image_remove_bg_cache.png";
            carModel = new CarModel(vehicleNameValue, Integer.parseInt(seatNumberValue),
                    Integer.parseInt(priceValue), categoryValue, gradientDark, gradientLight,brandValue,cacheUrl);
            carModel.addCarModel();
            carInfo = new Car(vehicleNameValue,chassisNumberValue,plateNumberValue,manufacturingValue,1);
            carInfo.addCarInfo();
            clearValue();
        }else{
            AdminVehiclePageController.instance.setButtonDisableFalse();
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
            if(!Objects.equals(vehicleNameValue, carModel.getCarModel())){
                ImageTools.renameCarImage(imageURL, vehicleNameValue);
            }
            carInfo.setCarModel(vehicleNameValue);
            carModel.setCarModel(vehicleNameValue);
            //here
            carModel.updateCarModel(vehicleNameValue, Integer.parseInt(seatNumberValue), Integer.parseInt(priceValue),
                    categoryValue, gradientDark, gradientLight);
            carInfo.updateCarInfo(vehicleNameValue, chassisNumberValue, plateNumberValue, manufacturingValue, carStatus);
            //System.out.println(vehicleNameValue + " " + seatNumberValue + " " + priceValue + " " + categoryValue + " " + gradientDark + " " + gradientLight);
        }else{
            isSaved = true;
            getValue();
            //Platform.runLater(this::getValue);
            //get value draft
        }
        refreshPane();
        clearProperty();
    }

    @FXML
    private void btnDeleteVehicleClicked() {
        if(!isSaved && AdminVehiclePageController.defaultDisplay.equals("editVehicle")){
            carModel.delete();
            carInfo.delete();
            AdminVehiclePageController.instance.setButtonDisableFalse();
        }else if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            //delete draft
            StatusContainer.currentNewCarInfo.clearProperty();
            isSaved = false;
        }
        refreshPane();
    }

    @FXML
    private void clearBtnClicked() {
        //clear the imageURL
        showDragPane();
        clearColorPlateColor();
        lblClearImage.setVisible(false);
        imageURL = null;
        Pane[] colorPane = {colorOne,colorTwo,colorThree,colorFour,colorFive,colorSix,colorSeven,colorEight};
        for(Pane pane : colorPane){
            pane.setVisible(false);
        }
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle")){
            btnSaveOption.setDisable(isNull());
            btnOperation.setDisable(true);
        }else{
            btnSaveOption.setDisable(true);
        }
        gradientDark = "#FFFFFF";
        gradientLight = "#FFFFFF";
        imgPane.setStyle("-fx-background-color: linear-gradient(to left, "+gradientDark+", "+gradientLight+");");
    }

    @FXML
    private void imgVehicleDrop(DragEvent event) {
        //get the drag image path
       // System.out.println("get!");
        imageURL = event.getDragboard().getFiles().get(0).getAbsolutePath();
        System.out.println(imageURL);
        showImgPane("image");
    }

    private void refreshPane(){
        AdminVehiclePageController.defaultDisplay = "overview";
        AdminVehiclePageController.instance.refreshDisplayPane();
        AdminVehiclePageController.instance.setDefaultFocus();
    }

    private void getValue() {
        vehicleNameValue = txtVehicleName.getText();
        plateNumberValue = txtPlateNumber.getText();
        chassisNumberValue = txtChassisNumber.getText();
        manufacturingValue = txtManufacturing.getText();
        priceValue = txtPrice.getText();
        categoryValue = cmbCategory.getValue();
        seatNumberValue = cmbSeatNumber.getValue();
        if(AdminVehiclePageController.defaultDisplay.equals("addVehicle") && isSaved){
            brandValue = cmbCarBrand.getValue();
            StatusContainer.currentNewCarInfo = new NewCar(vehicleNameValue, chassisNumberValue, plateNumberValue, manufacturingValue,
                    priceValue, seatNumberValue, categoryValue, brandValue, gradientDark, gradientLight, imageURL, newCarImage);

        }else if(AdminVehiclePageController.defaultDisplay.equals("editVehicle")){
            //get the key by value from cmbCarBrand
            for (Map.Entry<Integer, String> entry : statusMap.entrySet()) {
                if (Objects.equals(cmbCarBrand.getValue(), entry.getValue())) {
                    carStatus = entry.getKey();
                    //System.out.println(carStatus);
                }
            }
            //carStatus = statusMap.get(brandValue);
        }else{
            brandValue = cmbCarBrand.getValue();
            System.out.println(brandValue);
        }
    }

    private boolean isNull(){
        if(txtVehicleName.getText().isEmpty() || txtPlateNumber.getText().isEmpty() || txtChassisNumber.getText().isEmpty() ||
                txtManufacturing.getText().isEmpty() || txtPrice.getText().isEmpty() || cmbCategory.getValue() == null ||
                cmbCarBrand.getValue() == null || cmbSeatNumber.getValue() == null || imageURL == null){
            return true;
        }else{
            return false;
        }
    }

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

    public void setBackAllValue(){
        NewCar newCar = StatusContainer.currentNewCarInfo;
        txtVehicleName.setText(newCar.getCarModel());
        txtPlateNumber.setText(newCar.getCarNumber());
        txtChassisNumber.setText(newCar.getChassisNumber());
        txtManufacturing.setText(newCar.getManufacturingDate());
        txtPrice.setText(newCar.getPrice());
        cmbCategory.setValue(newCar.getGearType());
        cmbSeatNumber.setValue(newCar.getSeatNumber());
        cmbCarBrand.setValue(newCar.getCarBrand());
        imageURL = newCar.getImageUrl();
        gradientDark = newCar.getGradientDark();
        gradientLight = newCar.getGradientLight();
        System.out.println(gradientDark + " : " + gradientLight);
        if(imageURL != null){
            showImgPane("image");
            initColorPlate(imageURL);
        }else{
            showImgPane("url");
        }
        imgPane.setStyle(imgPane.getStyle() + "-fx-background-color: " + "linear-gradient(to left, " + gradientDark + ", " + gradientLight + ");");
    }

    private void showImgPane(String type){
        if(Objects.equals(type, "url")) {
            if (imageURL != null) {
                imgVehicle.setImage(new javafx.scene.image.Image("file:///" + imageURL));
                imgPane.setVisible(true);
                dragPane.setVisible(false);
            } else {
                imgPane.setVisible(false);
                dragPane.setVisible(true);
            }
        }else if(Objects.equals(type, "image")) {
            newCarImage = ImageTools.removeBackground(imageURL);    //when testing please comment this line
            imgVehicle.setImage(newCarImage);
            imgPane.setVisible(true);
            dragPane.setVisible(false);
        }
        ImageTools.centerImage(imgVehicle);
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
        carInfo = new Car(carID);
        carModel = new CarModel(carID);

        Platform.runLater(() -> {
            vehicleNameValue = carModel.getCarModel();
            plateNumberValue = carInfo.getCarNumber();
            chassisNumberValue = carInfo.getChassisNumber();
            //System.out.println("chassisNum: " + chassisNumberValue);
            manufacturingValue = carInfo.getManufacturingDate();
            priceValue = String.valueOf(carModel.getPrice());
            categoryValue = carModel.getGearType();
            seatNumberValue = String.valueOf(carModel.getSeats());
            brandValue = carModel.getCarBrand();
            carStatus = carInfo.getStatus();

            imageURL = carModel.getImageURL();
            gradientLight = carModel.getLightColor();
            gradientDark = carModel.getDarkColor();
            initTextField();
            initVehiclePane();
            initColorPlate(imageURL);
        });
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
