package com.example.car_rental_sys.sqlParser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.File;
import java.util.*;

public class Parser {

    ////////////////////////////////////////
    // configs of the database
    //
    ////////////////////////////////////////
    public static String dataFilesPath = "src/main/resources/com/example/car_rental_sys/data/";

// region # validatation
    ////////////////////////
    // validation
    //
    ////////////////////////
    // 1. if table exist
    // 2. if column exist
    // 3. if data type is correct
    // 4. if where logic  is correct
    public static void validateSQL(String sql) {
        String tableName = getTableName(sql);
        if (!ifTableExist(tableName)) {
            System.out.println("\"" + tableName + "\"" + " does not exist");
            return;
        }

        if (!ifColsExist(sql)) {
            return;
        }

        if(!checkSQLOperateType(sql).equals("INSERT")) {
            getWhereClauseLogic(sql);
        }

        ifDataTypeCorrect(sql);
    }

    public static boolean ifTableExist(String tableName) {
        String tablePath = getFilePathFromTableName(tableName);
        File tableFile = new File(tablePath);
        return tableFile.exists();
    }

    public static boolean ifColsExist(String sql) {
        String tableName = getTableName(sql);
        String[] tableHeadName = getTableColsNameList(tableName);
        //System.out.println("tableHeadName: " + Arrays.toString(tableHeadName));
        
        String[] sqlColsName = null;
        String[] whereClauseConditions = null;

        String sqlType = checkSQLOperateType(sql);
        switch (sqlType) {
            case "INSERT":
                sqlColsName = getInsertColNames(sql);
                break;
            case "UPDATE":
                sqlColsName = getUpdateColNames(sql);
                whereClauseConditions = getWhereClauseCondition(sql);
                break;
            case "DELETE":
                whereClauseConditions = getWhereClauseCondition(sql);
                break;
            case "SELECT":
                sqlColsName = getSelectColNames(sql);
                whereClauseConditions = getWhereClauseCondition(sql);
                break;
        }

        if(sqlColsName == null && whereClauseConditions == null){
            throw new IllegalArgumentException("sqlColsName and whereClauseConditions are both null");
        }

        if(sqlColsName != null){
            for (String colName : sqlColsName) {
                if (!Arrays.asList(tableHeadName).contains(colName)) {
                    System.out.println("column " + "\"" + colName + "\"" + " does not exist");
                    return false;
                }
            }
        }

        if(whereClauseConditions != null){
            for (int i = 0; i < whereClauseConditions.length; i++) {
                if(i % 3 == 0){
                    if (!Arrays.asList(tableHeadName).contains(whereClauseConditions[i])) {
                        System.out.println("column " +"\"" + whereClauseConditions[i] + "\"" + " does not exist");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void ifDataTypeCorrect(String sql) {
        String tableName = getTableName(sql);
        //System.out.println("tableHeadName: " + Arrays.toString(tableHeadName));

        String[] sqlColsName;
        String[] whereClauseConditions = null;

        String sqlType = checkSQLOperateType(sql);
        switch (sqlType) {
            case "INSERT": {
                sqlColsName = getInsertColNames(sql);
                String[] sqlColsValue = getInsertColValues(sql);
                ifInsertClauseDataTypeCorrect(tableName, sqlColsName, sqlColsValue);
                return;
            }
            case "UPDATE": {
                sqlColsName = getUpdateColNames(sql);
                String[] sqlColsValue = getUpdateColValues(sql);
                //whereClauseConditions = getWhereClauseCondition(sql);
                ifUpdateClauseDataTypeCorrect(tableName, sqlColsName, sqlColsValue);
                return;
            }
            case "DELETE":
            case "SELECT":
                whereClauseConditions = getWhereClauseCondition(sql);
                break;
        }

        if(whereClauseConditions!=null){
            ifWhereConditionDataTypeCorrect(tableName, whereClauseConditions);
        }
    }

    public static void ifWhereConditionDataTypeCorrect(String tableName, String[] whereClauseConditions){
        // System.out.println( "whereClauseConditions: " + Arrays.toString(whereClauseConditions));
        Map<String, String> colNameDataTypeMap = getTableDefaultDataType(tableName);
        for (int i = 0; i < whereClauseConditions.length; i++) {
            if(i % 3 == 0){
                String colName = whereClauseConditions[i];
                String colValue = whereClauseConditions[i + 2];
                String colDataType = strOrNum(colValue);
                if(!colDataType.equals(colNameDataTypeMap.get(colName))){
                    System.out.println("column " +"\"" + colName + ":" +colValue+ "\"" + " data type is not correct");
                    return;
                }
            }
        }
    }

    public static void ifInsertClauseDataTypeCorrect(String tableName, String[] sqlColsName, String[] sqlColsValue){
        Map<String, String> colNameDataTypeMap = getTableDefaultDataType(tableName);
        int colsNum = sqlColsName.length;

        for (int i = 0; i < sqlColsValue.length; i++) {
            if (i % colsNum == 0){
                for (int j = 0; j < colsNum; j++) {
                    String colName = sqlColsName[j];
                    String colValue = sqlColsValue[i + j];
                    String colDataType = strOrNum(colValue);
                    if(!colDataType.equals(colNameDataTypeMap.get(colName))){
                        System.out.println("column " +"\"" + colName + ":" +colValue+ "\"" + " data type is not correct");
                        return;
                    }
                }
            }
        }
    }

    public static void ifUpdateClauseDataTypeCorrect(String tableName, String[] sqlColsNames, String[] sqlColsValues){
        Map<String, String> colNameDataTypeMap = getTableDefaultDataType(tableName);

        for (int i = 0; i < sqlColsNames.length; i++) {
            String colName = sqlColsNames[i];
            String colValue = sqlColsValues[i];
            String colDataType = strOrNum(colValue);
            if(!colDataType.equals(colNameDataTypeMap.get(colName))){
                System.out.println("column " +"\"" + colName + ":" +colValue+ "\"" + " data type is not correct");
                return;
        }
        }
    }

    public static String strOrNum(String data){
        if(data.matches("[0-9]+")){
            return "num";}

        if(data.charAt(0) == '\'' && data.endsWith("'")){
            return "str";
        }else {
            throw new IllegalArgumentException("\"" + data + "\" " + "data type is not correct");
        }
    }
// endregion

//region # basic data
    ////////////////////////////////////////
    // Get basic data from sql
    //
    ////////////////////////////////////////
    // get table name from sql
    public static String checkSQLOperateType(String sql) {
        sql = sql.toUpperCase();
        String[] sqlKeyWordsList = sql.split(" ");
        sqlKeyWordsList = StringArrayEmptyFilter(sqlKeyWordsList);

        List<String> sqlKeyWords = Arrays.asList(sqlKeyWordsList);

        if (sqlKeyWordsList[0].equals("INSERT")
                && sqlKeyWordsList[1].equals("INTO")
                && sqlKeyWords.contains("VALUES")) {
            return "INSERT";
        } else if (sqlKeyWordsList[0].equals("DELETE")
                && sqlKeyWordsList[1].equals("FROM")
                && sqlKeyWordsList[3].equals("WHERE")) {
            return "DELETE";
        } else if (sqlKeyWordsList[0].equals("SELECT")
                && sqlKeyWords.contains("FROM")) {
            return "SELECT";
        } else if (sqlKeyWordsList[0].equals("UPDATE")
                && sqlKeyWordsList[2].equals("SET")) {
            return "UPDATE";
        } else {
            throw new IllegalArgumentException(
                    "Sql statement has mistakes, just have 'select','update','delete','insert' four types");
        }
    }

    // get table name
    public static String getTableName(String sql) {
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);
        return tableList.get(0);
    }

    // get table relative path
    public static String getFilePathFromTableName(String tableName) {
        return Parser.dataFilesPath + tableName + ".txt";
    }

    // get default value of table
    public static String getDefaultValueFromIndex(String tableName, int index) {
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        String[] tableHead = data.get(0);
        // System.out.println("defaultValue: " + defaultValue);
        return tableHead[index].split("=")[1].trim();
    }

    // get table cols number
    public static int getTableColsNumber(String tableName) {
        String path = Parser.dataFilesPath + tableName + ".txt";
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        String[] tableHead = data.get(0);
        return tableHead.length;
    }


    public static String[] getTableColsNameList(String tableName) {
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        String[] tableHead = data.get(0);
        String[] tableHeadName = new String[tableHead.length];
        for (int i = 0; i < tableHead.length; i++) {
            tableHeadName[i] = tableHead[i].split(":")[0].trim();
        }
        return StringArrayEmptyFilter(tableHeadName);
    }

    // get index from column name
    public static int getColIndexFromColName(String tableName, String colName) {
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        String[] tableHead = data.get(0);
        for (int i = 0; i < tableHead.length; i++) {
            String ColName = tableHead[i].split(":")[0].trim();
            if (ColName.equalsIgnoreCase(colName)) {
                return i;
            }
        }
        throw new IllegalArgumentException(
                "\"" + colName + "\" dose not exist in table \"" + tableName + "\"");
    }

    // get index from column name list
    public static int[] getColsIndexesListFromColNames(String tableName, String[] colNameList) {
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        int[] res = new int[colNameList.length];
        String[] tableHead = data.get(0);
        for (int i = 0; i < colNameList.length; i++) {
            res[i] = -1;
            for (int j = 0; j < tableHead.length; j++) {
                String ColName = tableHead[j].split(":")[0].trim();
                if (ColName.equalsIgnoreCase(colNameList[i].trim())) {
                    res[i] = j;
                }
            }
        }
        for (int i = 0; i < res.length; i++) {
            if (res[i] == -1) {
                throw new IllegalArgumentException(
                        "\"" + colNameList[i] + "\" dose not exist in table \"" + tableName + "\"");
            }
        }
        return res;
    }

    public static Map<String,String> getTableDefaultDataType(String tableName){
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        String[] tableHead = data.get(0);
        Map<String,String> res = new HashMap<>();
        for (String s : tableHead) {
            String ColName = s.split(":")[0].trim();
            String ColType = s.split(":")[1].split("=")[0].trim();
            res.put(ColName, ColType);
        }
        return res;
    }

    public static String[] StringArrayEmptyFilter(String[] array) {
        return Arrays.stream(
                        array)
                .filter(s -> (s != null && s.length() > 0))
                .map(String::trim)
                .toArray(String[]::new);
    }

    public static int[] getColIndexesFromWhereCondation(String tableName, String[] whereCondation) {
        int[] colIndexList = new int[whereCondation.length/3];
        for (int i = 0; i <whereCondation.length ; i++) {
            if (i % 3 == 0) {
                colIndexList[i / 3] = getColIndexFromColName(tableName, whereCondation[i]);
            }
        }

        return colIndexList;
    }

    public static String[] getColValuesFromWhereCondation(String[] whereCondation) {

        String[] colIndexValue = new String[whereCondation.length/3];
        for (int i = 0; i <whereCondation.length /3; i++) {
            colIndexValue[i] = whereCondation[i*3+2];
        }

        return colIndexValue;
    }

    public static String[] getColOperatorFromWhereCondation(String[] whereCondation) {

        String[] colOperators = new String[whereCondation.length/3];
        for (int i = 0; i <whereCondation.length /3; i++) {
            colOperators[i] = whereCondation[i*3+1];
        }
        return colOperators;
    }

    public static int[] getQualifiedRowsIndex(String sql){
        if (checkSQLOperateType(sql).equals("INSERT")){
            throw new IllegalArgumentException("\"INSERT\" statement dose not need to check qualified rows");
        }

        String tableName = getTableName(sql);
        String[] whereCondition = getWhereClauseCondition(sql);
        //System.out.println( "whereCondition: " + Arrays.toString(whereCondition));
        String logicOperator = getWhereClauseLogic(sql);
        if(Objects.equals(logicOperator, "SINGLE") || Objects.equals(logicOperator, "AND")){
            return getQualifiedRowsIndexLogicAnd(tableName,whereCondition);
        }else  if (Objects.equals(logicOperator, "OR")){
            return getQualifiedRowsIndexLogicOr(tableName,whereCondition);
        }
        return null;
    }

    public static int[] getQualifiedRowsIndexLogicAnd(String tableName, String[] whereCondition){
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        ArrayList<Integer> qualifiedRowsIndex = new ArrayList<>();


        int[] colIndexList = getColIndexesFromWhereCondation(tableName,whereCondition);
        //System.out.println( "colIndexList: " + Arrays.toString(colIndexList));

        String[] colValueList = getColValuesFromWhereCondation(whereCondition);
        //System.out.println( "colValueList: " + Arrays.toString(colValueList));

        String[] colOperatorList = getColOperatorFromWhereCondation(whereCondition);
        //System.out.println( "colOperatorList: " + Arrays.toString(colOperatorList));

        for (int i = 1; i < data.size(); i++) {
            String[] line = data.get(i);
            boolean qualified = true;
            for (int j = 0; j < colIndexList.length; j++) {
                String currentColValue = line[colIndexList[j]];
                String currentWhereValue = colValueList[j];
                String currentOperator = colOperatorList[j];
                if (!valueCompare(currentColValue, currentWhereValue, currentOperator)) {
                    qualified = false;
                    break;
                }
            }
            if (qualified){
                qualifiedRowsIndex.add(i);
            }
        }


        return qualifiedRowsIndex.stream().mapToInt(i->i).toArray();
    }

    public static int[] getQualifiedRowsIndexLogicOr(String tableName, String[] whereCondition){
        String path = getFilePathFromTableName(tableName);
        ArrayList<String[]> data = FileOperate.readFileToArray(path);
        ArrayList<Integer> qualifiedRowsIndex = new ArrayList<>();


        int[] colIndexList = getColIndexesFromWhereCondation(tableName,whereCondition);
        String[] colValueList = getColValuesFromWhereCondation(whereCondition);
        String[] colOperatorList = getColOperatorFromWhereCondation(whereCondition);

        for (int i = 1; i < data.size(); i++) {
            String[] line = data.get(i);
            boolean qualified = false;
            for (int j = 0; j < colIndexList.length; j++) {
                String currentColValue = line[colIndexList[j]];
                String currentWhereValue = colValueList[j];
                String currentOperator = colOperatorList[j];
                if(valueCompare(currentColValue,currentWhereValue,currentOperator)){
                    qualified = true;
                }
            }
            if (qualified){
                qualifiedRowsIndex.add(i);
            }
        }


        return qualifiedRowsIndex.stream().mapToInt(i->i).toArray();
    }

    public static boolean valueCompare(String colValue, String whereValue, String operator){
        if(whereValue.charAt(0) == '\'' && whereValue.endsWith("'")){
            whereValue = whereValue.substring(1,whereValue.length()-1);
        }

        if (operator.equals("=")){
            return colValue.equals(whereValue);
        }else if (operator.equals("<>")){
            return !Objects.equals(colValue, whereValue);
        }

        double colValueDouble = Double.parseDouble(colValue);
        double whereValueDouble = Double.parseDouble(whereValue);

        switch (operator) {
            case ">":
                return colValueDouble > whereValueDouble;
            case "<":
                return colValueDouble < whereValueDouble;
            case ">=":
                return colValueDouble >= whereValueDouble;
            case "<=":
                return colValueDouble <= whereValueDouble;
            default:
                throw new IllegalArgumentException("Operator \"" + operator + "\" is not supported");
        }
    }

    //clean data
    public static String cleanData(String data){
        if(data.charAt(0) == '\'' && data.endsWith("'")){
           return   data.substring(1,data.length()-1);
        }
        return  data;
    }
// endregion

// region # parser data
    ////////////////////////
    // parse sql data
    //
    ////////////////////////


    // get where clause
    public static ArrayList<String> getWhereClause(String sql,String type) {
        String sqlType = checkSQLOperateType(sql);
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        String whereClause = "";
        switch (sqlType) {
            case "SELECT":
                Select select = (Select) statement;
                assert select != null;
                PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
                whereClause = plainSelect.getWhere().toString();
                break;
            case "UPDATE":
                Update update = (Update) statement;
                assert update != null;
                whereClause = update.getWhere().toString();
                break;
            case "DELETE":
                Delete delete = (Delete) statement;
                assert delete != null;
                whereClause = delete.getWhere().toString();
                break;
            case "INSERT":
                throw new IllegalArgumentException(
                        "Insert statement has no where clause");
        }

        Expression expr = null;
        try {
            expr = CCJSqlParserUtil.parseCondExpression(whereClause);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }

        ArrayList<String> conditionList = new ArrayList<>();
        ArrayList<String> conditionLogic = new ArrayList<>();

        if (expr == null){
            throw  new IllegalArgumentException("Where clause is null");
        }

        expr.accept(new ExpressionVisitorAdapter() {

            @Override
            protected void visitBinaryExpression(BinaryExpression expr) {

                if (expr instanceof ComparisonOperator) {
                    //System.out.println("left=" + expr.getLeftExpression() + "  op=" +  expr.getStringExpression() + "  right=" + expr.getRightExpression());
                    conditionList.add(expr.getLeftExpression().toString());
                    conditionList.add(expr.getStringExpression());
                    conditionList.add(expr.getRightExpression().toString());
                }

                if(expr instanceof AndExpression){
                    //System.out.println("AndExpression");
                    conditionLogic.add("AND");
                } else if(expr instanceof OrExpression){
                    //System.out.println("OrExpression");
                    conditionLogic.add("OR");
                }
                super.visitBinaryExpression(expr);
            }
        });


        if(type.equals("condition")){
            return conditionList;}
        else if(type.equals("logic")){
            return conditionLogic;
        }
        else{
            throw new IllegalArgumentException(
                    "type must be 'condition' or 'logic'");
        }
    }

    // get where clause condition
    public static String[] getWhereClauseCondition(String sql) {
        return getWhereClause(sql,"condition").toArray(new String[0]);
    }

    // check where clause type
    public static String getWhereClauseLogic(String sql) {
        ArrayList<String> conditionLogic = getWhereClause(sql,"logic");
        if(conditionLogic.size() == 0){
            return "SINGLE";
        }

        for (String current : conditionLogic) {
            String first = conditionLogic.get(0);
            if (!Objects.equals(first, current)) {
                throw new IllegalArgumentException(
                        "where clause has more than one logic");
            }
        }

        return conditionLogic.get(0);
    }

    // get select col names
    public static String[] getSelectColNames(String sql) {
        ArrayList<String> colNames = new ArrayList<>();
        Select select = null;
        try {
            select = (Select) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        assert select != null;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        for (SelectItem selectItem : selectItems) {
            colNames.add(selectItem.toString());
            //System.out.println(selectItem.toString());
        }

        if(colNames.get(0).equals("*")){
            String tableName = getTableName(sql);
            return getTableColsNameList(tableName);
        }

        return colNames.toArray(new String[0]);
    }

    public static String[] getInsertClause(String sql,String type) {
        ArrayList<String> colNames = new ArrayList<>();
        ArrayList<String> colValues = new ArrayList<>();
        Insert insert = null;
        try {
            insert = (Insert) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }

        if(insert == null){
            throw new IllegalArgumentException(
                    "Insert statement has no column names");
        }

        List<Column> columns = insert.getColumns();
        if(columns == null){
            String[] colNamesArray = getTableColsNameList(getTableName(sql));
            Collections.addAll(colNames, colNamesArray);
        } else{
            for (Column column : columns) {
                colNames.add(column.toString());
                //System.out.println(column.toString());
            }
        }


        ItemsList itemsList = insert.getItemsList();


        //System.out.println(itemsList.toString());
        if (itemsList instanceof ExpressionList) {
            ExpressionList expressionList = (ExpressionList) itemsList;
            //System.out.println( expressionList.getExpressions());
            List<Expression> expressions = expressionList.getExpressions();
            //System.out.println("expressions.toString(): " + expressions.toString());
            for (Expression expression : expressions) {
                colValues.add(expression.toString());
                //System.out.println(expression.toString());
            }
        }else if (itemsList instanceof MultiExpressionList) {
            for (ExpressionList expressionList :  ((MultiExpressionList) itemsList).getExpressionLists()) {
                for (Expression expression : expressionList.getExpressions()) {
                    colValues.add(expression.toString());
                    //System.out.println(expression.toString());
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "Unsupported SQL Expression Exception: " + sql);
        }

        if(type.equals("colNames")){
            return colNames.toArray(new String[0]);}
        else if(type.equals("colValues")){
            return colValues.toArray(new String[0]);
        }
        else{
            throw new IllegalArgumentException(
                    "type must be 'colNames' or 'colValues'");
        }
    }

    public static String[] getInsertColNames(String sql) {
        return getInsertClause(sql,"colNames");
    }

    public static String[] getInsertColValues(String sql) {
        return getInsertClause(sql,"colValues");
    }

    public static String[] getUpdateClause(String sql,String type) {
        ArrayList<String> colNames = new ArrayList<>();
        ArrayList<String> colValues = new ArrayList<>();
        Update update = null;
        try {
            update = (Update) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }

        if(update == null){
            throw new IllegalArgumentException(
                    "Update statement has no column names");
        }

        ArrayList<UpdateSet> set =  update.getUpdateSets();
        for (UpdateSet updateSet : set) {
            colNames.add(updateSet.getColumns().get(0).toString());
            colValues.add(updateSet.getExpressions().get(0).toString());
        }

        if(Objects.equals(type, "colNames")){
            return colNames.toArray(new String[0]);}
        else if(Objects.equals(type, "colValues")){
            return colValues.toArray(new String[0]);
        }
        else{
            throw new IllegalArgumentException(
                    "type must be 'colNames' or 'colValues'");
        }
    }

    public static String[] getUpdateColNames(String sql) {
        return getUpdateClause(sql,"colNames");
    }

    public static String[] getUpdateColValues(String sql) {
        return getUpdateClause(sql,"colValues");
    }
// endregion



}
