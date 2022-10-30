package com.example.car_rental_sys.sqlParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static ArrayList<String> readFile(String path) {
        ArrayList<String> content = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content.add(data);
            }

            myReader.close();
            return content;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return content;
        }
    }

    public static ArrayList<String[]> readFileToArray(String path) {
        ArrayList<String> contentStr = readFile(path);
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
}
