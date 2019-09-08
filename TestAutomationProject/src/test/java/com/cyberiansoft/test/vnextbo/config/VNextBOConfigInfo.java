package com.cyberiansoft.test.vnextbo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class VNextBOConfigInfo {

    private static VNextBOConfigInfo instance = null;

    private Properties properties;

    private VNextBOConfigInfo() {
        properties = new Properties();
        File file = new File("src/test/java/com/cyberiansoft/test/vnextbo/config/nextbo.properties");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            System.out.println("Can't load VNext environment properties");
            e.printStackTrace();
        }
    }

    public synchronized static VNextBOConfigInfo getInstance() {
        if (instance == null)
            instance = new VNextBOConfigInfo();
        return instance;
    }

    public String getVNextBOURL() {
        return properties.getProperty("vnextbo.url");
    }

    public String getVNextBOCompanionappURL() {
        return properties.getProperty("vnextbo.companionapp.url");
    }

    public String getVNextBOCompanionappQCURL() {
        return properties.getProperty("vnextbo.companionapp.qc.url");
    }

    public String getVNextBOMail() {
        return properties.getProperty("vnextbo.mail");
    }

    public String getVNextBONadaMail() {
        return properties.getProperty("vnextbo.nada.mail");
    }

    public String getVNextBOPassword() {
        return properties.getProperty("vnextbo.password");
    }

    public String getVNextR360URL() {
        return properties.getProperty("vnextr360.url");
    }

    public String getVNextR360Mail() {
        return properties.getProperty("vnextr360.mail");
    }

    public String getVNextR360Password() {
        return properties.getProperty("vnextr360.password");
    }

    public String getBOoldURL() {
        return properties.getProperty("backofficeold.url");
    }

    public String getDefaultBrowser() {
        return checkForSystemProperty("browser");
    }

    public String getAzureURL() {
        return checkForSystemProperty("azure.url");
    }

    private String checkForSystemProperty(String property) {
        try {
            if (!System.getProperty(property).isEmpty()) {
                properties.setProperty(property, System.getProperty(property));
            }
        } catch (NullPointerException ignored) {}
        return properties.getProperty(property);
    }
}