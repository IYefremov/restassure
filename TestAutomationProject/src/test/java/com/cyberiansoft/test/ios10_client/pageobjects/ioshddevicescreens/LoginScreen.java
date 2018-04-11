package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "CodeField1")
    private IOSElement firstcode;
	
	@iOSFindBy(accessibility = "CodeField2")
    private IOSElement secondcode;
	
	@iOSFindBy(accessibility = "CodeField3")
    private IOSElement thirdcode;
	
	@iOSFindBy(accessibility = "Verify")
    private IOSElement registerbtn;
	
	@iOSFindBy(accessibility = "Don't Allow")
    private IOSElement dontallowbtn;
	
	public LoginScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void assertRegisterButtonIsValidCaption() {
		/*if (dontallowbtn.isDisplayed()) {
			dontallowbtn.click();
		}*/
		Assert.assertEquals("Verify", registerbtn.getAttribute("name"));
	}

	public void registeriOSDevice(String regCode)
			throws InterruptedException {
		
		appiumdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//firstcode.setValue(regCode.substring(0, 4));
		TouchAction action = new TouchAction(appiumdriver);
		action.press(firstcode).waitAction(Duration.ofSeconds(1)).release().perform();
		firstcode.click();
		firstcode.sendKeys(regCode.substring(0, 4));
		secondcode.sendKeys(regCode.substring(5, 9));
		thirdcode.sendKeys(regCode.substring(10, 14));
		/*firstcode.setValue(regCode.substring(0, 4));
		secondcode.setValue(regCode.substring(5, 9));
		thirdcode.setValue(regCode.substring(10, 14));*/
		//thirdcode.click();
		action = new TouchAction(appiumdriver);
		action.press(registerbtn).waitAction(Duration.ofSeconds(1)).release().perform();	
		//registerbtn.click();
		
		appiumdriver.manage().timeouts().implicitlyWait(60*9, TimeUnit.SECONDS);
		appiumdriver.findElementByAccessibilityId("UpdateMainDatabaseButton");
		//Helpers.element(MobileBy.name("UpdateMainDatabaseButton"));
		
	}

}
