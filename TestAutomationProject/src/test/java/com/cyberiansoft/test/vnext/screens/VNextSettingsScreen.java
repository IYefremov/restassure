package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextSettingsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//input[@action='manual-update']")
	private WebElement manualsendradio;
	
	@FindBy(xpath="//a[@class='link icon-only back']")
	private WebElement backbtn;
	
	public VNextSettingsScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(manualsendradio));
	}
	
	public VNextSettingsScreen setManualSendOn() {
		if (manualsendradio.getAttribute("checked") == null)
			tap(manualsendradio);
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public VNextSettingsScreen setManualSendOff() {
		if (manualsendradio.getAttribute("checked") != null)
			tap(manualsendradio);
		return new VNextSettingsScreen(appiumdriver);
	}
	
	public VNextHomeScreen clickBackButton() {
		tap(backbtn);
		return new VNextHomeScreen(appiumdriver);
	}

}
