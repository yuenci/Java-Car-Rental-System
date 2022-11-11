package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.controllers.LoadingPageController;
import com.teamdev.jxbrowser.engine.Engine;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class SelfTestTools {
    public static ArrayList<Test> testList = new ArrayList<>();

    static class Test {
        public String name;
        public boolean isPass;
        public String description;

        public Test(String name, boolean isPass, String description) {
            this.name = name;
            this.isPass = isPass;
            this.description = description;
            registerTest();
        }

        private void registerTest() {
            testList.add(this);
        }
    }

    private  static long startTimeWholeTest = 0;

    public static void executeSelfTest() {
        startTimeWholeTest  = System.currentTimeMillis();


        isNetworkConnected();
        isDataFileComplete();
        isBackendServerRunning();
        isSecretFilesDecrypted();
        isAllFilesDecrypted();
//        isSecretFileExist();
        isOriginalSecretFileExist();
        isJxBrowserLicensed();


        showTestMsg();
    }
    private static long startTime ;
    private static void setStarTime(){
        startTime=System.currentTimeMillis();
    }

    private static String getTimeCost(){
        long endTime=System.currentTimeMillis();
       return " runtime： "+(endTime-startTime)+" ms";
    }

    private static void isNetworkConnected() {
        setStarTime();

        boolean res =  NetTools.netIsAvailable(ConfigFile.connectTestUrl);

//        boolean res = NetTools.netIsAvailable(ConfigFile.connectTestUrl);
        if (res) {
            new Test("Network Connection", true, "Network connection is available.");
        } else {
            new Test("Network Connection", false, NetTools.netErrorMsg);
        }

        System.out.println("Network Connection: " + res + "||" + DateTools.getNow() + "||" + getTimeCost()) ;
        showSelfTestMessage("Connecting to the network...");
    }

    private static void isDataFileComplete() {
        setStarTime();
        String[] dataFileList = ConfigFile.dataFileList;

        boolean isPass = true;
        StringBuilder description = new StringBuilder();
        for (String fileName : dataFileList) {
            boolean res = DataTools.ifDataFileExist(fileName);
            if (!res) {
                isPass = false;
                description.append("File ").append(fileName).append(" is missing. ");
            }
        }

        if (isPass) {
            new Test("Data integrity", true, "Data files are complete.");
        } else {
            new Test("Data integrity", false, description.toString());
        }

        System.out.println("Data integrity: " + isPass + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        showSelfTestMessage("Checking data files integrity...");
    }

    // is backend server running
    private static void isBackendServerRunning() {
        setStarTime();
        boolean res = NetTools.netIsAvailable(ConfigFile.backendPost);
        if (res) {
            new Test("Backend Server", true, "Backend server is running.");
        } else {
            new Test("Backend Server", false, StatusContainer.backEndErrorMessage);
        }

        System.out.println("Backend Server: " + res + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        showSelfTestMessage("Checking backend server running status...");
    }

    private static void isSecretFilesDecrypted(){
        setStarTime();
        boolean res = StatusContainer.isDataDecrypted;
        if (res) {
            new Test("Secret File Decrypted", true, "Secret files are decrypted.");
        } else {
            new Test("Secret File Decrypted", false, "Secret files are not decrypted.");
        }

        System.out.println("Secret File Decrypted: " + res + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        showSelfTestMessage("Checking secret file...");
    }


    // if secret original files（without encrypt） exist(already decrypt should exist)
    private static void isOriginalSecretFileExist() {
        setStarTime();
        String[] secretFileList = DataTools.getAllFileNameFromPath();

        boolean isPass = true;
        StringBuilder description = new StringBuilder();
        for (String fileName : secretFileList) {
            boolean res = DataTools.ifDataFileExist(fileName);
            if (!res) {
                isPass = false;
                description.append("File ").append(fileName).append(" should exist but not  exist. ");
            }
        }

        if (isPass) {
            new Test("Secret File", true, "Secret files are not exist.");
        } else {
            new Test("Secret File", false, description.toString());
        }

        System.out.println("Secret original File exist: " + isPass + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        showSelfTestMessage("Checking secret files...");
    }

    // if secret files exist(should not exist, delete after decrypt)
    private static void isSecretFileExist() {
        setStarTime();
        String[] secretFileList = DataTools.getAllFileNameFromPath();

        boolean isPass = true;
        StringBuilder description = new StringBuilder();
        for (String fileName : secretFileList) {
            boolean res = DataTools.ifDataFileExist(fileName + ".secret");
            if (res) {
                isPass = false;
                description.append("File ").append(fileName).append(" should not exist but exist. ");
            }
        }

        if (isPass) {
            new Test("Secret File", true, "Secret files are exist.");
        } else {
            new Test("Secret File", false, description.toString());
        }

        showSelfTestMessage("Checking secret files...");
    }


    // is all files decrypted successfully, Through the successful operation of the decryption process
    private static void isAllFilesDecrypted() {
        setStarTime();
        String decryptedErrorMsg = DataTools.encryptErrorMessage;
        if (StatusContainer.isDataDecrypted) {
            new Test("Encrypted data decryption status", true, "Data files are complete.");
        } else {
            new Test("Encrypted data decryption status", false, decryptedErrorMsg);
        }

        System.out.println("Encrypted data decryption status: " + StatusContainer.isDataDecrypted + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        showSelfTestMessage("Checking data files decryption status...");
    }

    private static void isJxBrowserLicensed() {
        setStarTime();
        try {
            new Thread(() -> {
                Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
                new Test("JxBrowser", true, "JxBrowser is licensed.");
            }).start();
        } catch (Exception e) {
            new Test("JxBrowser", false, "JxBrowser is not licensed.");
            System.out.println("JxBrowser is not licensed.");
        }

        System.out.println( "JxBrowser: " + true + "||" + DateTools.getNow() + "||" + getTimeCost()) ;
        showSelfTestMessage("Checking necessary license...");
    }

    private static void showTestMsg(){
        long costTimeWholeTest=System.currentTimeMillis() - startTimeWholeTest;
        System.out.println("Test Number: " + testList.size() +  "|| whole Test cost: " +  costTimeWholeTest + " ms"  );
    }

    private static void showSelfTestMessage(String text){
        LoadingPageController.instance.setLoadingTest(text);

    }

}
// DONE backend test
// TODO delete file if should not exist (decrypt first ,then there are decrypted files, should not have .secret files)