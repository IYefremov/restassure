package com.cyberiansoft.test.targetprocessintegration.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TargetProcessConfig {

    private static TargetProcessConfig _instance = null;

    private Properties props = null;

    private TargetProcessConfig() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/targetprocessintegration/config/targetprocessconfig.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load target process data properties");
            e.printStackTrace();
        }
    }

    public synchronized static TargetProcessConfig getInstance() {
        if (_instance == null)
            _instance = new TargetProcessConfig();
        return _instance;
    }

    public String getTargetProcessToken() { return props.getProperty("tp.token"); }
}
