package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class MonitorScreen {
    protected WebDriver webDriver;

    public MonitorScreen() {
        this.webDriver = DriverBuilder.getInstance().getAppiumDriver();
        PageFactory.initElements(DriverBuilder.getInstance().getAppiumDriver(), this);
    }
}
