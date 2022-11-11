package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ui_components.BrowserModal;

import java.io.IOException;
import java.util.function.Function;

public class PlatformTools {
    public static void exebat(String strcmd) {
        strcmd = "cmd /k start " + strcmd;
        System.out.println(strcmd);
        Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime() return current runtime object
        Process ps = null;  //Process can track the execution of the child process or get the information of the child process.
        try {
            ps = rt.exec(strcmd);
            //ps = rt.exec(strcmd);   // execute the command
            ps.waitFor();  // wait for the process to complete
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }

        assert ps != null;
        int i = ps.exitValue();  //get return value
        if (i == 0) {
            System.out.println("Server start.");
        } else {
            System.out.println("Server failed.");
        }

        ps.destroy();  //destroy  process
    }

    public static void startWindowSetting(){
        String windowsSettingPath = "ms-settings:network-wifi";
        exebat(windowsSettingPath);
    }

    public static void callWhatsApp(String phoneNumber) {
        String url = "https://api.whatsapp.com/send/?phone=" + phoneNumber;
        BrowserModal browserModal = new BrowserModal(500, 450, url);
        Function<String, Void> func = (message) -> {
            //System.out.println(message);
            return null;
        };
        browserModal.setFunction(func);
        browserModal.show();
    }
}
