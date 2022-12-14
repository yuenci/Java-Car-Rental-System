package com.example.car_rental_sys.funtions;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.DateTools;
import com.example.car_rental_sys.ToolsLib.NetTools;
import com.example.car_rental_sys.ToolsLib.PlatformTools;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.sqlParser.SQL;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void test(){
        //errorLogTest();
        //txtFilEncryptTest();
        //base64Test();
//        dataFilesDecryptTest();
//        deleteFileTest();
//        getAllFileNameFromPathTest();
        //startWindowSettingTest();
//        getLocalHostIPTest();
//        userObjTest();
//        loginLogTest();
        //platformTypeTest();
        //loginLogTest();
        //SelfTestTools.isBackendServerRunning();
//        screenShotTest();
        //openScreenShotWithMsPaintTest();
        //getMessageJson();
        //keepLoginTest();
        //gettodoListTest();
        //getStampTest();
        //getCardInfoTest();
        //feedbackFrameTest();
        dashboardTest();
    }



    private static void errorLogTest(){
        try {
            throw new Exception("Test");
            //int a  = 2/0;
        } catch (Exception e) {
            Tools.logError(e);
        }
    }
    
    private static void base64Test(){
        String str = "Hello World\n:=";
        String sercert =  Encryption.AESEncrypt(ConfigFile.AESKey,str);
        System.out.println(sercert);
        String decode = Encryption.AESDecrypt(ConfigFile.AESKey,sercert);
        System.out.println(decode);
        
        
    }
    private static void txtFilEncryptTest(){
        Encryption.dataFileEncrypt("password.txt");
        Encryption.dataFileEncrypt("hello.txt");
    }


    private static void dataFilesDecryptTest(){
        Encryption.dataFileDecrypt("password.txt");
//        Encryption.dataFilesDecrypt("hello.txt");
    }

    private static void deleteFileTest(){
        DataTools.deleteFile("hello.txt");
    }

    private static void getAllFileNameFromPathTest(){
        DataTools.getAllFileNameFromPath();
    }

    private static void startWindowSettingTest(){
        PlatformTools.startWindowNetworkSetting();
    }

    private static void getLocalHostIPTest(){
        NetTools.getLocalHostIP("hostName");
    }

    private static void userObjTest(){
        User user1 = new User("1575270674@qq.com");
        System.out.println(user1);
    }

    private static void loginLogTest(){
        DataTools.logLogin(true);
        System.out.println("done");
    }

    private static void platformTypeTest(){
        System.out.println(PlatformTools.getPropertyOsName());
    }

    public static void screenShotTest(){
        System.out.println(PlatformTools.getScreenShot());
    }

    //openScreenShotWithMsPaint

    public static void openScreenShotWithMsPaintTest(){
        PlatformTools.openScreenShotWithMsPaint();
    }

    public static void  getMessageJson(){

        //DataTools.generateMessageJSON(4);
        DataTools.generateMessageJSON(StatusContainer.currentUser.getUserID());
        System.out.println("getMessage Json done");
    }

    private static void keepLoginTest(){
        DataTools.keepUserLoggedIn();
    }

    private static void gettodoListTest(){
        DataTools.getAdminToDo();
    }

    private static void getStampTest(){
        ArrayList<String[]> list = FileOperate.readFileToArray("src/main/resources/com/example/car_rental_sys/data/carStatus.txt");
        for (int i = 1; i < list.size(); i++) {
            System.out.println(DateTools.timeStampToDateTime(list.get(i)[1]));
        }
    }

    private static void getCardInfoTest(){
        int cusID = StatusContainer.currentUser.getUserID();
        //cusID = 4;
        String sql = "select cardNumber,cardType,validDate from bankCardInfo where userID = " + cusID + " and cardType <> 'paypal'";

        System.out.println(sql);
        ArrayList<String[]> data= SQL.query(sql);
        for (String[] datum : data) {
            System.out.println(Arrays.toString(datum));
        }
        System.out.println(data.size());
    }

    private static void feedbackFrameTest(){
//        new FeedBackFrame("test");

    }

    private static void dashboardTest(){
        DataTools.generateDashboardData();
    }
}
