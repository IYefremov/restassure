package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularSettingsScreen extends iOSRegularBaseScreen {
	
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
		swipeToElement(table.findElements(By.xpath("//XCUIElementTypeSwitch[@name='Show all services']/..")).get(1));
		IOSElement option = ((IOSElement) table.findElements(By.xpath("//XCUIElementTypeSwitch[@name='Show all services']")).get(1));
		if (option.getAttribute("value").equals("false"))
			option.click();
	}
	
	public void setShowAllServicesOff() {
		if (showallservicestoggle.getAttribute("value").equals("1"))
			showallservicestoggle.click();
	}
}
