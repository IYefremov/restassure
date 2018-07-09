package com.cyberiansoft.test.ibs.config;

import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class IBSConfigInfo {

    private static IBSConfigInfo instance = null;

    private Properties properties;

    private IBSConfigInfo() {
        properties = new Properties();
        File file = new File("src/test/java/com/cyberiansoft/test/ibs/config/ibs.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            Assert.fail("Can't load IBS environment properties", e);
        }
    }

    public synchronized static IBSConfigInfo getInstance() {
        if (instance == null)
            instance = new IBSConfigInfo();
        return instance;
    }

    public String getIbsUrl() {
        return properties.getProperty("ibs.url");
    }

    public String getIbsBoUrl() {
        return properties.getProperty("ibs-bo.url");
    }

    public String getReconProDevicesUrl() {
        return properties.getProperty("reconPro.devices.url");
    }

    public String getUserName() {
        return properties.getProperty("user.name");
    }

    public String getIbsBoName() {
        return properties.getProperty("ibs-bo.name");
    }

    public String getIbsBoPassword() {
        return properties.getProperty("ibs-bo.password");
    }

    public String getUserEmail() {
        return properties.getProperty("user.mail");
    }
    public String getUserPassword() {
        return properties.getProperty("user.password");
    }

    public String getDefaultBrowser() {
        return properties.getProperty("default.browser");
    }
}