package com.cyberiansoft.test.baseutils;

import org.openqa.selenium.By;

import com.cyberiansoft.test.driverutils.DriverBuilder;

public class WebDriverUtils {
	
	public static void webdriverGotoWebPage(String url) {
		DriverBuilder.getInstance().getDriver().manage().window().maximize();
		DriverBuilder.getInstance().getDriver().get(url);
		if (DriverBuilder.getInstance().getBrowser().equals("ie")) {
			if (DriverBuilder.getInstance().getDriver().findElements(By.id("overridelink")).size() > 0) {
				DriverBuilder.getInstance().getDriver().navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		}
	}

}
