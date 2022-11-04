package com.example.car_rental_sys;

import com.example.car_rental_sys.sqlParser.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Tools {
    /**
     * Switching scenes， use for onAction call back
     * @param actionEvent
     * @param fxmlName "loginPage.fxml"
     * @return void
     */
    public  void reSetScene(ActionEvent actionEvent, String fxmlName) {
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

    /**
     * Switching scenes， use for non-static call back
     * @param fxmlName "loginPage.fxml"
     * @return void
     */
    public void reSetScene(String fxmlName) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/"+ fxmlName));
        try {
            Scene scene = new Scene( fxmlLoader.load());
            HelloApplication.stageInstance.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switching scenes， use for static call back
     * @param fxmlName "loginPage.fxml"
     * @return void
     */
    public static void changeScene(String fxmlName) {

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
            String cmdStr = "http-server \""+ projectPath +"\\src\\main\\resources\\com\\example\\car_rental_sys\\html\" -p 8080";
            //System.out.println(cmdStr);
            Tools.exebat(cmdStr);
        });
        thread.start();

        // http-server "E:\Materials\Semester 3\【OODJ】\assignment\version0.1\crs\src\main\resources\com\example\car_rental_sys\html\datePicker"
    }

    /**
     * verify if user's email and password match
     * @param email
     * @param password
     * @return
     */
    public static int loginValidation(String email, String password){
        try {
            ArrayList<String[]> result = SQL.query("SELECT userID FROM userInfo WHERE email = '"+ email +"'");
            if(result.size() == 0)  return 100 ; // email not found return 100

            String userID = result.get(0)[0];

            String passwordMD5InDB = SQL.query("SELECT password FROM password WHERE userID = "+ userID).get(0)[0];
            String passwordInputMD5 = Encryption.med5Encrypt(password);

            if(passwordInputMD5.equals(passwordMD5InDB)) return 200; // password match return 200
            else return 300; // password not match return 300

        }catch (Exception e){
            return 400; //unknown error
        }
        // return code
        // 200: success
        // 100: email not found
        // 300: password not match
        // 400: unknown error
    }
}
