package com.cyberiansoft.test.driverutils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SelenoidConfiguration {

    public DesiredCapabilities getCapabilities(ChromeOptions selenoidChromeOptions) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion(WebDriverManager.chromedriver().getDownloadedVersion().substring(0, 4));
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("sessionTimeout", "2m");
        capabilities.setCapability("name", "SessionName");
        capabilities.setCapability(ChromeOptions.CAPABILITY, selenoidChromeOptions);
        return capabilities;
    }
}
