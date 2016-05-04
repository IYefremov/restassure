package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class ApproveSummaryPopup extends iOSHDBaseScreen {
	
	@iOSFindBy(uiAutomator =".popover().navigationBar().buttons()[\"Approve\"]")
    private IOSElement approvebtn;
	
	public ApproveSummaryPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickApproveButton() {
		approvebtn.click();
	}

}
