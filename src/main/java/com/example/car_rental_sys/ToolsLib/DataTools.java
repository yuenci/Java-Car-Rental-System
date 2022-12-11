package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.FileOperate;
import com.example.car_rental_sys.funtions.SendEmail;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Order;
import com.example.car_rental_sys.orm.UserFactory;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.units.qual.A;
import javafx.stage.FileChooser;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataTools {
    public static String[] getRenterNameAndPostFromUserID(int userID) {
        String sql = "select userName,post from userInfo where userID = " + userID;
        ArrayList<String[]> res = SQL.query(sql);
        if (res.size() == 0) {
            return null;
        } else {
            return res.get(0);
        }
    }

    public static String[] getCarSeatsSpeedPowerFromCarModel(String carModel) {
        String sql = "SELECT seats,speed,power FROM carModels WHERE carModel = '" + carModel + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            System.out.println("No car found");
        } else {
            return result.get(0);
        }
        return null;
    }

    public static boolean ifFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean ifDataFileExist(String fileName) {
        String path = ConfigFile.dataFilesRootPath + fileName;
        return ifFileExist(path);
    }

    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.delete()) {
                throw new Exception(filePath + " Delete file failed");
            }
            return true;
        } catch (Exception e) {
            Tools.logError(e);
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteDataFile(String fileName) {
        String path = ConfigFile.dataFilesRootPath + fileName;
        return deleteFile(path);
    }

    public static String[] getAllFileNameFromPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (Objects.requireNonNull(files).length == 0) {
            return null;
        }
        String[] res = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            res[i] = files[i].getName();
        }
        return res;
    }

    public static String[] getAllFileNameFromPath() {
        String path = ConfigFile.dataFilesRootPath;
        return getAllFileNameFromPath(path);
    }

    public static int getID(String tableName) {
        int lastID = FileOperate.getFileLineNum(ConfigFile.dataFilesRootPath + tableName + ".txt");
        return lastID - 1;
    }

    public static String encryptErrorMessage = null;

    public static String getProjectPath() {
        return System.getProperty("user.dir");
        //E:\Materials\Semester 3\【OODJ】\assignment\version0.1\crs
    }

    public static int getNewUserID() {
        ArrayList<String[]> result = com.example.car_rental_sys.sqlParser.FileOperate.readFileToArray("src/main/resources/com/example/car_rental_sys/data/userInfo.txt");
        //System.out.println(result.size() );
        return result.size();
    }

    public static boolean checkPassword(String password) {
        if (password.length() < 8 ) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must be at least 8 characters");
            messageFrame.show();
            return false;
        }
        if (ConfigFile.passwordStrength == 1) return true;

        if (!password.matches(".*[A-Z].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one uppercase letter");
            messageFrame.show();
            return false;
        }
        if (ConfigFile.passwordStrength == 2) return true;

        if (!password.matches(".*[a-z].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one lowercase letter");
            messageFrame.show();
            return false;
        }
        if (ConfigFile.passwordStrength == 3) return true;

        if (!password.matches(".*[0-9].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one number");
            messageFrame.show();
            return false;
        }
        if (ConfigFile.passwordStrength == 4) return true;

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*")) {
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

    public static boolean checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^\\+[0-9]{1,2}-[0-9]{9,11}$")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.ERROR, "Please enter a valid phone number");
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

    public static boolean encryptDataFiles() {
        String[] dataFiles = ConfigFile.sensitiveDataFileList;
        StringBuilder errorMessages = new StringBuilder();

        boolean ifAllSuccess = true;

        // encrypt all .txt to .secret files and delete .txt files
        for (String dataFile : dataFiles) {
            boolean res = Encryption.dataFileEncrypt(dataFile);
            if (res) {
                deleteDataFile(dataFile);
            } else {
                errorMessages.append(dataFile).append(" encrypt failed, ");
                ifAllSuccess = false;
            }
        }
        encryptErrorMessage = errorMessages.toString();
        return ifAllSuccess;
    }

    public static boolean decryptDataFiles() {
        String[] dataFiles = ConfigFile.sensitiveDataFileList;
        StringBuilder errorMessages = new StringBuilder();

        boolean ifAllSuccess = true;

        // decrypt all .secret to .txt files and delete .secret files
        for (String dataFile : dataFiles) {
            boolean res = Encryption.dataFileDecrypt(dataFile);
            if (res) {
                deleteDataFile(dataFile + ".secret");
            } else {
                errorMessages.append(dataFile).append(" decrypt failed, ");
                ifAllSuccess = false;
            }
        }

        if (ifAllSuccess) {
            StatusContainer.isDataDecrypted = true;
        } else {
            encryptErrorMessage = errorMessages.toString();
        }

        return ifAllSuccess;
    }

    public static boolean logLogin(Boolean ifRememberMe) {
        int loginID = DataTools.getID("loginLog");
        int userID = StatusContainer.currentUser.getUserID();
        String loginUIP = NetTools.getExternalHostIP();
        String platformType = PlatformTools.getPropertyOsName();
        String deviceType = StatusContainer.deviceType;
        String deviceName = NetTools.getLocalHostIP("hostName");
        String IpGeo = NetTools.getGeoIPInfo();
        String time = DateTools.getNow();
        String EXP = time;

        if (ifRememberMe) EXP = DateTools.getDataTimeAfterDays(ConfigFile.remeberMeDays);

        String sql = "INSERT INTO loginLog VALUES (" + loginID + "," +
                userID + ",'" + loginUIP + "','" + platformType + "','" + deviceType + "','" + deviceName + "','" +
                IpGeo + "','" + time + "','" + EXP + "')";
        //System.out.println(sql);
        return SQL.execute(sql);
    }

    public static int getCustomerOrderNum(int customerID) {
        String sql = "select count(orderID) from orders where userID = " + customerID;
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return 0;
        } else {
            return Double.valueOf(result.get(0)[0]).intValue();
        }
    }

    public static int getTotalOrderNum(){
        String sql = "SELECT * FROM orders";   //here
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return 0;
        } else {
            return Double.valueOf(result.get(0)[0]).intValue();
        }
    }

    public static String getOrderIDStr(int orderID){
        return String.format("%08d", orderID);
    }

    public static String[] getCustomerBankCardsList(int customerID) {
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

    private static JSONObject converArrayToJSON(int userID, String[] strings) {
        JSONObject msgContent = new JSONObject();
        JSONObject msg = new JSONObject();
        try {
            msgContent.put("status", strings[1]);
            msgContent.put("type", strings[2]);
            msgContent.put("time", strings[5]);
            msgContent.put("message", strings[6]);

            int isFromMe = strings[3].equals(String.valueOf(userID)) ? 1 : 0;
            msgContent.put("isFromMe", isFromMe);

            msg.put("msgContent", msgContent);

            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int[] removeRepetition(int[] noList) {
        int[] newList = new int[noList.length];
        int count = 0;
        for (int k : noList) {
            boolean isRepetition = false;
            for (int i : newList) {
                if (k == i) {
                    isRepetition = true;
                    break;
                }
            }
            if (!isRepetition) {
                newList[count] = k;
                count++;
            }
        }
        int[] result = new int[count];
        System.arraycopy(newList, 0, result, 0, count);
        return result;
    }

    public static String getUserNameFromUserID(int userID) {
        String sql = "SELECT userName FROM userInfo WHERE userID = " + userID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static String getUserRoleFromUserEmail(String userEmail) {
        String sql = "SELECT userGroup FROM userInfo WHERE email = '" + userEmail + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static String getUserRoleFromUserID(int userID) {
        String email = getEmailFromUserID(userID);
        return getUserRoleFromUserEmail(email);
    }

    public static String getEmailFromUserID(int  userID) {
        String sql = "SELECT email FROM userInfo WHERE userID = " + userID ;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static boolean generateMessageJSON(int userID) {
        String sql = "SELECT * FROM messages WHERE senderID = " + userID + " OR receiverID = " + userID + " ORDER BY time ASC";
        ArrayList<String[]> result = SQL.query(sql);

        JSONObject msgRes = new JSONObject();

        for (String[] strings : result) {
            JSONObject msg = new JSONObject();
            try {
                msg.put("status", Integer.valueOf(strings[1]));
                msg.put("type", Integer.valueOf(strings[2]));

                int isFromMe = strings[3].equals(String.valueOf(userID)) ? 1 : 0;
                //msg.put("receiverID",Integer.valueOf(strings[1]));
                int  chatterID = isFromMe == 1 ? Integer.valueOf(strings[4]) : Integer.valueOf(strings[3]);
                String chatterName = DataTools.getUserNameFromUserID(chatterID);
                if(chatterName ==null){
                    chatterName = "System";
                }

                msg.put("chatter", chatterName);
                msg.put("chatterID", chatterID);
                msg.put("time", strings[5]);
                msg.put("message", strings[6]);
                msg.put("isFromMe", isFromMe);
                msgRes.put(strings[0], msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String json = "let messageData= '" + msgRes + "';let currentUserID = " + userID + ";";
        String path = "src/main/resources/com/example/car_rental_sys/html/contactUs/messageData.js";
        FileOperate.rewriteFile(path,json);

        File avatarFile =new File( "src/main/resources/com/example/car_rental_sys/image/avatar/" + userID + ".png");
        String avatarPath = "let currentUserAvatarPath='" +  avatarFile.toURI() + "';";
        String avatarSettingFilepath = "src/main/resources/com/example/car_rental_sys/html/contactUs/avatarFilepath.js";
        FileOperate.rewriteFile(avatarSettingFilepath,avatarPath);
        //System.out.println(avatarPath);


//        for (String[] strings : result) {
//            System.out.println(Arrays.toString(strings));
//        }

        return true;
    }

    public static String generateRandomInvoiceNo(int OrderNumber){
        String[] numbers = new String[10];
        Random random = new Random(OrderNumber);
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(10);
            numbers[i] =  String.valueOf(number);
        }
        return String.join("", numbers);
    }

    public static int  getCarUnitPriceFromCarID(int carID){
        String model = getCarModelFromCarID(carID);
        String sql = "SELECT price FROM carModels WHERE carModel = '" + model + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if(result.size() == 1){
            int price = Integer.parseInt(result.get(0)[0]);
            return price /24;
        }else {
            return 0;
        }
    }

    public static String getGenderFromUserID(int userID) {
        String sql = "Select gender FROM userInfo WHERE userID = " + userID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static boolean ifCurrentCustomerHasDLNumber(){
        if(StatusContainer.currentUser instanceof Customer){
            String sql = "SELECT DLNumber FROM userInfo WHERE userID = " + StatusContainer.currentUser.getUserID();
            ArrayList<String[]> result = SQL.query(sql);
            if(result.size() == 1){
                String DLNumber = result.get(0)[0];
                return !Objects.equals(DLNumber, "null") && !DLNumber.equals("");
            }
            return false;
        }
        return false;
    }

    public static boolean keepUserLoggedIn(){
        ArrayList<String[]> result = FileOperate.readFileToArray(ConfigFile.dataFilesRootPath + "loginLog.txt");
        for (int i =result.size()-1; i >=0 ; i--) {
            String startTime = result.get(i)[10];
            String endTime = result.get(i)[11];

            if (!Objects.equals(startTime, endTime)){
                String timeNow = DateTools.getNow();
                int diff = DateTools.getHourDiff(timeNow,endTime);
                if(diff >0){
                    int userID = Integer.parseInt(result.get(i)[1]);
                    StatusContainer.currentUser = UserFactory.getUser(userID);
                    //System.out.println("User " + userID + " is logged in");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean logOut(){
        ArrayList<String[]> result = FileOperate.readFileToArray(ConfigFile.dataFilesRootPath + "loginLog.txt");

        StringBuilder newResult = new StringBuilder();
        new Thread(() -> {
            for (String[] strings : result) {
                if (!Objects.equals(strings[10], strings[11])) {
                    strings[11] = strings[10];
                }
                newResult.append(String.join(",", strings)).append("\n");
            }

            FileOperate.rewriteFile(ConfigFile.dataFilesRootPath + "loginLog.txt", newResult.toString());
        }).start();

        return true;
    }

    public static boolean setOrderStatus(int orderID,int status ){
        System.out.println("Order " + orderID + " status is set to " + status);
        String relate = "null";
        return setOrderStatus( orderID, status, relate );
    }
    /*
    order - status
    - 0: not paid
    - 1: paid
    - -1: canceled
    - 2: delivering
    - 3: delivered
    - 4: driving
    - 5: finished
    */

    public static boolean setOrderStatus(int orderID,int status,String relate ){
        // log event
        String time = DateTools.getNow();
        int scheduleID = getID("schedule");
        String sql = "Insert into schedule   VALUES (" +scheduleID + "," + orderID + "," + status + ",'" + relate + "','" + time + "')";

        // update car status
        String sql2 = "SELECT carID FROM orders WHERE orderID = " + orderID ;
        String carID = SQL.query(sql2).get(0)[0];
        int carIDInt = Integer.parseInt(carID);
        updateCarStatus(carIDInt,status);

        // sent system message
        int messageID = getID("messages");
        String message =getStatusMessage(status,orderID);
        String sql3 = "SELECT userID FROM orders WHERE orderID = " + orderID ;
        String userID = SQL.query(sql3).get(0)[0];
        String sql4 = "INSERT INTO messages VALUES (" +messageID +",0,1,0," + userID +",'" + time + "','" + message + "')";
        SQL.execute(sql4);

        return SQL.execute(sql);
    }

    public static boolean updateCarStatus(int carID,int status){
        String sql = "UPDATE carInfo SET status = " + status + " WHERE carID = " + carID;
        return SQL.execute(sql);
    }

    public static String getStatusMessage(int orderID, int status){
        switch (status){
            case 0:
                return "Thank you for choosing our service, please complete the payment within three hours.";
            case 1:
                return "Your payment has been received and we will prepare your Car as soon as possible.";
            case -1:
                return "Order:"+ orderID +"was cancelled successfully. Please comment on our service.";
            case 2:
                return "You car has already left and will arrive in half an hour.";
            case 3:
                return "Your vehicle has arrived, please go to the appointed place to get it.";
            case 4:
                return "Your confirmation has been received. Enjoy driving";
            case 5:
                return "Order:"+ orderID +" has been finished, and we look forward to serving you next time.";
            default:
                return "Unknown";
        }

    }

    public static ArrayList<String[]> getAdminToDo(){
        String sql = "SELECT * FROM todos WHERE status = 0 and userID = " + StatusContainer.currentUser.getUserID() + " ORDER BY due DESC";
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);

        ArrayList<String[]> newResult = new ArrayList<>();

        if(result.size()>=2){
            newResult.add(result.get(0));
            newResult.add(result.get(1));
        }else if(result.size() ==1){
            newResult.add(result.get(0));
        }

        return newResult;
    }

    public static boolean setTaskAsDone(int todoID){
        String sql = "UPDATE todos SET status = 1 WHERE todoID = " + todoID;
        return SQL.execute(sql);
    }


    public static boolean addTask(String content,String due){
        int todoID = getID("todos");
        String sql = "INSERT INTO todos VALUES (" + todoID + ",'"+content+ "',"+ StatusContainer.currentUser.getUserID() +  ",'" + due + "'"+ ",0" +")";
        //System.out.println(sql);
    return SQL.execute(sql);
    }

    public static String fileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
                //new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                //new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
//            welcomeText.setText(selectedFile.getAbsolutePath());
            System.out.println(selectedFile.getAbsolutePath());
//            System.out.println(selectedFile.getAbsolutePath());
            //copyFileUsingApacheCommonsIO(selectedFile.getAbsolutePath(), "C:\\Users\\Public\\Pictures\\Sample Pictures\\test.jpg");
        }
        if( selectedFile == null) {
            return null;
        }
        else{
            return selectedFile.getAbsolutePath();
        }

    }

    public static void copyFileUsingApacheCommonsIO(String sourceAbsolutePath, String destAbsolutePath) throws IOException {
        File source = new File(sourceAbsolutePath);
        File dest = new File(destAbsolutePath);
        FileUtils.copyFile(source, dest);
    }

    public static String[] getOrderInfoFromOrderID(String orderID){
        String sql = "SELECT * FROM orders WHERE orderID = " + orderID;
        ArrayList<String[]> result = SQL.query(sql);

        if(result.size() == 0){
            System.out.println("No such order");
            throw new RuntimeException("No such order");
        }else{
            return result.get(0);
        }
    }

    public static double getCarStar(){
        double star = 3 + (5 - 3) * new Random().nextDouble();
        star = Math.round(star * 10) / 10.0;
        return star;
    }

    public static int getCarSpeed(){
        return getRandomInt(200,480);
    }

    public static double getCarPower(){
        //the power must higher than 100 and lower than 300
        double power = 100 + (300 - 100) * new Random().nextDouble();
        power = Math.round(power * 10) / 10.0;
        return power;
    }

    public static int getRandomCarStatus(){
        return new Random().nextInt(5);
    }

    public static ArrayList<String[]> getTableData(ArrayList<String[]> data,int page,int max){
        ArrayList<String[]> result = new ArrayList<>();
        try{
            for (int i = 0; i < max; i++) {
                String[] row = data.get((page - 1) * max + i);
                result.add(row);
            }
        }catch (Exception e){
            Tools.logError(e);
        }

        return result;
    }
}

// TODO: No comma "," content is allowed.
// TODO: add table view for all data
// TODO: logLogin is ture then can login