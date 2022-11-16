package com.example.car_rental_sys;

import com.example.car_rental_sys.controllers.Controller;
import com.example.car_rental_sys.controllers.PaymentController;
import com.example.car_rental_sys.orm.*;
import com.example.car_rental_sys.ui_components.OrderCard;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class StatusContainer {
    public static String currentCarChose = "Huracan_EVO";
    //Porsche_718

    public static boolean isFirstViewCar = true;
    public static String locationChose = null;
    public static String pickDateTime = null;
    public static String returnDateTime = null;
    public static String currentPinCode = "1234";
    public static boolean ifVerify = false;
    public static PaymentController paymentControllerInstance = null;
    public static RadioButton radioBtn2 = null;
    public static Controller currentPageController= null;
    public static String currentResetPassWordEmail = null;
    public static String currentPaymentMethod = null;
    public static String currentUserEmail = "1575270dad@qq.com";
    public static String currentChooseBankCardNum = "1234567890123456";
    public static Boolean isHideDriverSideBar = false;
//    public static User currentUser = null;
//    public static User currentUser = new Customer("1575270674@qq.com");
    public static User currentUser = new Driver("cervantesmichael@yahoo.com");
    //public static User currentUser =  new Admin("david32@hotmail.com");

    public static Order currentOrder = new Order(7);
    public static String loginMethod = "password";
    public static String deviceType = "desktop";
    public static Stage currentStage = null;
    public static OrderCard currentOrderCard = null;



    // self-test statues during loading
    public static boolean selfTestResult = false;
    public static boolean isRunable = false;
    // selfTestResult isRunable
    // t t, all 4 is running
    // f t, just net not working
    // f f , 1/3 have serious issues


    public static boolean isNetworkConnected = true;
    public static boolean isDataFileComplete = true;
    public static boolean idBackEndServerStart = true;
    public static boolean isJxBrowserRegistered  = true;



    public static String backEndErrorMessage = null;




    public static boolean isDataDecrypted = true;
    //public static boolean isDataDecrypted = false;
    public static boolean isConfigComplete = false;
    //public static boolean isJxBrowserRegistered  = false;
    public static boolean isTestResetPassword = false;
    public static boolean isTestCarList = false;
    public static boolean isTestCarDetail = false;
    public static boolean isTestCarBooking = false;
    public static boolean isTestCarBooking2 = false;
    public static boolean isTestCarBooking3 = false;
    public static boolean isTestCarBooking4 = false;
}
