package com.example.car_rental_sys;

import com.example.car_rental_sys.ToolsLib.*;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.sqlParser.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Tools {
    /**
     * Switching scenes， use for onAction call back
     *
     * @param actionEvent
     * @param fxmlName    "loginPage.fxml"
     * @return void
     */
    public void reSetScene(ActionEvent actionEvent, String fxmlName) {
        Pane pane = null;
        try {
            URL url = getClass().getResource("fxml/" + fxmlName);
            assert url != null;
            pane = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Button) actionEvent.getSource()).getScene().setRoot(pane);
    }

    /**
     * Switching scenes， use for non-static call back
     *
     * @param fxmlName "loginPage.fxml"
     * @return void
     */
    public void reSetScene(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxmlName));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Application.stageInstance.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * verify if user's email and password match
     *
     * @param email
     * @param password
     * @return
     */
    public static int loginValidation(String email, String password) {
        try {
            ArrayList<String[]> result = SQL.query("SELECT userID FROM userInfo WHERE email = '" + email + "'");
            if (result.size() == 0) return 100; // email not found return 100

            String userID = result.get(0)[0];
            System.out.println( "userID: " + userID);

            String passwordMD5InDB = SQL.query("SELECT password FROM password WHERE userID = " + userID).get(0)[0];
            String passwordInputMD5 = Encryption.med5Encrypt(password);

            if (passwordInputMD5.equals(passwordMD5InDB)) return 200; // password match return 200
            else return 300; // password not match return 300

        } catch (Exception e) {
            System.out.println(e.getMessage());
            PlatformTools.logError(e);
            return 400; //unknown error
        }
        // return code
        // 200: success
        // 100: email not found
        // 300: password not match
        // 400: unknown error
    }

}
