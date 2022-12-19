package com.example.car_rental_sys.funtions;

import com.example.car_rental_sys.ToolsLib.PlatformTools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperate {
    public static boolean rewriteFile(String path, String content) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println(file.createNewFile());
            }
            OutputStream fOut = new FileOutputStream(file);
            fOut.write(content.getBytes());
            fOut.close();
            return true;
        } catch (Exception e) {
            PlatformTools.logError(e);
            return false;
        }
    }
    public static ArrayList<String> readFile(String path ) {
       return  readFile(path, false);
    }

    public static ArrayList<String> readFile(String path,boolean removeHeader) {
        ArrayList<String> content = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content.add(data);
            }
            myReader.close();
            //remove the first line
            if(removeHeader) content.remove(0);
            return content;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return content;
        }
    }

    public static ArrayList<String[]> readFileToArray(String path) {
        return readFileToArray(path, false);
    }

    public static ArrayList<String[]> readFileToArray(String path,boolean removeHeader){
        ArrayList<String> contentStr;
        if(removeHeader){
            contentStr = readFile(path,true);
        }else {
            contentStr = readFile(path);
        }
        ArrayList<String[]> content = new ArrayList<>();
        for (String ele : contentStr) {
            content.add(ele.split(","));
        }
        return content;
    }

    public static boolean addStringToFile(String path, String content) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println(file.createNewFile());
            }
            OutputStream fOut = new FileOutputStream(file, true);
            String str = content + "\n";
            fOut.write(str.getBytes());
            fOut.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String readFileToString(String path) {
        StringBuilder content = new StringBuilder();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content.append(data).append("\n");
            }
            myReader.close();
            return content.toString();
        } catch (Exception e) {
            PlatformTools.logError(e);
            return content.toString();
        }
    }

    public static int getFileLineNum(String filePath) {
        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filePath))){
            lineNumberReader.skip(Long.MAX_VALUE);
            int lineNumber = lineNumberReader.getLineNumber();
            return lineNumber + 1;//实际上是读取换行符数量 , 所以需要+1
        } catch (IOException e) {
            return -1;
        }
    }
}