package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class DevicesPopupScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(uiAutomator = ".popover().navigationBar().buttons()[\"Assign\"]")
    private IOSElement assignlbtn;
	
	
	@iOSFindBy(uiAutomator = ".popover().navigationBar().buttons()[\"Cancel\"]")
    private IOSElement cancelbtn;
	
	
	public DevicesPopupScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickCancelButton() {
		cancelbtn.click();
	}
	
	public boolean isAssignButtonDisplayed() {
		return assignlbtn.isDisplayed();
	}

}
