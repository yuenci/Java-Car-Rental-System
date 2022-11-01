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


    private static void send(String receiveEmailAddress,String userName) throws Exception {

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

        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveEmailAddress,userName);

        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String useName) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, "Rent.Inc", "UTF-8"));

        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "Dear xx(user)", "UTF-8"));

        message.setSubject("Your Rent.Inc signup verification is in progres", "UTF-8");

        int min = 100000;
        int max = 999999;
        int pinCode = min + (int)(Math.random() * (max-min+1));

        String content = "<div><img src=\""  + Config.logoAddress + "\"></div>"
                + "<div>Hi" + useName + ",</div>"
                + "<div>" + "Your verification code is: " + pinCode + "</div>";


        message.setContent(content, "text/html;charset=UTF-8");
//        message.setContent("<h1>This is actual message</h1>", "text/html" ); // 发送 HTML 消息, 可以插入html标签
        StatusContainer.currentPinCode = String.valueOf(pinCode);
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }

    public static void sendVerificationEmail(String receiveEmailAddress,String uerName) throws Exception {
        SendEmail.send("yuenci1575270674@gmail.com","Innis");
    }

}
