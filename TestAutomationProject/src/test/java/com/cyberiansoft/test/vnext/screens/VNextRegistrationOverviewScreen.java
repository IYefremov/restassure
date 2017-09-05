package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextRegistrationOverviewScreen extends VNextBaseScreen {
	
	@FindBy(id="summary-view")
	private WebElement registrationoverviewscreen;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.user.firstName')]")
	private WebElement firstnamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.user.lastName')]")
	private WebElement lastnamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.personalInfo.companyName')]")
	private WebElement companynamefld;
	
	@FindBy(xpath="//span[@data-bind='text: data.user.phone']")
	private WebElement userphonefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.user.email')]")
	private WebElement usermailfld;
	
	@FindBy(xpath="//input[@data-bind='value: data.personalInfo.addressLine1']")
	private WebElement address1fld;
	
	@FindBy(xpath="//input[@data-bind='value: data.personalInfo.addressLine2']")
	private WebElement address2fld;
	
	@FindBy(xpath="//input[@data-bind='value: data.personalInfo.city']")
	private WebElement cityfld;
	
	@FindBy(xpath="//input[@data-bind='value: data.personalInfo.zipCode']")
	private WebElement zipfld;
	
	@FindBy(xpath="//input[@data-bind='value: data.personalInfo.stateText']")
	private WebElement statefld;
	
	@FindBy(xpath="//input[@data-bind='value: data.personalInfo.countryText']")
	private WebElement countryfld;
	
	public VNextRegistrationOverviewScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(registrationoverviewscreen));
	}
	
	public String getUserFirstNameValue() {
		return firstnamefld.getAttribute("value");
	}
	
	public String getUserLastNameValue() {
		return lastnamefld.getAttribute("value");
	}
	
	public String getUserCompanyNameValue() {
		return companynamefld.getAttribute("value");
	}
	
	public String getUserPhoneValue() {
		return userphonefld.getText();
	}
	
	public String getUserEmailValue() {
		return usermailfld.getAttribute("value");
	}
	
	public String getAddress1Value() {
		return address1fld.getAttribute("value");
	}
	
	public String getAddress2Value() {
		return address2fld.getAttribute("value");
	}
	
	public String getCityValue() {
		return cityfld.getAttribute("value");
	}
	
	public String getZipValue() {
		return zipfld.getAttribute("value");
	}
	
	public String getStateValue() {
		return statefld.getAttribute("value");
	}
	
	public String getCountryValue() {
		return countryfld.getAttribute("value");
	}
	
	public void clickDoneButton() {
		tap(registrationoverviewscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span/i")));
		log(LogStatus.INFO, "Click Done button");
	}

}
