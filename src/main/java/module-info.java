module com.example.car_rental_sys {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jsqlparser;


    opens com.example.car_rental_sys to javafx.fxml;
//    opens com.example.javafxwebview to javafx.fxml;
    exports com.example.car_rental_sys;
}