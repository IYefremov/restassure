package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SinglePageInspectionScreen extends BaseWizardScreen {
	
	//@iOSFindBy(accessibility  = "window screen")
    //private IOSElement windowscreen;
	
	//@iOSFindBy(accessibility  = "notes")
   // private IOSElement signatureelement;
	
	
	public SinglePageInspectionScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getInspectionNumber() {
		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByIosNsPredicate("name contains 'E-00'").getText();
	}
	
	public void expandToFullScreeenSevicesSection() throws InterruptedException {
		((IOSElement) appiumdriver.findElementsByAccessibilityId("full screen").get(0)).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeToolbar/XCUIElementTypeButton[@name=\"full screen\"]").click();
	}
	
	public void expandToFullScreeenQuestionsSection() throws InterruptedException {
		((IOSElement) appiumdriver.findElementsByAccessibilityId("full screen").get(1)).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeOther[3]/XCUIElementTypeToolbar/XCUIElementTypeButton[@name=\"full screen\"]").click();
	}
	
	public void collapseFullScreen() throws InterruptedException {
		appiumdriver.findElementByAccessibilityId("window screen").click();
	}
	
	public boolean isSignaturePresent() {
		return appiumdriver.findElementsByAccessibilityId("notes").size() > 0;
	}
	
	public boolean isAnswerPresent(String _answer) {
		return appiumdriver.findElementsByAccessibilityId(_answer).size() > 0;
	}


	public void selectNextScreen(String screenname) {
		IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
		navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
		appiumdriver.findElementByAccessibilityId(screenname).click();
	}


	public void clickSaveButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
		appiumdriver.findElementByAccessibilityId("Save").click();
	}
}
