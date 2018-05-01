package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class RegularCustomersScreen extends iOSRegularBaseScreen {
	
	/*@iOSFindBy(accessibility = "btnWholesale")
    private IOSElement btnwholesale;
	
	@iOSFindBy(accessibility = "btnRetail")
    private IOSElement btnretail;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement addcustomerbtn;
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIASearchBar[1]")
    private IOSElement searchbar;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility = "Select")
    private IOSElement selectpopupmenu;
	
	@iOSFindBy(accessibility = "Top Customers")
	private List<IOSElement> topcustomers;*/
	
	public RegularCustomersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void swtchToRetailMode() {
		if (elementExists("btnWholesale")) {
			appiumdriver.findElement(MobileBy.AccessibilityId("btnWholesale")).click();
		}
	}

	public void swtchToWholesaleMode() {
		if (elementExists("btnRetail")) {
			appiumdriver.findElement(MobileBy.AccessibilityId("btnRetail")).click();
		}
	}

	public boolean isCustomerExists(String customer) {
		return Helpers.elementExists(customer);
	}
	
	public RegularAddCustomerScreen clickAddCustomersButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Add")).click();
		return new RegularAddCustomerScreen(appiumdriver);				
	}

	public void selectFirstCustomerWithoutEditing() {
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[1]"))
				.click();
		appiumdriver.findElement(MobileBy.AccessibilityId("Select")).click();
	}
	
	public void selectCustomer(String customer) {
		MobileElement customercell = (MobileElement) appiumdriver.findElementByAccessibilityId(customer);
		if (!customercell.isDisplayed()) {
			swipeToElement(appiumdriver.findElementByClassName("XCUIElementTypeTable").
					findElement(By.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + customer + "']/..")));
				appiumdriver.findElementByAccessibilityId(customer).click();
		} else
			customercell.click();
	}
	
	public void selectOnlineCustomer(String customer) {
		appiumdriver.findElementByAccessibilityId("Online").click();
		if (!elementExists(customer)) {	
			appiumdriver.findElementByAccessibilityId("Search").click();
			IOSElement searchfld = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Search' and type = 'XCUIElementTypeSearchField'"));
			searchfld.click();
			searchfld.sendKeys(customer + "\n");
			
		}
		appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public RegularHomeScreen selectCustomerWithoutEditing(String customer) {
		selectCustomer(customer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Select")).click();
		return new RegularHomeScreen(appiumdriver);
	}
	
	public RegularAddCustomerScreen selectCustomerToEdit(String customer) {
		selectCustomer(customer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Edit")).click();
		return new RegularAddCustomerScreen(appiumdriver);
	}

	public boolean checkCustomerExists(String customer) {
		return elementExists(customer);
	}
	
	public boolean checkTopCustomersExists() {
		return elementExists("Top Customers");
	}

}
