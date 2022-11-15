package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ToolsLib.DataTools;

import java.util.Locale;
import java.util.Objects;

public class UserFactory {

    public static User getUser(String email) {
        String role = DataTools.getUserRoleFromUserEmail(email);
        switch (Objects.requireNonNull(role)) {
            case "driver":
                return new Admin(email);
            case "admin":
                return new Driver(email);
            case "customer":
                return new Customer(email);
            default:
                return null;
        }
    }

    public static User getUser(int userID) {
        String email = DataTools.getEmailFromUserID(userID);
        String role = DataTools.getUserRoleFromUserID(userID);
        switch (role) {
            case "driver":
                return new Admin(email);
            case "admin":
                return new Driver(email);
            case "customer":
                return new Customer(email);
            default:
                return null;
        }
    }

}
