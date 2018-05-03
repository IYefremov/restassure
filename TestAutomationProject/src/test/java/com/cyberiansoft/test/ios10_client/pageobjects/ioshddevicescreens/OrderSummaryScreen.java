package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class OrderSummaryScreen extends iOSHDBaseScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String ordersummaryscreencapt = "Summary";
	
	/*@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Default")
    private IOSElement defaultinvoicetype;
	
	@iOSFindBy(accessibility = "Total Sale")
    private IOSElement totalsalefinal;*/
	
	private By approveandcreateinvoicechekbox = By.name("black unchecked");

	public OrderSummaryScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public boolean checkDefaultServiceIsSelected() {
		return appiumdriver.findElementByAccessibilityId(defaultServiceValue).isDisplayed();
	}

	public boolean checkServiceIsSelected(String servicename) {
		return appiumdriver.findElementByAccessibilityId(servicename).isDisplayed();
	}

	public void checkApproveAndCreateInvoice() {
		
		appiumdriver.findElement(approveandcreateinvoicechekbox).click();
		//savebtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
	
	public void checkCreateInvoice() {
		appiumdriver.findElement(approveandcreateinvoicechekbox).click();
		clickSave();
	}
	
	public void checkApproveAndSaveWorkOrder() {
		
		appiumdriver.findElement(approveandcreateinvoicechekbox).click();
		clickSave();
	}
	
	public boolean isApproveAndCreateInvoiceExists() {
		return appiumdriver.findElements(approveandcreateinvoicechekbox).size() > 0;
	}

	public InvoiceInfoScreen selectDefaultInvoiceType() {
		appiumdriver.findElementByAccessibilityId("Default").click();
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

	public String getOrderSumm() {
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}

	public String getWorkOrderNumber() {
		return appiumdriver.findElementByClassName("XCUIElementTypeToolbar")
				.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[3]")).getText();
	}

	public String getTotalSaleValue() {
		WebElement par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[@value='Total Sale']/.."));
		return par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1] ")).getAttribute("value");
	}
	
	public void setTotalSale(String totalsale)  {
		WebElement par = appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[@value='Total Sale']/.."));
		IOSElement totalsalefld = (IOSElement)  par.findElement(MobileBy.xpath("//XCUIElementTypeTextField[1] "));
		totalsalefld.click();
		
		totalsalefld.clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(totalsale);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}
	
	public boolean isTotalSaleFieldPresent()  {
		return appiumdriver.findElementsByAccessibilityId("Total Sale").size() > 0;
	}

	public static String getOrderSummaryScreenCaption() {
		return ordersummaryscreencapt;
	}
	
	public void clickSaveButton() {
		clickSave();
		/*appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Save']").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);*/
	}
	
	public void clickSave() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(1000);
	}
}
