package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class TeamWorkOrdersScreen extends MyWorkOrdersScreen {
	
	@iOSFindBy(accessibility  = "Monitor")
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
    private IOSElement searccsavebtn;
	
	public TeamWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickOnWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).click();
	}
	
	public OrderMonitorScreen selectWOMonitor() {
		womonitor.click();
		return new OrderMonitorScreen(appiumdriver);
	}
	
	public void selectEditWO() {
		editmanu.click();
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
		invoicenewbtn.click();		
	}	
	
	public void clickSearchButton() {
		searchbtn.click();
	}
	
	public void clickSearchSaveButton() {
		searccsavebtn.click();
		Helpers.waitABit(1000);
	}
	
	public void selectSearchLocation(String _location) {
		locationfld.click();
		appiumdriver.findElementByName(_location).click();
	}
	
	public void setSearchType(String wotype)  {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Type']").click();
		Helpers.waitABit(500);
		selectWorkOrderType(wotype);
	}

}
