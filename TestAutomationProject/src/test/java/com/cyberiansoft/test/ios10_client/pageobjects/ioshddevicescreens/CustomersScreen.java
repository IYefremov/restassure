package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class CustomersScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility  = "btnWholesale")
    private IOSElement btnwholesale;
	
	@iOSFindBy(accessibility  = "btnRetail")
    private IOSElement btnretail;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement addcustomerbtn;
	
	@iOSFindBy(accessibility = "btnSearch")
    private IOSElement searchbtn;
	
	@iOSFindBy(accessibility = "Close")
    private IOSElement closesearchbtn;
	
	@iOSFindBy(xpath = "//XCUIElementTypeSearchBar[1]")
    private IOSElement searchbar;
	
	@iOSFindBy(accessibility  = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility  = "Select")
    private IOSElement selectpopupmenu;
	
	public CustomersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void swtchToRetailMode() {
		if (appiumdriver.findElementsByAccessibilityId("btnWholesale").size() > 0)
			btnwholesale.click();
	}

	public void swtchToWholesaleMode() {
		if (appiumdriver.findElementsByAccessibilityId("btnRetail").size() > 0)
			btnretail.click();
	}

	public AddCustomerScreen clickAddCustomersButton() {
		addcustomerbtn.click();
		return new AddCustomerScreen(appiumdriver);				
	}

	public void searchCustomer(String customer)
			throws InterruptedException {
		searchbtn.click();
		//searchbar.setValue(customer);
		appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(customer);
		closesearchbtn.click();
		Helpers.waitABit(1000);
	}

	public AddCustomerScreen selectFirstCustomerToEdit() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
		editpopupmenu.click();
		return new AddCustomerScreen(appiumdriver);
	}
	
	public void clickOnCustomer(String customer) {
		appiumdriver.findElementByAccessibilityId(customer).click();
	}

	public void selectCustomer(String customer)
			throws InterruptedException {
		clickOnCustomer(customer);
		selectpopupmenu.click();
		/*element(
				MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\"Select\"]"))
				.click();*/
	}
	
	

	public void selectFirstCustomerWithoutEditing()
			throws InterruptedException {
		Thread.sleep(2000);
		element(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]"))
				.click();
		selectpopupmenu.click();
	}
	
	public void selectCustomerWithoutEditing(String customer)
			throws InterruptedException {
		Helpers.waitABit(1000);
		clickOnCustomer(customer);
		selectpopupmenu.click();
	}

	public void assertCustomerDoesntExists(String customer) {
		Assert.assertTrue(appiumdriver.findElements(MobileBy.AccessibilityId(customer)).size() < 1);
	}

	public void assertCustomerExists(String customer) {
		Assert.assertTrue(appiumdriver.findElements(MobileBy.AccessibilityId(customer)).size() > 0);
	}
	
	public void assertTopCustomersExists() {
		Assert.assertTrue(appiumdriver.findElements(MobileBy.name("Top Customers")).size() > 0);
	}
	
	public boolean customerIsPresent(String customer) {		
		return element(MobileBy.name(customer)).isDisplayed();
	}

}
