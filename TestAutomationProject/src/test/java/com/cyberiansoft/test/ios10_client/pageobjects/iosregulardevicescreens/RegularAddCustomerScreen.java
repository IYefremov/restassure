package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularAddCustomerScreen extends iOSRegularBaseScreen {

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField")
	private IOSElement firstnamefld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeTextField")
    private IOSElement lastnamefld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[3]/XCUIElementTypeTextField")
    private IOSElement companyfld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[6]/XCUIElementTypeTextView")
    private IOSElement address1fld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[7]/XCUIElementTypeTextView")
	private IOSElement address2fld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[8]/XCUIElementTypeTextField")
    private IOSElement cityfld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[10]/XCUIElementTypeTextField")
	private IOSElement statefld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[9]/XCUIElementTypeTextField")
    private IOSElement countryfld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[11]/XCUIElementTypeTextField")
    private IOSElement zipfld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[5]/XCUIElementTypeTextField")
    private IOSElement phonefld;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[4]/XCUIElementTypeTextField")
    private IOSElement mailfld;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility = "Clients")
    private IOSElement clientsbtn;
	
	public RegularAddCustomerScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void addCustomer(RetailCustomer appCustomer) {
		setFirstName(appCustomer.getFirstName());
		setLastName(appCustomer.getLastName());
		setCompanyName(appCustomer.getCompany());
		Helpers.waitABit(2000);
		setMail(appCustomer.getMailAddress());
		setPhone(appCustomer.getCustomerPhone());
		setAddress1(appCustomer.getCustomerAddress1());
		setCity(appCustomer.getCustomerCity());
		selectCountry(appCustomer.getCustomerCountry());
		Helpers.waitABit(2000);
		selectState(appCustomer.getCustomerState());
		Helpers.waitABit(4000);
		setZip(appCustomer.getCustomerZip());
	}

	public void editCustomer(RetailCustomer appCustomer) {
		Helpers.waitABit(1000);
		setFirstName(appCustomer.getFirstName());
		setLastName(appCustomer.getLastName());
		setCompanyName(appCustomer.getCompany());
		Helpers.waitABit(2000);
		setMail(appCustomer.getMailAddress());
		setPhone(appCustomer.getCustomerPhone());
		setAddress1(appCustomer.getCustomerAddress1());
		setCity(appCustomer.getCustomerCity());
		setZip(appCustomer.getCustomerZip());
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
