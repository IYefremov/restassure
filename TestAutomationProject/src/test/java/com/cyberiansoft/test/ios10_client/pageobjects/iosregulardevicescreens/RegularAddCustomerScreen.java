package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularAddCustomerScreen extends iOSRegularBaseScreen {

	@iOSXCUITFindBy(accessibility = "First Name")
	private IOSElement firstnamefld;

	@iOSXCUITFindBy(accessibility = "Last Name")
    private IOSElement lastnamefld;

	@iOSXCUITFindBy(accessibility = "Company")
    private IOSElement companyfld;

	@iOSXCUITFindBy(accessibility = "Address 1")
    private IOSElement address1fld;

	@iOSXCUITFindBy(accessibility = "Address 2")
	private IOSElement address2fld;

	@iOSXCUITFindBy(accessibility = "City")
    private IOSElement cityfld;

	@iOSXCUITFindBy(accessibility = "State\nProvince")
	private IOSElement statefld;

	@iOSXCUITFindBy(accessibility = "Country")
    private IOSElement countryfld;

	@iOSXCUITFindBy(accessibility = "ZIP\nPostal")
    private IOSElement zipfld;

	@iOSXCUITFindBy(accessibility = "Phone")
    private IOSElement phonefld;

	@iOSXCUITFindBy(accessibility = "Email")
    private IOSElement mailfld;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Clients")
    private IOSElement clientsbtn;
	
	public RegularAddCustomerScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void addCustomer(String firstname, String lastname,
			String companyname, String address1, String city, String state,
			String zip, String country, String phone, String mail) {
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		Helpers.waitABit(2000);
		setMail(mail);
		setPhone(phone);
		setAddress1(address1);
		setCity(city);
		selectCountry(country);
		Helpers.waitABit(2000);
		selectState(state);
		Helpers.waitABit(4000);
		setZip(zip);
			
		
	}

	public void editCustomer(String firstname, String lastname,
			String companyname, String address1, String city, String state,
			String zip, String country, String phone, String mail) {
		Helpers.waitABit(1000);
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		Helpers.waitABit(2000);
		setMail(mail);
		setPhone(phone);
		setAddress1(address1);
		setCity(city);
		setZip(zip);
	}

	public void setFirstName(String firstname) {
		firstnamefld.clear();
		firstnamefld.sendKeys(firstname+"\n");
	}

	public void setLastName(String lastname) {
		lastnamefld.click();
		lastnamefld.clear();
		lastnamefld.sendKeys(lastname + "\n");
	}

	public void setCompanyName(String companyname) {
		companyfld.clear();
		companyfld.sendKeys(companyname + "\n");
	}

	public void setAddress1(String address1) {
		address1fld.clear();
		address1fld.setValue(address1 + "\n");
	}

	public void setAddress2(String address2) {
		address2fld.clear();
		address2fld.setValue(address2 + "\n");
	}

	public void setCity(String city) {
		cityfld.clear();
		cityfld.sendKeys(city + "\n");
	}

	public void setZip(String zip) {
		zipfld.clear();
		zipfld.sendKeys(zip + "\n");
	}

	public void setPhone(String phone) {
		phonefld.clear();
		phonefld.click();
		phonefld.sendKeys(phone + "\n");
	}

	public void setMail(String mail) {
		mailfld.clear();
		mailfld.sendKeys(mail + "\n");
	}

	public void selectState(String state) {
		statefld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId(state)).click();		
	}

	public void selectCountry(String country) {
		countryfld.click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(MobileBy.AccessibilityId(country)));
		if (!appiumdriver.findElementByAccessibilityId(country).isDisplayed())
			SwipeUtils.swipeToElement(country);
		appiumdriver.findElement(MobileBy.AccessibilityId(country)).click();
	}

	public void clickSaveBtn() {
		savebtn.click();
	}


}
