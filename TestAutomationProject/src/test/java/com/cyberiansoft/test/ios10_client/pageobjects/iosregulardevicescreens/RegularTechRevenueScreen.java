package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class RegularTechRevenueScreen extends iOSRegularBaseScreen {
	
	public RegularTechRevenueScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public boolean isTechIsPresentInReport(String techname) {
		return appiumdriver.findElementsByAccessibilityId(techname).size() > 0;
	}

}
