package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;


public class VNextRegistrationPersonalInfoScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//select[@data-template='personal-info-country-phone-code-template']")
	private WebElement phonenumberselect;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.firstName')]")
	private WebElement firstnamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.lastName')]")
	private WebElement lastnamefld;
	
	@FindBy(id="personal-info-phone")
	private WebElement phonenumberfld;
	
	@FindBy(xpath="//input[@type='email']")
	private WebElement emailfld;
	
	@FindBy(xpath="//a[contains(@data-bind, 'navigateNext')]/span/i")
	private WebElement donebtn;
	
	@FindBy(xpath="//button[@class='btn btn-red']")
	private WebElement clearuserbtn;
	
	public VNextRegistrationPersonalInfoScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		//VNextRegistrationPersonalInfoScreen.WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		//wait.until(ExpectedConditions. visibilityOf(phonenumberselect));
	}
	
	public void setUserRegistrationInfoAndSend(String firstname, String lastname, String countrycode, String phonenumber, String usermail) {
		setUserRegistrationInfo(firstname, lastname, countrycode, phonenumber, usermail);
		clickDoneButton();
	}
	
	public void setUserRegistrationInfo(String firstname, String lastname, String countrycode, String phonenumber, String usermail) {
		setFirstName(firstname);
		setLastName(lastname);
		selectPhoneNumberCountryCode(countrycode);
		setPhoneNumber(phonenumber);
		setEmail(usermail);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.hideKeyboard();
	    switchToWebViewContext();
	   // if (appiumdriver.findElements(By.xpath("//iframe")).size() > 0)
	    //	appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		
	}
	
	public void selectPhoneNumberCountryCode(String countrycode) {
		Select sel = new Select(phonenumberselect);
		sel.selectByValue(countrycode);
	}
	
	public void setFirstName(String firstname) {
		firstnamefld.clear();
		firstnamefld.sendKeys(firstname);
	}
	
	public void setLastName(String lastname) {
		lastnamefld.clear();
		lastnamefld.sendKeys(lastname);
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
	
	public void clickClearUserButton() {
		tap(clearuserbtn);
	}
}
