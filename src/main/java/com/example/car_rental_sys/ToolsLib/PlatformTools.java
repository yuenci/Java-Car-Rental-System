package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ui_components.BrowserModal;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


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

    public static void startWindowNetworkSetting() {
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

    public static String getPropertyOsName() {
        Properties props = System.getProperties(); //get platform information
        return props.getProperty("os.name");
    }

    public static String screenShot(){
        String path = "src/main/resources/com/example/car_rental_sys/image/others/screenshot.png";
        try {
            Robot robot = new Robot();
            // get screen size
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            //Rectangle screenRect = new Rectangle(1280, 832);
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, "png", new File(path));
            System.out.println("A full screenshot saved!");
            return path;
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
            return null;
        }

    }

    public static void openScreenShotWithMsPaint(){
        String path = "src/main/resources/com/example/car_rental_sys/image/others/screenshot.png";
        String filePath = new File(path).getAbsolutePath();
        //System.out.println(filePath);
        String cmd = "mspaint " + "\"" + filePath + "\"";
        exebat(cmd);
    }
}
