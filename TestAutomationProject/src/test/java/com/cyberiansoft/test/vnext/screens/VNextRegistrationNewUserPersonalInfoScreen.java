package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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

    public VNextRegistrationNewUserPersonalInfoScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("personal-info-view")));
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
		selectNewUserCountry(newusercountry);
		selectNewUserState(newuserstate);
		setNewUserZIP(newuserzip);


	}
	
	public void setNewUserCompanyName(String usercompanyname) {
		tap(usercompanynamefld);
		usercompanynamefld.sendKeys(usercompanyname);
		//appiumdriver.getKeyboard().sendKeys(usercompanyname);
		//appiumdriver.hideKeyboard();
	}
	
	public void setNewUserAddress1(String address1line) {
		tap(adress1fld);
		adress1fld.sendKeys(address1line);
	}
	
	public void setNewUserAddress2(String address2line) {
		tap(adress2fld);
		adress2fld.sendKeys(address2line);
		//appiumdriver.getKeyboard().sendKeys(address2line);
		//appiumdriver.hideKeyboard();
	}
	
	public void setNewUserCity(String usercity) {
		tap(cityfld);
		cityfld.sendKeys(usercity);
		//appiumdriver.getKeyboard().sendKeys(usercity);
	}
	
	public void setNewUserZIP(String userzipcode) {
		tap(zipfld);
		zipfld.sendKeys(userzipcode);
		//appiumdriver.getKeyboard().sendKeys(userzipcode);
		//appiumdriver.hideKeyboard();
	}
	
	public void selectNewUserCountry(String usercountry) {
		tap(countryfld);
		BaseUtils.waitABit(1000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(countriespage));
		
		String datauid = countriespage.findElement(By.xpath(".//span[@class='selection-text' and text()='" + usercountry + "']/../..")).getAttribute("data-uid");
		if (appiumdriver instanceof JavascriptExecutor)
			try {
				((JavascriptExecutor)appiumdriver).executeScript("$('body').trigger('scrollto', $('[data-uid="+ datauid + "]'))");
			} catch (WebDriverException e) {
		    	//for some reason JS code is crashed but scrolled
		    }
		
		tap(countriespage.findElement(By.xpath(".//span[@class='selection-text' and text()='" + usercountry + "']")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(usercompanynamefld));
	}
	
	public void selectNewUserState(String userstate) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(statefld));
		tap(statefld);
		BaseUtils.waitABit(1000);
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(statespage));
		tap(statespage.findElement(By.xpath(".//span[@class='selection-text' and text()='" + userstate + "']")));
		BaseUtils.waitABit(1000);
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@data-bind, 'data.stateText')]")));
	}

	public void clickBackButton() {
		personalinfouserscreen.findElement(By.xpath(".//a[@data-role='button']")).click();
	}

	public void clickDoneButton() {
		System.out.println(personalinfouserscreen.findElements(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]")).size());
		tap(personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/div[contains(@data-bind, 'navigateNext')]")));
	}

	public boolean isStateProvinceErrorMessageVisible() {
		return appiumdriver.findElement(By.xpath("//li[@data-name='state']"))
				.findElement(By.xpath((".//div[@class='validation-message']/div"))).getText().length() > 1;
	}

	public String getStateProvinceErrorMessage() {
		return appiumdriver.findElement(By.xpath("//li[@data-name='state']"))
				.findElement(By.xpath((".//div[@class='validation-message']/div"))).getText();
	}

}
