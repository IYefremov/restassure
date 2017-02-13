package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class ApproveSummaryPopup extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility ="Approve")
    private IOSElement approvebtn;
	
	public ApproveSummaryPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickApproveButton() {
		approvebtn.click();
	}

}
