package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public enum ChromeDriverProvider {
    INSTANCE;

    private WebDriver chromeWebDriver;
    private WebDriver chromeMobileWebDriver;

    //todo: refactor!!!!
    @SneakyThrows
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

    @SneakyThrows
    public WebDriver getMobileChromeDriver()  {
        if (BaseTestClass.getBrowserType().equals(BrowserType.CHROME)) {

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

        } else {
            if (chromeMobileWebDriver == null || ((RemoteWebDriver) chromeMobileWebDriver).getSessionId() == null) {
                WebDriverManager.chromedriver().setup();

                Map<String, Object> deviceMetrics = new HashMap<>();
                deviceMetrics.put("width", 360);
                deviceMetrics.put("height", 640);
                deviceMetrics.put("pixelRatio", 3.0);

                Map<String, Object> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceMetrics", deviceMetrics);
                mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

                ChromeOptions selenoidChromeOptions = new ChromeOptions();
                selenoidChromeOptions.setCapability("disable-user-media-security", true);
                selenoidChromeOptions.addArguments("--unlimited-storage");
                selenoidChromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_setting_values.notifications", 1);
                selenoidChromeOptions.setExperimentalOption("prefs", prefs);
                selenoidChromeOptions.addArguments("--window-size=1800,1000");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setVersion("78.0");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);
                capabilities.setCapability("sessionTimeout", "2m");
                capabilities.setCapability("name", "SessionName");
                capabilities.setCapability(ChromeOptions.CAPABILITY, selenoidChromeOptions);
                //chromeOptions.addArguments("--user-data-dir=C:/AutoProfile1");
                chromeMobileWebDriver = new RemoteWebDriver(
                        URI.create("http://aqc-linux2.westus.cloudapp.azure.com:4444/wd/hub").toURL(),
                        capabilities
                );
            }
        }
        chromeMobileWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return chromeMobileWebDriver;
    }

}
