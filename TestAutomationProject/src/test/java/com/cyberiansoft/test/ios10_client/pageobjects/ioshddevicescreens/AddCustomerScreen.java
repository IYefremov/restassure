package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class AddCustomerScreen extends iOSHDBaseScreen {
	
	private AppiumDriver appiumdriver;

	final static String scrollviewxpath = ".scrollViews()[0]";
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[0]")
    private IOSElement firstnamefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[1]")
    private IOSElement lastnamefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[2]")
    private IOSElement companyfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[3]")
    private IOSElement streetfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[5]")
    private IOSElement cityfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[6]")
    private IOSElement statefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[7]")
    private IOSElement zipfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[8]")
    private IOSElement phonefld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".textFields()[9]")
    private IOSElement mailfld;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".buttons()[0]")
    private IOSElement statebtn;
	
	@iOSFindBy(uiAutomator = scrollviewxpath + ".buttons()[1]")
    private IOSElement countrybtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Clients")
    private IOSElement clientsbtn;
	
	public AddCustomerScreen(AppiumDriver driver) {
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
		setStreet(street);
		setCity(city);
		//Helpers.acceptAlert();
		selectState(state);
		Thread.sleep(2000);
		setZip(zip);
		Thread.sleep(2000);
		selectCountry(country);
		Thread.sleep(2000);
		setPhone(phone);
		Thread.sleep(2000);
		setMail(mail);
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
	}

	public void setLastName(String lastname) {
		lastnamefld.setValue(lastname);
	}

	public void setCompanyName(String companyname) {
		companyfld.setValue(companyname);
	}

	public void setStreet(String street) {
		streetfld.setValue(street);
	}

	public void setCity(String city) {
		cityfld.setValue(city);
	}

	public void setState(String state) {
		statefld.setValue(state);
	}

	public void setZip(String zip) {
		zipfld.setValue(zip);
	}

	public void setPhone(String phone) {
		phonefld.setValue(phone);
	}

	public void setMail(String mail) {
		mailfld.setValue(mail);
	}

	public void selectState(String state) throws InterruptedException {
		statebtn.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(state))).click();		
	}

	public void selectCountry(String country)
			throws InterruptedException {
		countrybtn.click();
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
