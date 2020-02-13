package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
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
		waitWorkOrderSummaryScreenLoad();
		return elementExists(approveandcreateinvoicechekbox);
	}
	
	public void selectEmployee(String employee) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(employee)));
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

	public String getOrderSumm() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		return  wait.until(ExpectedConditions.visibilityOf(wototalamaunt)).getAttribute("value");
	}

	public String getTotalSaleValue() {
		return appiumdriver.findElementByAccessibilityId("OrderSummaryCell_TotalSale")
				.findElement(MobileBy.AccessibilityId("OrderSummaryCell_TextFieldValue")).getAttribute("value");
	}
	
	public void setTotalSale(String totalsale) {
		waitWorkOrderSummaryScreenLoad();
		setTotalSaleWithoutHidingkeyboard(totalsale+"\n");
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Total Sale")));
	}
	
	public void setTotalSaleWithoutHidingkeyboard(String totalsale) {
		appiumdriver.findElementByAccessibilityId("OrderSummaryCell_TotalSale")
				.findElement(MobileBy.AccessibilityId("OrderSummaryCell_TextFieldValue")).sendKeys(totalsale);
	}
	
	public void clickSave() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
		appiumdriver.findElementByAccessibilityId("Save").click();
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
}
