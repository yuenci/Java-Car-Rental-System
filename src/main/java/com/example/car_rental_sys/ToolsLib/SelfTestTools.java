package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;

import java.util.ArrayList;

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

    public static String isNetworkConnected() {
        boolean res = NetTools.netIsAvailable(ConfigFile.connectTestUrl);
        if (res) {
            new Test("Network Connection", true, "Network connection is available.");
        } else {
            new Test("Network Connection", false, StatusContainer.errorMessage);
        }
        return "Connecting to the network...";
    }

    public static String isDataFileComplete() {
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
            new Test("GeoIP", true, "Data files are complete.");
        } else {
            new Test("GeoIP", false, description.toString());
        }

        return "Checking data files integrity...";
    }

    // is backend server running
    public static String isBackendServerRunning() {
        boolean res = NetTools.netIsAvailable(ConfigFile.backendPost);
        if (res) {
            new Test("Backend Server", true, "Backend server is running.");
        } else {
            new Test("Backend Server", false, StatusContainer.errorMessage);
        }

        return "Checking backend server running status...";
    }

    // if secret files exist(should not exist)
    public static String isSecretFileExist() {
        String[] secretFileList = DataTools.getAllFileNameFromPath();

        boolean isPass = true;
        StringBuilder description = new StringBuilder();
        for (String fileName : secretFileList) {
            boolean res = fileName.contains(".secret");
            if (res) {
                isPass = false;
                description.append("File ").append(fileName).append(" should not exist but exist. ");
            }
        }

        if (isPass) {
            new Test("Secret File", true, "Secret files are not exist.");
        } else {
            new Test("Secret File", false, description.toString());
        }

        return "Checking secret files...";
    }


    // is all files decrypted successfully
    public static String isAllFilesDecrypted() {
        String decryotedErrorMsg = DataTools.encryptErrorMessage;
        if (decryotedErrorMsg == null) {
            new Test("GeoIP", true, "Data files are complete.");
        } else {
            new Test("GeoIP", false, decryotedErrorMsg);
        }

        return "Checking data files decryption status...";
    }

}
// DONE backend test