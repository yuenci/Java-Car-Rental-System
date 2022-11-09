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
            System.out.println(Arrays.toString(res.get(0)));
            return res.get(0);
        }

    }
}
