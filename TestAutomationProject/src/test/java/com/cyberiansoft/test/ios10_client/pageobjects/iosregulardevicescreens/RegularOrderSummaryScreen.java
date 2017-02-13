package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.PricesCalculations;

public class RegularOrderSummaryScreen extends iOSRegularBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String ordersummaryscreencapt = "Summary";
	
	@iOSFindBy(accessibility = "Default")
    private IOSElement defaultinvoicetype;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	private By approveandcreateinvoicechekbox = MobileBy.AccessibilityId("checkbox unchecked");

	public RegularOrderSummaryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void checkApproveAndCreateInvoice() {
		appiumdriver.findElement(approveandcreateinvoicechekbox).click();
	}
	
	public boolean checkApproveAndCreateInvoiceExists() {
		return elementExists(approveandcreateinvoicechekbox);
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByAccessibilityId("Enter password here")).setValue(password);
		Helpers.acceptAlert();
	}

	public RegularInvoiceInfoScreen selectDefaultInvoiceType() {
		defaultinvoicetype.click();
		return new RegularInvoiceInfoScreen(appiumdriver);
	}
	
	public RegularInvoiceInfoScreen selectInvoiceType(String invoicetype) {
		appiumdriver.findElementByName(invoicetype).click();
		return new RegularInvoiceInfoScreen(appiumdriver);
	}

	public void selectWorkOrderDetails(String workorderdetails) {

		appiumdriver.findElementByXPath("//XCUIElementTypeCell[@name=\"" + workorderdetails + "\"]").click();
	}

	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), summ);
	}
	
	public void setTotalSale(String totalsale) throws InterruptedException {
		setTotalSaleWithoutHidingkeyboard(totalsale+"\n");
		WebElement par = getTableParentNode("Total Sale");
		Assert.assertEquals(par.findElement(By.xpath("./XCUIElementTypeTextField[1]")).getAttribute("value"), PricesCalculations.getPriceRepresentation(totalsale));
	}
	
	public void setTotalSaleWithoutHidingkeyboard(String totalsale) throws InterruptedException {
		WebElement par = getTableParentNode("Total Sale");
		par.findElement(By.xpath("./XCUIElementTypeTextField[1]")).sendKeys(totalsale);
		//appiumdriver.findElement(By.xpath("//XCUIElementTypeButton[@name='Return']")).click();
	}

	public static String getOrderSummaryScreenCaption() {
		return ordersummaryscreencapt;
	}
	
	public void clickSaveButton() {
		savebtn.click();
	}
	
	public WebElement getTableParentNode(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + cellname + "']/.."));
	}
	
	public void closeDublicaterServicesWarningByClickingEdit() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void closeDublicaterServicesWarningByClickingCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Cancel").click();
	}
	
	public void closeDublicaterServicesWarningByClickingOverride() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Override").click();
	}
}
