package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularTeamWorkOrdersScreen extends iOSRegularBaseScreen {
	
	private By discardbtnxpath = By.name("Discard");
	
	@iOSFindBy(accessibility = "Monitor")
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
	
	@iOSFindBy(accessibility = "Approve")
    private IOSElement approvebtn;
	
	public RegularTeamWorkOrdersScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("TeamOrdersTable")));
	}
	
	public void clickCreateInvoiceForWO(String wonumber) {
		WebElement table = appiumdriver.findElementByAccessibilityId("TeamOrdersTable");
		table.findElement(By.xpath("//XCUIElementTypeCell[@name='"
						+ wonumber + "']/XCUIElementTypeOther")).click();
	}
	
	public void clickOnWO(String wonumber) {
		appiumdriver.findElementByAccessibilityId(wonumber).click();
	}
	
	public RegularOrderMonitorScreen selectWOMonitor() {
		womonitor.click();
		Helpers.waitABit(5000);
		return new RegularOrderMonitorScreen(appiumdriver);
	}
	
	public void selectEditWO() {
		editmanu.click();
	}
	
	public void selectWOInvoiceType(String invoicetype) {
		appiumdriver.findElementByAccessibilityId(invoicetype).click();
	}
	
	public void verifyCreateInvoiceIsActivated(String wonumber) throws InterruptedException {
		Helpers.waitABit(1000);
		Assert.assertTrue(appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name= '"
				+ wonumber + "']/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonChecked\")]").size() > 0);
Assert.assertTrue(appiumdriver.findElementsByXPath("//XCUIElementTypeButton[@name='invoice new']").size() > 0);	
	}
	
	public void clickiCreateInvoiceButton()  {
		invoicenewbtn.click();		
	}	
	
	public void clickSearchButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(searchbtn)).click();
		Helpers.waitABit(500);
	}
	
	public void clickSearchSaveButton() {
		searccsavebtn.click();
		Helpers.waitABit(2000);
	}
	
	public void selectSearchLocation(String _location) {
		locationfld.click();
		Helpers.waitABit(500);
		appiumdriver.findElementByName(_location).click();
	}
	
	public void setSearchType(String wotype)  {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Type']").click();
		Helpers.waitABit(500);
		selectWorkOrderType(wotype);
	}
	
	public void selectWorkOrderForApprove(String wonumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		WebElement wotable = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("TeamOrdersTable"))); 
		wotable.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther")).click();
		
	}
	
	public void approveWorkOrder(String wo, String employee, String pwd) {
		selectWorkOrderForApprove(wo);
		selectEmployeeAndTypePassword(employee, pwd);
		approvebtn.click();
	}
	
	public void selectWorkOrderType(String workordertype) {

		if (Helpers.elementExists(discardbtnxpath)) {
			appiumdriver.findElement(discardbtnxpath).click();
		}
		if (! appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workordertype + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workordertype + "']/..")));
			IOSTouchAction iostouch = new IOSTouchAction(appiumdriver);
			iostouch.tap(appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workordertype + "']"))).perform();
		}
		appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + workordertype + "']")).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByAccessibilityId("Enter password here")).setValue(password);
		Helpers.acceptAlert();
		Helpers.waitABit(1000);
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading team order")));
		}
		Helpers.waitABit(1000);
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void setFilterLocation(String _location)  {
		appiumdriver.findElementByAccessibilityId("Location").click();
		Helpers.waitABit(500);
		appiumdriver.findElementByAccessibilityId(_location).click();
	}
	
	public void clickSaveFilter() {
		clickSaveButton();
	}
	
	public boolean woExists(String wonumber) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(wonumber)).size() > 0;	
	}
	
	public boolean isWorkOrderHasApproveIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_78')]")).size() > 0;
	}
	
	public boolean isWorkOrderHasActionIcon(String wonumber) {
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + wonumber + "']/XCUIElementTypeOther[contains(@name, 'ButtonImageId_79')]")).size() > 0;
	}
	
	public String getFirstWorkOrderNumberValue() {		
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelOrderNumber']")).getAttribute("label");
	}
	
	public void selectWorkOrderForEidt(String wo) throws InterruptedException {
		selectWorkOrder(wo);
		appiumdriver.findElementByAccessibilityId("Edit").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Loading team order")));
		}
		Helpers.waitABit(1000);
	}
	
	public void selectWorkOrder(String wonumber) {
		new WebDriverWait(appiumdriver, 10)
				  .until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(wonumber))).click();

	}


}
