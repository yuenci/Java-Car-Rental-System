package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.FileOperate;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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

    public static String getProjectPath() {
        return System.getProperty("user.dir");
        //E:\Materials\Semester 3\CRS-version0.2
    }

    public static int getNewUserID() {
        ArrayList<String[]> result = com.example.car_rental_sys.sqlParser.FileOperate.readFileToArray("src/main/resources/com/example/car_rental_sys/data/userInfo.txt");
        //System.out.println(result.size() );
        return result.size();
    }

    public static boolean checkPassword(String password) {
        if (password.length() < 8) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must be at least 8 characters");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one uppercase letter");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one lowercase letter");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one number");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one special character");
            messageFrame.show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.ERROR, "Please enter a valid email address");
            messageFrame.show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean resetPassword(String email, String newPassword) {
        ArrayList<String[]> result = SQL.query("SELECT userID FROM userInfo WHERE email = '" + email + "'");
        if (result.size() == 0) return false; // email not found return 100

        try {
            String userID = result.get(0)[0];
            String newPasswordMD5 = Encryption.med5Encrypt(newPassword);
            SQL.execute("UPDATE password SET password = '" + newPasswordMD5 + "' WHERE userID = " + userID);
            //SendEmail.sendEmail(email, "Your new password", "Your new password is: " + newPassword);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static String getCarModelFromCarID(int carID) {
        String sql = "SELECT carModel FROM carInfo WHERE carID = " + carID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static String getCarNumberFromCarID(int carID) {
        String sql = "SELECT carNumber FROM carInfo WHERE carID = " + carID;
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return StringTools.capitalizeWord(result.get(0)[0]);
        } else {
            return null;
        }
    }

    public static String getGradientColorFromCarID(int carID) {
        String model = getCarModelFromCarID(carID);

        String sql = "SELECT darkColor,lightColor FROM carModels WHERE carModel = '" + model + "'";

        ArrayList<String[]> result = SQL.query(sql);

        if (result.size() == 1) {
            return result.get(0)[0] + "," + result.get(0)[1];
        } else {
            return "#7EB712,#CAE943";
        }
    }

    public static boolean encryptDataFiles(){
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

    public static boolean decryptDataFiles(){
        String[] dataFiles = ConfigFile.sensitiveDataFileList;
        StringBuilder errorMessages = new StringBuilder();

        boolean ifAllSuccess = true;

        // decrypt all .secret to .txt files and delete .secret files
        for (String dataFile : dataFiles) {
            boolean res = Encryption.dataFileDecrypt(dataFile);
            if (res){
                //deleteDataFile(dataFile + ".secret");
            }else{
                errorMessages.append(dataFile).append(" decrypt failed, ");
                ifAllSuccess = false;
            }
        }

        if(ifAllSuccess){
            StatusContainer.isDataDecrypted = true;
        }else{
            encryptErrorMessage = errorMessages.toString();
        }

        return ifAllSuccess;
    }

    public static boolean logLogin(){
        int loginID = DataTools.getID("loginLog");
        int userID = StatusContainer.currentUser.getUserID();
        String loginUIP =NetTools.getExternalHostIP();
        String platformType =PlatformTools.getPropertyOsName();
        String deviceType = StatusContainer.deviceType;
        String deviceName = NetTools.getLocalHostIP("hostName");
        String IpGeo = NetTools.getGeoIPInfo();
        String time = DateTools.getNow();
        String EXP =time;

        if (StatusContainer.ifRememberMe) EXP = DateTools.getDataTimeAfterAWeek();

        String sql = "INSERT INTO loginLog VALUES (" + loginID +"," +
                userID + ",'" + loginUIP + "','" + platformType + "','" + deviceType + "','" + deviceName + "','" +
                IpGeo + "','"+time + "','" +  EXP + "')";
        System.out.println(sql);
        return SQL.execute(sql);
    }

    public   static  int getCustomerOrderNum(int customerID){
        String sql = "select count(orderID) from orders where userID = " + customerID;
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return 0;
        } else {
            return  Double.valueOf( result.get(0)[0]).intValue();
        }
    }

    public  static  String[] getCustomerBankCardsList(int customerID){
        String sql = "select cardNumber from bankCardInfo where userID = " + customerID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return null;
        } else {
            String[] bankCardsList = new String[result.size()];
            for (int i = 0; i < result.size(); i++) {
                bankCardsList[i] = result.get(i)[0];
            }
            return bankCardsList;
        }
    }

    public static int getRandomInt(int min, int max) {
        //int num = min + (int)(Math.random() * (max-min+1));
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}

// TODO: No comma "," content is allowed.
// TODO: add table view for all data
// TODO: logLogin is ture then can login