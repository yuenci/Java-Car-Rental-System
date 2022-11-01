package com.example.car_rental_sys;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Tools {
    public  void reSetScence(ActionEvent actionEvent,String fxmlName) {
        Pane pane  = null;
        try {
            URL url = getClass().getResource("fxml/" + fxmlName);
            assert url != null;
            pane = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Button)actionEvent.getSource()).getScene().setRoot(pane);
    }


    public void reSetScence(String fxmlName) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/"+ fxmlName));
        try {
            Scene scene = new Scene( fxmlLoader.load());
            HelloApplication.stageInstance.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScence(String fxmlName) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/"+ fxmlName));
        try {
            Scene scene = new Scene( fxmlLoader.load());
            HelloApplication.stageInstance.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image  getImageObjFromPath(String path){
        File file = new File(path);
        try{
            return new Image(file.toURI().toString());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public static void exebat(String strcmd){
        strcmd = "cmd /c start " + strcmd;
        Runtime rt = Runtime.getRuntime(); //Runtime.getRuntime() return current runtime object
        Process ps = null;  //Process can track the execution of the child process or get the information of the child process.
        try {
            ps = rt.exec(strcmd);   // execute the command
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
    public static String getProjectPath(){
        return   System.getProperty("user.dir");
        //E:\Materials\Semester 3\CRS-version0.2
    }


    public static void StartHttpServer(){
        Thread thread = new Thread(() -> {
            String projectPath = Tools.getProjectPath();
            String cmdStr = "http-server \""+ projectPath +"\\crs\\src\\main\\resources\\com\\example\\car_rental_sys\\html\\datePicker\"";
            Tools.exebat(cmdStr);
        });
        thread.start();

    }
}
