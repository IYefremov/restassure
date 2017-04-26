package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class InspectionScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Make']/XCUIElementTypeTextField")
    private IOSElement makefld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Model']/XCUIElementTypeTextField")
    private IOSElement modelfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Year']/XCUIElementTypeTextField")
    private IOSElement yearfldvalue;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savechangesbtn;
	
	@iOSFindBy(accessibility  = "Advisor")
    private IOSElement advisorcell;

	public InspectionScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void saveChanges() {
		savechangesbtn.click();
	}

	public String clickSaveWithAlert() {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin) throws InterruptedException  {
		appiumdriver.findElementByAccessibilityId("VIN#").click();

		//appiumdriver.findElementByAccessibilityId("VIN#").click();
		Helpers.waitABit(500);
		((IOSDriver) appiumdriver).getKeyboard().pressKey(vin);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
		Helpers.waitABit(500);
	}

	public String getMake() {
		return makefld.getAttribute("value");
	}

	public String getModel() {
		return modelfld.getAttribute("value");

	}

	public String getYear() {
		return yearfldvalue.getAttribute("value");
	}

	public void seletAdvisor(String advisor) {
		advisorcell.click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
	}

}
