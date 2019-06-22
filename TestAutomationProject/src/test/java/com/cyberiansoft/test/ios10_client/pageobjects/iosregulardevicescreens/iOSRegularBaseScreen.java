package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class iOSRegularBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//XCUIElementTypePicker";
	
	public iOSRegularBaseScreen() {
		super();

	}
		
	public boolean elementExists(String elementName) {
		boolean exists = false;
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exists =  appiumdriver.findElementsByAccessibilityId(elementName).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	public RegularHomeScreen clickHomeButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();

		return new RegularHomeScreen();
	}
	
	public void clickCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		WebElement picker = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Cancel")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}
	
	public void selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		WebElement picker = wait.until(ExpectedConditions.presenceOfElementLocated( MobileBy.className("XCUIElementTypePicker")));

BaseUtils.waitABit(5000);
		((IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel")).setValue(value);
		/*while (!(appiumdriver.findElementByClassName("XCUIElementTypePickerWheel").getAttribute("value").contains(value))) {
			TouchAction action = new TouchAction(appiumdriver);
			action.tap(PointOption.point(picker.getSize().getWidth()/2, picker
					.getLocation().getY() + picker.getSize().getHeight()/2+40)).perform();
			Helpers.waitABit(1000);
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}*/
	}
	
	public void swipeToElement(WebElement elementtoswipe) {
		boolean swipe = true;
		int screenheight = (int) (appiumdriver.manage().window().getSize().getHeight()*0.90);

		while (swipe) {
			if (elementtoswipe.isDisplayed()) {
				swipe = false;
				break;
			} else if ((elementtoswipe.getLocation().getY() > screenheight)) {

				JavascriptExecutor js1 = (JavascriptExecutor) appiumdriver;
				HashMap<String, String> scrollObject1 = new HashMap<String, String>();
				scrollObject1.put("direction", "up");
				js1.executeScript("mobile: swipe", scrollObject1);

			}
			else
				swipe = false;
		}
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

	public void scrollToElement(String elementValue) {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
		HashMap<String, Object> scrollObject = new HashMap<>();
		scrollObject.put("predicateString", "name == '" + elementValue + "'");
		js.executeScript("mobile: scroll", scrollObject);
	}

}
