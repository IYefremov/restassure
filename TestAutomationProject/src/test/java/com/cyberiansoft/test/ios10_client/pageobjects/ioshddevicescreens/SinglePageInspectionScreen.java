package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class SinglePageInspectionScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility  = "window screen")
    private IOSElement windowscreen;
	
	@iOSFindBy(accessibility  = "notes")
    private IOSElement signatureelement;
	
	
	public SinglePageInspectionScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getInspectionNumber() {
		return appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[5]/XCUIElementTypeStaticText[3]").getText();
	}
	
	public void expandToFullScreeenSevicesSection() throws InterruptedException {
		appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[2]/XCUIElementTypeButton[@name=\"full screen\"]").click();
		Thread.sleep(2000);
	}
	
	public void expandToFullScreeenQuestionsSection() throws InterruptedException {
		appiumdriver.findElementByXPath("//XCUIElementTypeToolbar[3]/XCUIElementTypeButton[@name=\"full screen\"]").click();
		Thread.sleep(2000);
	}
	
	public void collapseFullScreen() throws InterruptedException {
		windowscreen.click();
		Thread.sleep(2000);
	}
	
	public boolean isSignaturePresent() throws InterruptedException {
		return signatureelement.isDisplayed();
	}
	
	public boolean isAnswerPresent(String _answer) throws InterruptedException {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + _answer + "']").isDisplayed();
	}

}
