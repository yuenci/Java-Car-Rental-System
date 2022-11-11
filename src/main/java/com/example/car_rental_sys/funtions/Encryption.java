package com.example.car_rental_sys.funtions;

import com.example.car_rental_sys.ConfigFile;
import com.example.car_rental_sys.Tools;
import org.apache.commons.codec.digest.DigestUtils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;



public class Encryption {
    public  static String med5Encrypt(String str){
        return  DigestUtils.md5Hex(str);
    }

    public static String AESEncrypt( String secret,  String data) {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return java.util.Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while encrypting data", e);
        }

    }

    public static String AESDecrypt( String secret, String encryptedString) {

        byte[] decodedKey = java.util.Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occurred while decrypting data", e);
        }
    }

    // String[] encryptFiles = ConfigFile.sensitiveDataFileList
    public static boolean dataFileEncrypt(String fileName){
        try{
            String path= ConfigFile.dataFilesRootPath + fileName;
            String fileContent = FileOperate.readFileToString(path);
            System.out.println(fileContent);

            String decryptedContent = AESEncrypt(ConfigFile.AESKey, fileContent);
            FileOperate.rewriteFile(path + ".secret",decryptedContent);
        }catch (Exception e){
            Tools.logError(e);
            return false;
        }
        return true;
    }

    public static boolean dataFileDecrypt(String fileName){
        try {
            String path= ConfigFile.dataFilesRootPath + fileName  + ".secret";
            String fileContent = FileOperate.readFileToString(path);
            fileContent = fileContent.replace("\n","");
            System.out.println(fileContent);

            String decryptedContent = AESDecrypt(ConfigFile.AESKey, fileContent);
            System.out.println(decryptedContent);
            //FileOperate.rewriteFile(path + ".txt",decryptedContent);
            FileOperate.rewriteFile(path.replace( ".secret",""),decryptedContent);
        }catch (Exception e){
            Tools.logError(e);
            return false;
        }
        return true;
    }
}



