package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularAddCustomerScreen extends iOSRegularBaseScreen {
	
	private AppiumDriver appiumdriver;

	final static String scrollviewxpath = "//XCUIElementTypeTable";
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[1]/XCUIElementTypeTextField[1]")
    private IOSElement firstnamefld;
	
	//@iOSXCUITFindByuiAutomator = scrollviewxpath + ".cells()['Last Name'].textFields()[0]")
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[2]/XCUIElementTypeTextField[1]")
    private IOSElement lastnamefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[3]/XCUIElementTypeTextField[1]")
    private IOSElement companyfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[6]/XCUIElementTypeTextView[1]")
    private IOSElement streetfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[8]/XCUIElementTypeTextField[1]")
    private IOSElement cityfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[10]/XCUIElementTypeTextField[1]")
    private IOSElement statefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[9]/XCUIElementTypeTextField[1]")
    private IOSElement countryfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[11]/XCUIElementTypeTextField[1]")
    private IOSElement zipfld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[5]/XCUIElementTypeTextField[1]")
    private IOSElement phonefld;
	
	@iOSXCUITFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[4]/XCUIElementTypeTextField[1]")
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
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail) {
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		Helpers.waitABit(2000);
		setMail(mail);
		setPhone(phone);
		setStreet(street);
		setCity(city);
		selectCountry(country);
		Helpers.waitABit(2000);
		selectState(state);
		Helpers.waitABit(4000);
		setZip(zip);
			
		
	}

	public void editCustomer(String firstname, String lastname,
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail) {
		Helpers.waitABit(1000);
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		Helpers.waitABit(2000);
		setMail(mail);
		setPhone(phone);
		setStreet(street);
		setCity(city);
		setZip(zip);
	}

	public void setFirstName(String firstname) {
		firstnamefld.clear();
		firstnamefld.sendKeys(firstname+"\n");
		//appiumdriver.hideKeyboard();
	}

	public void setLastName(String lastname) {
		lastnamefld.click();
		lastnamefld.clear();
		lastnamefld.sendKeys(lastname + "\n");
		//appiumdriver.hideKeyboard();
	}

	public void setCompanyName(String companyname) {
		companyfld.clear();
		companyfld.sendKeys(companyname + "\n");
		//appiumdriver.hideKeyboard();
	}

	public void setStreet(String street) {
		streetfld.clear();
		streetfld.setValue(street + "\n");
		//appiumdriver.hideKeyboard();
	}

	public void setCity(String city) {
		cityfld.clear();
		cityfld.sendKeys(city + "\n");
		//appiumdriver.hideKeyboard();
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
		phonefld.click();
		phonefld.sendKeys(phone + "\n");
		//appiumdriver.hideKeyboard();
	}

	public void setMail(String mail) {
		mailfld.clear();
		mailfld.sendKeys(mail + "\n");
		//appiumdriver.hideKeyboard();
	}

	public void selectState(String state) {
		statefld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId(state)).click();		
	}

	public void selectCountry(String country) {
		countryfld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId(country)).click();
	}

	public void clickSaveBtn() {
		savebtn.click();
	}

	public void clickClientsBtn() {
		clientsbtn.click();
	}

}
