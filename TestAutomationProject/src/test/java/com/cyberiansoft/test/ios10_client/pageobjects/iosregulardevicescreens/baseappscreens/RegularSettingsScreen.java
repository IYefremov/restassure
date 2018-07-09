package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class RegularSettingsScreen extends RegularBaseAppScreen {
	
	@iOSFindBy(xpath = "//XCUIElementTypeSwitch[@name='Show all services']")
    private IOSElement showallservicestoggle;
	
	public RegularSettingsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void setCheckDuplicatesOn() {
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("SettingsTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Check VIN duplicates']/..")));
		
		if (table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Check VIN duplicates']")).getAttribute("value").equals("0")) {
			table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Check VIN duplicates']")).click();
		}
	}

	public void setCheckDuplicatesOff() {
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("SettingsTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Check VIN duplicates']/..")));
		
		if (table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Check VIN duplicates']")).getAttribute("value").equals("1")) {
			table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Check VIN duplicates']")).click();
		}
	}
	
	public void setShowTopCustomersOn() {
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("SettingsTable");
		if (table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Top customers']")).getAttribute("value").equals("0")) {
			table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Top customers']")).click();
			
		}
	}
	
	public void setShowTopCustomersOff() {
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("SettingsTable");
		if (table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Top customers']")).getAttribute("value").equals("1")) {
			//swipeToElement(table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Top customers']/..")));
			table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Top customers']")).click();
		}
	}

	public void setShowAllServicesOn() {
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("SettingsTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Inspections_ShowAllServices']/..")));
		IOSElement option = ((IOSElement) table.findElement(By.name("Inspections_ShowAllServices")));
		if (option.getAttribute("value").equals("0"))
			option.click();
	}
	
	public void setShowAllServicesOff() {
		if (showallservicestoggle.getAttribute("value").equals("1"))
			showallservicestoggle.click();
	}

	public void setInsvoicesCustomLayoutOff() {
		MobileElement  table  = (MobileElement) appiumdriver.findElementByAccessibilityId("SettingsTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Use in invoices']/..")));
		if (table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Use in invoices']")).getAttribute("value").equals("1"))
			table.findElement(By.xpath("//XCUIElementTypeSwitch[@name='Use in invoices']")).click();
	}
}
