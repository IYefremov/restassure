package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LicensesScreen extends iOSHDBaseScreen {
	
	public LicensesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@iOSXCUITFindBy(accessibility = "Add")
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
