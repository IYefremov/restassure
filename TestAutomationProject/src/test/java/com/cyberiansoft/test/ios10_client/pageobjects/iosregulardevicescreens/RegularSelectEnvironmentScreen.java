package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularSelectEnvironmentScreen extends iOSRegularBaseScreen {

	@iOSXCUITFindBy(accessibility = "Select Environment")
	private IOSElement environmentspopup;
	
	public RegularSelectEnvironmentScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 25);
		wait.until(ExpectedConditions.visibilityOf(environmentspopup));
	}
	
	public LoginScreen selectEnvironment(String environmentName) {
		appiumdriver.findElementByAccessibilityId(environmentName).click();
		return new LoginScreen();
	}

}
