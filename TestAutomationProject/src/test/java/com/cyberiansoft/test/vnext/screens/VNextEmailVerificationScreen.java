package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextEmailVerificationScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@data-page='email-verification']")
	private WebElement mailverificationscreen;
	
	@FindBy(xpath="//*[@action='activate']")
	private WebElement activatebtn;
	
	public VNextEmailVerificationScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(mailverificationscreen));
	}

	public VNextStatusScreen clickActivateButton() {
		tap(activatebtn);
		log(LogStatus.INFO, "Click Activate Back Office button");
		VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
		informationdialog.clickInformationDialogOKButton();
		return new VNextStatusScreen(appiumdriver);
	}
}
