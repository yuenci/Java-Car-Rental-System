module com.example.car_rental_sys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.car_rental_sys to javafx.fxml;
    exports com.example.car_rental_sys;
}