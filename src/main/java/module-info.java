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
    requires java.desktop;
    requires javafx.media;
    requires org.json;
    requires javafx.swing;
    requires org.apache.commons.io;
    requires core;
    requires org.apache.httpcomponents.httpclient.fluent;
    requires org.apache.httpcomponents.httpmime;

    exports com.example.car_rental_sys.orm;

    opens com.example.car_rental_sys to javafx.fxml;
    exports com.example.car_rental_sys;
    exports com.example.car_rental_sys.controllers;
    opens com.example.car_rental_sys.controllers to javafx.fxml;
    exports com.example.car_rental_sys.ui_components;
    opens com.example.car_rental_sys.ui_components to javafx.fxml;
    exports com.example.car_rental_sys.funtions;
    opens com.example.car_rental_sys.funtions to javafx.fxml;
}