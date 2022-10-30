package com.example.car_rental_sys.sqlParser;

import java.util.ArrayList;

public class sqlDelete implements IExecute {
    private String sql;

    public sqlDelete(String sql) {
        this.sql = sql;
    }

    public boolean execute() {
        String tableName = Parser.getTableName(this.sql);
        String path = Parser.getFilePathFromTableName(tableName);

        ArrayList<String[]> data = FileOperate.readFileToArray(path);

        int[] indexes = Parser.getQualifiedRowsIndex(this.sql);

        if(indexes == null){
            return false;
        }

        for (int i = indexes.length - 1; i >= 0; i--) {
            data.remove(indexes[i]);
        }

        // write arraylist to file
        StringBuilder content = new StringBuilder();

        for (String[] row : data) {
            content.append(String.join(",", row)).append(System.lineSeparator());
        }

        FileOperate.rewriteFile(path, content.toString());

        return true;
    }
}
