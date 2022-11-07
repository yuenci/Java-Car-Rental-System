package com.example.car_rental_sys;

import com.example.car_rental_sys.controllers.Controller;
import com.example.car_rental_sys.controllers.PaymentController;
import javafx.scene.control.RadioButton;

public class StatusContainer {
    public static boolean isLogin = true;
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
}
