package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.PrintSelectorPopup;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TeamInvoicesScreen extends BaseTypeScreenWithTabs {

	@iOSXCUITFindBy(accessibility ="Change PO#")
	private IOSElement changepo;
	
	public TeamInvoicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void printInvoice(String invoicenum, String printserver) {
		selectInvoice(invoicenum);
		PrintSelectorPopup printselectorpopup = clickPrintPopup();
		printselectorpopup.checkRemotePrintServerAndSelectPrintServer(printserver);
		printselectorpopup.clickPrintSelectorPrintButton();
		printselectorpopup.clickPrintOptionsPrintButton();
	}

	public void selectInvoice(String invoice) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(invoice)));
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name= \""
								+ invoice + "\"]").click();
	}
	
	public PrintSelectorPopup clickPrintPopup() {
		appiumdriver.findElementByAccessibilityId("Print").click();
		return new PrintSelectorPopup();
	}
	
	public IOSElement getFirstInvoice() {
		return (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]");
	}

	public boolean isInvoicePrintButtonExists(String invoicenumber) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + invoicenumber + "']/XCUIElementTypeImage[@name='INVOICE_PRINTED']").size() > 0;
	}
	
	public void clickChangePOPopup() {
		changepo.click();
	}
	
	public void changePO(String newpo) {
		Helpers.waitABit(500);
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='PO#']/..");
		par.findElement(By.className("XCUIElementTypeTextField")).clear();
		par.findElement(By.className("XCUIElementTypeTextField")).sendKeys(newpo);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(newpo);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

	public boolean teamInvoicesIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Team Invoices").isDisplayed();
	}

	public void clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
	}

	public boolean isInvoiceExists(String invoice) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + invoice + "']").size() > 0;
	}

}
