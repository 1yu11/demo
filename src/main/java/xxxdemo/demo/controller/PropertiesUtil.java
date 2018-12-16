package xxxdemo.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static String host;
    public static String clientid;
    public static String username;
    public static String password;
    public static int timeout;
    public static int keepalive;



    static {
        host = loadMqttProperties().getProperty("host");
        clientid = loadMqttProperties().getProperty("clientid");
        username = loadMqttProperties().getProperty("username");
        password = loadMqttProperties().getProperty("password");
        timeout = Integer.valueOf(loadMqttProperties().getProperty("timeout"));
        keepalive = Integer.valueOf(loadMqttProperties().getProperty("keepalive"));

    }


    private static Properties loadMqttProperties() {
        InputStream inputstream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
      //  InputStream inputstream = PropertiesUtil.class.getResourceAsStream("src/main/resources/conf.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputstream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputstream != null) {
                    inputstream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    }



