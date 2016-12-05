package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularCustomersScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "btnWholesale")
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
	private List<IOSElement> topcustomers;
	
	public RegularCustomersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void swtchToRetailMode() {
		if (elementExists(MobileBy.AccessibilityId("Wholesale Mode"))) {
			btnwholesale.click();
		}
	}

	public void swtchToWholesaleMode() {
		if (elementExists(MobileBy.AccessibilityId("Retail Mode"))) {
			btnretail.click();
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
		addcustomerbtn.click();
		return new RegularAddCustomerScreen(appiumdriver);				
	}

	public void selectFirstCustomerWithoutEditing()
			throws InterruptedException {
		Thread.sleep(2000);
		element(MobileBy.xpath("//UIATableView[1]/UIATableCell[1]"))
				.click();
		selectpopupmenu.click();
	}
	
	public void selectCustomer(String customer) {
		//appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()['CustomersTable'].cells()['" + customer + "']")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(customer)).click();
	}
	
	public void selectCustomerWithoutEditing(String customer) {
		selectCustomer(customer);
		selectpopupmenu.click();
	}
	
	public RegularAddCustomerScreen selectCustomerToEdit(String customer) {
		selectCustomer(customer);
		editpopupmenu.click();
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
		return element(MobileBy.AccessibilityId(customer)).isDisplayed();
	}

}
