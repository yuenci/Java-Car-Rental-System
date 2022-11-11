package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.FileOperate;
import com.example.car_rental_sys.sqlParser.SQL;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class DataTools {
    public  static String[] getRenterNameAndPostFromUserID(int userID){
        String sql = "select userName,post from userInfo where userID = " + userID;
        ArrayList<String[]> res = SQL.query(sql);
        if (res.size() == 0){
            return null;
        }
        else{
            return res.get(0);
        }
    }

    public static String[] getCarSeatsSpeedPowerFromCarModel(String carModel){
        String sql = "SELECT seats,speed,power FROM carModels WHERE carModel = '" + carModel + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0){
            System.out.println("No car found");
        }else {
            return result.get(0);
        }
        return null;
    }

    public static boolean ifFileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean ifDataFileExist(String filePath){
        String path = ConfigFile.dataFilesRootPath + filePath;
        return ifFileExist(path);
    }

    public static boolean deleteFile(String filePath){
        try{
            File file = new File(filePath);
            if(!file.delete()){
                throw new Exception(filePath + " Delete file failed");
            }
            return true;
        }catch(Exception e){
            Tools.logError(e);
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDataFile(String filePath){
        String path = ConfigFile.dataFilesRootPath + filePath;
        return deleteFile(path);
    }
    public static String[] getAllFileNameFromPath(String path){
        File file = new File(path);
        if(!file.exists()){
            return null;
        }

        File[] files = file.listFiles();
        if(Objects.requireNonNull(files).length == 0){
            return null;
        }
        String[] res = new String[files.length];
        for (int i = 0; i < files.length; i++){
            res[i] = files[i].getName();
        }
        return res;
    }

    public static String[] getAllFileNameFromPath(){
        String path  = ConfigFile.dataFilesRootPath;
        return  getAllFileNameFromPath(path);
    }

    public static int getID(String tableName){
        int lastID =  FileOperate.getFileLineNum(ConfigFile.dataFilesRootPath + tableName + ".txt");
        return lastID - 1;
    }

    public static String encryptErrorMessage = null;
    public boolean encryptDataFiles(){
        String[] dataFiles = ConfigFile.sensitiveDataFileList;
        StringBuilder errorMessages = new StringBuilder();

        boolean ifAllSuccess = true;

        // encrypt all .txt to .secret files and delete .txt files
        for (String dataFile : dataFiles) {
            boolean res = Encryption.dataFileEncrypt(dataFile);
            if (res){
                deleteDataFile(dataFile);
            }else{
                errorMessages.append(dataFile).append(" encrypt failed, ");
                ifAllSuccess = false;
            }
        }
        encryptErrorMessage = errorMessages.toString();
        return ifAllSuccess;
    }

    public boolean decryptDataFiles(){
        String[] dataFiles = ConfigFile.sensitiveDataFileList;
        StringBuilder errorMessages = new StringBuilder();

        boolean ifAllSuccess = true;

        // decrypt all .secret to .txt files and delete .secret files
        for (String dataFile : dataFiles) {
            boolean res = Encryption.dataFileDecrypt(dataFile);
            if (res){
                deleteDataFile(dataFile + ".secret");
            }else{
                errorMessages.append(dataFile).append(" decrypt failed, ");
                ifAllSuccess = false;
            }
        }
        encryptErrorMessage = errorMessages.toString();
        return ifAllSuccess;
    }

    public static boolean logLogin(){
        return true;
    }
}
