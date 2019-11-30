package com.cyberiansoft.test.vnextbo.utils;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverUtils {

    public static void webdriverGotoWebPage(String url) {
        WebDriver driver;
        try {
            driver = DriverBuilder.getInstance().getDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        } catch (Exception ignored) {
            BrowserType browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
            DriverBuilder.getInstance().setDriver(browserType);
            driver = DriverBuilder.getInstance().getDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        }
        driver.get(url);
        if (DriverBuilder.getInstance().getBrowser().equals("ie")) {
            if (driver.findElements(By.id("overridelink")).size() > 0) {
                driver.navigate().to("javascript:document.getElementById('overridelink').click()");
            }
        }
    }
}
