package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

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
	//final String uipickerxpath = ".popover().pickers()[0]";
	//final String uipickerxpath = "//XCUIElementTypePicker";
	
	public iOSBaseScreen() {
		appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
		//PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		//appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	protected boolean elementExists(By locator) {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = appiumdriver.findElements(locator).size() != 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;
	}
	
	public void waitForAlert() {
		appiumdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeAlert")));
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void acceptAlert() {
		//waitForAlert();
		Alert alert = appiumdriver.switchTo().alert();
		alert.accept();
	}

	public boolean elementExists(String xpath) {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = appiumdriver.findElements(By.xpath(xpath)).size() != 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
		
        /*swipe = new TouchAction(appiumdriver).press(startx, endy)
                .waitAction(Duration.ofSeconds(2)).moveTo(startx, starty).release();
        swipe.perform();*/
		//appiumdriver.swipe(startx, starty, startx, endy, 500);
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
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(endx, starty).waitAction(1000) .moveTo(startx, starty).release().perform();
		TouchAction swipe = new TouchAction(appiumdriver).press(PointOption.point(endx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(startx, starty)).release();
        swipe.perform();
		//appiumdriver.swipe(endx, starty, startx, starty, 2000);
	}
	
	public void swipeScreenLeft() {
		Helpers.waitABit(300);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.20);
		int endx = (int) (size.width * 0.80);
		int starty = size.height / 3;	
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(1000) .moveTo(endx, starty).release().perform();
		
		TouchAction swipe = new TouchAction(appiumdriver).press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(endx, starty)).release();
        swipe.perform();
		//appiumdriver.swipe(startx, starty, endx, starty, 2000);
	}

	public void hideKeyboard() {
		appiumdriver.hideKeyboard();
		Helpers.waitABit(1000);
	}
}
