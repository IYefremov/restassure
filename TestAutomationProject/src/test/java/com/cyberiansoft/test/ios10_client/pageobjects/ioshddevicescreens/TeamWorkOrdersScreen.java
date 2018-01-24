package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class TeamWorkOrdersScreen extends MyWorkOrdersScreen {
	
	/*@iOSFindBy(accessibility  = "Monitor")
    private IOSElement womonitor;
	
	@iOSFindBy(accessibility = "Search")
    private IOSElement searchbtn;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editmanu;
	
	@iOSFindBy(accessibility = "invoice new")
    private IOSElement invoicenewbtn;
	
	@iOSFindBy(accessibility = "Location")
    private IOSElement locationfld;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement searccsavebtn;*/
	
	public TeamWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Team Work Orders"))); 
	}
	
	public void clickOnWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).click();
	}
	
	public OrderMonitorScreen selectWOMonitor() {
		appiumdriver.findElementByAccessibilityId("Monitor").click();
		return new OrderMonitorScreen(appiumdriver);
	}
	
	public void selectEditWO() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void selectWOInvoiceType(String invoicetype) {
			appiumdriver.findElementByAccessibilityId(invoicetype).click();
	}
	
	public void verifyCreateInvoiceIsActivated(String wonumber) {
		Helpers.waitABit(2000);		
		Assert.assertTrue(appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name= '"
						+ wonumber + "']/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonChecked\")]").size() > 0);
		Assert.assertTrue(appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@name='invoice new']").size() > 0);		
	}
	
	public void clickiCreateInvoiceButton()  {
		appiumdriver.findElementByAccessibilityId("invoice new").click();		
	}	
	
	public void clickSearchButton() {
		appiumdriver.findElementByAccessibilityId("Search").click();
	}
	
	public void clickSearchSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		Helpers.waitABit(2000);
	}
	
	public void selectSearchLocation(String _location) {
		appiumdriver.findElementByAccessibilityId("Location").click();
		appiumdriver.findElementByName(_location).click();
	}
	
	public void setSearchType(String workordertype)  {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Type']").click();
		Helpers.waitABit(500);
		IOSElement wostable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'"));

		if (!wostable.findElementByAccessibilityId(workordertype).isDisplayed()) {
			swipeTableUp(wostable.findElementByAccessibilityId(workordertype),
					wostable);
			wostable.findElementByAccessibilityId(workordertype).click();
		}
		//if (appiumdriver.findElementsByAccessibilityId(workordertype).size() > 0)
		//	appiumdriver.findElementByAccessibilityId(workordertype).click();
	}

}
