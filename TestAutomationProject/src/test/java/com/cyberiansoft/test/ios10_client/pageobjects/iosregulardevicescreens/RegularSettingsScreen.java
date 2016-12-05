package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularSettingsScreen extends iOSRegularBaseScreen {
	
	final String chekdublicates = "Check duplicates";
	
	@iOSFindBy(uiAutomator = ".tableViews()[0].cells()[\"" + chekdublicates + "\"].switches()[\"" + chekdublicates + "\"]")
    private IOSElement duplicatestoggle;
	
	@iOSFindBy(uiAutomator = ".tableViews()[0].cells()[\"Top customers\"].switches()[\"Top customers\"]")
    private IOSElement showtopcustomerstoggle;
	
	@iOSFindBy(uiAutomator = ".tableViews()[0].cells()[\"Show all services\"].switches()[\"Show all services\"]")
    private IOSElement showallservicestoggle;
	
	public RegularSettingsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void setCheckDuplicatesOn() {
		Helpers.scroolTo(chekdublicates);
		duplicatestoggle.setValue("1");
	}

	public void setCheckDuplicatesOff() {
		Helpers.scroolTo(chekdublicates);
		duplicatestoggle.setValue("0");
	}
	
	public void setShowTopCustomersOn() {
		showtopcustomerstoggle.setValue("1");
	}
	
	public void setShowTopCustomersOff() {
		showtopcustomerstoggle.setValue("0");
	}

	public void setShowAllServicesOn() {
		Helpers.scroolToElement((WebElement) appiumdriver.findElements(MobileBy.xpath("//UIATableView[@name='SettingsTable']/UIATableCell[@name='Show all services']/UIASwitch[@name='Show all services']")).get(1));
		Helpers.waitABit(1000);		
		IOSElement option = ((IOSElement) appiumdriver.findElements(MobileBy.xpath("//UIATableView[@name='SettingsTable']/UIATableCell[@name='Show all services']/UIASwitch[@name='Show all services']")).get(1));
		option.setValue("1");
	}
	
	public void setShowAllServicesOff() {
		showallservicestoggle.setValue("0");
	}
}
