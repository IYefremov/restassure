package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens;

import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.AddCustomerScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomersScreen extends BaseAppScreen {
	
	/*@iOSXCUITFindBy(accessibility  = "btnWholesale")
    private IOSElement btnwholesale;
	
	@iOSXCUITFindBy(accessibility  = "btnRetail")
    private IOSElement btnretail;
	
	@iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addcustomerbtn;
	
	@iOSXCUITFindBy(accessibility = "btnSearch")
    private IOSElement searchbtn;
	
	@iOSXCUITFindBy(accessibility = "Close")
    private IOSElement closesearchbtn;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeSearchBar[1]")
    private IOSElement searchbar;
	
	@iOSXCUITFindBy(accessibility  = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSXCUITFindBy(accessibility  = "Select")
    private IOSElement selectpopupmenu;*/
	
	public CustomersScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitCustomersScreenLoaded() {
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
		return new AddCustomerScreen();
	}

	public void searchCustomer(String customer) {
		
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Customers"))); 
		if (elementExists("ClientsView")) {
			appiumdriver.findElementByAccessibilityId("Search").click();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(customer + "\n");
			//appiumdriver.findElementByAccessibilityId("Close").click();
		} else {
			
			appiumdriver.findElementByAccessibilityId("Search").click();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(customer + "\n");
		}
	}

	public AddCustomerScreen selectCustomerToEdit(RetailCustomer retailCustomer) {
		clickOnCustomer(retailCustomer.getFirstName());
		appiumdriver.findElementByAccessibilityId("Edit").click();
		return new AddCustomerScreen();
	}
	
	public void clickOnCustomer(String customer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 3);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(customer)));
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

	public void selectCustomer(AppCustomer appCustomer) {
		if (appCustomer instanceof RetailCustomer)
			selectCustomer(appCustomer.getFirstName().trim());
		else
			selectCustomer(appCustomer.getFullName().trim());
	}
	
	public HomeScreen selectCustomerWithoutEditing(String customer) {
		selectCustomer(customer);
		return new HomeScreen();
	}

	public boolean isCustomerExists(String customer) {
		return elementExists(customer);
	}
	
	public boolean isTopCustomersExists() {
		return elementExists("Top Customers");
	}

}
