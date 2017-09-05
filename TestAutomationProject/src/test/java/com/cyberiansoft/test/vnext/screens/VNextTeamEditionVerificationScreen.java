package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

public class VNextTeamEditionVerificationScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@class='verification-form']")
	private WebElement phonevereficationform;
	
	@FindBy(xpath="//input[@name='regName']")
	private WebElement regfld;
	
	@FindBy(xpath="//a[@action='verify']")
	private WebElement verifyBtn;
	
	public VNextTeamEditionVerificationScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(phonevereficationform));
	}
	
	public void setDeviceRegistrationCode(String regcode) {
		setValue(regfld, regcode);
		//switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		//appiumdriver.hideKeyboard();
	    //switchToWebViewContext();
		log(LogStatus.INFO, "Set registration code: " + regcode);
	}
	
	public void clickVerifyButton() {
		tap(verifyBtn);
		log(LogStatus.INFO, "Tap Verify button");
	}

}
