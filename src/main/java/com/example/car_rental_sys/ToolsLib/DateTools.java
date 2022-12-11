package com.example.car_rental_sys.ToolsLib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTools {

    public static String getFormatDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
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

    public static String getDataTimeAfterAWeek(){
        Date date = new Date();
        date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDataTimeAfterDays(int days){
        Date date = new Date();
        date.setTime(date.getTime() + (long) days * 24 * 60 * 60 * 1000);
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static long startTime ;
    public static void setStarTime(){
        startTime=System.currentTimeMillis();
    }

    public static String getTimeCost(){
        long endTime=System.currentTimeMillis();
        return " runtime： "+(endTime-startTime)+" ms";
    }

    public static int getHourDiff(Date start, Date end){
        long diff = end.getTime() - start.getTime();
        return (int) (diff / (1000 * 60 * 60));
    }

    public static int getHourDiff(String startStr, String endStr){
        Date start = stringToDateObje(startStr);
        Date end = stringToDateObje(endStr);

        long diff = end.getTime() - start.getTime();
        return (int) (diff / (1000 * 60 * 60));
    }

    // 2022-09-24 02:07:32 | 2022-09-24 to timestamp
    public static long dateTimeToTimestamp(String time) {
        SimpleDateFormat sdf  = null;
        if (time.length() == 19){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else{
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }

        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    // timestamp to 2022-09-24 02:07:32
    public static String timeStampToDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    public static String timeStampToDateTime(String timestamp) {
        long time = Long.parseLong(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time * 1000));
    }



    public static boolean validateDate(String startTime, String endTime) {


        // startTime must later than now
//        if (dateTimeToTimestamp(startTime) < System.currentTimeMillis()){
//            return false;
//        }

        long start = dateTimeToTimestamp(startTime);
        long end = dateTimeToTimestamp(endTime);

        return start < end;
    }
}
