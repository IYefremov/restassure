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
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.personalInfo.firstName')]")
	private WebElement firstnamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.personalInfo.lastName')]")
	private WebElement lastnamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.personalInfo.companyName')]")
	private WebElement companynamefld;
	
	@FindBy(xpath="//span[@data-bind='text: data.user.phone']")
	private WebElement userphonefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.user.email')]")
	private WebElement usermailfld;
	
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
	
	public void clickDoneButton() {
		tap(registrationoverviewscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span/i")));
		log(LogStatus.INFO, "Click Done button");
	}

}
