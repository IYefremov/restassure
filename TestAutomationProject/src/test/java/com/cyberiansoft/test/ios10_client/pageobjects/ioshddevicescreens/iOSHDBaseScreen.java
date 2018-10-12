package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
public abstract class iOSHDBaseScreen extends iOSBaseScreen {
	
	
	
	//@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"].withValueForKey(1, \"isVisible\")")
	//@iOSFindBy(accessibility = "Back")
	//private IOSElement backbtn;
	
	/*@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;*/

	
	
	public iOSHDBaseScreen() {
		super();
		//PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
	}

	public boolean elementExists(String elementName) {
		boolean exists = false;
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exists =  appiumdriver.findElementsByAccessibilityId(elementName).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	public void clickCancelButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Cancel")));
		appiumdriver.findElementByAccessibilityId("Cancel").click();		
	}

	public void closeDublicaterServicesWarningByClickingEdit() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void closeDublicaterServicesWarningByClickingCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton[@name='Cancel']").click();
	}
	
	public void closeDublicaterServicesWarningByClickingOverride() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Override").click();
	}
	
	public void swipeScreenRight() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height *0.85);
		
		int startx = (int) (size.width * 0.15);
		int endx = (int) (size.width * 0.80);
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
        HashMap<String, String> swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "left");
        js.executeScript("mobile:swipe", swipeObject);
		
	}
	
	public boolean selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		boolean found = false;
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypePicker")));
		//IOSElement picker = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePicker");
		IOSElement pickerwhl = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel");

		if (!pickerwhl.getAttribute("value").contains(value)) {
			pickerwhl.setValue(value);
			//picker.setValue(value);
		}
		return true;
	}
	
	public void swipeScreenUp() {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
        HashMap<String, String> swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "up");
        js.executeScript("mobile:swipe", swipeObject);
        
        /*js = (JavascriptExecutor) appiumdriver;
        swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "up");
        js.executeScript("mobile:swipe", swipeObject);*/
	}
	
	public void swipeTableUp(WebElement tableCell, WebElement table) {
		int tableHeight = (int) (table.getSize().getHeight());
		boolean swipe = true;
		
		while (swipe) {
			//if (!tableCell.isDisplayed()) {
			if (tableCell.isDisplayed()) {
				swipe = false;
				break;
			} else if ((tableCell.getLocation().getY()*0.9 > tableHeight)) {
				JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
				HashMap<String, String> scrollObject = new HashMap<String, String>();
				scrollObject.put("direction", "up");
				//scrollObject.put("element", ((IOSElement) table).getId());
				scrollObject.put("element", ((IOSElement) table).getId());
				js.executeScript("mobile: swipe", scrollObject);
			} else
				swipe = false;
		}
	}

	public void scrollToElement(MobileElement element) {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		scrollObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: scroll", scrollObject);
	}
		
	public void scrollToElement(String elementValue) {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
		HashMap scrollObject = new HashMap<>();
		scrollObject.put("predicateString", "value == '" + elementValue + "'");
		js.executeScript("mobile: scroll", scrollObject);
	}
	
}
