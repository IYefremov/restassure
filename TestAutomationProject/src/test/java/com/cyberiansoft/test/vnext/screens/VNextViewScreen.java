package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VNextViewScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='view']")
	private WebElement viewscreen;
	
	public VNextViewScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, Duration.ofSeconds(10)), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(viewscreen));
		if (checkHelpPopupPresence()) {
			wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.elementToBeClickable (viewscreen.findElement(By.xpath(".//div[@class='help-button' and text()='OK, got it']"))));
			tap(viewscreen.findElement(By.xpath(".//div[@class='help-button' and text()='OK, got it']")));

		}
	}

}
