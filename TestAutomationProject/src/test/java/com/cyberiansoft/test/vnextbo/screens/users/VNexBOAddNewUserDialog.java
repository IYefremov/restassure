package com.cyberiansoft.test.vnextbo.screens.users;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNexBOAddNewUserDialog extends VNextBOBaseWebPage {

	@FindBy(xpath = "//div[@id='users-form-popup']//div[@class='modal-content']")
	public WebElement dialogContent;

	@FindBy(id = "users-firstName")
	public WebElement firstNameFld;
	
	@FindBy(id = "users-lastName")
	public WebElement lastNameFld;
	
	@FindBy(id = "users-email")
	public WebElement userMailFld;
	
	@FindBy(xpath = "//div[@class='amt-widget-phone-input']/input")
	public WebElement userPhoneFld;
	
	@FindBy(xpath = "//input[@data-bind='checked: data.webAccess']")
	public WebElement webAccessCheckbox;
	
	@FindBy(xpath = "//div[@class='modal-content']//button[@type='submit' and text()='Save']")
	public WebElement saveBtn;
	
	@FindBy(xpath = "//div[@id='users-form-popup']//button[@aria-label='Close']")
	public WebElement closeDialogBtn;

	@FindBy(xpath = "//div[@id='users-form-popup']//div[@class='text-red']")
	public List<WebElement> errorMessagesList;

	
	public VNexBOAddNewUserDialog() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}
}
