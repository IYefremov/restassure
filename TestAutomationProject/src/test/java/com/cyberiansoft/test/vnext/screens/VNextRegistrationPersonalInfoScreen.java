package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextRegistrationPersonalInfoScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//select[@data-template='personal-info-country-phone-code-template']")
	private WebElement phonenumberselect;
	
	@FindBy(id="personal-info-phone")
	private WebElement phonenumberfld;
	
	@FindBy(xpath="//input[@type='email']")
	private WebElement emailfld;
	
	@FindBy(xpath="//a[contains(@data-bind, 'navigateNext')]/span/i")
	private WebElement donebtn;
	
	public VNextRegistrationPersonalInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		System.out.println("++++++");
		//VNextRegistrationPersonalInfoScreen.WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		//wait.until(ExpectedConditions. visibilityOf(phonenumberselect));
	}
	
	public void setUserRegistrationInfo(String countrycode, String phonenumber, String usermail) {
		selectPhoneNumberCountryCode(countrycode);
		setPhoneNumber(phonenumber);
		setEmail(usermail);
		clickDoneButton();
	}
	
	public void selectPhoneNumberCountryCode(String countrycode) {
		Select sel = new Select(phonenumberselect);
		sel.selectByValue(countrycode);
	}
	
	public void setPhoneNumber(String phonenumber) {
		phonenumberfld.clear();
		phonenumberfld.sendKeys(phonenumber);
	}
	
	public void setEmail(String usermail) {
		emailfld.clear();
		emailfld.sendKeys(usermail);
	}
	
	public void clickDoneButton() {
		tap(donebtn);
	}
}
