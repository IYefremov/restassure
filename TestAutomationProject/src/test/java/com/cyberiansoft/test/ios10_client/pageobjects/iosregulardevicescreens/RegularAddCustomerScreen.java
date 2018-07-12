package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularAddCustomerScreen extends iOSRegularBaseScreen {
	
	private AppiumDriver appiumdriver;

	final static String scrollviewxpath = "//XCUIElementTypeTable";
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[1]/XCUIElementTypeTextField[1]")
    private IOSElement firstnamefld;
	
	//@iOSFindBy(uiAutomator = scrollviewxpath + ".cells()['Last Name'].textFields()[0]")
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[2]/XCUIElementTypeTextField[1]")
    private IOSElement lastnamefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[3]/XCUIElementTypeTextField[1]")
    private IOSElement companyfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[6]/XCUIElementTypeTextView[1]")
    private IOSElement streetfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[8]/XCUIElementTypeTextField[1]")
    private IOSElement cityfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[10]/XCUIElementTypeTextField[1]")
    private IOSElement statefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[9]/XCUIElementTypeTextField[1]")
    private IOSElement countryfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[11]/XCUIElementTypeTextField[1]")
    private IOSElement zipfld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[5]/XCUIElementTypeTextField[1]")
    private IOSElement phonefld;
	
	@iOSFindBy(xpath = scrollviewxpath + "/XCUIElementTypeCell[4]/XCUIElementTypeTextField[1]")
    private IOSElement mailfld;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Clients")
    private IOSElement clientsbtn;
	
	public RegularAddCustomerScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
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
		setPhone(phone);
		setStreet(street);
		setCity(city);
		selectCountry(country);	
		Thread.sleep(2000);
		selectState(state);		
		Thread.sleep(4000);
		setZip(zip);
			
		
	}

	public void editCustomer(String firstname, String lastname,
			String companyname, String street, String city, String state,
			String zip, String country, String phone, String mail)
			throws InterruptedException {
		Thread.sleep(1000);
		setFirstName(firstname);
		setLastName(lastname);
		setCompanyName(companyname);
		Thread.sleep(2000);
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

	public void selectState(String state) throws InterruptedException {
		statefld.click();
		appiumdriver.findElement(MobileBy.AccessibilityId(state)).click();		
	}

	public void selectCountry(String country)
			throws InterruptedException {
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
