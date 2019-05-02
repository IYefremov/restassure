package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectEnvironmentPopup extends iOSHDBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "EnvironmentSelection")
    private IOSElement envselectiontable;
	
	public SelectEnvironmentPopup() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("EnvironmentSelection"))); 
	}
	
	public LoginScreen selectEnvironment(String environmentName) {
		envselectiontable.findElementByAccessibilityId(environmentName).click();
		return new LoginScreen();
	}

}
