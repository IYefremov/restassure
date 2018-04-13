package com.cyberiansoft.test.ios10_client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DentWizardIOSInfo {

    private static DentWizardIOSInfo _instance = null;

    private Properties props = null;

    private DentWizardIOSInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/ios10_client/config/dwenvironment.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static DentWizardIOSInfo getInstance() {
        if (_instance == null)
            _instance = new DentWizardIOSInfo();
        return _instance;
    }

    public String getBackOfficeURL() {
        return props.getProperty("backoffice.url");
    }

    public String getUserMail() {
        return props.getProperty("user.mail");
    }

    public String getUserName() {
        return props.getProperty("user.name");
    }

    public String getUserPassword() {
        return props.getProperty("user.password");
    }
}
