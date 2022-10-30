package com.example.car_rental_sys.sqlParser;

import java.util.ArrayList;
import java.util.Arrays;

public class sqlSelect implements IQuery {
    private String sql;

    public sqlSelect(String sql) {
        this.sql = sql;
    }

    public ArrayList<String[]> query() {
        String tableName = Parser.getTableName(this.sql);
        String path = Parser.getFilePathFromTableName(tableName);

        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        ArrayList<String[]> result = new ArrayList<>();

        int[] indexes = Parser.getQualifiedRowsIndex(this.sql);

        String[] colNames = Parser.getSelectColNames(this.sql);
        int[] colIndexes = Parser.getColsIndexesListFromColNames(tableName, colNames);
//        System.out.println( Arrays.toString(colNames));
//        System.out.println( Arrays.toString(colIndexes));

        if(indexes == null){
            return result;
        }


        for (int index : indexes) {
            String[] line =  new String[colIndexes.length];
            String[] row = data.get(index);
            for (int j = 0; j < colIndexes.length; j++) {
                line[j] = row[colIndexes[j]];
            }
            result.add(line);
        }


        return result;
    }

}