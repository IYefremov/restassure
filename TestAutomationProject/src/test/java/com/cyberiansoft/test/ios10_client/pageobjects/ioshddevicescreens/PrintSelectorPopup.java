package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class PrintSelectorPopup extends iOSHDBaseScreen {
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name='Print Selector']/XCUIElementTypeButton[@name='Print']")
    private IOSElement printserverprintbtn;
	
	@iOSFindBy(accessibility = "Print")
	 private IOSElement printoptionsprintbtn;
	
	public PrintSelectorPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void checkRemotePrintServerAndSelectPrintServer(String printserver) {
		appiumdriver.findElement(MobileBy.AccessibilityId("Remote")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(printserver)).click();
		Helpers.waitABit(500);
		IOSElement par = (IOSElement) appiumdriver.findElement(MobileBy.
				xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@value='" + printserver + "']/.."));
		IOSElement remoteserverchkbox = (IOSElement) par.findElement(MobileBy.xpath("//XCUIElementTypeButton[1]"));
		if (remoteserverchkbox.getAttribute("name").equals("unselected"))
			remoteserverchkbox.click();	
		Helpers.waitABit(1000);
	}
	
	public void clickPrintSelectorPrintButton() {
		//appiumdriver.findElement(MobileBy.AccessibilityId("Print Selector")).findElement(MobileBy.AccessibilityId("Print")).click();
		printserverprintbtn.click();
		Helpers.waitABit(3000);
	}
	
	public void clickPrintOptionsPrintButton() {
		printoptionsprintbtn.click();
		Helpers.waitABit(5000);
	}
}
