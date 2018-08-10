package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	public void setEmailAddress(String emailBox) {
		appiumdriver.findElement(By.id("verificationEmail")).sendKeys(emailBox);
	}

	public VNextStatusScreen clickActivateButton() {
		tap(activatebtn);
		VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
		informationdialog.clickInformationDialogOKButton();
		return new VNextStatusScreen(appiumdriver);
	}
}
