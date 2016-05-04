package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularAddCustomerScreen extends iOSRegularBaseScreen {
	
	private AppiumDriver appiumdriver;

	final static String scrollviewxpath = ".tableViews()[0]";
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['First Name'].textFields()[0]")
    private IOSElement firstnamefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Last Name'].textFields()[0]")
    private IOSElement lastnamefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Company'].textFields()[0]")
    private IOSElement companyfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Address 1'].textViews()[0]")
    private IOSElement streetfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['City'].textFields()[0]")
    private IOSElement cityfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['State Province'].textFields()[0]")
    private IOSElement statefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Country'].textFields()[0]")
    private IOSElement countryfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['ZIP Postal'].textFields()[0]")
    private IOSElement zipfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Phone'].textFields()[0]")
    private IOSElement phonefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Email'].textFields()[0]")
    private IOSElement mailfld;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Save\"]")
    private IOSElement savebtn;
	
	@iOSFindBy(xpath = "//UIAKeyboard[1]/UIAButton[@name=\"Return\"]")
    private IOSElement returnkeyboardbtn;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Clients\"]")
    private IOSElement clientsbtn;
	
	public RegularAddCustomerScreen(AppiumDriver driver) {
		super(driver);
		appiumdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public void addCustomer(String firstname, String lastname,
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail)
			throws InterruptedException {
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		Thread.sleep(2000);
		setMail(mail);
		setStreet(street);
		setCity(city);
		//Helpers.acceptAlert();
		selectState(state);
		Thread.sleep(2000);
		setPhone(phone);
		Thread.sleep(2000);
		setZip(zip);
		Thread.sleep(2000);
		selectCountry(country);		
		
	}

	public void editCustomer(String firstname, String lastname,
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail)
			throws InterruptedException {
		Thread.sleep(2000);
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		setStreet(street);
		setCity(city);
		Thread.sleep(2000);
		setZip(zip);
		Thread.sleep(2000);
		setPhone(phone);
		Thread.sleep(2000);
		setMail(mail);
	}

	public void setFirstName(String firstname) {
		firstnamefld.setValue(firstname);
		returnkeyboardbtn.click();
	}

	public void setLastName(String lastname) {
		lastnamefld.setValue(lastname);
		returnkeyboardbtn.click();
	}

	public void setCompanyName(String companyname) {
		companyfld.setValue(companyname);
		returnkeyboardbtn.click();
	}

	public void setStreet(String street) {
		streetfld.setValue(street);
		returnkeyboardbtn.click();
	}

	public void setCity(String city) {
		cityfld.setValue(city);
		returnkeyboardbtn.click();
	}

	public void setState(String state) {
		statefld.setValue(state);
		returnkeyboardbtn.click();
	}

	public void setZip(String zip) {
		Helpers.scroolTo("ZIP Postal");
		zipfld.setValue(zip);
		returnkeyboardbtn.click();
	}

	public void setPhone(String phone) {
		phonefld.setValue(phone);
		returnkeyboardbtn.click();
	}

	public void setMail(String mail) {
		mailfld.setValue(mail);
	}

	public void selectState(String state) throws InterruptedException {
		statefld.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(state))).click();		
	}

	public void selectCountry(String country)
			throws InterruptedException {
		countryfld.click();
		Helpers.scroolTo(country);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(country))).click();
	}

	public void clickSaveBtn() {
		savebtn.click();
	}

	public void clickClientsBtn() {
		clientsbtn.click();
	}

}
