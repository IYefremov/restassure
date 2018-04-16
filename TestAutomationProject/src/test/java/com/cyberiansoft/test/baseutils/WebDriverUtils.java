package com.cyberiansoft.test.baseutils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

public class WebDriverUtils {
	
	public static void webdriverGotoWebPage(String url) {
	    try {
            DriverBuilder.getInstance().getDriver().manage().window().maximize();
        } catch (WebDriverException e) {
            System.err.println("The window maximize exception:\n" + e);
        }
		DriverBuilder.getInstance().getDriver().get(url);
		if (DriverBuilder.getInstance().getBrowser().equals("ie")) {
			if (DriverBuilder.getInstance().getDriver().findElements(By.id("overridelink")).size() > 0) {
				DriverBuilder.getInstance().getDriver().navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		}
	}
}
