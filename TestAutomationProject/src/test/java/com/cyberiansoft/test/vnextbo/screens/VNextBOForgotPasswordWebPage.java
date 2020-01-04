package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOForgotPasswordWebPage extends VNextBOBaseWebPage {

	@FindBy(id = "confirmEmail")
	private WebElement confirmMailFld;
	
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement submitBtn;
	
	@FindBy(xpath = "//button[@class='btn btn-autofocus' and text()='OK']")
	private WebElement alertOKBtn;

	@FindBy(id = "loginLogin")
	private WebElement loginLink;

	@FindBy(xpath = "//div[contains(@data-bind, 'confirmEmail.hasError')]/p")
	private WebElement errorMessage;
	
	public VNextBOForgotPasswordWebPage() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}
	
	public VNextBOLoginScreenWebPage sendConfirmationMail(String userMail) {
		setConfirmationMailFieldValue(userMail);
		clickSubmitButton();
		Utils.clickElement(alertOKBtn);

        return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}
	
	public void setConfirmationMailFieldValue(String userMail) {
		WaitUtilsWebDriver.getWait().until(ExpectedConditions.elementToBeClickable(confirmMailFld));
		Utils.clearAndType(confirmMailFld, userMail);
	}
	
	public boolean isConfirmationMailFieldDisplayed() {
		return confirmMailFld.isDisplayed();
	}

	public boolean isLoginLinkDisplayed() {
		return loginLink.isDisplayed();
	}

	public boolean isSubmitButtonDisplayed()  { return submitBtn.isDisplayed(); }
	
	public void clickSubmitButton() {
		submitBtn.click();
	}

	public void clickLoginLink() {
		loginLink.click();
	}
	
	public String getErrorMessageValue() {
		return Utils.getText(errorMessage);
	}
}
