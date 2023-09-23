### Motivation
Whenever we are working with any java project, we make a properties file to keep data like database password, host details etc,
sometime these types of data become sensitive, so to ensure the safety of data I have created this project.

## JAR to Encrypt Porperties file in java project
#### Jar file is inside out directory `out/artifacts/EncryptDecrypt_jar`
#### How to used
```java
// At first add EncryptDecrypt jar in your project lib
// make a properties file and add data to it

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
// This will encrypt the given key value

```
