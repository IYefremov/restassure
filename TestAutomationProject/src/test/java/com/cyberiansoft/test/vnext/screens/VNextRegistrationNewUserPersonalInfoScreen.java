package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;


public class VNextRegistrationNewUserPersonalInfoScreen extends VNextBaseScreen {
	
	@FindBy(id="personal-info-view")
	private WebElement personalinfouserscreen;
	
	@FindBy(xpath="//li[@data-name='companyName']/label/input")
	private WebElement usercompanynamefld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.addressLine1')]")
	private WebElement adress1fld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.addressLine2')]")
	private WebElement adress2fld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.city')]")
	private WebElement cityfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.zipCode')]")
	private WebElement zipfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.countryText')]")
	private WebElement countryfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.stateText')]")
	private WebElement statefld;
	
	@FindBy(id="selection-list-view")
	private WebElement countriespage;
	
	////////////States
	@FindBy(id="selection-list-view")
	private WebElement statespage;
	
	public VNextRegistrationNewUserPersonalInfoScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(personalinfouserscreen));
	}
	
	public void setNewUserPersonaInfo(String newusercompanyname, String userstate) {
		setNewUserCompanyName(newusercompanyname);
		selectNewUserState(userstate);
	}
	
	public void setNewUserPersonaInfo(String newusercompanyname,
			String newuseraddress1, String newuseraddress2, String newusercity, String newuserzip,
			String newusercountry, String newuserstate) {
		setNewUserCompanyName(newusercompanyname);
		setNewUserAddress1(newuseraddress1);
		setNewUserAddress2(newuseraddress2);
		setNewUserCity(newusercity);
		setNewUserZIP(newuserzip);
		selectNewUserCountry(newusercountry);
		selectNewUserState(newuserstate);
	}
	
	public void setNewUserCompanyName(String usercompanyname) {
		usercompanynamefld.clear();
		usercompanynamefld.sendKeys(usercompanyname);
		appiumdriver.hideKeyboard();
	}
	
	public void setNewUserAddress1(String address1line) {
		adress1fld.clear();
		adress1fld.sendKeys(address1line + "\n");
	}
	
	public void setNewUserAddress2(String address2line) {
		adress2fld.clear();
		adress2fld.sendKeys(address2line);
		appiumdriver.hideKeyboard();
	}
	
	public void setNewUserCity(String usercity) {
		cityfld.clear();
		cityfld.sendKeys(usercity);
		appiumdriver.hideKeyboard();
	}
	
	public void setNewUserZIP(String userzipcode) {
		zipfld.clear();
		zipfld.sendKeys(userzipcode);
		appiumdriver.hideKeyboard();
	}
	
	public void selectNewUserCountry(String usercountry) {
		tap(countryfld);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(countriespage));
		tap(countriespage.findElement(By.xpath(".//span[@class='selection-text' and text()='" + usercountry + "']")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(usercompanynamefld));
		log(LogStatus.INFO, "Select new user Country: " + usercountry);
	}
	
	public void selectNewUserState(String userstate) {
		tap(statefld);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(statespage));
		tap(statespage.findElement(By.xpath(".//span[@class='selection-text' and text()='" + userstate + "']")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(usercompanynamefld));
		log(LogStatus.INFO, "Select new user State: " + userstate);
	}
	
	public void clickDoneButton() {
		tap(personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span/i")));
		log(LogStatus.INFO, "Click Done button");
	}

}
