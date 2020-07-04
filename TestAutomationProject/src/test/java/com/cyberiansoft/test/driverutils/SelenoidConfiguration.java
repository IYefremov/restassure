package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.core.WebDriverConfigInfo;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SelenoidConfiguration {

    public DesiredCapabilities getCapabilities(ChromeOptions selenoidChromeOptions) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion(WebDriverConfigInfo.getInstance().getChromeVersion());
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        capabilities.setCapability("sessionTimeout", "8m");
        capabilities.setCapability("name", "SessionName");
        capabilities.setCapability(ChromeOptions.CAPABILITY, selenoidChromeOptions);
        return capabilities;
    }
}
