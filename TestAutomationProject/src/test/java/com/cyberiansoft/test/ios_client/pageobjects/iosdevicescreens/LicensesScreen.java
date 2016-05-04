package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class LicensesScreen extends iOSHDBaseScreen {
	
	public LicensesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Add\"]")
    private IOSElement addinspbtn;
	
	
	public void clickAddLicenseButton() {
		addinspbtn.click();
	}
	
	public void clickAddLicenseButtonAndAcceptAlert() {
		clickAddLicenseButton();
		String alerttext = Helpers.getAlertTextAndAccept();
		Assert.assertEquals(alerttext, "Would you like to verify your license?");
	}

}
