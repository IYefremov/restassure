package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class SettingsScreen extends iOSHDBaseScreen {
	
	
	@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[2]")
    private IOSElement inspectionsinglepagetoggle;
	
	@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[4]")
    private IOSElement duplicatestoggle;
	
	@iOSFindBy(xpath = "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeSwitch[19]")
    private IOSElement showtopcustomerstoggle;
	
	public SettingsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
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
