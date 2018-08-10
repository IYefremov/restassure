package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
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
	
	public VNextRegistrationNewUserPersonalInfoScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
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
		setNewUserZIP(newuserzip);
		selectNewUserCountry(newusercountry);
		selectNewUserState(newuserstate);
	}
	
	public void setNewUserCompanyName(String usercompanyname) {
		tap(usercompanynamefld);
		appiumdriver.getKeyboard().sendKeys(usercompanyname);
		appiumdriver.hideKeyboard();
	}
	
	public void setNewUserAddress1(String address1line) {
		tap(adress1fld);
		appiumdriver.getKeyboard().sendKeys(address1line);
		appiumdriver.hideKeyboard();
		//adress1fld.clear();
		//adress1fld.sendKeys(address1line + "\n");
	}
	
	public void setNewUserAddress2(String address2line) {
		tap(adress2fld);
		appiumdriver.getKeyboard().sendKeys(address2line);
		appiumdriver.hideKeyboard();
	}
	
	public void setNewUserCity(String usercity) {
		tap(cityfld);
		appiumdriver.getKeyboard().sendKeys(usercity);
		appiumdriver.hideKeyboard();
	}
	
	public void setNewUserZIP(String userzipcode) {
		tap(zipfld);
		appiumdriver.getKeyboard().sendKeys(userzipcode);
		appiumdriver.hideKeyboard();
	}
	
	public void selectNewUserCountry(String usercountry) {
		tap(countryfld);
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
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(statespage));
		tap(statespage.findElement(By.xpath(".//span[@class='selection-text' and text()='" + userstate + "']")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@data-bind, 'data.stateText')]")));
	}

	public void clickBackButton() {
		personalinfouserscreen.findElement(By.xpath(".//a[@data-role='button']")).click();
	}

	public void clickDoneButton() {
		System.out.println(personalinfouserscreen.findElements(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]")).size());
		tap(personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/div[contains(@data-bind, 'navigateNext')]")));
		/*personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]")).click();
		WebElement el = personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]"));
		new TouchAction(appiumdriver).tap(TapOptions.tapOptions().withElement(element (el, el.getLocation().getX()+3, el.getLocation().getY()+3)))
                .waitAction(waitOptions(ofSeconds(2))).perform();
	System.out.println("++++++++++++++++++++");
	MobileElement elm = (MobileElement) el;
	System.out.println("+++++++++++" + elm.getCenter().getX());
		System.out.println("+++++++++++" + elm.getCenter().getY());


		new TouchAction(appiumdriver)
				.tap(point(elm.getCenter().getX(), elm.getCenter().getY()))
				.tap(element(elm, 5, 5));
		System.out.println("++++++++++++++++++++");

		new TouchAction(appiumdriver)
				.press(element(elm,-5, elm.getCenter().getY() - elm.getLocation().getY()))
				.waitAction(waitOptions(ofSeconds(2)))
				.release();

		JavascriptExecutor executor = (JavascriptExecutor)appiumdriver;
		executor.executeScript("arguments[0].click();", el);

		if (appiumdriver instanceof JavascriptExecutor)
			((JavascriptExecutor)appiumdriver).executeScript("arguments[0].click();", el);
		System.out.println("===============================");

		el = personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span"));
		if (appiumdriver instanceof JavascriptExecutor)
			((JavascriptExecutor)appiumdriver).executeScript("arguments[0].click();", el);
		System.out.println("===============================");

		el = personalinfouserscreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span/i"));
		if (appiumdriver instanceof JavascriptExecutor)
			((JavascriptExecutor)appiumdriver).executeScript("arguments[0].click();", el);*/
	}



}
