package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularSelectEnvironmentScreen extends iOSRegularBaseScreen {

	@iOSXCUITFindBy(accessibility = "Select Environment")
	private IOSElement environmentspopup;
	
	public RegularSelectEnvironmentScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public LoginScreen selectEnvironment(String environmentName) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 25);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Select Environment")));
		appiumdriver.findElementByAccessibilityId(environmentName).click();
		return new LoginScreen();
	}

}
