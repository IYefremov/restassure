package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class TeamInvoicesScreen extends MyInvoicesScreen {
	
	@iOSFindBy(accessibility  = "Print")
    private IOSElement printmenu;
	
	public TeamInvoicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
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
		printmenu.click();
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

}
