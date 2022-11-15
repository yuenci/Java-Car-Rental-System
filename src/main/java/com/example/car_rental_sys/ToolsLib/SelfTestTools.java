package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.controllers.LoadingPageController;
import com.teamdev.jxbrowser.engine.Engine;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.car_rental_sys.StatusContainer.isNetworkConnected;
import static com.example.car_rental_sys.StatusContainer.isDataFileComplete;
import static com.example.car_rental_sys.StatusContainer.idBackEndServerStart;
import static com.example.car_rental_sys.StatusContainer.isJxBrowserRegistered;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class SelfTestTools {
    public static ArrayList<Test> testList = new ArrayList<>();

    public static class Test {
        public String name;
        public boolean isPass;
        public String message;
        public String description;
        public Pane pane;
        public VBox vBox;

        public Test(String name, boolean isPass,String message, String description) {
            this.name = name;
            this.isPass = isPass;
            this.description = description;
            this.message = message;
            registerTest();
            initPane();
        }

        private void registerTest() {
            testList.add(this);
        }

        private void initPane(){
            vBox= new VBox();
            vBox.setPrefWidth(455);
            vBox.setMaxHeight(200);
//            pane.setPrefHeight(70);
            vBox.setStyle("-fx-background-color:orange; -fx-border-color: #dddddd; " +
                    "-fx-border-width: 1px; -fx-border-radius: 5px; " +
                    "-fx-background-radius: 5px; -fx-padding: 10px;");
            Label name = new Label(this.name);
            name.setWrapText(true);
            name.setLayoutX(10);
            name.setLayoutY(10);
            name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            VBox.setMargin(name, new javafx.geometry.Insets(0, 0, 10, 0));

            Label description = new Label(this.description);
            description.setWrapText(true);
            description.setLayoutX(10);
            description.setLayoutY(40);
            description.setMaxWidth(455);
            description.setStyle("-fx-font-size: 15px;");

            vBox.getChildren().addAll(name, description);
        }
    }

    private  static long startTimeWholeTest = 0;

    public static void executeSelfTest() {
        startTimeWholeTest  = System.currentTimeMillis();
        isNetworkConnected();
        isDataFileComplete();
        isBackendServerRunning();
//        isSecretFilesDecrypted();
//        isAllFilesDecrypted();
//        isSecretFileExist();
        //isOriginalSecretFileExist();
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

        String message = "Connecting to the network...";
        if (res) {
            new Test("Network Connection", true,message, "Network connection is available.");
        } else {
            isNetworkConnected  = false;
            new Test("Network Connection", false,message, NetTools.netErrorMsg);
        }

        System.out.println("Network Connection: " + res + "||" + DateTools.getNow() + "||" + getTimeCost()) ;
        //showSelfTestMessage("Connecting to the network...");
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
        String message = "Checking data files integrity...";
        if (isPass) {
            new Test("Data integrity", true,message, "Data files are complete.");
        } else {
            StatusContainer.isDataFileComplete =false;
            new Test("Data integrity", false, message,description.toString());
        }

        System.out.println("Data integrity: " + isPass + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        //showSelfTestMessage("Checking data files integrity...");
    }

    // is backend server running
    private static void isBackendServerRunning() {
        setStarTime();
        boolean res = NetTools.netIsAvailable(ConfigFile.backendPost);

        String message = "Checking backend server running status...";
        if (res) {
            new Test("Backend Server", true,message, "Backend server is running.");
        } else {
            new Test("Backend Server", false,message, StatusContainer.backEndErrorMessage);
        }

        System.out.println("Backend Server: " + res + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        //showSelfTestMessage("Checking backend server running status...");
    }

    private static void isSecretFilesDecrypted(){
        setStarTime();
        boolean res = StatusContainer.isDataDecrypted;
        String message = "Checking secret file...";
        res = false;
        if (res) {
            new Test("Secret File Decrypted", true, message,"Secret files are decrypted.");
        } else {
            new Test("Secret File Decrypted", false, message,"Secret files are not decrypted."
            + " Please contact the administrator to decrypt the files." + "Secret files are not decrypted."
            + " Please contact the administrator to decrypt the files." + "Secret files are not decrypted."
            + " Please contact the administrator to decrypt the files." + "Secret files are not decrypted.");
        }

        //System.out.println("Secret File Decrypted: " + res + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

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
        String message = "Checking secret files...";
        isPass = false;
        if (isPass) {
            new Test("Secret File", true,message, "Secret files are not exist.");
        } else {
            new Test("Secret File", false, message,description.toString());
        }

        //System.out.println("Secret original File exist: " + isPass + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

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
        String message = "Checking secret files...";
        if (isPass) {
            new Test("Secret File", true,message, "Secret files are exist.");
        } else {
            new Test("Secret File", false, message,description.toString());
        }

        showSelfTestMessage("Checking secret files...");
    }


    // is all files decrypted successfully, Through the successful operation of the decryption process
    private static void isAllFilesDecrypted() {
        setStarTime();
        String decryptedErrorMsg = DataTools.encryptErrorMessage;

        StatusContainer.isDataDecrypted = false;

        String message = "Checking data files decryption status...";
        if (StatusContainer.isDataDecrypted) {
            new Test("Encrypted data decryption status", true, message,"Data files are complete.");
        } else {
            new Test("Encrypted data decryption status", false, message,decryptedErrorMsg);
        }

        //System.out.println("Encrypted data decryption status: " + StatusContainer.isDataDecrypted + "||" + DateTools.getNow() + "||" + getTimeCost()) ;

        showSelfTestMessage("Checking data files decryption status...");
    }

    private static void isJxBrowserLicensed() {
        setStarTime();
        String message = "Checking necessary license...";
        try {
            Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);
            new Test("JxBrowser", true, message,"JxBrowser is licensed.");
        } catch (Exception e) {
            StatusContainer.isJxBrowserRegistered =false;
            new Test("JxBrowser", false, message,"JxBrowser is not licensed.");
            System.out.println("JxBrowser is not licensed.");
        }

        System.out.println( "JxBrowser: " + true + "||" + DateTools.getNow() + "||" + getTimeCost()) ;
        //showSelfTestMessage("Checking necessary license...");
    }

    private static void showTestMsg(){
        long costTimeWholeTest=System.currentTimeMillis() - startTimeWholeTest;
        System.out.println("Test Number: " + testList.size() +  "|| whole Test cost: " +  costTimeWholeTest + " ms"  );

        if(isDataFileComplete && idBackEndServerStart&& isJxBrowserRegistered){
            if(isNetworkConnected){
                StatusContainer.selfTestResult = true;
                StatusContainer.isRunable = true;
            }else{
                StatusContainer.selfTestResult = false;
                StatusContainer.isRunable = true;
            }
        }else{
            StatusContainer.selfTestResult = false;
            StatusContainer.isRunable = false;
        }
        // selfTestResult isRunable
        // t t, all 4 is running
        // f t, just net not working
        // f f , 1/3 have serious issues





    }

    private static void showSelfTestMessage(String text){
        //LoadingPageController.instance.setLoadingTest(text);

    }

}
// DONE backend test
// TODO delete file if should not exist (decrypt first ,then there are decrypted files, should not have .secret files)