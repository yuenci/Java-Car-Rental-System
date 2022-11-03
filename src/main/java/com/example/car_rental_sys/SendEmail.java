package com.example.car_rental_sys;



import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * JavaMail 版本: 1.6.0
 * JDK 版本: JDK 1.7 以上（必须）
 */
public class SendEmail {

    public static String myEmailAccount = Config.myEmailAccount;
    public static String myEmailPassword = Config.myEmailPassword;
    public static String myEmailSMTPHost = Config.myEmailSMTPHost;


    private static void send(String receiveEmailAddress,String userName,String type) throws Exception {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);


        Session session = Session.getInstance(props);
        session.setDebug(false);

        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveEmailAddress,userName,type);

        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String useName,String type) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, "Rent.Inc", "UTF-8"));

        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "Dear xx(user)", "UTF-8"));

        message.setSubject("Your Rent.Inc "+type+" verification is in progres", "UTF-8");

        int min = 100000;
        int max = 999999;
        int pinCode = min + (int)(Math.random() * (max-min+1));

        StatusContainer.currentPinCode = String.valueOf(pinCode);
        String content = getHtmlContent(StatusContainer.currentPinCode,useName,type);

        message.setContent(content, "text/html;charset=UTF-8");
//        message.setContent("<h1>This is actual message</h1>", "text/html" ); // 发送 HTML 消息, 可以插入html标签

        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }



    public static String getHtmlContent(String PINCode,String userName,String type){
        return "<div style=\"margin: 0  auto ;width: 500px;height: 300px;border-style: solid;border-width: thin;" +
                "border-color: #dadce0;border-radius: 8px;padding: 40px 20px;\"><div style=\"text-align:center;\">" +
                "<img src=\"" +
                Config.logoAddress+
                "\"" +
                "alt=\"Logo\"style=\"height: 50px;\"></div><h3 style=\"text-align: center;padding-bottom: 20px;" +
                "border-bottom: 1px solid #dadce0;\">Your Rent.Inc " +
                type +
                " verification is in progres</h3><p " +
                "style=\"margin: 30px 0px ;\">Hi, " +
                userName +
                ", Your verification code is:</p><pre style=\"padding:16px 24px;" +
                "border:1px solid #eeeeee;background-color:#f4f4f4;border-radius:3px;font-family:monospace;" +
                "margin-bottom:24px\">" +
                PINCode+
                "</pre><div>Thank you for choosing our service. For more information,please click this " +
                "<a href=\"https://github.com/yuenci/Java-Car-Rental-System\">link</a>.</div></div><div " +
                "style=\"text-align:center; margin-top: 20px;\"><a href=\"#\"style=\"width:24px;height:24px;" +
                "display:inline-block;margin-right:20px\"target=\"_blank\">" +
                "<img src=\"https://cdn.tngdigital.com.my/resource/2022/6/27/a02d1c02-e2dd-41e6-93a7-27741476b3d1.png\"" +
                "style=\"width:24px;height:24px\"alt=\"\"></a>" +
                "<a href=\"#\"style=\"width:24px;height:24px;display:inline-block;margin-right:20px\"target=\"_blank\">" +
                "<img src=\"https://cdn.tngdigital.com.my/resource/2022/6/27/6ce6ce99-b65b-4f3a-9d7f-7e68ca600640.png\"" +
                "style=\"width:24px;height:24px\"alt=\"\" ></a><a href=\"#\"" +
                "style=\"width:24px;height:24px;display:inline-block;margin-right:20px\"target=\"_blank\">" +
                "<img src=\"https://cdn.tngdigital.com.my/resource/2022/6/27/d09a1d99-bb2b-41a5-a4c4-594c09f9da3c.png\"" +
                "style=\"width:24px;height:24px\"alt=\"\" ></a><a href=\"#\"" +
                "style=\"width:24px;height:24px;display:inline-block;margin-right:20px\"target=\"_blank\">" +
                "<img src=\"https://cdn.tngdigital.com.my/resource/2022/6/27/fbc0a520-9f70-4532-ba6f-39fc52697f1e.png\"" +
                "style=\"width:24px;height:24px\"alt=\"\" ></a><a href=\"#\"" +
                "style=\"width:24px;height:24px;display:inline-block\"target=\"_blank\">" +
                "<img src=\"https://cdn.tngdigital.com.my/resource/2022/6/27/5baa24d9-e6c4-48d6-a3bd-27c7acfcbf31.png\"" +
                "style=\"width:24px;height:24px\"alt=\"\" ></a></div><div " +
                "style=\"color:#969696;text-align:center; margin-top: 10px;\">©2022 Rent.Inc.All rights reserved.</div>";
    }

    public static void sendVerificationEmail(String receiveEmailAddress,String uerName,String type) throws Exception {
        SendEmail.send("yuenci1575270674@gmail.com","Innis","signUp");
        // sign up ; login; reset password
    }

}
