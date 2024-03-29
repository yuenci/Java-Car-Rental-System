package com.example.car_rental_sys.ToolsLib;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.StatusContainer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NetTools {
    public static String getExternalHostIP() {
        URL whatIsMyIP = null;
        try {
            whatIsMyIP = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (whatIsMyIP == null) {
            return null;
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatIsMyIP.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in == null) {
            return null;
        }

        String ip = null; //you get the IP as a String
        try {
            ip = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(ip);
        return ip;
    }

    public static String getLocalHostIP(String type){
        InetAddress addObj = null;
        try {
            addObj = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            PlatformTools.logError(e);
            e.printStackTrace();
        }
        assert addObj != null;
        if(Objects.equals(type, "hostAddress")){
            return addObj.getHostAddress();
        }else if(Objects.equals(type, "hostName")){
            return addObj.getHostName();
        }else{
            return null;
        }
    }

    public static String getGeoIPInfo(){
        String ip = getExternalHostIP();
        String url = ConfigFile.apiGeoSP + ip;
        String res = sendRequest(url,"GET");
        String[] resArr = new String[4];
        try {
            JSONObject jsonObject = new JSONObject(res);


            String country = jsonObject.getString("country");
            String region = jsonObject.getString("regionName");
            String city = jsonObject.getString("city");
            double lat = jsonObject.getDouble("lat");
            double lon = jsonObject.getDouble("lon");
            String latLon = lat + " " + lon;
            resArr[0] = country;
            resArr[1] = region;
            resArr[2] = city;
            resArr[3] = latLon;

        }catch (JSONException e){
            System.out.println( e);
        }
        // join
        return String.join("','", resArr);
    }

    public static String sendRequest(String urlParam,String requestType) {
//        https://blog.csdn.net/longshehe9319/article/details/80509829
        HttpURLConnection con;

        BufferedReader buffer;
        StringBuilder resultBuffer;

        try {
            URL url = new URL(urlParam);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestType);
            con.setRequestProperty("Content-Type", "application/json;charset=GBK");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = con.getInputStream();
                resultBuffer = new StringBuilder();
                String line;
                buffer = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                while ((line = buffer.readLine()) != null) {
                    resultBuffer.append(line);
                }
                return resultBuffer.toString();
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String netErrorMsg = "";

    public static boolean netIsAvailable(String urlParam) {
        //automatic return false after 5 second

        FutureTask<Boolean> future = new FutureTask<>(() -> {
            Thread.sleep(ConfigFile.netDetectMaxTime);
            return false;
        });
        future.run();


        try {
            final URL url = new URL(urlParam);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            netErrorMsg = e.toString();
            return false;
        }
    }

    public static void StartHttpServer() {
        try{
            Thread thread = new Thread(() -> {
                String projectPath = DataTools.getProjectPath();
                String cmdStr = "http-server \"" + projectPath + "\\src\\main\\resources\\com\\example\\car_rental_sys\\html\" -p 8174";
                //System.out.println(cmdStr);
                PlatformTools.exebat(cmdStr);
            });
            thread.start();
            StatusContainer.idBackEndServerStart = true;
        }catch (Exception e){
            StatusContainer.idBackEndServerStart = false;
            PlatformTools.logError(e);
            StatusContainer.backEndErrorMessage = e.toString();
            e.printStackTrace();
        }

        // http-server "E:\Materials\Semester 3\【OODJ】\assignment\version0.1\crs\src\main\resources\com\example\car_rental_sys\html\datePicker"
    }

    public static void registerJxBrowserLicence(){
        System.setProperty("jxbrowser.license.key", ConfigFile.jxBrowserLicense);
    }


}
