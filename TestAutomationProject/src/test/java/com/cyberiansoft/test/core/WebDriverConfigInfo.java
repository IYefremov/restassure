package com.cyberiansoft.test.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class WebDriverConfigInfo {
    private static WebDriverConfigInfo _instance = null;

    private Properties props = null;

    private WebDriverConfigInfo() {
        props = new Properties();
        File file =
                new File("src/test/java/com/cyberiansoft/test/core/webdriver.config");
        try {
            FileInputStream fileInput = new FileInputStream(file);
            props.load(fileInput);
            fileInput.close();
        }
        catch (IOException e) {
            System.out.println("Can't load WebDriver properties");
        }
    }

    public synchronized static WebDriverConfigInfo getInstance() {
        if (_instance == null)
            _instance = new WebDriverConfigInfo();
        return _instance;
    }


    public String getChromeVersion() {
        return Optional.ofNullable(getSystemProperty("chrome.version")).orElse("");
    }

    public String getDefaultBrowser() {
        return getSystemProperty("browser");
    }

    public String getAzureURL() {
        return getSystemProperty("azure.url");
    }

    public String getSystemProperty(String property) {
        Optional.ofNullable(System.getProperty(property))
                .filter(option -> !option.isEmpty())
                .ifPresent(option -> props.setProperty(property, option));
        return props.getProperty(property);
    }
}
