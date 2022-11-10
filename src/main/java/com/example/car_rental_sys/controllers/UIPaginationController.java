package com.example.car_rental_sys.controllers;

import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Arrays;

public class UIPaginationController {
    private static final String query1 = "SELECT * FROM fakeData WHERE id = 1;";

    public UIPaginationController(){
        countDisplayPage();
//        System.out.println(data.toString());
    }

    private void countDisplayPage(){

    }

    //pass condition for select data
    //using query select specific data from database
    public static ArrayList<String[]> selectData(){
        //        for (String[] strings : data) {
//            System.out.println(Arrays.toString(strings));
//
//        }
        return SQL.query(query1);
    }
}
