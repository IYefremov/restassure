package com.cyberiansoft.test.vnextbo.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverUtils {
	
	public static void webdriverGotoWebPage(String url) {
        final WebDriver driver = DriverBuilder.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(url);
		if (DriverBuilder.getInstance().getBrowser().equals("ie")) {
			if (driver.findElements(By.id("overridelink")).size() > 0) {
				driver.navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		}
	}
}
