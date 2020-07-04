package com.cyberiansoft.test.driverutils;

import com.cyberiansoft.test.core.BrowserType;
import org.openqa.selenium.WebDriver;

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
		DriverBuilder.getInstance().setBrowserType(browserType).setDriver();
		return DriverBuilder.getInstance().getDriver();
		}

}
