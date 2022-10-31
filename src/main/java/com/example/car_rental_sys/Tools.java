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

    /**
     * 执行bat文件，
     *
     * @param file          bat文件路径
     * @param isCloseWindow 执行完毕后是否关闭cmd窗口
     * @return bat文件输出log
     */

    public static String executeBatFile(String file, boolean isCloseWindow) {
        String cmdCommand;
        if (isCloseWindow) {
            cmdCommand = "cmd.exe /c " + file;
        } else {
            cmdCommand = "cmd.exe /k " + file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(" ");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
