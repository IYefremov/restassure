package com.cyberiansoft.test.driverutils;

import org.openqa.selenium.WebDriver;

import com.cyberiansoft.test.core.BrowserType;

public class WebdriverInicializator {
	
	private static WebdriverInicializator instance = null;
	
	private WebdriverInicializator() {
	}
	
	public static WebdriverInicializator getInstance() {
        if ( instance == null ) {
            instance = new WebdriverInicializator();
        }

        return instance;
    }
	
	public WebDriver initWebDriver(BrowserType browserType) {
		DriverBuilder.getInstance().setDriver(browserType);
		return DriverBuilder.getInstance().getDriver();
		}

}
