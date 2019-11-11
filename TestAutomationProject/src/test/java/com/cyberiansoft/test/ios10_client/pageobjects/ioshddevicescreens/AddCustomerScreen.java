package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerScreen extends iOSHDBaseScreen {

	final static String scrollviewxpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther";
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[1]")
    private IOSElement firstnamefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[2]")
    private IOSElement lastnamefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[3]")
    private IOSElement companyfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[4]")
    private IOSElement streetfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[6]")
    private IOSElement cityfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[7]")
    private IOSElement statefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[8]")
    private IOSElement zipfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[9]")
    private IOSElement phonefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeTextField[10]")
    private IOSElement mailfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeButton[1]")
    private IOSElement statebtn;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeButton[2]")
    private IOSElement countrybtn;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Clients")
    private IOSElement clientsbtn;
	
	public AddCustomerScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void addCustomer(RetailCustomer retailCustomer)	{
		setFirstName(retailCustomer.getFirstName());
		setLastName(retailCustomer.getLastName());
		setCompanyName(retailCustomer.getCompany());
		setStreet(retailCustomer.getCustomerAddress1());
		setCity(retailCustomer.getCustomerCity());
		selectCountry(retailCustomer.getCustomerCountry());
		selectState(retailCustomer.getCustomerState());
		setZip(retailCustomer.getCustomerZip());
		setPhone(retailCustomer.getCustomerPhone());
		setMail(retailCustomer.getMailAddress());
	}

	public void editCustomer(RetailCustomer retailCustomer) {
		setFirstName(retailCustomer.getFirstName());
		setLastName(retailCustomer.getLastName());
		setCompanyName(retailCustomer.getCompany());
		setStreet(retailCustomer.getCustomerAddress1());
		setCity(retailCustomer.getCustomerCity());
		setZip(retailCustomer.getCustomerZip());
		setPhone(retailCustomer.getCustomerPhone());
		setMail(retailCustomer.getMailAddress());
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
		companyfld.setValue(companyname);
	}

	public void setStreet(String street) {
		streetfld.clear();
		streetfld.setValue(street);
	}

	public void setCity(String city) {
		cityfld.clear();
		cityfld.setValue(city + "\n");
	}

	public void setState(String state) {
		statefld.setValue(state + "\n");
		//appiumdriver.hideKeyboard();
	}

	public void setZip(String zip) {
		zipfld.clear();
		zipfld.setValue(zip + "\n");
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
