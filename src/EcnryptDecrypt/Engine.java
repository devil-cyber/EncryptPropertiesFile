package EcnryptDecrypt;

import CustomException.HandleCustomException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public interface Engine {
    public void encryptProperties(String key) throws NoSuchAlgorithmException, IOException, HandleCustomException;
    public String decryptProperties(String key) throws NoSuchAlgorithmException, HandleCustomException;
}
