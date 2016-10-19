package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularTeamWorkOrdersScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility  = "Monitor")
    private IOSElement womonitor;
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Search\"]")
    private IOSElement searchbtn;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAButton[@name= \"Edit\"]")
    private IOSElement editmanu;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"invoice new\"]")
    private IOSElement invoicenewbtn;
	
	@iOSFindBy(xpath = "//UIATableView[1]/UIATableCell[@name= \"Location\"]")
    private IOSElement locationfld;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name= \"Search\"]/UIAButton[@name= \"Save\"]")
    private IOSElement searccsavebtn;
	
	public RegularTeamWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickCreateInvoiceForWO(String wonumber) {
		appiumdriver.findElementByXPath("//UIATableView[@name=\"TeamOrdersTable\"]/UIATableCell[@name=\""
						+ wonumber + "\"]/UIAButton[1]").click();
	}
	
	public void clickOnWO(String wonumber) {
		appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
						+ wonumber + "\")]").click();
	}
	
	public RegularOrderMonitorScreen selectWOMonitor() {
		womonitor.click();
		return new RegularOrderMonitorScreen(appiumdriver);
	}
	
	public void selectEditWO() {
		editmanu.click();
	}
	
	public void selectWOInvoiceType(String invoicetype) {
		if (Helpers.elementExists("//UIAPopover[1]")) {
			appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView/UIATableCell[@name= \"" + invoicetype + "\"]").click();
		} else {
			appiumdriver.findElementByXPath("//UIATableView/UIATableCell[@name= \"" + invoicetype + "\"]").click();
		}
	}
	
	public void verifyCreateInvoiceIsActivated(String wonumber) throws InterruptedException {
		Thread.sleep(2000);
		Assert.assertTrue(appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()['TeamOrdersTable'].cells()['" + wonumber + "'].buttons().firstWithPredicate(\"name BEGINSWITH 'EntityInfoButtonChecked' \")")).isDisplayed());
		//Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView/UIATableCell[contains(@name, \""
		//				+ wonumber + "\")]/UIAButton[@name=\"EntityInfoButtonChecked\"]").isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(MobileBy.IosUIAutomation(".toolbars()[0].buttons()['invoice new']")).isDisplayed());		
	}
	
	public void clickiCreateInvoiceButton()  {
		invoicenewbtn.click();		
	}	
	
	public void clickSearchButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn)).click();
	}
	
	public void clickSearchSaveButton() {
		searccsavebtn.click();
	}
	
	public void selectSearchLocation(String _location) {
		locationfld.click();
		appiumdriver.findElementByName(_location).click();
	}

}
