package com.example.car_rental_sys.sqlParser;

import java.util.Arrays;

public class sqlInsert implements IExecute {
    private String sql;

    public sqlInsert(String sql) {
        this.sql = sql;
    }

    public boolean execute() {
        // System.out.println(this.sql);
        String tableName = Parser.getTableName(this.sql);
        String path = Parser.getFilePathFromTableName(tableName);

        String[] colNames = Parser.getInsertColNames(sql);

        //System.out.println( Arrays.toString(colNames));

        String[] colValues = Parser.getInsertColValues(sql);
        //System.out.println( Arrays.toString(colValues));

        int len = colNames.length;
        int times = colValues.length / colNames.length;
        String[] values = new String[len];
        for (int i = 0; i < times; i++) {
            System.arraycopy(colValues, i * len, values, 0, len);
            String insertRow = generateInsertRow(colNames, values);
            //System.out.println(insertRow);
            FileOperate.addStringToFile(path, insertRow);
        }

        return true;
    }

    public String generateInsertRow(String[] colNames , String[] colValues){
        String tableName = Parser.getTableName(this.sql);

        int[] colIndexes = Parser.getColsIndexesListFromColNames(tableName,colNames);

        int colNum = Parser.getTableColsNumber(tableName);
        //System.out.println("colNum: "+colNum);


        String[] values = new String[colNum];

        for (int i = 0; i < colIndexes.length; i++) {
            values[colIndexes[i]] = Parser.cleanData(colValues[i]);
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                values[i] = Parser.getDefaultValueFromIndex(tableName,i);
            }
        }

        // join values
        return String.join(",", values);
    }
}
