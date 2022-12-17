package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.funtions.FileOperate;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.UserFactory;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.stage.FileChooser;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

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

    // Path: "src/main/resources/com/example/car_rental_sys/html/dashboard/data.js";
    // output: E:\Materials\Semester 3\【OODJ】\assignment\version0.1\crs\src\main\resources\com\example\car_rental_sys\html\dashboard\data.js

    public static  String getAbsolutePath(String relativePath) {
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }

    public static int getNewUserID() {
        ArrayList<String[]> result = FileOperate.readFileToArray("src/main/resources/com/example/car_rental_sys/data/userInfo.txt");
        //System.out.println(result.size() );
        return result.size();
    }

    public static int getNewID(String tableName) {
        ArrayList<String[]> result = FileOperate.readFileToArray("src/main/resources/com/example/car_rental_sys/data/" + tableName + ".txt");
        //System.out.println(result.size() );
        return result.size();
    }

    public static String getRandomGender(){
        String[] gender = {"Male", "Female"};
        return gender[getRandomInt(0,1)];
    }

    public static boolean checkPassword(String password) {
        if (password.length() < 8) {
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
            String sql = "UPDATE password SET password = '" + newPasswordMD5 + "' WHERE userID = " + userID;
            //System.out.println(sql);
            SQL.execute(sql);
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


    public static int getModelIDFromCarModel(String carModel) {
        String sql = "SELECT modelID FROM carModels WHERE carModel = '" + carModel + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return Integer.parseInt(result.get(0)[0]);
        } else {
            return -1;
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

    public static int gerCustomerLevel(int customerID){
        int orderCount = getCustomerOrderNum(customerID);
        if (orderCount <= 5) {
            return 0;
        } else if (orderCount <= 10) {
            return 1;
        } else if (orderCount <= 15) {
            return 2;
        } else if (orderCount <= 20) {
            return 3;
        } else {
            return 4;
        }
    }

    public static int getTotalOrderNum() {
        String sql = "SELECT * FROM orders";   //here
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return 0;
        } else {
            return Double.valueOf(result.get(0)[0]).intValue();
        }
    }

    public static int getTotalTransactionNum(int customerID){
        String sql = "select count(transactionID) from transactionRecord where userID = " + customerID;
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return 0;
        } else {
            return Double.valueOf(result.get(0)[0]).intValue();
        }
    }

    public static String getTotalSpending(int customerID){
        String sql = "select sum(amount) from transactionRecord where userID = " + customerID + " and type = 'rental'";
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return "0";
        } else {
            return result.get(0)[0];
        }
    }

    public static String getOrderIDStr(int orderID) {
        return String.format("%08d", orderID);
    }

    public static int getOrderStatusWithID(int orderID){
        String sql = "select status from orders where orderID = " + orderID;
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return Integer.parseInt(result.get(0)[0]);
        } else {
            return 0;
        }
    }

    public static void updateOrderStatusWithID(int orderID, int status){
        String sql = "update orders set status = " + status + " where orderID = " + orderID;
        //System.out.println(sql);
        System.out.println(SQL.execute(sql));
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

    public static ArrayList<String[]> getCustomerBankCardsData(int customerID) {
        String sql = "select * from bankCardInfo where userID = " + customerID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0) {
            return null;
        } else {
            return result;
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

    public static String getEmailFromUserID(int userID) {
        String sql = "SELECT email FROM userInfo WHERE userID = " + userID;
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
                int chatterID = isFromMe == 1 ? Integer.valueOf(strings[4]) : Integer.valueOf(strings[3]);
                String chatterName = DataTools.getUserNameFromUserID(chatterID);
                if (chatterName == null) {
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
        FileOperate.rewriteFile(path, json);

        File avatarFile = new File("src/main/resources/com/example/car_rental_sys/image/avatar/" + userID + ".png");
        String avatarPath = "let currentUserAvatarPath='" + "avatar/" + userID + ".png" + "';";
        String avatarSettingFilepath = "src/main/resources/com/example/car_rental_sys/html/contactUs/avatarFilepath.js";
        FileOperate.rewriteFile(avatarSettingFilepath, avatarPath);
        //System.out.println(avatarPath);


//        for (String[] strings : result) {
//            System.out.println(Arrays.toString(strings));
//        }
        System.out.println("messageData.js has been generated");
        return true;
    }

    public static String generateRandomInvoiceNo(int OrderNumber) {
        String[] numbers = new String[10];
        Random random = new Random(OrderNumber);
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(10);
            numbers[i] = String.valueOf(number);
        }
        return String.join("", numbers);
    }

    public static int getCarUnitPriceFromCarID(int carID) {
        String model = getCarModelFromCarID(carID);
        String sql = "SELECT price FROM carModels WHERE carModel = '" + model + "'";
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            int price = Integer.parseInt(result.get(0)[0]);
            return price / 24;
        } else {
            return 0;
        }
    }
    public static int getCarUnitPriceFromCarModel(String carModel) {
        int carID = getModelIDFromCarModel(carModel);
        return getCarUnitPriceFromCarID(carID);
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

    public static boolean ifCurrentCustomerHasDLNumber() {
        if (StatusContainer.currentUser instanceof Customer) {
            String sql = "SELECT DLNumber FROM userInfo WHERE userID = " + StatusContainer.currentUser.getUserID();
            ArrayList<String[]> result = SQL.query(sql);
            if (result.size() == 1) {
                String DLNumber = result.get(0)[0];
                return !Objects.equals(DLNumber, "null") && !DLNumber.equals("");
            }
            return false;
        }
        return false;
    }

    public static boolean keepUserLoggedIn() {
        ArrayList<String[]> result = FileOperate.readFileToArray(ConfigFile.dataFilesRootPath + "loginLog.txt");
        for (int i = result.size() - 1; i >= 0; i--) {
            String startTime = result.get(i)[10];
            String endTime = result.get(i)[11];

            if (!Objects.equals(startTime, endTime)) {
                String timeNow = DateTools.getNow();
                int diff = DateTools.getHourDiff(timeNow, endTime);
                if (diff > 0) {
                    int userID = Integer.parseInt(result.get(i)[1]);
                    StatusContainer.currentUser = UserFactory.getUser(userID);
                    //System.out.println("User " + userID + " is logged in");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean logOut() {
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

    public static boolean setOrderStatus(int orderID, int status) {
        System.out.println("Order " + orderID + " status is set to " + status);
        String relate = "null";
        return setOrderStatus(orderID, status, relate);
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

    public static boolean setOrderStatus(int orderID, int status, String relate) {
        // log event
        String time = DateTools.getNow();
        int scheduleID = getID("schedule");
        String sql = "Insert into schedule   VALUES (" + scheduleID + "," + orderID + "," + status + ",'" + relate + "','" + time + "')";

        // update car status
        String sql2 = "SELECT carID FROM orders WHERE orderID = " + orderID;
        String carID = SQL.query(sql2).get(0)[0];
        int carIDInt = Integer.parseInt(carID);
        updateCarStatus(carIDInt, status);

        // sent system message
        int messageID = getID("messages");
        String message = getStatusMessage(status, orderID);
        String sql3 = "SELECT userID FROM orders WHERE orderID = " + orderID;
        String userID = SQL.query(sql3).get(0)[0];
        String sql4 = "INSERT INTO messages VALUES (" + messageID + ",0,1,0," + userID + ",'" + time + "','" + message + "')";
        SQL.execute(sql4);

        return SQL.execute(sql);
    }

    public static boolean updateCarStatus(int carID, int status) {
        String sql = "UPDATE carInfo SET status = " + status + " WHERE carID = " + carID;
        return SQL.execute(sql);
    }

    public static String getStatusMessage(int orderID, int status) {
        switch (status) {
            case 0:
                return "Thank you for choosing our service, please complete the payment within three hours.";
            case 1:
                return "Your payment has been received and we will prepare your Car as soon as possible.";
            case -1:
                return "Order:" + orderID + "was cancelled successfully. Please comment on our service.";
            case 2:
                return "You car has already left and will arrive in half an hour.";
            case 3:
                return "Your vehicle has arrived, please go to the appointed place to get it.";
            case 4:
                return "Your confirmation has been received. Enjoy driving";
            case 5:
                return "Order:" + orderID + " has been finished, and we look forward to serving you next time.";
            default:
                return "Unknown";
        }

    }

    public static ArrayList<String[]> getAdminToDo() {
        String sql = "SELECT * FROM todos WHERE status = 0 and userID = " + StatusContainer.currentUser.getUserID() + " ORDER BY due DESC";
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);

        ArrayList<String[]> newResult = new ArrayList<>();

        if (result.size() >= 2) {
            newResult.add(result.get(0));
            newResult.add(result.get(1));
        } else if (result.size() == 1) {
            newResult.add(result.get(0));
        }

        return newResult;
    }

    public static boolean setTaskAsDone(int todoID) {
        String sql = "UPDATE todos SET status = 1 WHERE todoID = " + todoID;
        return SQL.execute(sql);
    }


    public static boolean addTask(String content, String due) {
        int todoID = getID("todos");
        String sql = "INSERT INTO todos VALUES (" + todoID + ",'" + content + "'," + StatusContainer.currentUser.getUserID() + ",'" + due + "'" + ",0" + ")";
        //System.out.println(sql);
        return SQL.execute(sql);
    }

    public static String fileChooser() {
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
        if (selectedFile == null) {
            return null;
        } else {
            return selectedFile.getAbsolutePath();
        }

    }

    public static void copyFileUsingApacheCommonsIO(String sourceAbsolutePath, String destAbsolutePath) throws IOException {
        File source = new File(sourceAbsolutePath);
        File dest = new File(destAbsolutePath);
        FileUtils.copyFile(source, dest);
    }

    public static String[] getOrderInfoFromOrderID(String orderID) {
        String sql = "SELECT * FROM orders WHERE orderID = " + orderID;
        ArrayList<String[]> result = SQL.query(sql);

        if (result.size() == 0) {
            System.out.println("No such order");
            throw new RuntimeException("No such order");
        } else {
            return result.get(0);
        }
    }

    public static double getCarStar() {
        double star = 3 + (5 - 3) * new Random().nextDouble();
        star = Math.round(star * 10) / 10.0;
        return star;
    }

    public static int getCarSpeed() {
        return getRandomInt(200, 480);
    }

    public static double getCarPower() {
        //the power must higher than 100 and lower than 300
        double power = 100 + (300 - 100) * new Random().nextDouble();
        power = Math.round(power * 10) / 10.0;
        return power;
    }

    public static int getRandomCarStatus() {
        return new Random().nextInt(5);
    }

    public static ArrayList<Integer> getAvailableCars() {
        String dataPath = ConfigFile.carStatusPath;
        ArrayList<String[]> carStatusData =
                FileOperate.readFileToArray(dataPath, true);

        return getAvailableData(carStatusData);
    }

    public static boolean ifCarsAvailable(String carModel) {
        int carModelID = getModelIDFromCarModel(carModel);
        ArrayList<Integer> availableCars = carsAvailable(carModelID);
        return availableCars.size() != 0;
    }

    public static ArrayList<Integer> carsAvailable(String carModel) {
        int carModelID = getModelIDFromCarModel(carModel);
        return carsAvailable(carModelID);
    }

    public static ArrayList<Integer> carsAvailable(int carModelID) {
        String dataPath = "src/main/resources/com/example/car_rental_sys/data/carStatus.txt";
        ArrayList<String[]> carStatusAllData =
                FileOperate.readFileToArray(dataPath, true);

        ArrayList<String[]> carStatusData = new ArrayList<>();

        for (String[] strings : carStatusAllData) {
            if (Integer.parseInt(strings[1]) == carModelID) {
                carStatusData.add(strings);
            }
        }
        return getAvailableData(carStatusData);
    }

    private static ArrayList<Integer> getAvailableData(ArrayList<String[]> data) {
        ArrayList<Integer> availableCars = new ArrayList<>();
        for (String[] strings : data) {
            // cars already in use
            long start = strings[2].length() == 0 ? 0 : Long.parseLong(strings[2]) * 1000;
            long end = strings[3].length() == 0 ? 0 : Long.parseLong(strings[3]) * 1000;
            long pickupTime = StatusContainer.pickUpTimeStamp;
            long returnTime = StatusContainer.returnTimeStamp;
//            System.out.println(DateTools.timeStampToDateTime(start) + " " + DateTools.timeStampToDateTime(end) );
//            System.out.println(DateTools.timeStampToDateTime(pickupTime) + " " + DateTools.timeStampToDateTime(returnTime));

            if (returnTime < start || pickupTime > end) {
                // available
                availableCars.add(Integer.parseInt(strings[0]));
            }
        }
        return availableCars;
    }


    public static ArrayList<String[]> getTableData(ArrayList<String[]> data, int page, int max) {
        ArrayList<String[]> result = new ArrayList<>();
        try {
            for (int i = 0; i < max; i++) {
                String[] row = data.get((page - 1) * max + i);
                result.add(row);
            }
        } catch (Exception e) {
            Tools.logError(e);
        }

        return result;
    }

    public static String getTax(){
        int tax = (int) (ConfigFile.tax * 100);
        return tax + "%";
    }

    public static double getDiscount(){
        int customerLevel = gerCustomerLevel(StatusContainer.currentUser.getUserID());
        double[] discount = {
                ConfigFile.discountLeveL0,
                ConfigFile.discountLeveL1,
                ConfigFile.discountLeveL2,
                ConfigFile.discountLeveL3,
                ConfigFile.discountLeveL4,
        };

        return discount[customerLevel];
    }

    public static int  getDiscountNum(int unitPrice, int hours){
        int price = unitPrice * hours;
        double discount = getDiscount();

        return (int) (price * discount);
    }

    public static int getBalance(){
        String sql = "select balance from bankCardInfo where userID = " + StatusContainer.currentUser.getUserID();
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 0)return 0;

        int balance = 0;
        for (String[] strings : result) {
            balance += Integer.parseInt(strings[0]);
        }
        return balance;
    }

    public static String getPayPalAccountFromUserID(int userID){
        String sql = "select cardNumber from bankCardInfo where userID = " + userID;
        ArrayList<String[]> data = SQL.query(sql);
        if (data.size() == 0) return null;
        return data.get(0)[0];
    }

    public static String getPhoneFromUserID(int userID){
        String sql = "select phone from userInfo where userID = " + userID;
        ArrayList<String[]> data = SQL.query(sql);
        if (data.size() == 0) return null;
        return data.get(0)[0];
    }
    public static int getCarIDFromOrderID(int carID){
        String sql = "select carID from orders where orderID = " + carID;
        ArrayList<String[]> data = SQL.query(sql);
        if (data.size() == 0) return -1;
        return Integer.parseInt(data.get(0)[0]);
    }

    public static boolean  storeCommentToDB(int commentID, String type, String payload, int posterID, String commentStr, int relevantCommentID,
                                            String commentDateTime, int like, int star, int ratingStr, int orderIDStr){
        String sql = "insert into comments values (" +
                commentID + ", " +
                "'" + type + "', " +
                "'" + payload + "', " +
                posterID + ", " +
                "'" + commentStr + "', " +
                relevantCommentID + ", " +
                "'" + commentDateTime + "', " +
                like + ", " +
                star + ", " +
                ratingStr + ", " +
                orderIDStr +
                ")";
        //System.out.println(sql);

        return SQL.execute(sql);
    }

    public static boolean storeComment(int rating , String comment, int orderID){
        String commentID = getNewID("comments") + "";
        String type = "modelComment";
        String payload = getCarIDFromOrderID(orderID) + ""; // carID
        String posterID = StatusContainer.currentUser.getUserID() + "";
        String relevantCommentID = "0";
        String commentDateTime = DateTools.getNow();
        String like = "0";
        String star = "0";
        String ratingStr = rating + "";
        String orderIDStr = orderID + "";

        return  storeCommentToDB(Integer.parseInt(commentID), type, payload, Integer.parseInt(posterID), comment, Integer.parseInt(relevantCommentID),
                commentDateTime, Integer.parseInt(like), Integer.parseInt(star), Integer.parseInt(ratingStr), Integer.parseInt(orderIDStr));
    }

    public static  boolean sendSystemMessage(int userID, String message){
        int messageID = getNewID("messages");
        int status = 0;
        int type = 0;
        int senderID = 0;
        String time = DateTools.getNow();
        String sql = "insert into messages values (" +
                messageID + ", " +
                status + ", " +
                type + ", " +
                senderID + ", " +
                userID + ", " +
                "'" + time + "', " +
                "'" + message + "'" +
                ")";

        return SQL.execute(sql);
    }
    private static String headerData(){
        //let headerData = [12495, 22495, 32495]
        ArrayList<String[]> data =FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);
        double dailyIncome = 0;
        double weeklyIncome = 0;
        double monthlyIncome = 0;

        for (String[] strings : data) {
            if(strings.length == 0) continue;
            long timeStamp = DateTools.dateTimeToTimestamp(strings[2]);
            long now = System.currentTimeMillis();
            long day = 24 * 60 * 60 * 1000;
            long week = 7 * day;
            long month = 30 * day;
            if (now - timeStamp < day){
                dailyIncome += Double.parseDouble(strings[8]);
            }
            if (now - timeStamp < week){
                weeklyIncome += Double.parseDouble(strings[8]);
            }
            if (now - timeStamp < month){
                monthlyIncome += Double.parseDouble(strings[8]);
            }
        }

        return "let headerData = [" + (int)dailyIncome + "," + (int)weeklyIncome + "," + (int)monthlyIncome + "];";

    }

    private static String[] getDaysOFWeek(){
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minus(7, ChronoUnit.DAYS);
        String[] productData = new String[7];

        LocalDate currentDate = endDate;
        int productDataIndex = 6;
        while (!currentDate.isBefore(startDate)) {
            if( productDataIndex < 0) break;


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
            String formattedDate = currentDate.format(formatter);
            //System.out.println(formattedDate);

            String data = "{ product: '" +   formattedDate + "', ";
            productData[productDataIndex] = data;

            currentDate = currentDate.minus(1, ChronoUnit.DAYS);
            productDataIndex--;
        }
        return productData;
    }
    
    private static ArrayList<int[]> getOrderDataIn7Days(){
        // get last 7 days every day's income
        long day1 = 24 * 60 * 60 * 1000;
        long day2 = 2 * day1;
        long day3 = 3 * day1;
        long day4 = 4 * day1;
        long day5 = 5 * day1;
        long day6 = 6 * day1;
        long day7 = 7 * day1;

        int[] dataDay1 = new int[3];
        int[] dataDay2 = new int[3];
        int[] dataDay3 = new int[3];
        int[] dataDay4 = new int[3];
        int[] dataDay5 = new int[3];
        int[] dataDay6 = new int[3];
        int[] dataDay7 = new int[3];

        ArrayList<String[]> data =FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);


        for (String[] strings : data) {
            if(strings.length == 0) continue;
            long timeStamp = DateTools.dateTimeToTimestamp(strings[2]);

            long now = System.currentTimeMillis();
            if (now - timeStamp < day1){
                if(Objects.equals(strings[11], "1")){
                    dataDay1[0]++;
                }
                else if(Objects.equals(strings[11], "3")) {
                    dataDay1[1]++;
                }
                else if(Objects.equals(strings[11], "5")) {
                    dataDay1[2]++;
                }
            }
            if (now - timeStamp < day2){
                if(Objects.equals(strings[11], "1")) dataDay2[0]++;
                else if(Objects.equals(strings[11], "3")) dataDay2[1]++;
                else if(Objects.equals(strings[11], "5")) dataDay2[2]++;
            }
            if (now - timeStamp < day3){
                if(Objects.equals(strings[11], "1")) dataDay3[0]++;
                else if(Objects.equals(strings[11], "3")) dataDay3[1]++;
                else if(Objects.equals(strings[11], "5")) dataDay3[2]++;
            }
            if (now - timeStamp < day4){
                if(Objects.equals(strings[11], "1")) dataDay4[0]++;
                else if(Objects.equals(strings[11], "3")) dataDay4[1]++;
                else if(Objects.equals(strings[11], "5")) dataDay4[2]++;
            }
            if (now - timeStamp < day5){
                if(Objects.equals(strings[11], "1")) dataDay5[0]++;
                else if(Objects.equals(strings[11], "3")) dataDay5[1]++;
                else if(Objects.equals(strings[11], "5")) dataDay5[2]++;
            }
            if (now - timeStamp < day6){
                if(Objects.equals(strings[11], "1")) dataDay6[0]++;
                else if(Objects.equals(strings[11], "3")) dataDay6[1]++;
                else if(Objects.equals(strings[11], "5")) dataDay6[2]++;
            }
            if (now - timeStamp < day7){
                if(Objects.equals(strings[11], "1")) dataDay7[0]++;
                else if(Objects.equals(strings[11], "3")) dataDay7[1]++;
                else if(Objects.equals(strings[11], "5")) dataDay7[2]++;
            }
        }
        ArrayList<int[]> orderData = new ArrayList<>();
        orderData.add(dataDay1);
        orderData.add(dataDay2);
        orderData.add(dataDay3);
        orderData.add(dataDay4);
        orderData.add(dataDay5);
        orderData.add(dataDay6);
        orderData.add(dataDay7);
        return orderData;
    }

    private static String lineChartData(){

        //System.out.println(Arrays.toString(productData));
        String[] productData = getDaysOFWeek();
        ArrayList<int[]> orderData = getOrderDataIn7Days();

        String dataDay1Str =  productData[0] + "Paid: " + orderData.get(0)[0]+ ", " + "Delivered: " + orderData.get(0)[1] + ", " + "Finished: " + orderData.get(0)[2] + "},";
        String dataDay2Str =  productData[1] + "Paid: " + orderData.get(1)[0]+ ", " + "Delivered: " + orderData.get(1)[1] + ", " + "Finished: " + orderData.get(1)[2] + "},";
        String dataDay3Str =  productData[2] + "Paid: " + orderData.get(2)[0]+ ", " + "Delivered: " + orderData.get(2)[1] + ", " + "Finished: " + orderData.get(2)[2] + "},";
        String dataDay4Str =  productData[3] + "Paid: " + orderData.get(3)[0]+ ", " + "Delivered: " + orderData.get(3)[1] + ", " + "Finished: " + orderData.get(3)[2] + "},";
        String dataDay5Str =  productData[4] + "Paid: " + orderData.get(4)[0]+ ", " + "Delivered: " + orderData.get(4)[1] + ", " + "Finished: " + orderData.get(4)[2] + "},";
        String dataDay6Str =  productData[5] + "Paid: " + orderData.get(5)[0]+ ", " + "Delivered: " + orderData.get(5)[1] + ", " + "Finished: " + orderData.get(5)[2] + "},";
        String dataDay7Str =  productData[6] + "Paid: " + orderData.get(6)[0]+ ", " + "Delivered: " + orderData.get(6)[1] + ", " + "Finished: " + orderData.get(6)[2] + "}";

        //System.out.println(dataStr);

        return "let lineData = [" + dataDay1Str + dataDay2Str + dataDay3Str + dataDay4Str + dataDay5Str + dataDay6Str + dataDay7Str + "];";
    }

    private  static int[] get7DaysOrderStatus(){
        int[] data = new int[5];
        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);
        for (String[] strings : orderData) {
            if(strings.length == 0) continue;
            long timeStamp = DateTools.dateTimeToTimestamp(strings[2]);
            long now = System.currentTimeMillis();
            long day7 = 7 * 24 * 60 * 60 * 1000;
            if (now - timeStamp < day7){
                if(Objects.equals(strings[11], "-1")) data[0]++;
                else if(Objects.equals(strings[11], "1")) data[1]++;
                else if(Objects.equals(strings[11], "2")) data[2]++;
                else if(Objects.equals(strings[11], "3")) data[3]++;
                else if(Objects.equals(strings[11], "5")) data[4]++;
            }
        }
        return data;
    }

    private static String pieChartData(){
        int [] data = get7DaysOrderStatus();
        String res = "let pieData = [";
        res += "{value: " + data[0] + ", name: 'Canceled'},";
        res += "{value: " + data[1] + ", name: 'Paid'},";
        res += "{value: " + data[2] + ", name: 'Driving'},";
        res += "{value: " + data[3] + ", name: 'Delivered'},";
        res += "{value: " + data[4] + ", name: 'Finished'}";
        res += "];";

        //System.out.println(dataStr);
        return res;
    }
    private static int getUserIDFromOrderID(int OrderID){
        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);
        for (String[] strings : orderData) {
            if(strings.length == 0) continue;
            if(Integer.parseInt(strings[0]) == OrderID){
                return Integer.parseInt(strings[1]);
            }
        }
        return -1;
    }

    private static String activityCardData(){
        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.schedulePath,true);
        StringBuilder dataStr = new StringBuilder("let activityCardData = [");
        for (int i = orderData.size() -6; i < orderData.size(); i++) {
            String[] shcedule = orderData.get(i);

            String userID = getUserIDFromOrderID(Integer.parseInt(shcedule[1])) + "";
            String userName ="\"" + getUserNameFromUserID(Integer.parseInt(userID)) +"\"";
            String time = "\"" + shcedule[4].substring(11,16) +"\"";
            String status = shcedule[2];
            if(Objects.equals(status, "-1")) status = "\"Canceled\"";
            else if(Objects.equals(status, "1")) status = "\"Paid\"";
            else if(Objects.equals(status, "2")) status = "\"Delivering\"";
            else if(Objects.equals(status, "3")) status = "\"Delivered\"";
            else if(Objects.equals(status, "5")) status = "\"Finished\"";

            dataStr.append("[").append(userID).append(",").append(userName).append(",").append(time).append(",").append(status).append("],");
        }
        dataStr.append("];");
        //System.out.println(dataStr);

        return dataStr.toString();
    }

    private static String tableData(){
        ArrayList<String[]> avtivityCardData = FileOperate.readFileToArray(ConfigFile.transactionRecordPath,true);
        StringBuilder dataStr = new StringBuilder("let tableData = [");
        for (int i = avtivityCardData.size() -5; i < avtivityCardData.size(); i++) {
            String[] transactionRecord = avtivityCardData.get(i);
            String transactionRecordID = transactionRecord[0];
            String userID = getUserIDFromOrderID(Integer.parseInt(transactionRecord[1])) + "";
            String userName = "\"" + getUserNameFromUserID(Integer.parseInt(userID)) +"\"";
            String invoice = generateRandomInvoiceNo(Integer.parseInt(transactionRecordID));
            String time = "\"" + transactionRecord[6].substring(0,10)+ "\"";
            String amount = transactionRecord[5];

            dataStr.append("[").append(userName).append(",").append(invoice).append(",").append(time).append(",").append(amount).append("],");
        }
        dataStr.append("];");
        //System.out.println(dataStr);
        return dataStr.toString();
    }

    public static boolean generateDashboardData(){
        String avatarFolder = "let avatarFolderPath = \"" +
                getAbsolutePath("src/main/resources/com/example/car_rental_sys/image/avatar")+ "\";";
        String headerData = headerData();
        String lineChartData= lineChartData();
        String pieChartData = pieChartData();
        String tableData= tableData();
        String activityCardData = activityCardData();

        String res = avatarFolder + "\n" + headerData + "\n" + lineChartData + "\n" +
                pieChartData + "\n" + tableData + "\n" + activityCardData ;

        //src/main/resources/com/example/car_rental_sys/html/dashboard/data.js
        // get system path
        String path = "src/main/resources/com/example/car_rental_sys/html/dashboard/data.js";
        FileOperate.rewriteFile(path,res);
//        System.out.println("Dashboard data generated");

        return  true;
    }

    private static String getMonthOrderData(){
        long day30 = 30L * 24 * 60 * 60 * 1000;
        long now = System.currentTimeMillis();

        long income = 0;
        long orderCount = 0;
        long maxIncome = 0;

        long[] res = new long[4];

        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);

        for (String[] strings : orderData) {
            if(strings.length == 0) continue;
            long timeStamp = DateTools.dateTimeToTimestamp(strings[2]);
            if (now - timeStamp < day30){
                income += Integer.parseInt(strings[8]);
                orderCount++;
                if(Integer.parseInt(strings[8]) > maxIncome) maxIncome = Integer.parseInt(strings[8]);
            }
        }
//        System.out.println(income);
//        System.out.println(orderCount);
//        System.out.println(maxIncome);

        res[0] = income;
        res[1] = orderCount;
        res[2] = getMonthTransactionData();
        res[3] = maxIncome;

        return "let monthOrderData = [" + res[0] + "," + res[1] + "," + res[2] + "," + res[3] + "];";
    }
    private static int getMonthTransactionData(){
        long day30 = 30L * 24 * 60 * 60 * 1000;
        long now = System.currentTimeMillis();

        int transactionCount = 0;


        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.transactionRecordPath,true);

        for (String[] strings : orderData) {
            if(strings.length == 0) continue;
            long timeStamp = DateTools.dateTimeToTimestamp(strings[6]);
            if (now - timeStamp < day30){
                transactionCount++;
            }
        }
        return transactionCount;
    }

    private static  String getMonthlyIncome(){
        // get this year
        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);

        long[] income = {0,0,0,0,0,0,0,0,0,0,0,0};

        for (String[] strings : orderData) {
            if(strings.length == 0) continue;
            String[] date = strings[2].split(" ");
            String[] yearMonth = date[0].split("-");
            if(yearMonth[0].equals(year)){
                income[Integer.parseInt(yearMonth[1]) - 1] += Integer.parseInt(strings[8]);
            }
        }

        StringBuilder res = new StringBuilder();
        for (long l : income) {
            res.append(l).append(",");
        }
        return "let barData = [" +  res + "]" ;
    }

    private static  String getHourlyIncome(){
        // get this year
        String year = Calendar.getInstance().get(Calendar.YEAR) + "";
        ArrayList<String[]> orderData = FileOperate.readFileToArray(ConfigFile.orderInfoPath,true);

        long[] hours = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (String[] strings : orderData) {
            if(strings.length == 0) continue;
            String[] date = strings[2].split(" ");
            String[] yearMonth = date[0].split("-");
            String[] time = date[1].split(":");
            if(yearMonth[0].equals(year)){
                hours[Integer.parseInt(time[0])] ++;
            }
        }

        StringBuilder res = new StringBuilder();
        for (long l : hours) {
            res.append(l).append(",");
        }
        return "let lineData = [" +  res + "]" ;
    }

    public static boolean generateAnalysisData(){
        String MonthOrderData =  getMonthOrderData();

        String HourlyIncome =  getHourlyIncome();

        String MonthlyIncome =  getMonthlyIncome();

        String res = MonthOrderData + "\n" + HourlyIncome + "\n" + MonthlyIncome;


        String path = "src/main/resources/com/example/car_rental_sys/html/incomeAnalysis/data.js";
        FileOperate.rewriteFile(path,res);

        System.out.println("Analysis data generated");


        return  true;
    }

    public static boolean generateWalletRatioData(String carData){
//        String carData = carData();
//
        String path = "src/main/resources/com/example/car_rental_sys/html/wallet/radio.js";
        FileOperate.rewriteFile(path,carData);
        return true;
    }

    // get user transaction data in last 6 months
    public static boolean generateWalletLineData(){
        //src/main/resources/com/example/car_rental_sys/html/wallet/bar.js
        String res = "let barData = [";

        ArrayList<String[]> transactionData = FileOperate.readFileToArray(ConfigFile.transactionRecordPath,true);
        int[] monthIncome = new int[6];

        for (String[] strings : transactionData) {
            if(strings.length == 0) continue;
            if(Integer.parseInt(strings[1]) == StatusContainer.currentUser.getUserID()){
                String[] date = strings[6].split(" ");
                String[] yearMonth = date[0].split("-");
                int month = Integer.parseInt(yearMonth[1]);
                int year = Integer.parseInt(yearMonth[0]);
                int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
                int nowYear = Calendar.getInstance().get(Calendar.YEAR);
                if(year == nowYear && month >= nowMonth - 5){
                    monthIncome[nowMonth - month] += Integer.parseInt(strings[5]);
                }
            }
        }

        //System.out.println( Arrays.toString(monthIncome));
        res += monthIncome[0] + "," + monthIncome[1] + "," + monthIncome[2] + "," + monthIncome[3] + "," + monthIncome[4] + "," + monthIncome[5] + "];";

        String path = "src/main/resources/com/example/car_rental_sys/html/wallet/bar.js";
        FileOperate.rewriteFile(path,res);
        return true;
    }
}

// TODO: No comma "," content is allowed.
// TODO: add table view for all data
// TODO: logLogin is ture then can login