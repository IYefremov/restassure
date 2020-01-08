package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class MonitorScreen {
    protected WebDriver webDriver;

    public MonitorScreen() {
        this.webDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        PageFactory.initElements(webDriver, this);
    }
}
