package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CustomersScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility  = "btnWholesale")
    private IOSElement btnwholesale;
	
	@iOSFindBy(accessibility  = "btnRetail")
    private IOSElement btnretail;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Add\"]")
    private IOSElement addcustomerbtn;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"btnSearch\"]")
    private IOSElement searchbtn;
	
	@iOSFindBy(uiAutomator = ".popover().buttons()[\"Close\"]")
    private IOSElement closesearchbtn;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIASearchBar[1]")
    private IOSElement searchbar;
	
	@iOSFindBy(accessibility  = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility  = "Select")
    private IOSElement selectpopupmenu;
	
	public CustomersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void swtchToRetailMode() {
		if (elementExists(By.name("Wholesale Mode"))) {
			btnwholesale.click();
		}
	}

	public void swtchToWholesaleMode() {
		if (elementExists(By.name("Retail Mode"))) {
			btnretail.click();
		}
	}

	public AddCustomerScreen clickAddCustomersButton() {
		addcustomerbtn.click();
		return new AddCustomerScreen(appiumdriver);				
	}

	public void searchCustomer(String customer)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn)).click();
		searchbar.setValue(customer);
		
		wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(closesearchbtn)).click();
	}

	public AddCustomerScreen selectFirstCustomerToEdit() {
		element(MobileBy.xpath("//UIATableView[1]/UIATableCell[1]"))
				.click();
		editpopupmenu.click();
		return new AddCustomerScreen(appiumdriver);
	}

	public void selectCustomer(String customer)
			throws InterruptedException {
		text(customer).click();
		selectpopupmenu.click();
		/*element(
				MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\"Select\"]"))
				.click();*/
	}

	public void selectFirstCustomerWithoutEditing()
			throws InterruptedException {
		Thread.sleep(2000);
		element(MobileBy.xpath("//UIATableView[1]/UIATableCell[1]"))
				.click();
		selectpopupmenu.click();
	}
	
	public void selectCustomerWithoutEditing(String customer)
			throws InterruptedException {
		Thread.sleep(2000);
		element(MobileBy.name(customer))
				.click();
		selectpopupmenu.click();
	}

	public void assertCustomerDoesntExists(String customer) {
		Assert.assertTrue(elements(
						MobileBy.xpath("//UIATableCell/UIAStaticText[@visible=\"true\" and (contains(@name,\""
								+ customer + "\"))] ")).size() < 1);
	}

	public void assertCustomerExists(String customer) {
		Assert.assertTrue(elements(
						MobileBy.xpath("//UIATableCell/UIAStaticText[@visible=\"true\" and (contains(@name,\""
								+ customer + "\"))] ")).size() > 0);
	}
	
	public void assertTopCustomersExists() {
		Assert.assertTrue(appiumdriver.findElements(MobileBy.name("Top Customers")).size() > 0);
	}
	
	public boolean customerIsPresent(String customer) {		
		return element(MobileBy.name(customer)).isDisplayed();
	}

}
