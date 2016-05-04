package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class SinglePageInspectionScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(name = "window screen")
    private IOSElement windowscreen;
	
	@iOSFindBy(name = "notes")
    private IOSElement signatureelement;
	
	
	public SinglePageInspectionScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getInspectionNumber() {
		return appiumdriver.findElementByXPath("//UIAToolbar[5]/UIAStaticText[3]").getText();
	}
	
	public void expandToFullScreeenSevicesSection() throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAToolbar[2]/UIAButton[@name=\"full screen\"]").click();
		Thread.sleep(2000);
	}
	
	public void expandToFullScreeenQuestionsSection() throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAToolbar[3]/UIAButton[@name=\"full screen\"]").click();
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
		return appiumdriver.findElementByXPath("//UIATableCell[@name=\"" + _answer + "\"]/UIAStaticText[@name=\"" + _answer + "\"]").isDisplayed();
	}

}
