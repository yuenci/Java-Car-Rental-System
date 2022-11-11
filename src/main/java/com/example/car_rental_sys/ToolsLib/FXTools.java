package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.sqlParser.SQL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FXTools {
    /**
     * Switching scenes， use for static call back
     *
     * @param fxmlName "loginPage.fxml"
     * @return void
     */
    public static void changeScene(String fxmlName) {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxmlName));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Application.stageInstance.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void componentTest(String fxmlName, double width, double height) throws IOException {
        Stage stage = Application.stageInstance;

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("orderDetails!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void yAxisFlip(ImageView imageView, double a1, double a2) {
        Translate flipTranslation = new Translate(imageView.getImage().getWidth() - a1, 0);
        Rotate flipRotation = new Rotate(a2, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipTranslation, flipRotation);
    }


    public static String getGenderFromUserID(int userID) {
        String sql = "Select gender FROM userInfo WHERE userID = " + userID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static double[] getCenterOfScreen() {
        double[] center = new double[2];
        center[0] = Screen.getPrimary().getBounds().getWidth() / 2;
        center[1] = Screen.getPrimary().getBounds().getHeight() / 2;
        return center;
    }

    public static void setStageShowCenterOfScreen(Stage stage) {
        double[] center = getCenterOfScreen();
        stage.setX(center[0] - stage.getWidth() / 2);
        stage.setY(center[1] - stage.getHeight() / 2);
    }
}
