package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PrintSelectorPopup extends iOSHDBaseScreen {
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Print Selector']/XCUIElementTypeButton[@name='Print']")
    private IOSElement printserverprintbtn;
	
	@iOSXCUITFindBy(accessibility = "Print")
	 private IOSElement printoptionsprintbtn;
	
	public PrintSelectorPopup() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void checkRemotePrintServerAndSelectPrintServer(String printserver) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Remote")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Remote")).click();
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(printserver)));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(printserver))).click();
		IOSElement par = (IOSElement) appiumdriver.findElement(MobileBy.
				xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + printserver + "']/.."));
		IOSElement remoteserverchkbox = (IOSElement) par.findElement(MobileBy.xpath("//XCUIElementTypeButton[1]"));
		if (remoteserverchkbox.getAttribute("name").equals("unselected"))
			remoteserverchkbox.click();
	}
	
	public void clickPrintSelectorPrintButton() {
		printserverprintbtn.click();
	}
	
	public void clickPrintOptionsPrintButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Print")));
		printoptionsprintbtn.click();
	}
}
