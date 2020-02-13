package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularAddCustomerScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class RegularCustomersScreen extends RegularBaseAppScreen {

	@iOSXCUITFindBy(accessibility = "Customers")
	private IOSElement rootElement;

	@iOSXCUITFindBy(accessibility = "Add")
	private IOSElement addcustomerbtn;

	@iOSXCUITFindBy(accessibility = "CustomersTable")
	private IOSElement customersTable;
	
	/*@iOSXCUITFindBy(accessibility = "btnWholesale")
    private IOSElement btnwholesale;
	
	@iOSXCUITFindBy(accessibility = "btnRetail")
    private IOSElement btnretail;
	

	
	@iOSXCUITFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSXCUITFindBy(xpath = "//UIAPopover[1]/UIASearchBar[1]")
    private IOSElement searchbar;
	
	@iOSXCUITFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Select")
    private IOSElement selectpopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Top Customers")
	private List<IOSElement> topcustomers;*/

	@iOSXCUITFindBy(accessibility = "Search")
	private IOSElement searchbtn;
	
	public RegularCustomersScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void swtchToRetailMode() {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		if (appiumdriver.findElements(MobileBy.AccessibilityId("btnWholesale")).size() > 0)
			appiumdriver.findElement(MobileBy.AccessibilityId("btnWholesale")).click();
	}

	public void swtchToWholesaleMode() {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		if (appiumdriver.findElements(MobileBy.AccessibilityId("btnRetail")).size() > 0)
			appiumdriver.findElement(MobileBy.AccessibilityId("btnRetail")).click();
	}

	public boolean isCustomerExists(String customer) {
		return Helpers.elementExists(customer);
	}
	
	public void clickAddCustomersButton() {
		addcustomerbtn.click();
	}
	
	public void selectCustomer(String customerName) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		searchbtn.click();
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSearchField"))).sendKeys(customerName);
		appiumdriver.findElementByAccessibilityId(customerName).click();
	}

	public void selectCustomer(AppCustomer appCustomer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		if (appCustomer instanceof WholesailCustomer) {
			swtchToWholesaleMode();
			wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Search")));
			searchbtn.click();
			wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSearchField"))).sendKeys(appCustomer.getCompany());
		}
		else {
			swtchToRetailMode();
			wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Search")));
			searchbtn.click();
			wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeSearchField")));
			appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(appCustomer.getLastName());
		}
		appiumdriver.findElementByAccessibilityId(appCustomer.getFullName()).click();
	}

	public void clickOnCustomer(AppCustomer appCustomer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		appiumdriver.findElementByAccessibilityId(appCustomer.getFullName()).click();
	}
	
	public void selectOnlineCustomer(String customer) {
		appiumdriver.findElementByAccessibilityId("Online").click();
		if (!elementExists(customer)) {	
			appiumdriver.findElementByName("CustomerSelectionView").findElement(MobileBy.name("Search")).click();
			IOSElement searchfld = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Search' and type = 'XCUIElementTypeSearchField'"));
			searchfld.click();
			searchfld.sendKeys(customer + "\n");
			
		}
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(customer)));
		appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public RegularHomeScreen selectCustomerWithoutEditing(String customer) {
		selectCustomer(customer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Select")).click();
		RegularHomeScreen homeScreen = new RegularHomeScreen();
		homeScreen.waitHomeScreenLoaded();
		return homeScreen;
	}
	
	public RegularAddCustomerScreen selectCustomerToEdit(String customer) {
		selectCustomer(customer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Edit")).click();
		return new RegularAddCustomerScreen();
	}

	public boolean checkCustomerExists(String customer) {
		return elementExists(customer);
	}
	
	public boolean checkTopCustomersExists() {
		return elementExists("Top Customers");
	}


}
