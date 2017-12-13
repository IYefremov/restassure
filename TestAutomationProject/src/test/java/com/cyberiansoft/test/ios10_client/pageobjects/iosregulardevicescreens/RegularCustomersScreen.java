package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

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
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

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
		if (appiumdriver.findElements(MobileBy.AccessibilityId("btnWholesale")).size() > 0) {
			appiumdriver.findElement(MobileBy.AccessibilityId("btnWholesale")).click();
		}
	}

	public void swtchToWholesaleMode() {
		if (appiumdriver.findElements(MobileBy.AccessibilityId("btnRetail")).size() > 0) {
			appiumdriver.findElement(MobileBy.AccessibilityId("btnRetail")).click();
		}
	}

	public boolean isCustomerExists(String customer) {
		return Helpers.elementExists(customer);
	}

	/*public void selectCustomer(String customer)
			throws InterruptedException {
		text(customer).click();
		selectpopupmenu.click();
	}*/
	
	public RegularAddCustomerScreen clickAddCustomersButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Add")).click();
		return new RegularAddCustomerScreen(appiumdriver);				
	}

	public void selectFirstCustomerWithoutEditing()
			throws InterruptedException {
		Thread.sleep(2000);
		appiumdriver.findElement(MobileBy.xpath("//UIATableView[1]/UIATableCell[1]"))
				.click();
		appiumdriver.findElement(MobileBy.AccessibilityId("Select")).click();
	}
	
	public void selectCustomer(String customer) {

		if (!appiumdriver.findElementByAccessibilityId(customer).isDisplayed()) {
			swipeToElement(appiumdriver.findElementByClassName("XCUIElementTypeTable").
					findElement(By.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + customer + "']/..")));
				appiumdriver.findElementByAccessibilityId(customer).click();
		}
		appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public void selectCustomerWithoutEditing(String customer) {
		selectCustomer(customer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Select")).click();
	}
	
	public RegularAddCustomerScreen selectCustomerToEdit(String customer) {
		selectCustomer(customer);
		appiumdriver.findElement(MobileBy.AccessibilityId("Edit")).click();
		return new RegularAddCustomerScreen(appiumdriver);
	}

	public void assertCustomerDoesntExists(String customer) {
		Assert.assertFalse(Helpers.elementExists(MobileBy.AccessibilityId(customer)));
	}

	public void assertCustomerExists(String customer) {
		Assert.assertTrue(Helpers.elementExists(MobileBy.AccessibilityId(customer)));
	}
	
	public void assertTopCustomersExists() {
		Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("Top Customers")).isDisplayed());
	}
	
	public boolean customerIsPresent(String customer) {	
		return appiumdriver.findElement(MobileBy.AccessibilityId(customer)).isDisplayed();
	}

}
