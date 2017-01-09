package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class AddCustomerScreen extends iOSHDBaseScreen {
	
	private AppiumDriver appiumdriver;

	final static String scrollviewxpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther";
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[1]")
    private IOSElement firstnamefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[2]")
    private IOSElement lastnamefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[3]")
    private IOSElement companyfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[4]")
    private IOSElement streetfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[6]")
    private IOSElement cityfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[7]")
    private IOSElement statefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[8]")
    private IOSElement zipfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[9]")
    private IOSElement phonefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[10]")
    private IOSElement mailfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeButton[1]")
    private IOSElement statebtn;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeButton[2]")
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
			String zip, String country, String phone, String mail)	{
		Helpers.waitABit(2000);
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		setStreet(street);
		Helpers.waitABit(2000);
		setCity(city);
		Helpers.waitABit(2000);
		selectCountry(country);
		selectState(state);
		setZip(zip);
		setPhone(phone);
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
		firstnamefld.clear();
		firstnamefld.setValue(firstname);
		Helpers.waitABit(300);
	}

	public void setLastName(String lastname) {
		lastnamefld.clear();
		lastnamefld.setValue(lastname);
		Helpers.waitABit(300);
	}

	public void setCompanyName(String companyname) {
		companyfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(companyname);
		Helpers.waitABit(300);
	}

	public void setStreet(String street) {
		streetfld.clear();
		streetfld.setValue(street);
		Helpers.waitABit(300);
	}

	public void setCity(String city) {
		cityfld.clear();	
		((IOSDriver) appiumdriver).getKeyboard().pressKey(city + "\n");
		Helpers.waitABit(300);
	}

	public void setState(String state) {
		statefld.setValue(state);
		appiumdriver.hideKeyboard();
	}

	public void setZip(String zip) {
		zipfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(zip + "\n");
		Helpers.waitABit(1000);
	}

	public void setPhone(String phone) {
		phonefld.clear();		
		phonefld.sendKeys(phone + "\n");
		Helpers.waitABit(1000);
	}

	public void setMail(String mail) {
		mailfld.clear();
		mailfld.sendKeys(mail + "\n");
		Helpers.waitABit(1000);
	}

	public void selectState(String state) {
		statebtn.click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.AccessibilityId(state))).waitAction(300).release().perform();
		Helpers.waitABit(2000);
	}

	public void selectCountry(String country) {
		countrybtn.click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElement(MobileBy.AccessibilityId(country))).waitAction(300).release().perform();
		Helpers.waitABit(2000);
	}

	public void clickSaveBtn() {
		savebtn.click();
	}

	public void clickClientsBtn() {
		clientsbtn.click();
	}

}
