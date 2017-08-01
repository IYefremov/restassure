package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.PricesCalculations;

public class OrderSummaryScreen extends iOSHDBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String ordersummaryscreencapt = "Summary";
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Default")
    private IOSElement defaultinvoicetype;
	
	@iOSFindBy(accessibility = "Total Sale")
    private IOSElement totalsalefinal;
	
	private By approveandcreateinvoicechekbox = By.name("black unchecked");

	public OrderSummaryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void assertDefaultServiceIsSelected() {
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId(defaultServiceValue).isDisplayed());
	}

	public void assertServiceIsSelected(String servicename) {
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId(servicename).isDisplayed());
	}

	public void checkApproveAndCreateInvoice() {
		savebtn.click();
		WebElement approvecell = appiumdriver.
				findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Approve and create invoice']/..");
		if (approvecell.findElements(By.xpath("//XCUIElementTypeButton[@name='unselected']")).size() > 0)
			approvecell.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
	
	public void checkCreateInvoice() {
		savebtn.click();
		WebElement approvecell = appiumdriver.
				findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Create invoice']/..");
		if (approvecell.findElements(By.xpath("//XCUIElementTypeButton[@name='unselected']")).size() > 0)
			approvecell.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
	
	public void checkApproveAndSaveWorkOrder() {
		savebtn.click();
		WebElement approvecell = appiumdriver.
				findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='Approve']/..");
		if (approvecell.findElements(By.xpath("//XCUIElementTypeButton[@name='unselected']")).size() > 0)
			approvecell.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).click();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
	
	public boolean isApproveAndCreateInvoiceExists() {
		return appiumdriver.findElements(approveandcreateinvoicechekbox).size() > 0;
	}

	public InvoiceInfoScreen selectDefaultInvoiceType() {
		defaultinvoicetype.click();
		Helpers.waitABit(500);
		return new InvoiceInfoScreen(appiumdriver);
	}
	
	public InvoiceInfoScreen selectInvoiceType(String invoicetype) {
		appiumdriver.findElementByName(invoicetype).click();
		return new InvoiceInfoScreen(appiumdriver);
	}

	public void selectWorkOrderDetails(String workorderdetails) {

		appiumdriver.findElementByName(workorderdetails).click();
	}

	public void assertOrderSummIsCorrect(String summ) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), summ);
	}

	public String getWorkOrderNumber() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar/XCUIElementTypeOther/XCUIElementTypeStaticText[3]").getText();
	}
	
	public void setTotalSale(String totalsale)  {
		WebElement par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[@value='Total Sale']/.."));
		IOSElement totalsalefld = (IOSElement)  par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1] "));
		totalsalefld.click();
		
		totalsalefld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(totalsale);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
		par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[@value='Total Sale']/.."));
		totalsalefld = (IOSElement)  par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1] "));
		Assert.assertEquals(totalsalefld.getAttribute("value"), PricesCalculations.getPriceRepresentation(totalsale));
	}
	
	public boolean isTotalSaleFieldPresent()  {
		return appiumdriver.findElementsByAccessibilityId("Total Sale").size() > 0;
	}

	public static String getOrderSummaryScreenCaption() {
		return ordersummaryscreencapt;
	}
	
	public void clickSaveButton() {
		clickSave();
		appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
	
	public void clickSave() {
		savebtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
}
