package com.example.car_rental_sys.orm;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.funtions.FileOperate;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public class Customer extends User {
    private int orderNum;
    private String[] bankCardList;
    private ArrayList<String[]> bankCardData;
    private Image vipBadge;
    private Image vipCard;

    public Customer() {
        super();
    }

    public Customer(String email) {
        super(email);
        initCustomerData();
    }

    private void initCustomerData() {
        this.orderNum = DataTools.getCustomerOrderNum(userID);
        //System.out.println("orderNum: " + orderNum);
        this.bankCardList = DataTools.getCustomerBankCardsList(userID);
        this.bankCardData = DataTools.getCustomerBankCardsData(userID);
        this.vipBadge = ImageTools.getBadgeImage(userID);
        this.vipCard = ImageTools.getVIPCardImage(userID);
    }

    public Customer(String userFirstName, String userLastName, String password, String userGroup, String post,
                    String securityProblem, String securityAnswer, String birthday, String gender, String country,
                    String DLNumber, String phone, String email, String address, String about, String regTime) {
        super(userFirstName, userLastName, password, userGroup, post, securityProblem, securityAnswer, birthday, gender, country, DLNumber, phone, email, address, about, regTime);
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Image getVipBadge() {
        return vipBadge;
    }

    public void setVipBadge(Image vipBadge) {
        this.vipBadge = vipBadge;
    }

    public Image getVipCard() {
        return vipCard;
    }

    public void setVipCard(Image vipCard) {
        this.vipCard = vipCard;
    }

    public boolean storeCustomerInfo() throws IOException {
        String userID = String.valueOf(DataTools.getNewUserID());
        String[] userinfo = new String[]{
                userID, userFirstName + "-" + userLastName, userGroup, post, securityProblem, securityAnswer,
                birthday, gender, country, DLNumber, phone, email, address, about, regTime
        };
        String userAndPassword = userID + "," + password;

        String userinfoStr = String.join(",", userinfo);
        try {
            FileOperate.addStringToFile(ConfigFile.dataFilesRootPath + "userinfo.txt", userinfoStr);
            FileOperate.addStringToFile(ConfigFile.dataFilesRootPath + "password.txt", userAndPassword);

            // add default avatar
            ImageTools.addDefaultAvatar(Integer.parseInt(userID));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public ArrayList<String[]> getBankCardData() {
        return bankCardData;
    }

    public void setBankCardData(ArrayList<String[]> bankCardData) {
        this.bankCardData = bankCardData;
    }
}

//  TODO: add bankCard list property
