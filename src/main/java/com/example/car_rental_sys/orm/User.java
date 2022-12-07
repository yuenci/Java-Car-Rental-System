package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.sqlParser.SQL;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
    protected int userID = 0;
    protected String userName ="null";
    protected String userFirstName ="null";
    protected String userLastName ="null";
    protected String password = "0123456789";
    protected String userGroup = "customer";
    protected String post = "null";
    protected String securityProblem = "null";
    protected String securityAnswer = "null";
    protected String birthday = "2022-01-01 00:00:00";
    protected String gender = "null";
    protected String country = "null";
    protected String DLNumber   = "null";
    protected String phone = "0123456789";
    protected String email = "rent@mail.com";
    protected String address = "null";
    protected String about = "null";
    protected String regTime = "null";
    protected Image avatar;

    public User(){
        // do nothing
    }

    public User(String email) {
        this.email = email;
        initUser();
    }



    // for creating new user
    public User(String userFirstName, String userLastName, String password, String userGroup, String post, String securityProblem,
                String securityAnswer, String birthday, String gender, String country, String DLNumber,
                String phone, String email, String address, String about, String regTime){
        this.userName = userFirstName + "-" + userLastName;
        this.password = password;
        this.userGroup = userGroup;
        this.post = post;
        this.securityProblem = securityProblem;
        this.securityAnswer = securityAnswer;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.DLNumber = DLNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.about = about;
        this.regTime = regTime;
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
        this.userName = user[1].replace("-", " ");
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
        this.avatar = ImageTools.getAvatarFromUserID(userID);
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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
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

    public void updateUserInfo(){
        String sql = "UPDATE userInfo SET userName = '" + userName + "', birthday = '" + birthday + "', phone = '" + phone + "', email = '"
                + email + "', address = '" + address + "', about = '" + about + "' WHERE userID = " + userID;
        SQL.execute(sql);
    }
}
