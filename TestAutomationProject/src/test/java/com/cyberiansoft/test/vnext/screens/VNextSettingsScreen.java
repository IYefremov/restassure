package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextSettingsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//input[@action='manual-update']")
	private WebElement manualsendradio;
	
	@FindBy(xpath="//a[@class='link icon-only back']")
	private WebElement backbtn;
	
	public VNextSettingsScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(manualsendradio));
	}
	
	public VNextSettingsScreen setManualSendOn() {
		if (manualsendradio.getAttribute("checked") == null)
			tap(manualsendradio);
		testReporter.log(LogStatus.INFO, "Set Manual Send to ON");
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public VNextSettingsScreen setManualSendOff() {
		if (manualsendradio.getAttribute("checked") != null)
			tap(manualsendradio);
		testReporter.log(LogStatus.INFO, "Set Manual Send to ON");
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		testReporter.log(LogStatus.INFO, "Tap Back button");
		return new VNextHomeScreen(appiumdriver);
	}

}
