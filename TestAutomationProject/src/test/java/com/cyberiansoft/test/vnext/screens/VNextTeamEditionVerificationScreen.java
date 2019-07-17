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

public class VNextTeamEditionVerificationScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//*[@class='verification-form']")
	private WebElement phonevereficationform;
	
	@FindBy(xpath="//*[@data-autotests-id='reg-code-1']")
	private WebElement regfld1;
	
	@FindBy(xpath="//*[@data-autotests-id='reg-code-2']")
	private WebElement regfld2;
	
	@FindBy(xpath="//*[@data-autotests-id='reg-code-3']")
	private WebElement regfld3;
	
	@FindBy(xpath="//*[@action='verify']")
	private WebElement verifyBtn;
	
	public VNextTeamEditionVerificationScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void setDeviceRegistrationCode(String regCode) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-autotests-id='reg-code-1']")));
		setValue(regfld1, regCode.substring(0, 4));
		setValue(regfld2, regCode.substring(5, 9));
		setValue(regfld3, regCode.substring(10, 14));
	}
	
	public void clickVerifyButton() {
		tap(verifyBtn);
	}

}
