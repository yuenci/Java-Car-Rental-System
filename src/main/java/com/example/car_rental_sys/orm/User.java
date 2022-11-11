package com.example.car_rental_sys.orm;

public class User {
    private int userID;
    private String userName;
    private String userGroup;
    private String post;
    private String securityProblem;
    private String securityAnswer;
    private String birthday;
    private String gender;
    private String country;
    private String DLNumber;
    private String phone;
    private String email;
    private String address;
    private String about;
    private String regTime;

    public User(String email) {
        this.email = email;

    }

}
