module com.example.car_rental_sys {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jsqlparser;
    requires jxbrowser;
    requires jxbrowser.javafx;
    requires java.mail;
    requires org.apache.commons.codec;
    requires jdk.jsobject;


    opens com.example.car_rental_sys to javafx.fxml;
//    opens com.example.javafxwebview to javafx.fxml;
    exports com.example.car_rental_sys;
    exports com.example.car_rental_sys.controllers;
    opens com.example.car_rental_sys.controllers to javafx.fxml;
    exports com.example.car_rental_sys.ui_components;
    opens com.example.car_rental_sys.ui_components to javafx.fxml;
    exports com.example.car_rental_sys.funtions;
    opens com.example.car_rental_sys.funtions to javafx.fxml;
}