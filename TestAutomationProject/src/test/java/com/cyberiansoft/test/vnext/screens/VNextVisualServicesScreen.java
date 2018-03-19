package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextVisualServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='visual/services']")
	private WebElement visualservicesscreen;
	
	public VNextVisualServicesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(visualservicesscreen));
	}
	
	public VNextVisualScreen selectCustomService(String servicename) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(visualservicesscreen.findElement(By.xpath(".//div[@class='service']/div[contains(text(), '" + servicename + "')]"))));
		tap(visualservicesscreen.findElement(By.xpath(".//div[@class='service']/div[contains(text(), '" + servicename + "')]")));
		log(LogStatus.INFO, "Tap Custom Service: " + servicename);
		return new VNextVisualScreen(appiumdriver);
	}

}
