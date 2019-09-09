package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.ElementOption.element;

public abstract class iOSBaseScreen {
	
	protected AppiumDriver appiumdriver;
	
	public iOSBaseScreen() {
		appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
	}

	protected boolean elementExists(By locator) {
		boolean exists = appiumdriver.findElements(locator).size() != 0;
		return exists;
	}
	
	public void waitForAlert() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeAlert")));
	}
	
	public void acceptAlert() {
		//waitForAlert();
		Alert alert = appiumdriver.switchTo().alert();
		alert.accept();
	}

	public boolean elementExists(String xpath) {
		boolean exists = appiumdriver.findElements(By.xpath(xpath)).size() != 0;
		return exists;
	}
	
	public void swipeScreenUp() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height * 0.80);
		//Find endy point which is at top side of screen.
		int endy = (int) (size.height * 0.20);
		//Find horizontal point where you wants to swipe. It is in middle of screen width.
		int startx = size.width / 2;
		//System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);
		//Swipe from Bottom to Top.
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(2000) .moveTo(startx, endy).release().perform();
		MobileElement table = (MobileElement) appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/.."));
		
		TouchAction swipe = new TouchAction(appiumdriver).press(element(table, table.getSize().width/2, table.getSize().height-10))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(element(table, table.getSize().width/2, 10)).release();
        swipe.perform();

		BaseUtils.waitABit(1000);
	}
	
	public void scrollScreenUp() {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
	}
	
	public void swipeScreenRight() {
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.20);
		int endx = (int) (size.width * 0.80);
		int starty = (int) size.height / 2;
		TouchAction swipe = new TouchAction(appiumdriver).press(PointOption.point(endx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(startx, starty)).release();
        swipe.perform();
	}
	
	public void swipeScreenLeft() {
		Helpers.waitABit(300);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.20);
		int endx = (int) (size.width * 0.80);
		int starty = size.height / 3;
		
		TouchAction swipe = new TouchAction(appiumdriver).press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(endx, starty)).release();
        swipe.perform();
	}

	public void hideKeyboard() {
		appiumdriver.hideKeyboard();
		Helpers.waitABit(1000);
	}
}
