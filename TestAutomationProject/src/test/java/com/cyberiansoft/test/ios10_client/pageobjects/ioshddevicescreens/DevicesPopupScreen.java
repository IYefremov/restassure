package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class DevicesPopupScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "Assign")
    private IOSElement assignlbtn;
	
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	
	public DevicesPopupScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public boolean isAssignButtonDisplayed() {
		Helpers.waitABit(500);
		return appiumdriver.findElementsByAccessibilityId("Assign").size() > 0;
	}

}
