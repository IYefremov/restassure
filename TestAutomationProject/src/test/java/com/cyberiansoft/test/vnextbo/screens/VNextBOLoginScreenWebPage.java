package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOLoginScreenWebPage extends VNextBOBaseWebPage {

	@FindBy(xpath = "//div[@class='loginForm']")
	private WebElement loginForm;
	
	@FindBy(id = "email")
	private TextField emailField;
	
	@FindBy(id = "password")
	private TextField passwordField;
	
	@FindBy(xpath = "//input[@value='Login']")
	private WebElement loginButton;
	
	@FindBy(xpath = "//a[text()='Forgot password?']")
	private WebElement forgotPasswordLink;

	@FindBy(xpath = "//a[text()='Terms and Conditions']")
	private WebElement termsAndConditionsLink;

	@FindBy(xpath = "//span[@data-bind='text: email.errorText']")
	private WebElement emailErrorText;

	@FindBy(xpath = "//span[@data-bind='text: password.errorText']")
	private WebElement passwordErrorText;
	
	public VNextBOLoginScreenWebPage() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
}
