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
	
	@iOSFindBy(uiAutomator = ".popovers()[0].navigationBars()['Print Selector'].buttons()['Print']")
    private IOSElement printserverprintbtn;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Print']")
	 private IOSElement printoptionsprintbtn;
	
	public PrintSelectorPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void checkRemotePrintServerAndSelectPrintServer(String printserver) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['Remote']")).click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['" + printserver + "']")).click();
		IOSElement remoteserverchkbox = (IOSElement) appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['Remote'].buttons()[0]"));
		if (remoteserverchkbox.getAttribute("name").equals("black unchecked"))
			remoteserverchkbox.click();				
	}
	
	public void clickPrintSelectorPrintButton() {
		printserverprintbtn.click();
		Helpers.waitABit(3000);
	}
	
	public void clickPrintOptionsPrintButton() {
		printoptionsprintbtn.click();
		Helpers.waitABit(3000);
	}
}
