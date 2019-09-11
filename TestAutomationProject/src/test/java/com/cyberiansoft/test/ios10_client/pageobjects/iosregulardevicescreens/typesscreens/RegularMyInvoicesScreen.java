package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularMyInvoicesScreen extends RegularBaseTypeScreenWithTabs {

	@iOSXCUITFindBy(accessibility = "InvoicesTable")
	private IOSElement invoicesTable;
	
	public RegularMyInvoicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitInvoicesScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("InvoicesTable")));
	}

	public boolean myInvoiceExists(String invoiceNumber) {
		return invoicesTable.findElements(MobileBy.AccessibilityId(invoiceNumber)).size() > 0;
	}
	
	public String getWOsForInvoice(String invoiceNumber) {
		waitInvoicesScreenLoaded();
		return invoicesTable.findElement(MobileBy.AccessibilityId(invoiceNumber)).findElement(MobileBy.AccessibilityId("labelDetails")).getAttribute("value");
	}
	
	public void selectInvoice(String invoiceNumber) {
		waitInvoicesScreenLoaded();
		invoicesTable.findElement(MobileBy.AccessibilityId(invoiceNumber)).click();
	}
	
	public boolean isInvoiceHasInvoiceNumberIcon(String invoiceNumber) {
		return invoicesTable.findElement(MobileBy.AccessibilityId(invoiceNumber)).findElement(MobileBy.AccessibilityId("INVOICE_NO")).isDisplayed();
	}
	
	public boolean isInvoiceHasInvoiceSharedIcon(String invoiceNumber) {
		return invoicesTable.findElement(MobileBy.AccessibilityId(invoiceNumber)).findElement(MobileBy.AccessibilityId("INVOICE_SHARED")).isDisplayed();
	}
	
	public void selectCustomer(String customer) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Customers")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name(customer)));
		appiumdriver.findElementByName(customer).click();
	}
	
	public String getInvoicePrice(String invoiceNumber) {
		return invoicesTable.findElementByAccessibilityId(invoiceNumber).findElementByAccessibilityId("labelInvoiceAmount").getAttribute("label");
	}
	
	public void changePO(String newpo) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeCollectionView")));
		appiumdriver.findElementByClassName("XCUIElementTypeCollectionView").findElement(By.className("XCUIElementTypeTextField")).clear();
		appiumdriver.findElementByClassName("XCUIElementTypeCollectionView").findElement(By.className("XCUIElementTypeTextField")).sendKeys(newpo);
		appiumdriver.switchTo().alert().accept();
	}
	
	public boolean myInvoicesIsDisplayed() {
		return invoicesTable.isDisplayed();
	}
	
	public boolean teamInvoicesIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Team Invoices").isDisplayed();
	}
	
	public void clickActionButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Share")).click();
	}
	
	public void selectEmployee(String employee) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10); 
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Employees")));
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByXPath("//UIASecureTextField[@value=\"Enter password here\"]")).setValue(password);
		Helpers.acceptAlert();
	}
	
	public boolean isInvoiceApproveButtonExists(String invoiceNumber) {
		
		appiumdriver.findElementByAccessibilityId(invoiceNumber).click();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeScrollView")));
		boolean approved = appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
		clickCancel();
		new RegularMyInvoicesScreen();
		return approved;		
	}
	
	public void clickDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	
	public String getPriceForInvoice(String invoiceNumber) {
		return invoicesTable.
				findElement(MobileBy.AccessibilityId(invoiceNumber)).findElement(MobileBy.AccessibilityId("labelInvoiceAmount")).getAttribute("value");
	}
	
	public void selectInvoices(int numberInvoicesToSelect) {
		WaitUtils.elementShouldBeVisible(invoicesTable, true);
		List<MobileElement> invoices = invoicesTable.findElementsByClassName("XCUIElementTypeCell");
		for (int i = 0; i < numberInvoicesToSelect; i++)
			invoices.get(i).click();
	}

	public void clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
	}

}
