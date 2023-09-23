package EncryptDecryptImpl;

import CustomException.HandleCustomException;
import EcnryptDecrypt.Engine;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

public class EngineImplement implements Engine {
    private String path;
    private Properties properties;
    public EngineImplement(String path) throws IOException {
        this.path = path;
        this.properties = readPropertiesFile(path);
    }
    private Properties readPropertiesFile(String path) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(fileInputStream!=null)
                fileInputStream.close();
        }

        return properties;
    }

    @Override
    public void encryptProperties(String key) throws NoSuchAlgorithmException, IOException, HandleCustomException {
        SecretKey secretKey = generateSecretKey("4fa7b23464f82a138b75599fe0361b25");
        FileOutputStream fileOutputStream = null;
            String value = this.properties.getProperty(key);
            if(value==null){
                throw new HandleCustomException("Given" + key + " not found in properties file");
            }
            if(!isEncrypted(value)){
                byte[] encryptByte = encrypt(secretKey, value.getBytes(StandardCharsets.UTF_8));
                String encryptedValue = "<7>" + Base64.getEncoder().encodeToString(encryptByte);
                this.properties.setProperty(key, encryptedValue);
            }
            else{
               // throw new HandleCustomException(key + " value is already encrypted");
            }
            try{
                fileOutputStream = new FileOutputStream(path);
                properties.store(fileOutputStream, "Encrypted Value");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                if(fileOutputStream!=null)
                    fileOutputStream.close();
            }

    }

    @Override
    public String decryptProperties(String key) throws NoSuchAlgorithmException, HandleCustomException {
        String value = this.properties.getProperty(key);
        if(value==null){
            throw new HandleCustomException("Given" + key + " not found in properties file");
        }
        SecretKey secretKey = generateSecretKey("4fa7b23464f82a138b75599fe0361b25");
        if(isEncrypted(value)){
            String newString = value.substring(3, value.length());
            byte[] encryptedByte = Base64.getDecoder().decode(newString);
            String decryptedValue = new String(decrypt(secretKey, encryptedByte));
            return decryptedValue;
        }
        else{
            //throw new HandleCustomException(key + " value is already decrypted");
        }
        return null;
    }

    private  SecretKey generateSecretKey(String key) throws NoSuchAlgorithmException {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "AES");
    }

    private  byte[] encrypt(SecretKey secretKey, byte[] original){
        byte[] encryptedByte = null;

        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedByte = cipher.doFinal(original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedByte;
    }

    private  byte[] decrypt(SecretKey secretKey, byte[] encryptedByte) {
        byte[] decryptedByte = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedByte = cipher.doFinal(encryptedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedByte;
    }


    private boolean isEncrypted(String value){
        if(value.length()>3){
            String sub = value.substring(0, 3);
            if(sub.equals("<7>"))
                return true;
        }
        return false;
    }
}


