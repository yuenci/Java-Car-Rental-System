package com.example.car_rental_sys.sqlParser;

import java.util.ArrayList;

public class sqlUpdate implements IExecute {
    private String sql;

    public sqlUpdate(String sql) {
        this.sql = sql;
    }

    public boolean execute() {
        String tableName = Parser.getTableName(this.sql);
        String path = Parser.getFilePathFromTableName(tableName);

        ArrayList<String[]> data = FileOperate.readFileToArray(path);

        int[] indexes = Parser.getQualifiedRowsIndex(this.sql);

        String[] colNames = Parser.getUpdateColNames(this.sql);
        int[] colIndexes = Parser.getColsIndexesListFromColNames(tableName, colNames);
        //System.out.println( Arrays.toString(colNames));

        String[] colValue = Parser.getUpdateColValues(this.sql);
        //System.out.println( Arrays.toString(colValue));

        if(indexes == null){
            return false;
        }

        for (int index : indexes) {
            String[] row = data.get(index);
            for (int j = 0; j < colIndexes.length; j++) {
                row[colIndexes[j]] = Parser.cleanData(colValue[j]);
            }
            data.set(index, row);
        }

        StringBuilder content = new StringBuilder();

        for (String[] row : data) {
            content.append(String.join(",", row)).append(System.lineSeparator());
        }

        FileOperate.rewriteFile(path, content.toString());

        return true;
    }
}
