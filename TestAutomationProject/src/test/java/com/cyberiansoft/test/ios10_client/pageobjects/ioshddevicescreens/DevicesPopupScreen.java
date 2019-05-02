package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class DevicesPopupScreen extends iOSHDBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "Assign")
    private IOSElement assignlbtn;
	
	
	@iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	
	public DevicesPopupScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
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
