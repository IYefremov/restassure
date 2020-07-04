package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
public abstract class iOSHDBaseScreen extends iOSBaseScreen {
	
	
	
	//@iOSXCUITFindByuiAutomator = ".navigationBars()[0].buttons()[\"Back\"].withValueForKey(1, \"isVisible\")")
	//@iOSXCUITFindBy(accessibility = "Back")
	//private IOSElement backbtn;
	
	/*@iOSXCUITFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSXCUITFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;*/

	
	
	public iOSHDBaseScreen() {
		super();
		//PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
	}

	public boolean elementExists(String elementName) {
		boolean exists = false;
		exists =  appiumdriver.findElementsByAccessibilityId(elementName).size() > 0;
		return exists;
	}
	
	public void clickCancelButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Cancel")));
		appiumdriver.findElementByAccessibilityId("Cancel").click();		
	}
	
	public void swipeScreenRight() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height *0.85);
		
		int startx = (int) (size.width * 0.15);
		int endx = (int) (size.width * 0.80);
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
        HashMap<String, String> swipeObject = new HashMap<>();
        swipeObject.put("direction", "left");
        js.executeScript("mobile:swipe", swipeObject);
		
	}

	//Todo  always  return  true
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
        HashMap<String, String> swipeObject = new HashMap<>();
        swipeObject.put("direction", "up");
        js.executeScript("mobile:swipe", swipeObject);
        
        /*js = (JavascriptExecutor) appiumdriver;
        swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "up");
        js.executeScript("mobile:swipe", swipeObject);*/
	}
	
	public void swipeTableUp(WebElement tableCell, WebElement table) {
		int tableHeight = table.getSize().getHeight();
		boolean swipe = true;
		
		while (swipe) {
			if (tableCell.isDisplayed()) {
				swipe = false;
				break;
			} else if ((tableCell.getLocation().getY()*0.9 > tableHeight)) {
				JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
				HashMap<String, String> scrollObject = new HashMap<>();
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
		HashMap<String, String> scrollObject = new HashMap<>();
		//scrollObject.put("direction", "down");
		scrollObject.put("element", element.getId());
		scrollObject.put("toVisible", "true");
		js.executeScript("mobile: scroll", scrollObject);
	}

	public void scrollTable(MobileElement table, String elementName) {
		boolean visible = table.findElementByAccessibilityId(elementName).isDisplayed();
		int count = 0;
		while ((!visible) & (count < 20)) {
			JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
			HashMap<String, Object> scrollObject = new HashMap<>();

			scrollObject.put("element", table.getId());
			scrollObject.put("direction", "down");
			js.executeScript("mobile: scroll", scrollObject);
			count++;
			visible = table.findElementByAccessibilityId(elementName).isDisplayed();
		}
	}
		
	public void scrollToElement(String elementValue) {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
		HashMap<String, Object> scrollObject = new HashMap<>();
		scrollObject.put("predicateString", "name == '" + elementValue + "'");
		js.executeScript("mobile: scroll", scrollObject);
	}

	public void swipeScrollViewElement(WebElement elementtoswipe) {
		boolean swipe = true;

		while (swipe) {
			if (elementtoswipe.isDisplayed()) {
				swipe = false;
				break;
			} else {
				Map<String, Object> args = new HashMap<>();
				MobileElement list = (MobileElement) appiumdriver.findElement(By.className("XCUIElementTypeScrollView"));
				args.put("direction", "down");
				args.put("name", null);
				args.put("element", list.getId());
				appiumdriver.executeScript("mobile: scroll", args);
			}
		}
	}
	
}
