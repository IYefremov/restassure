package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularAddCustomerScreen extends iOSRegularBaseScreen {

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_FirstName")
	private IOSElement firstnamefld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_LastName")
    private IOSElement lastnamefld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Company")
    private IOSElement companyfld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Address")
    private IOSElement address1fld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Address2")
	private IOSElement address2fld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_City")
    private IOSElement cityfld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_State")
	private IOSElement statefld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Country")
    private IOSElement countryfld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Zip")
    private IOSElement zipfld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Phone")
    private IOSElement phonefld;

	@iOSXCUITFindBy(accessibility = "CustomerInfoCell_Email")
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
		setMail(appCustomer.getMailAddress());
		setPhone(appCustomer.getCustomerPhone());
		setAddress1(appCustomer.getCustomerAddress1());
		setCity(appCustomer.getCustomerCity());
		selectCountry(appCustomer.getCustomerCountry());
		selectState(appCustomer.getCustomerState());
		setZip(appCustomer.getCustomerZip());
	}

	public void editCustomer(RetailCustomer appCustomer) {
		setFirstName(appCustomer.getFirstName());
		setLastName(appCustomer.getLastName());
		setCompanyName(appCustomer.getCompany());
		setMail(appCustomer.getMailAddress());
		setPhone(appCustomer.getCustomerPhone());
		setAddress1(appCustomer.getCustomerAddress1());
		setCity(appCustomer.getCustomerCity());
		setZip(appCustomer.getCustomerZip());
	}

	public void setFirstName(String firstname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("CustomerInfoCellEditBox")));
		WebElement firstNameTxt = firstnamefld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		firstNameTxt.clear();
		firstNameTxt.sendKeys(firstname+"\n");
	}

	public void setLastName(String lastname) {
		WebElement lastNameTxt = lastnamefld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		lastNameTxt.clear();
		lastNameTxt.sendKeys(lastname + "\n");
	}

	public void setCompanyName(String companyname) {
		WebElement companyTxt = companyfld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		companyTxt.clear();
		companyTxt.sendKeys(companyname + "\n");
	}

	public void setAddress1(String address1) {
		WebElement addressTxt = address1fld.findElementByAccessibilityId("CustomerInfoCellTextView");
		addressTxt.clear();
		addressTxt.sendKeys(address1 + "\n");
	}

	public void setAddress2(String address2) {
		WebElement address2Txt = address2fld.findElementByAccessibilityId("CustomerInfoCellTextView");
		address2Txt.clear();
		address2Txt.sendKeys(address2 + "\n");
	}

	public void setCity(String city) {
		WebElement cityTxt = cityfld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		cityTxt.clear();
		cityTxt.sendKeys(city + "\n");
	}

	public void setZip(String zip) {
		WebElement zipTxt = zipfld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		zipTxt.clear();
		zipTxt.sendKeys(zip + "\n");
	}

	public void setPhone(String phone) {
		WebElement phoneTxt = phonefld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		phoneTxt.clear();
		phoneTxt.sendKeys(phone + "\n");
	}

	public void setMail(String mail) {
		WebElement mailTxt = mailfld.findElementByAccessibilityId("CustomerInfoCellEditBox");
		mailTxt.clear();
		mailTxt.sendKeys(mail + "\n");
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
