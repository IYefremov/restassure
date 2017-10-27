package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;

public class RegularSelectEnvironmentScreen extends iOSRegularBaseScreen{
	
	public RegularSelectEnvironmentScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), RegularSelectEnvironmentScreen.class);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Environments"))); 
	}
	
	public LoginScreen selectEnvironment(String environmentName) {
		appiumdriver.findElementByAccessibilityId(environmentName).click();
		return new LoginScreen(appiumdriver);
	}

}
