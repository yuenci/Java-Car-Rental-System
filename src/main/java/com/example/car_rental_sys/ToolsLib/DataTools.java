package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Arrays;

public class DataTools {
    public  static String[] getRenterNameAndPostFromUserID(int userID){
        String sql = "select userName,post from userInfo where userID = " + userID;
        ArrayList<String[]> res = SQL.query(sql);
        if (res.size() == 0){
            return null;
        }
        else{
            return res.get(0);
        }
    }

    public static String[] getCarSeatsSpeedPowerFromCarModel(String carModel){
        String sql = "SELECT seats,speed,power FROM carModels WHERE carModel = '" + carModel + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0){
            System.out.println("No car found");
        }else {
            return result.get(0);
        }
        return null;
    }
}
