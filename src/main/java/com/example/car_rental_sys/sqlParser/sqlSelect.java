package com.example.car_rental_sys.sqlParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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

        String functionArgWithBrackets =colNames[0]; // avg(250)
        boolean isMaxMinCount = false;
        // if function select
        boolean isDistinct = false;


        if(colNames.length == 1){
            if(Parser.isDistinct(this.sql)){
                isDistinct = true;
                colNames[0] = colNames[0].substring(1,colNames[0].length()-1);
            }

            String elements = colNames[0];
            if(Parser.isFunctionSelect(elements)){
                if(Parser.isMaxMinCount(elements)){
                    colNames[0] = Parser.getArgFromMaxMinCount(elements);
                    isMaxMinCount = true;
                }
            }
        }


        int[] colIndexes = Parser.getColsIndexesListFromColNames(tableName, colNames);

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

        if(isMaxMinCount){
            ArrayList<String[]> temp = new ArrayList<>(){
                {add(new String[1]);}
            };
            double funcRes =  Parser.getFunctionSelectResult(functionArgWithBrackets,result);
            temp.get(0)[0] = String.valueOf(funcRes);
            return temp;
        }
        if(isDistinct){
            return Parser.getDistinctResult(result);
        }

        if(Parser.isOrderSelect(sql)){
            return Parser.getOrderSelectResult(sql,result);
        }


        return result;
    }

}