package com.example.car_rental_sys;

public class ConfigFile {
    // keys
    public static String myEmailAccount = "yv1575270674@163.com";
    public static String myEmailPassword = "GBZYHLHTFOYUAXAP";
    public static String jxBrowserLicense = "1BNDHFSC1G4DC5TVZKE9U7YNLCVYB6MLFJBGX2YY77IHM90DE70M17RAXLI2TNSWDV0237";
    public static String googleMapApiKey = "todo";
    public static String  AESKey = "1234567890123456";


    // services
    public static String myEmailSMTPHost = "smtp.163.com";
    public static String apiGeoSP = "http://ip-api.com/json/";
    public static String backendPost = "http://127.0.0.1:8080/";
    public static String connectTestUrl = "http://www.google.com";

    // paths
    public static String carsDataPath = "src/main/resources/com/example/car_rental_sys/data/carModels.txt";
    public static String dataFilesRootPath = "src/main/resources/com/example/car_rental_sys/data/";
    public static String htmlComponents = "\\src\\main\\resources\\com\\example\\car_rental_sys\\html\\";
    public static String fakeData = "src/main/resources/com/example/car_rental_sys/data/fakeData.txt";
    public static String logoAddress  = "https://utoolsfigurebed.oss-cn-hangzhou.aliyuncs.com/logo_light_green_cloud.png";


    // files
    public static String[] dataFileList = {
            "bankCardInfo.txt",
            "carInfo.txt",
            "carModels.txt",
            "carsRadarData.txt",
            "comments.txt",
            "loginLog.txt",
            "messages.txt",
            "orders.txt",
            "password.txt",
            "schedule.txt",
            "systemLog.txt",
            "transactionRecord.txt",
            "userInfo.txt"
    };

    public static String[] sensitiveDataFileList = {
            "bankCardInfo1.txt",
            "password1.txt",
            "userInfo1.txt"
    };


    // bankCardThemes
    public  static  String[] bankCardTheme1 =  {"#dddddd", "#000000"};
    public  static  String[] bankCardTheme2 = {"#dddddd", "#000000"};

    // add bank card
    public static String[] banks = {
            " Alliance Bank",
            " BNP Paribas",
            " Bank Islam",
            " Bank of America",
            " Bank of China",
            " BigPay",
            " China Construction Bank",
            " Citibank",
            " Deutsche Bank",
            " HSBC Bank",
            " JP Morgan",
            " MBSB Bank",
            " MUFG Bank",
            " Maybank",
            " Public Bank",
            " Paypal",
            " RHB Bank"
    };
}
