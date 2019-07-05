package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularOrderSummaryScreen extends RegularBaseWizardScreen {

	final static String defaultServiceValue = "Test Tax";
	final static String ordersummaryscreencapt = "Summary";
	
	/*@iOSXCUITFindBy(accessibility = "Default")
    private IOSElement defaultinvoicetype;
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;*/

	@iOSXCUITFindBy(accessibility = "TotalAmount")
	private IOSElement wototalamaunt;
	
	private By approveandcreateinvoicechekbox = MobileBy.AccessibilityId("checkbox unchecked");

	public RegularOrderSummaryScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitWorkOrderSummaryScreenLoad() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'OrderSummaryView'")));
    }

	public void checkApproveAndCreateInvoice() {
		//savebtn.click();
		appiumdriver.findElementByAccessibilityId("checkbox unchecked").click();
	}
	
	public void checkApproveAndSaveWorkOrder() {
		//savebtn.click();
		appiumdriver.findElementByAccessibilityId("checkbox unchecked").click();
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

	public void selectDefaultInvoiceType() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Default")));
		appiumdriver.findElementByAccessibilityId("Default").click();
	}

	public void clickInvoiceType(String invoicetype) {
		if (!appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + invoicetype + "']")).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + invoicetype + "']/..")));
		}
		appiumdriver.findElementByName(invoicetype).click();
	}
	
	public RegularInvoiceInfoScreen selectInvoiceType(IInvoicesTypes invoiceType) {
		clickInvoiceType(invoiceType.getInvoiceTypeName());
		return new RegularInvoiceInfoScreen();
	}

	/*public void selectWorkOrderDetails(String workorderdetails) {

		appiumdriver.findElementByAccessibilityId(workorderdetails).click();
	}*/

	public String getOrderSumm() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(wototalamaunt));
		return wototalamaunt.getAttribute("value");
	}

	public String getTotalSaleValue() {
		WebElement par = getTableParentNode("Total Sale");
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}
	
	public void setTotalSale(String totalsale) {
		setTotalSaleWithoutHidingkeyboard(totalsale+"\n");
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Total Sale")));
	}
	
	public void setTotalSaleWithoutHidingkeyboard(String totalsale) {
		WebElement par = getTableParentNode("Total Sale");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(totalsale);
		//appiumdriver.findElement(By.xpath("//XCUIElementTypeButton[@name='Return']")).click();
	}

	public static String getOrderSummaryScreenCaption() {
		return ordersummaryscreencapt;
	}
	
	public void clickSave() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
	
	public WebElement getTableParentNode(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + cellname + "']/.."));
	}
	
	public void closeDublicaterServicesWarningByClickingEdit() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void closeDublicaterServicesWarningByClickingCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Cancel").click();
	}
	
	public void closeDublicaterServicesWarningByClickingOverride() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Override").click();
	}
	
	public boolean isTotalSaleFieldPresent()  {
		return appiumdriver.findElementsByAccessibilityId("Total Sale").size() > 0;
	}

	public RegularMyWorkOrdersScreen saveWizardAndAcceptAlert() {
		clickSave();
		Helpers.acceptAlert();
		return new RegularMyWorkOrdersScreen();
	}
}
