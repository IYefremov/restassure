package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerScreen extends iOSHDBaseScreen {

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
	
	public AddCustomerScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void addCustomer(String firstname, String lastname,
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail)	{
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		setStreet(street);
		setCity(city);
		selectCountry(country);
		selectState(state);
		setZip(zip);
		setPhone(phone);
		setMail(mail);
	}

	public void editCustomer(String firstname, String lastname,
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail) {
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		setStreet(street);
		setCity(city);
		setZip(zip);
		setPhone(phone);
		setMail(mail);
	}

	public void setFirstName(String firstname) {
		firstnamefld.clear();
		firstnamefld.setValue(firstname);
	}

	public void setLastName(String lastname) {
		lastnamefld.clear();
		lastnamefld.setValue(lastname);
	}

	public void setCompanyName(String companyname) {
		companyfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(companyname);
	}

	public void setStreet(String street) {
		streetfld.clear();
		streetfld.setValue(street);
	}

	public void setCity(String city) {
		cityfld.clear();	
		((IOSDriver) appiumdriver).getKeyboard().pressKey(city + "\n");
	}

	public void setState(String state) {
		statefld.setValue(state);
		appiumdriver.hideKeyboard();
	}

	public void setZip(String zip) {
		zipfld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(zip + "\n");
	}

	public void setPhone(String phone) {
		phonefld.clear();		
		phonefld.sendKeys(phone + "\n");
	}

	public void setMail(String mail) {
		mailfld.clear();
		mailfld.sendKeys(mail + "\n");
	}

	public void selectState(String state) {
		statebtn.click();
		appiumdriver.findElement(MobileBy.AccessibilityId(state)).click();
	}

	public void selectCountry(String country) {
		countrybtn.click();
		appiumdriver.findElement(MobileBy.AccessibilityId(country)).click();
	}

	public void clickSaveBtn() {
		savebtn.click();
	}

	public void clickClientsBtn() {
		clientsbtn.click();
	}

}
