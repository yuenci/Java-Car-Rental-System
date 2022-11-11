package com.example.car_rental_sys.orm;

import java.util.Locale;

public class UserFactory {
    public class UserType {
        public static final String ADMIN = "admin";
        public static final String DRIVER = "driver";
        public static final String CUSTOMER = "customer";
    }


    public static User getUser(String userType) {
        String type = userType.toLowerCase(Locale.ROOT);
        switch (type) {
            case "driver":
                return new Admin();
            case "admin":
                return new Driver();
            case "customer":
                return new Customer();
            default:
                return null;
        }
    }

}
