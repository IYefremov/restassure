package com.cyberiansoft.test.driverutils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public enum ChromeDriverProvider {
    INSTANCE;

    private WebDriver chromeWebDriver;
    private WebDriver chromeMobileWebDriver;

    public WebDriver getChromeWebDriver() {
        if (chromeWebDriver == null || ((ChromeDriver) chromeMobileWebDriver).getSessionId() == null) {
            WebDriverManager.chromedriver().setup();
            chromeWebDriver = new ChromeDriver();
        } else if (((ChromeDriver) chromeWebDriver).getSessionId() == null) {
            WebDriverManager.chromedriver().setup();
            chromeWebDriver = new ChromeDriver();
        }

        return chromeWebDriver;
    }

    public WebDriver getMobileChromeDriver() {
        if (chromeMobileWebDriver == null || ((ChromeDriver) chromeMobileWebDriver).getSessionId() == null) {
            WebDriverManager.chromedriver().setup();

            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", 360);
            deviceMetrics.put("height", 640);
            deviceMetrics.put("pixelRatio", 3.0);

            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("disable-user-media-security", true);
            chromeOptions.addArguments("--unlimited-storage");
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            //chromeOptions.addArguments("--user-data-dir=C:/AutoProfile1");
            chromeMobileWebDriver = new ChromeDriver(chromeOptions);
        }
        chromeMobileWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return chromeMobileWebDriver;
    }
};
