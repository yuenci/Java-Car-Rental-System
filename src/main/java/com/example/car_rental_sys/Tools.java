package com.example.car_rental_sys;

import com.example.car_rental_sys.ToolsLib.DataTools;
import com.example.car_rental_sys.ToolsLib.ImageTools;
import com.example.car_rental_sys.funtions.Encryption;
import com.example.car_rental_sys.sqlParser.FileOperate;
import com.example.car_rental_sys.sqlParser.SQL;
import com.example.car_rental_sys.ui_components.BrowserModal;
import com.example.car_rental_sys.ui_components.MessageFrame;
import com.example.car_rental_sys.ui_components.MessageFrameType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

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

    public static Image getImageObjFromPath(String path) {

        File file = new File(path);
        try {
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Image getImageObjFromImageName(String path) {
        path = "src/main/resources/com/example/car_rental_sys/image/" + path;
        File file = new File(path);
        try {
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


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

    public static String getProjectPath() {
        return System.getProperty("user.dir");
        //E:\Materials\Semester 3\CRS-version0.2
    }


    public static void StartHttpServer() {
        Thread thread = new Thread(() -> {
            String projectPath = Tools.getProjectPath();
            String cmdStr = "http-server \"" + projectPath + "\\src\\main\\resources\\com\\example\\car_rental_sys\\html\" -p 8174";
            //System.out.println(cmdStr);
            Tools.exebat(cmdStr);
        });
        thread.start();

        // http-server "E:\Materials\Semester 3\【OODJ】\assignment\version0.1\crs\src\main\resources\com\example\car_rental_sys\html\datePicker"
    }

    public static void startWindowSetting(){
        String windowsSettingPath = "ms-settings:network-wifi";
        Tools.exebat(windowsSettingPath);
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

            String passwordMD5InDB = SQL.query("SELECT password FROM password WHERE userID = " + userID).get(0)[0];
            String passwordInputMD5 = Encryption.med5Encrypt(password);

            if (passwordInputMD5.equals(passwordMD5InDB)) return 200; // password match return 200
            else return 300; // password not match return 300

        } catch (Exception e) {
            return 400; //unknown error
        }
        // return code
        // 200: success
        // 100: email not found
        // 300: password not match
        // 400: unknown error
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

    public static int getNewUserID() {
        ArrayList<String[]> result = FileOperate.readFileToArray("src/main/resources/com/example/car_rental_sys/data/userInfo.txt");
        //System.out.println(result.size() );
        return result.size();
    }

    public static String getFormatDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static boolean checkPassword(String password) {
        if (password.length() < 8) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must be at least 8 characters");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one uppercase letter");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one lowercase letter");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[0-9].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one number");
            messageFrame.show();
            return false;
        } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.WARNING, "Password must contain at least one special character");
            messageFrame.show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            MessageFrame messageFrame = new MessageFrame(MessageFrameType.ERROR, "Please enter a valid email address");
            messageFrame.show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean resetPassword(String email, String newPassword) {
        ArrayList<String[]> result = SQL.query("SELECT userID FROM userInfo WHERE email = '" + email + "'");
        if (result.size() == 0) return false; // email not found return 100

        try {
            String userID = result.get(0)[0];
            String newPasswordMD5 = Encryption.med5Encrypt(newPassword);
            SQL.execute("UPDATE password SET password = '" + newPasswordMD5 + "' WHERE userID = " + userID);
            //SendEmail.sendEmail(email, "Your new password", "Your new password is: " + newPassword);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static Date stringToDateObje(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObj;
    }

    public static String dateToString(Date date, String format) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getCarModelFromCarID(int carID) {
        String sql = "SELECT carModel FROM carInfo WHERE carID = " + carID;
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return result.get(0)[0];
        } else {
            return null;
        }
    }

    public static String getCarNumberFromCarID(int carID) {
        String sql = "SELECT carNumber FROM carInfo WHERE carID = " + carID;
        //System.out.println(sql);
        ArrayList<String[]> result = SQL.query(sql);
        if (result.size() == 1) {
            return capitalizeWord(result.get(0)[0]);
        } else {
            return null;
        }
    }

    public static String getGradientColorFromCarID(int carID) {
        String model = getCarModelFromCarID(carID);

        ArrayList<String[]> result = SQL.query("SELECT darkColor,lightColor FROM carModels WHERE carModel = '" + model + "'");

        if (result.size() == 1) {
            return result.get(0)[0] + "," + result.get(0)[1];
        } else {
            return null;
        }
    }

    // capitalize  a word
    public static String capitalizeWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    // capitalize a sentence of words
    public static String capitalizeFirstLetter(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        return result.toString().trim();
    }

    public static void yAxisFlip(ImageView imageView, double a1, double a2) {
        Translate flipTranslation = new Translate(imageView.getImage().getWidth() - a1, 0);
        Rotate flipRotation = new Rotate(a2, Rotate.Y_AXIS);
        imageView.getTransforms().addAll(flipTranslation, flipRotation);
    }

    public static Pane getView(String fileName) {
        Pane view = new Pane();
        try {
            String path = "src/main/resources/com/example/car_rental_sys/fxml/" + fileName;
            URL url = new File(path).toURI().toURL();
            view = new FXMLLoader().load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
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

    public static Image getImageObjFromUserID(int userID) {
        String avatarRoot = "src/main/resources/com/example/car_rental_sys/image/avatar/" + userID + ".png";
        File file = new File(avatarRoot);
        String path;
        if (file.exists()) {
            path = "file:" + avatarRoot;
            return ImageTools.getCircleImages(path);
        } else {
            if (Objects.equals(getGenderFromUserID(userID), "female")) {
                path = avatarRoot + "avatar_female.png";
                return new Image(path);
            }
            path = avatarRoot + "avatar_male.png";
            return ImageTools.getCircleImages(path);
        }

    }

    public static String replaceSpacialChar(String str) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥（）【】‘；：”“’。， 、？]";
        return str.replaceAll(regEx, " ");
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

    public static void logError(Exception e){
        String error = e.toString();
        String errorID = String.valueOf(DataTools.getID("systemLog"));
        String errorType =error.split(":")[0];
        String errorContent = error.split(":")[1];
        String errorPosition =  e.getStackTrace()[0].toString().split("/")[1];
        String errorTime = getNow();

        String sql = "INSERT INTO systemLog VALUES ("+errorID+",'"+errorType+"','"+errorContent+"','"+errorPosition+"','"+errorTime+"')";
        //System.out.println(sql);
        SQL.execute(sql);
    }
}
