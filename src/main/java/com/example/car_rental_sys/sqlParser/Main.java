package com.example.car_rental_sys.sqlParser;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //insertTest();
        //deleteTest();
        selectTest();
        //updateTest();
    }

    public  static  void insertTest(){
        String sqlInsert = "insert into testTable values ('Innis1', 99, 'USA')";
        SQL.excute(sqlInsert);
    }

    public  static  void deleteTest(){
        String sqlDelete = "delete from testTable where name = 'Innis1'";
        SQL.excute(sqlDelete);
    }

    public static  void selectTest(){
        String sqlSelect = "select name, age from testTable where name = 'Innis1'";
        ArrayList<String[]> result = SQL.query(sqlSelect);
        for (String[] line:result
        ) {
            System.out.println(Arrays.toString(line));
        }
    }

    public static  void updateTest(){
        String sqlUpdate = "update testTable set name = 'Innis_2022', age = 520, address = 'China' where name = 'Innis1'";
        SQL.excute(sqlUpdate);
    }







}
