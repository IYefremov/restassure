package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;


public abstract class iOSRegularBaseScreen extends iOSBaseScreen {
	
	public iOSRegularBaseScreen() {
		super();

	}
		
	public boolean elementExists(String elementName) {
		boolean exists = false;
		exists =  appiumdriver.findElementsByAccessibilityId(elementName).size() > 0;
		return exists;
	}
	
	public void clickHomeButton() {
		WaitUtils.getGeneralFluentWait().until(driver -> {
			appiumdriver.findElement(MobileBy.AccessibilityId("Back")).click();
			return true;
		});
	}
	
	public void clickCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Cancel")));
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
	}
	
	public void selectUIAPickerValue(String value) {

		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		IOSElement picker = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypePickerWheel")));
		picker.setValue(value);
	}
	
	public void swipeToElement(WebElement elementtoswipe) {
		boolean swipe = true;
		int screenheight = (int) (appiumdriver.manage().window().getSize().getHeight()*0.90);

		while (swipe) {
			if (elementtoswipe.isDisplayed()) {
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
