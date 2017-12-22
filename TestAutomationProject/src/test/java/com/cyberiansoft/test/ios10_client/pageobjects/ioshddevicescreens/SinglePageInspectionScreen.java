package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class SinglePageInspectionScreen extends iOSHDBaseScreen {
	
	//@iOSFindBy(accessibility  = "window screen")
    //private IOSElement windowscreen;
	
	//@iOSFindBy(accessibility  = "notes")
   // private IOSElement signatureelement;
	
	
	public SinglePageInspectionScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getInspectionNumber() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name contains 'E-00'").getText();
	}
	
	public void expandToFullScreeenSevicesSection() throws InterruptedException {
		((IOSElement) appiumdriver.findElementsByAccessibilityId("full screen").get(0)).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeToolbar/XCUIElementTypeButton[@name=\"full screen\"]").click();
		Thread.sleep(1000);
	}
	
	public void expandToFullScreeenQuestionsSection() throws InterruptedException {
		((IOSElement) appiumdriver.findElementsByAccessibilityId("full screen").get(1)).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeOther[3]/XCUIElementTypeToolbar/XCUIElementTypeButton[@name=\"full screen\"]").click();
		Thread.sleep(1000);
	}
	
	public void collapseFullScreen() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("window screen").click();
		Thread.sleep(1000);
	}
	
	public boolean isSignaturePresent() {
		return appiumdriver.findElementsByAccessibilityId("notes").size() > 0;
	}
	
	public boolean isAnswerPresent(String _answer) {
		return appiumdriver.findElementsByAccessibilityId(_answer).size() > 0;
	}

}
