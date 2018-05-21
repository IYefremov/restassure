package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.AddCustomerScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CustomersScreen extends BaseAppScreen {
	
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
	}

	public void swtchToRetailMode() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
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
			appiumdriver.findElementByAccessibilityId("Search").click();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
			((IOSDriver) appiumdriver).getKeyboard().pressKey(customer);
			appiumdriver.findElementByAccessibilityId("Close").click();
		} else {
			
			appiumdriver.findElementByAccessibilityId("Search").click();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
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

	public void selectCustomer(String customer) {

		if (!elementExists(customer))
			searchCustomer(customer);
		clickOnCustomer(customer);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 3);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Select")));
		appiumdriver.findElementByAccessibilityId("Select").click();
	}
	
	public HomeScreen selectCustomerWithoutEditing(String customer) {
		selectCustomer(customer);
		return new HomeScreen(appiumdriver);
	}

	public boolean isCustomerExists(String customer) {
		return elementExists(customer);
	}
	
	public boolean isTopCustomersExists() {
		return elementExists("Top Customers");
	}

}
