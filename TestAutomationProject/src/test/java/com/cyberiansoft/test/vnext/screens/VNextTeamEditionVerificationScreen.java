package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.utils.ControlUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextTeamEditionVerificationScreen extends VNextBaseScreen {

	private static final String XPATH_REG_COMMON = "//*[@data-autotests-id='reg-code']";

	@FindBy(xpath="//*[@class='verification-form']")
	private WebElement phonevereficationform;
	
	@FindBy(xpath="//*[@data-autotests-id='reg-code-1']")
	private WebElement regfld1;
	
	@FindBy(xpath="//*[@data-autotests-id='reg-code-2']")
	private WebElement regfld2;
	
	@FindBy(xpath="//*[@data-autotests-id='reg-code-3']")
	private WebElement regfld3;

	@FindBy(xpath=XPATH_REG_COMMON)
	private WebElement tbRegComomn;
	
	@FindBy(xpath="//*[@action='verify']")
	private WebElement verifyBtn;

    public VNextTeamEditionVerificationScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
	}
	
	public void setDeviceRegistrationCode(String regCode) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_REG_COMMON)));
        ControlUtils.setValue(tbRegComomn, regCode);
	}
	
	public void clickVerifyButton() {
		tap(verifyBtn);
	}

}
