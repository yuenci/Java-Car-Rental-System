package com.example.car_rental_sys;

public class ConfigFile {
    //configs
    public static final int netDetectMaxTime = 1500; //millisecond
    public static final boolean enableSQLParserSecurityMode = false;
    public static final int  passwordStrength = 1; //1:weak -  5:strong



    // keys
    public static final String myEmailAccount = "yv1575270674@163.com";
    public static final String myEmailPassword = "GBZYHLHTFOYUAXAP";
    public static final String jxBrowserLicense = "1BNDHFSC1G4DC5TVZKE9U7YNLCVYB6MLFJBGX2YY77IHM90DE70M17RAXLI2TNSWDV0237";
    public static final String googleMapApiKey = "todo";
    public static final String  AESKey = "1234567890123456";


    // services
    public static final String myEmailSMTPHost = "smtp.163.com";
    public static final String apiGeoSP = "http://ip-api.com/json/";
    public static final String backendPost = "http://127.0.0.1:8080/";
    public static final String connectTestUrl = "http://www.google.com";

    // paths
    public static final String carsDataPath = "src/main/resources/com/example/car_rental_sys/data/carModels.txt";
    public static final String dataFilesRootPath = "src/main/resources/com/example/car_rental_sys/data/";
    public static final String htmlComponents = "\\src\\main\\resources\\com\\example\\car_rental_sys\\html\\";
    public static final String fakeData = "src/main/resources/com/example/car_rental_sys/data/fakeData.txt";
    public static final String logoAddress  = "https://utoolsfigurebed.oss-cn-hangzhou.aliyuncs.com/logo_light_green_cloud.png";
    public static final String dataFilesPath = "src/main/resources/com/example/car_rental_sys/data/";


    // files
    public static final String[] dataFileList = {
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

    public static final String[] sensitiveDataFileList = {
            "bankCardInfo1.txt",
            "password1.txt",
            "userInfo1.txt"
    };


    // bankCardThemes
    public  static final  String[] bankCardTheme1 =  {"#dddddd", "#000000"};
    public  static final  String[] bankCardTheme2 = {"#dddddd", "#000000"};

    // add bank card
    public static final String[] banks = {
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
