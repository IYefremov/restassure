package com.cyberiansoft.test.vnext.screens;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.CompositeAction;
import org.openqa.selenium.interactions.touch.SingleTapAction;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;


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
	
	@FindBy(xpath="//*[@data-automation-id='next']")
	private WebElement donebtn;
	
	@FindBy(xpath="//button[@class='btn btn-red']")
	private WebElement clearuserbtn;
	
	@FindBy(xpath="//*[@data-automation-id='register-with-code']")
	private WebElement ihaveregcodelink;
	
	public VNextRegistrationPersonalInfoScreen(SwipeableWebDriver appiumdriver) {
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
		//switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		//appiumdriver.hideKeyboard();
	    //switchToWebViewContext();
	}
	
	public void selectPhoneNumberCountryCode(String countrycode) {
		Select sel = new Select(phonenumberselect);
		sel.selectByValue(countrycode);
	}
	
	public void setFirstName(String firstname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions. visibilityOf(firstnamefld));
		tap(firstnamefld);
		appiumdriver.getKeyboard().pressKey(firstname);
		//firstnamefld.clear();
		//firstnamefld.sendKeys(firstname);
	}
	
	public void setLastName(String lastname) {
		tap(lastnamefld);
		appiumdriver.getKeyboard().pressKey(lastname);
		//lastnamefld.clear();
		//lastnamefld.sendKeys(lastname);
	}
	
	public void setPhoneNumber(String phonenumber) {
		tap(phonenumberfld);
		appiumdriver.getKeyboard().pressKey(phonenumber);
		//phonenumberfld.clear();
		//phonenumberfld.sendKeys(phonenumber);
	}
	
	public void setEmail(String usermail) {
		tap(emailfld);
		emailfld.clear();
		appiumdriver.getKeyboard().pressKey(usermail);
		appiumdriver.hideKeyboard();
		//emailfld.clear();
		//emailfld.sendKeys(usermail);
	}
	
	public void clickDoneButton() {
		tap(donebtn);
		waitABit(5000);
	}
	
	public void clickClearUserButton() {
		tap(clearuserbtn);
	}
	
	public void clickIHaveRigistrationCodeLink() {
		tap(ihaveregcodelink);
	}
}
