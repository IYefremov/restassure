package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class SettingsScreen extends iOSHDBaseScreen {
	
	
	@iOSFindBy(uiAutomator = ".scrollViews()[0].switches()[2]")
    private IOSElement inspectionsinglepagetoggle;
	
	@iOSFindBy(uiAutomator = ".scrollViews()[0].switches()[3]")
    private IOSElement duplicatestoggle;
	
	@iOSFindBy(uiAutomator = ".scrollViews()[0].switches()[18]")
    private IOSElement showtopcustomerstoggle;
	
	public SettingsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setInspectionToSinglePageInspection() {
		inspectionsinglepagetoggle.setValue("1");
	}

	public void setInspectionToNonSinglePageInspection() {
		inspectionsinglepagetoggle.setValue("0");
	}

	public void setCheckDuplicatesOn() {
		duplicatestoggle.setValue("1");
	}

	public void setCheckDuplicatesOff() {
		duplicatestoggle.setValue("0");
	}
	
	public void setShowTopCustomersOn() {
		showtopcustomerstoggle.setValue("1");
	}
	
	public void setShowTopCustomersOff() {
		showtopcustomerstoggle.setValue("0");
	}

}
