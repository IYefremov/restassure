package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.PrintSelectorPopup;
import com.cyberiansoft.test.ios_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class TeamInvoicesScreen extends BaseTypeScreenWithTabs {
	
	public TeamInvoicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void printInvoice(String invoicenum, String printserver) {
		selectInvoice(invoicenum);
		PrintSelectorPopup printselectorpopup = clickPrintPopup();
		printselectorpopup.checkRemotePrintServerAndSelectPrintServer(printserver);
		printselectorpopup.clickPrintSelectorPrintButton();
		printselectorpopup.clickPrintOptionsPrintButton();
	}

	public void selectInvoice(String invoice) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name= \""
								+ invoice + "\"]").click();
	}
	
	public PrintSelectorPopup clickPrintPopup() {
		appiumdriver.findElementByAccessibilityId("Print").click();
		return new PrintSelectorPopup(appiumdriver);
	}
	
	public IOSElement getFirstInvoice() {
		return (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]");
	}
	
	public String getFirstInvoiceValue() {
		return getFirstInvoice().getAttribute("name");
	}
	
	public boolean isInvoicePrintButtonExists(String invoicenumber) {
		Helpers.waitABit(2000);
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + invoicenumber + "']/XCUIElementTypeImage[@name='INVOICE_PRINTED']").size() > 0;
	}
	
	public void clickChangePOPopup() {
		appiumdriver.findElementByAccessibilityId("Change PO#").click();
	}
	
	public void changePO(String newpo) {
		Helpers.waitABit(500);
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='PO#']/..");
		par.findElement(By.className("XCUIElementTypeTextField")).clear();
		par.findElement(By.className("XCUIElementTypeTextField")).sendKeys(newpo);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(newpo);
		appiumdriver.findElementByAccessibilityId("Done").click();
	}

}
