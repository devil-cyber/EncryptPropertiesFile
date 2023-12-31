import CustomException.HandleCustomException;
import EcnryptDecrypt.Engine;
import EncryptDecryptImpl.EngineImplement;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;


public class Main{
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, HandleCustomException {
        Engine engine = new EngineImplement("/root/EncryptDecrypt/resource/input.properties");
        engine.encryptProperties("password");
        engine.encryptProperties("ip");
        String name = engine.decryptProperties("name");
        String password = engine.decryptProperties("password");
        String ip = engine.decryptProperties("ip");

        System.out.println(name + password + ip);
    }


}