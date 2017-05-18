package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class SettingsScreen extends iOSHDBaseScreen {
	
	
	@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[3]")
    private IOSElement inspectionsinglepagetoggle;
	
	@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[1]")
	
    private IOSElement duplicatestoggle;
	
	@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[12]/XCUIElementTypeSwitch[2]")
    private IOSElement showtopcustomerstoggle;
	
	public SettingsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setInspectionToSinglePageInspection() {
		if (inspectionsinglepagetoggle.getAttribute("value").equals("false"))
			inspectionsinglepagetoggle.click();
	}

	public void setInspectionToNonSinglePageInspection() {
		if (inspectionsinglepagetoggle.getAttribute("value").equals("true"))
			inspectionsinglepagetoggle.click();
	}

	public void setCheckDuplicatesOn() {
		if (duplicatestoggle.getAttribute("value").equals("false"))
			duplicatestoggle.click();
	}

	public void setCheckDuplicatesOff() {
		if (duplicatestoggle.getAttribute("value").equals("true"))
			duplicatestoggle.click();
	}
	
	public void setShowTopCustomersOn() {
		if (showtopcustomerstoggle.getAttribute("value").equals("false"))
			showtopcustomerstoggle.click();
	}
	
	public void setShowTopCustomersOff() {
		if (showtopcustomerstoggle.getAttribute("value").equals("true"))
			showtopcustomerstoggle.click();
	}

}
