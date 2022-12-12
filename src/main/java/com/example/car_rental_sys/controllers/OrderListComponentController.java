package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.funtions.FileOperate;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.UIFilter;
import com.example.car_rental_sys.ui_components.UIOrderRow;
import com.example.car_rental_sys.ui_components.UIPagination;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Function;

public class OrderListComponentController {

    @FXML
    private Pane panelOrderList;
    private int totalOrders = 0;
    private ArrayList<String[]> data = null;
    public Label numOrder;
    public Button btnAllOrder,btnComplete,btnContinue,btnCancel;
    public Pane paneFilterBox;
    @FXML
    private Pane pagContainer;
    @FXML
    private Pane tableContainer,mainPane;
    @FXML
    private ImageView carImg;

    public static OrderListComponentController instance;
    private User user = StatusContainer.currentUser;

    @FXML
    public void initialize() {
        instance = this;
        initTotalOrder();
        initPagination();
        //Platform.runLater(this::initPagination);
        Platform.runLater(() -> {
            initButtonStyle();
            initTable();
            initTheme();
        });
        //System.out.println(User.instance.getUserID());
    }

    public void addFilterPane(){
        UIFilter filter = new UIFilter();
        paneFilterBox.getChildren().add(filter);
    }

    public void removeFilterPane(){
        SearchBarController.instance.setFilterState(0);
        Thread thread = new Thread(() -> Platform.runLater(() -> paneFilterBox.getChildren().clear()));
        thread.start();
    }

    private void initPagination(){
        pagContainer.getChildren().add(new UIPagination(totalOrders,15));
    }

    private void initButtonStyle(){
        btnAllOrder.getStyleClass().add("btnFocusStyle");
        btnAllOrder.getStyleClass().remove("controlBtn");
    }

    private void initTotalOrder(){
        if(user instanceof Customer){
            totalOrders = DataTools.getCustomerOrderNum(user.getUserID());
        }else{
            //totalOrders = DataTools.getTotalOrderNum();
            data = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);
            totalOrders = data.size();
        }
        Platform.runLater(() -> numOrder.setText(totalOrders + " Orders"));
    }

    private void initTable(){
        getDataArray();

        refreshTable(1);
    }

    public void getDataArray(){
        if(user instanceof Customer){
            String sql = "SELECT * FROM orders WHERE userID = " + user.getUserID();
            data = SQL.query(sql);
        }else{
            data = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);
        }
    }

    public void refreshTable(int page){
        tableContainer.getChildren().clear();
        ArrayList<String[]> orderData = DataTools.getTableData(data, page, 15);
        //get each row's data with column 1, 2, 3, 8, 11
        for(int i = 0; i < orderData.size(); i++){
            String[] row = orderData.get(i);
            UIOrderRow orderRow = new UIOrderRow(Integer.parseInt(row[0]),DataTools.getCarModelFromCarID(Integer.parseInt(row[1])),
                    row[2], Integer.parseInt(row[8]), Integer.parseInt(row[11]));
            orderRow.setLayoutY(i * 30);
            tableContainer.getChildren().add(orderRow);
        }
        initEmptyText();
    }

    @FXML
    private void headerButtonClickEvent(MouseEvent e){
        Object source = e.getSource();
        Button btn = (Button) source;
        clearFocusStyle();
        btn.getStyleClass().add("btnFocusStyle");
        btn.getStyleClass().remove("controlBtn");
    }


    private void clearFocusStyle(){
        Button[] btns = {btnAllOrder,btnComplete,btnContinue,btnCancel};
        for (Button btn : btns) {
            btn.getStyleClass().remove("btnFocusStyle");
            btn.getStyleClass().add("controlBtn");
        }
    }

    @FXML
    void btnAllOrderClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        if(user instanceof Customer){
            String query = "SELECT * FROM orders WHERE userID = " + user.getUserID();
            //System.out.println(query);
            data = SQL.query(query);
        }else{
            data = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);
        }
        recountTotalOrders();
        UIPagination.refreshPaginationState();
        refreshTable(1);
        //replaceButtonStyle();
    }

    @FXML
    void btnCompletedClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        String query;
        if(user instanceof Customer){
            query = "SELECT * FROM orders WHERE userID = " + user.getUserID() + " AND status = 5";
        }else{
            query = "SELECT * FROM orders WHERE status = 5";
        }
        //System.out.println(query);
        data = SQL.query(query);
        recountTotalOrders();
        UIPagination.refreshPaginationState();
        refreshTable(1);
        //replaceButtonStyle();
    }

    @FXML
    void btnContinueClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        String query = "SELECT * FROM orders WHERE status = 3 OR status = 4 OR status = 1";
        //System.out.println(query);
        data = SQL.query(query);
        if(user instanceof Customer){
            ArrayList<String[]> temp = new ArrayList<>();
            for (String[] row : data) {
                if(row[7].equals(user.getUserID() + "")){
                    temp.add(row);
                }
            }
            data = temp;
        }
        recountTotalOrders();
        UIPagination.refreshPaginationState();
        refreshTable(1);
        //replaceButtonStyle();
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        headerButtonClickEvent(event);
        String query;
        if(user instanceof Customer){
            query = "SELECT * FROM orders WHERE userID = " + user.getUserID() + " AND status = -1";
        }else{
            query = "SELECT * FROM orders WHERE status = -1";
        }
        //System.out.println(query);
        data = SQL.query(query);
        recountTotalOrders();
        UIPagination.refreshPaginationState();
        refreshTable(1);
        //replaceButtonStyle();
    }

    private void initTheme(){
        if(StatusContainer.currentUser instanceof Customer){
            panelOrderList.getStylesheets()
                    .add(new File("src/main/resources/com/example/car_rental_sys/style/orderListComponentDark.css")
                            .toURI().toString());
        }
    }

    private void recountTotalOrders(){
        totalOrders = data.size();
        pagContainer.getChildren().clear();
        if(totalOrders > 0){
            initPagination();
        }
        Platform.runLater(() -> numOrder.setText(totalOrders + " Orders"));
    }

    private void initEmptyText(){
        if (totalOrders == 0){
            Label label = new Label("No Order Records Found");
            label.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
            label.getStyleClass().add("lblPrimaryText");
            label.setLayoutX(150);
            label.setLayoutY(100);
            tableContainer.getChildren().add(label);
        }
    }


}
