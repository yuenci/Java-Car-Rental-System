package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.Application;
import com.example.car_rental_sys.StatusContainer;
import com.example.car_rental_sys.controllers.AdminServiceController;
import com.example.car_rental_sys.controllers.CustomerServiceController;
import com.example.car_rental_sys.controllers.DriverMainPageController;
import com.example.car_rental_sys.orm.Admin;
import com.example.car_rental_sys.orm.Customer;
import com.example.car_rental_sys.orm.Driver;
import com.example.car_rental_sys.orm.User;
import com.example.car_rental_sys.sqlParser.SQL;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

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
        executeBeforeChangeScene();
    }

    private static void executeBeforeChangeScene(){}

    public static void initFXML(Pane pane, String fxml)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxml));
        pane.getChildren().clear();
        pane.getChildren().add(fxmlLoader.load());
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

    private static void  showAStage(double width, double height, String fxmlName) throws IOException {
        Stage stage = new Stage();
        StatusContainer.currentStage = stage;
        //System.out.println("1:" + StatusContainer.currentStage);


        stage.setWidth(width);
        stage.setHeight(height);


        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/" + fxmlName));
        FXTools.setStageShowCenterOfScreen(stage);
        Scene scene = new Scene(fxmlLoader.load(), width, height);


        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("file:src/main/resources/com/example/car_rental_sys/image/UI/logoIcon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void startLoadingStage() throws IOException {
        showAStage(640, 416, "loadingPage.fxml");
    }

    public static void showNetworkErrorPage() throws IOException {
        showAStage(550, 340, "networkIssuePage.fxml");
    }

    public static void showErrorsPage() throws IOException {
        showAStage(550, 800, "ErrorReportPage.fxml");
    }

    public static boolean ifShowDiagnosticDataPageOpen = false;
    public static void showDiagnosticDataPage() throws IOException {
        if(!ifShowDiagnosticDataPageOpen){
            showAStage(550, 800, "diagnosticDataPage.fxml");
            ifShowDiagnosticDataPageOpen = true;
        }

    }

    public static boolean checkCurrentUserStage(){
        return StatusContainer.currentUser instanceof Customer;
    }

    public static void goToSettingPage(){
        User user = StatusContainer.currentUser;
        if(user instanceof Customer){
            CustomerServiceController.instance.showSettingPage();
        }else if(user instanceof Admin){
            AdminServiceController.instance.showSettingPage();
        }else if(user instanceof Driver){
            DriverMainPageController.driverMainPageInstance.showSettingPage();
        }else{
            System.out.println("Please login");
        }
    }

    public static double[] getMousePosition() {
            Robot robot = new Robot();
//            System.out.println("X is: " + robot.getMousePosition().getX());
//            System.out.println("Y is: " + robot.getMousePosition().getY());
            return new double[]{robot.getMousePosition().getX(), robot.getMousePosition().getY()};
    }

    public static void  printJavaFXNode(Node node){
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        job.getJobSettings().setPageLayout(pageLayout);
        boolean success = job.printPage(node);
        if (success) {
            job.endJob();
        }
    }

    public static Stage getStageFromFXNode(Node node){
        return (Stage) node.getScene().getWindow();
    }

    public static Pane getMapComponent(){
        Pane pane = new Pane();
        Engine engine = Engine.newInstance(HARDWARE_ACCELERATED);

        Browser browser = engine.newBrowser();
        if(StatusContainer.currentUser instanceof Customer){
            browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/directionsDark.html").getAbsolutePath());
        }else{
            browser.navigation().loadUrl(new File("src/main/resources/com/example/car_rental_sys/html/directionsLight.html").getAbsolutePath());
        }


        BrowserView view = BrowserView.newInstance(browser);
        view.setPrefSize(290,115);

        pane.getChildren().add(view);
        return pane;
    }

    public static String getNodeBgColor(Node node){
        String style = node.getStyle();
        //System.out.println(style);
        String[] styleArray = style.split(";");
        String colorStyle = "";
        //use a for loop to find the string which contains "-fx-background-color"
        for (String s : styleArray) {
            if (s.contains("-fx-background-color")) {
                //split the string with ":" and get the color style
                colorStyle = s.split(":")[1];
            }
        }
        //System.out.println(colorStyle);
        return colorStyle;
    }

    public static void validInputLength(TextField textField, String type, String oldValue, String newValue){
        if(!newValue.matches("[a-zA-Z0-9]*")){
            textField.setText(oldValue);
        }
        switch (type) {
            case "cardName":
                if (newValue.length() > 25) {
                    textField.setText(oldValue);
                }
                break;
            case "carNumber":
                if (newValue.length() > 8) {
                    textField.setText(oldValue);
                }
                break;
        }
    }

    public static void validInputNumber(TextField textField, String type, String oldValue, String newValue){
        if(!newValue.matches("\\d*")){
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
        switch (type){
            case "price":
                if(newValue.length() > 5){
                    textField.setText(oldValue);
                }
                break;
            case "chassis":
                if(newValue.length() > 10){
                    textField.setText(oldValue);
                }
                break;
        }
    }

    public static void validInputIsDate(TextField textField, String regex, String newValue) {
        if (newValue.length() == 10) {
            switch (regex) {
                case "-":
                    if (!newValue.matches("^((19|2[0-9])[0-9]{2})-[0-1][0-9]-[0-3][0-9]$")) {
                        Platform.runLater(() -> {
                            textField.clear();
                            textField.getStyleClass().add("txtErrorFocusStyle");
                        });
                    } else {
                        textField.getStyleClass().remove("txtErrorFocusStyle");
                    }
                    break;
                case "/":
                    if (!newValue.matches("^((19|2[0-9])[0-9]{2})/[0-1][0-9]/[0-3][0-9]$")) {
                        Platform.runLater(() -> {
                            textField.clear();
                            textField.getStyleClass().add("txtErrorFocusStyle");
                        });
                    } else {
                        textField.getStyleClass().remove("txtErrorFocusStyle");
                        break;
                    }
            }
        }
    }

    public static boolean validInputIsEmail(TextField textField, String newValue){
        if (!newValue.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,7}$")) {
            return false;
        }else{
            textField.getStyleClass().remove("txtErrorFocusStyle");
            return true;
        }
    }

    public static boolean validInputIsPhone(TextField textField, String newValue){
        if (!newValue.matches("^\\+[0-9]{1,2}-[0-9]{9,13}$")) {
            return false;
        }else{
            textField.getStyleClass().remove("txtErrorFocusStyle");
            return true;
        }
    }

    public static boolean validInputCardValid(TextField textField, String newValue){
        if(!newValue.matches("\\d*")){
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }

        return true;
    }

    public static boolean validInputCardNumber(TextField textField, String newValue){
        if(!newValue.matches("\\d*")){
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
        if(newValue.length() == 16){
            return true;
        }

        return true;
    }

    public static boolean validInputCardValueLength(TextField textField, String newValue){
        if(!newValue.matches("[a-zA-Z ]*")){
            textField.setText(newValue.replaceAll("[^a-zA-Z ]", ""));
        }
        if(newValue.length() > 8){
            return true;
        }

        return true;
    }

    public static boolean validInputBillingAddress(TextArea textArea, String oldValue, String newValue){
        if(!newValue.matches("[a-zA-Z0-9 ]*")){
            textArea.setText(oldValue);
        }
        if (newValue.length() > 8) {
            return true;
        }
        return true;
    }


    public static void pandaHead(){
        String pandaHead = "   ⣠⣤⣤⣤⡀⠀⠀⢀⣀⣀⣤⣤⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"+
                            "⠀⣾⣻⣻⣻⣻⡿⠚⠉⠉⠀⠀⠀⠀⠀⠀⠈⠙⠲⣴⣻⣻⣻⣷⣄⠀\n"+
                            "⢸⣻⣻⣻⣻⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣻⣻⣻⣻⡆\n"+
                            "⠀⠻⣻⣻⠏⠀⠀⠀⠀⣶⠿⠿⢿⣷⣄⢠⡿⠿⠶⠦⠀⢹⣻⣻⣻⠃\n"+
                            "⠀⠀⢈⡏⠀⠀⠀⠀⠈⠑⠺⣻⠟⠉⠁⠈⠛⢿⠆⠀⠀⠀⢻⠋⠁⠀\n"+
                            "⠀⠀⡼⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⠂⠀⢀⠀⢠⡄⠀⠀⠀⢸⡀⠀⠀\n"+
                            "⢀⣴⡇⠀⠀⠀⠀⠀⠀⠶⢿⣻⣉⣛⣻⣻⣛⣉⡻⣦⠀⠀⠀⣇⠀⠀\n"+
                            "⣾⣻⡇⠀⠀⠀⠀⠀⠀⠄⢸⡟⢿⣯⣭⣭⣽⣻⠃⠈⠀⠀⠀⣻⣧⠀\n"+
                            "⣻⣻⣻⣦⡀⠀⠀⠀⠀⠈⠈⠿⠶⣭⣭⣬⡭⠁⠀⠀⠀⢀⣼⣻⣻⣧\n"+
                            "⣻⣻⣻⣻⣻⣶⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣴⣻⣻⣻⣻⣻\n"+
                            "⣻⣻⣻⣻⣻⣻⣻⣻⣻⣷⣶⣶⣶⣶⣶⣶⣾⣻⣻⣻⣻⣻⣻⣻⣻⣻\n";
        System.out.println(pandaHead);

    }

    public static void dogHead(){
        String dogHead = "\n" +
                "                         ::\n" +
                "                        :;J7, :,                        ::;7:\n" +
                "                        ,ivYi, ,                       ;LLLFS:\n" +
                "                        :iv7Yi                       :7ri;j5PL\n" +
                "                       ,:ivYLvr                    ,ivrrirrY2X,\n" +
                "                       :;r@Wwz.7r:                :ivu@kexianli.\n" +
                "                      :iL7::,:::iiirii:ii;::::,,irvF7rvvLujL7ur\n" +
                "                     ri::,:,::i:iiiiiii:i:irrv177JX7rYXqZEkvv17\n" +
                "                  ;i:, , ::::iirrririi:i:::iiir2XXvii;L8OGJr71i\n" +
                "                :,, ,,:   ,::ir@mingyi.irii:i:::j1jri7ZBOS7ivv,\n" +
                "                   ,::,    ::rv77iiiriii:iii:i::,rvLq@huhao.Li\n" +
                "               ,,      ,, ,:ir7ir::,:::i;ir:::i:i::rSGGYri712:\n" +
                "             :::  ,v7r:: ::rrv77:, ,, ,:i7rrii:::::, ir7ri7Lri\n" +
                "            ,     2OBBOi,iiir;r::        ,irriiii::,, ,iv7Luur:\n" +
                "          ,,     i78MBBi,:,:::,:,  :7FSL: ,iriii:::i::,,:rLqXv::\n" +
                "          :      iuMMP: :,:::,:ii;2GY7OBB0viiii:i:iii:i:::iJqL;::\n" +
                "         ,     ::::i   ,,,,, ::LuBBu BBBBBErii:i:i:i:i:i:i:r77ii\n" +
                "        ,       :       , ,,:::rruBZ1MBBqi, :,,,:::,::::::iiriri:\n" +
                "       ,               ,,,,::::i:  @arqiao.       ,:,, ,:::ii;i7:\n" +
                "      :,       rjujLYLi   ,,:::::,:::::::::,,   ,:i,:,,,,,::i:iii\n" +
                "      ::      BBBBBBBBB0,    ,,::: , ,:::::: ,      ,,,, ,,:::::::\n" +
                "      i,  ,  ,8BMMBBBBBBi     ,,:,,     ,,, , ,   , , , :,::ii::i::\n" +
                "      :      iZMOMOMBBM2::::::::::,,,,     ,,,,,,:,,,::::i:irr:i:::,\n" +
                "      i   ,,:;u0MBMOG1L:::i::::::  ,,,::,   ,,, ::::::i:i:iirii:i:i:\n" +
                "      :    ,iuUuuXUkFu7i:iii:i:::, :,:,: ::::::::i:i:::::iirr7iiri::\n" +
                "      :     :rk@Yizero.i:::::, ,:ii:::::::i:::::i::,::::iirrriiiri::,\n" +
                "       :      5BMBBBBBBSr:,::rv2kuii:::iii::,:i:,, , ,,:,:i@petermu.,\n" +
                "            , :r50EZ8MBBBBGOBBBZP7::::i::,:::::,: :,:,::i;rrririiii::\n" +
                "                :jujYY7LS0ujJL7r::,::i::,::::::::::::::iirirrrrrrr:ii:\n" +
                "             ,:  :@kevensun.:,:,,,::::i:i:::::,,::::::iir;ii;7v77;ii;i,\n" +
                "             ,,,     ,,:,::::::i:iiiii:i::::,, ::::iiiir@xingjief.r;7:i,\n" +
                "          , , ,,,:,,::::::::iiiiiiiiii:,:,:::::::::iiir;ri7vL77rrirri::\n" +
                "           :,, , ::::::::i:::i:::i:i::,,,,,:,::i:i:::iir;@Secbone.ii:::\n" +
                "\n";
        System.out.println(dogHead);
    }

}
