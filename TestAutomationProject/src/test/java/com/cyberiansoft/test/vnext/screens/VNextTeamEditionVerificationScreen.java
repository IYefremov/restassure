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

public class VNextTeamEditionVerificationScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@class='verification-form']")
	private WebElement phonevereficationform;
	
	@FindBy(xpath="//input[@data-autotests-id='reg-code-1']")
	private WebElement regfld1;
	
	@FindBy(xpath="//input[@data-autotests-id='reg-code-2']")
	private WebElement regfld2;
	
	@FindBy(xpath="//input[@data-autotests-id='reg-code-3']")
	private WebElement regfld3;
	
	@FindBy(xpath="//a[@action='verify']")
	private WebElement verifyBtn;
	
	public VNextTeamEditionVerificationScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(verifyBtn));
	}
	
	public void setDeviceRegistrationCode(String regCode) {
		setValue(regfld1, regCode.substring(0, 4));
		setValue(regfld2, regCode.substring(5, 9));
		setValue(regfld3, regCode.substring(10, 14));
		//switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		//appiumdriver.hideKeyboard();
	    //switchToWebViewContext();
		log(LogStatus.INFO, "Set registration code: " + regCode);
	}
	
	public void clickVerifyButton() {
		tap(verifyBtn);
		log(LogStatus.INFO, "Tap Verify button");
	}

}
