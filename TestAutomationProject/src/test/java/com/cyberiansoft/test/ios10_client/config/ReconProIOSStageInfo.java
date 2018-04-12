package com.cyberiansoft.test.ios10_client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReconProIOSStageInfo {

    private static ReconProIOSStageInfo _instance = null;

    private Properties props = null;

    private ReconProIOSStageInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/ios10_client/config/reconproiosstsgeenvironment.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static ReconProIOSStageInfo getInstance() {
        if (_instance == null)
            _instance = new ReconProIOSStageInfo();
        return _instance;
    }

    public String getBackOfficeStageURL() {
        return props.getProperty("backofficestage.url");
    }

    public String getUserStageMail() {
        return props.getProperty("userstage.mail");
    }

    public String getUserStageUserName() {
        return props.getProperty("userstage.name");
    }

    public String getUserStageUserPassword() {
        return props.getProperty("userstage.password");
    }
}
