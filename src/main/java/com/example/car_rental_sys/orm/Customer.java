package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.sqlParser.FileOperate;

public class Customer extends User{
    /*


userID:num=0,
userName:str=null,
userGroup:str=customer,
post:str=null,
securityProblem:str=null,
securityAnswer:str=null,
birthday:str=null,
gender:str=null,
Country:str=null,
phone:str=0123456789,
email:str=rent@mail.com,
address:str=null,
about:str=null,
regTime:str=null

 */
    private int userID = 0;
    private String userFirstName = "null";
    private String userLastName = "null";
    private String password = "null";
    private String userGroup = "customer";
    private String post = "null";
    private String securityProblem = "null";
    private String securityAnswer = "null";
    private String birthday = "null";
    private String gender = "null";
    private String country = "null";
    private String idNumber = "null";
    private String phone = "null";
    private String email = "null";
    private String address = "null";
    private String about = "null";
    private String regTime = "null";

    public Customer() {
        super();
    }

    public Customer(String email) {
        super(email);
        this.email = email;
    }

    public Customer(String userFirstName, String userLastName, String password, String userGroup, String post,
                    String securityProblem, String securityAnswer, String birthday, String gender, String country,
                    String idNumber, String phone, String email, String address, String about, String regTime) {
        super(email);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userGroup = userGroup;
        this.post = post;
        this.securityProblem = securityProblem;
        this.securityAnswer = securityAnswer;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.idNumber = idNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.about = about;
        this.regTime = regTime;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public boolean storeCustomerInfo(){
        String userID = String.valueOf(this.userID);
        String[] userinfo= new String[]{
            userID,  userFirstName+"-"+userLastName,   userGroup,  post,   securityProblem,   securityAnswer,
                birthday,gender,country,idNumber,phone,email,address,about,regTime
        };
        String userAndPassword = userID +"," + password ;

        String userinfoStr = String.join(",",userinfo) ;
        try {
            FileOperate.addStringToFile(ConfigFile.dataFilesRootPath + "userinfo.txt",userinfoStr);
            FileOperate.addStringToFile(ConfigFile.dataFilesRootPath + "password.txt",userAndPassword);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

//  TODO: add bankCard list property
