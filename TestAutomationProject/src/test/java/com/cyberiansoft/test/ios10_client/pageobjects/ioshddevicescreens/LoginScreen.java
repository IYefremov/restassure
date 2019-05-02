package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginScreen extends iOSHDBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "CodeField1")
    private IOSElement firstcode;
	
	@iOSXCUITFindBy(accessibility = "CodeField2")
    private IOSElement secondcode;
	
	@iOSXCUITFindBy(accessibility = "CodeField3")
    private IOSElement thirdcode;
	
	@iOSXCUITFindBy(accessibility = "Verify")
    private IOSElement registerbtn;
	
	@iOSXCUITFindBy(accessibility = "Don't Allow")
    private IOSElement dontallowbtn;
	
	public LoginScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void registeriOSDevice(String regCode) {
		//firstcode.setValue(regCode.substring(0, 4));
		firstcode.click();

		firstcode.sendKeys(regCode.substring(0, 4));
		secondcode.sendKeys(regCode.substring(5, 9));
		thirdcode.sendKeys(regCode.substring(10, 14));
		/*firstcode.setValue(regCode.substring(0, 4));
		secondcode.setValue(regCode.substring(5, 9));
		thirdcode.setValue(regCode.substring(10, 14));*/
		//thirdcode.click();
		registerbtn.click();
		//registerbtn.click();
		
		appiumdriver.manage().timeouts().implicitlyWait(60*9, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 600);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("UpdateMainDatabaseButton")));
		appiumdriver.findElementByAccessibilityId("UpdateMainDatabaseButton");
		//Helpers.element(MobileBy.name("UpdateMainDatabaseButton"));
		
	}

}
