package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.sqlParser.SQL;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

    public User(){
        // do nothing
    }

    public User(String email) {
        this.email = email;
        initUser();
    }

    private void initUser() {
        String sql = "select * from userInfo where email = '" + this.email + "'";
        //System.out.println(sql);
        ArrayList<String[]> userInfo = SQL.query(sql);
        if (userInfo.size() == 0){
            throw new RuntimeException("No user found");
        }
        String[] user = userInfo.get(0);
        this.userID = Integer.parseInt(user[0]);
        this.userName = user[1];
        this.userGroup = user[2];
        this.post = user[3];
        this.securityProblem = user[4];
        this.securityAnswer = user[5];
        this.birthday = user[6];
        this.gender = user[7];
        this.country = user[8];
        this.DLNumber = user[9];
        this.phone = user[10];
        this.email = user[11];
        this.address = user[12];
        this.about = user[13];
        this.regTime = user[14];
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSecurityProblem() {
        return securityProblem;
    }

    public void setSecurityProblem(String securityProblem) {
        this.securityProblem = securityProblem;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDLNumber() {
        return DLNumber;
    }

    public void setDLNumber(String DLNumber) {
        this.DLNumber = DLNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    @Override
    public String toString() {
        return "User{" +  ",\n" +
                "userID=" + userID + ",\n" +
                "userName='" + userName + '\'' + ",\n" +
                "userGroup='" + userGroup + '\'' + ",\n" +
                "post='" + post + '\'' + ",\n" +
                "securityProblem='" + securityProblem + '\'' +",\n" +
                "securityAnswer='" + securityAnswer + '\'' + ",\n" +
                "birthday='" + birthday + '\'' + ",\n" +
                "gender='" + gender + '\'' + ",\n" +
                "country='" + country + '\'' + ",\n" +
                "DLNumber='" + DLNumber + '\'' + ",\n" +
                "phone='" + phone + '\'' + ",\n" +
                "email='" + email + '\'' + ",\n" +
                "address='" + address + '\'' + ",\n" +
                "about='" + about + '\'' + ",\n" +
                "regTime='" + regTime + '\'' + ",\n" +
                '}';
    }
}
