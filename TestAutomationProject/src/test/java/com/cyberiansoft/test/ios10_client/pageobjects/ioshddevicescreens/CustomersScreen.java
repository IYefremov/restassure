package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class CustomersScreen extends iOSHDBaseScreen {
	
	/*@iOSFindBy(accessibility  = "btnWholesale")
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
    private IOSElement selectpopupmenu;*/
	
	public CustomersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void swtchToRetailMode() {
		if (elementExists("btnWholesale"))
			appiumdriver.findElementByAccessibilityId("btnWholesale").click();
	}

	public void swtchToWholesaleMode() {
		if (elementExists("btnRetail"))
			appiumdriver.findElementByAccessibilityId("btnRetail").click();
	}

	public AddCustomerScreen clickAddCustomersButton() {
		appiumdriver.findElementByAccessibilityId("Add").click();
		return new AddCustomerScreen(appiumdriver);				
	}

	public void searchCustomer(String customer) {
		
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Customers"))); 
		if (elementExists("ClientsView")) {
			appiumdriver.findElementByAccessibilityId("btnSearch").click();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
			((IOSDriver) appiumdriver).getKeyboard().pressKey(customer);
			appiumdriver.findElementByAccessibilityId("Close").click();
		} else {
			
			appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Customers']/XCUIElementTypeButton[@name='Search']").click();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
			Helpers.waitABit(1000);
			((IOSDriver) appiumdriver).getKeyboard().pressKey(customer);
		}
	}

	public AddCustomerScreen selectFirstCustomerToEdit() {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]").click();
		appiumdriver.findElementByAccessibilityId("Edit").click();
		return new AddCustomerScreen(appiumdriver);
	}
	
	public void clickOnCustomer(String customer) {
		appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public void selectCustomerWithoutEditing(String customer) {
		if (!appiumdriver.findElement(MobileBy.AccessibilityId(customer)).isDisplayed())
			searchCustomer(customer);
		clickOnCustomer(customer);
		appiumdriver.findElementByAccessibilityId("Select").click();
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
		return appiumdriver.findElementsByAccessibilityId(customer).size() > 0;
	}

}
