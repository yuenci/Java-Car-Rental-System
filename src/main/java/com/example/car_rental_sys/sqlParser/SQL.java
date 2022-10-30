package com.example.car_rental_sys.sqlParser;

import java.util.ArrayList;

public class SQL {
    public static ArrayList<String[]> query(String sql) {
        Parser.validateSQL(sql);
        if (Parser.checkSQLOperateType(sql).equals("SELECT")) {
            return new sqlSelect(sql).query();
        }
        return new ArrayList<>();
    }

    public static boolean excute(String sql) {
        Parser.validateSQL(sql);
        String operateType = Parser.checkSQLOperateType(sql);
        switch (operateType) {
            case "DELETE":
                return new sqlDelete(sql).execute();
            case "INSERT":
                return new sqlInsert(sql).execute();
            case "UPDATE":
                return new sqlUpdate(sql).execute();
        }
        return false;
    }
}

