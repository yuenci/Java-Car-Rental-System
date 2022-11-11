package com.example.car_rental_sys.funtions;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.Tools;
import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.NetTools;

public class Test {
    public static void test(){
        //errorLogTest();
        //txtFilEncryptTest();
        //base64Test();
//        dataFilesDecryptTest();
//        deleteFileTest();
//        getAllFileNameFromPathTest();
        //startWindowSettingTest();
        getLocalHostIPTest();
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
        Tools.startWindowSetting();
    }

    private static void getLocalHostIPTest(){
        NetTools.getLocalHostIP("hostName");
    }
}
