package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public abstract class iOSBaseScreen {
	
	protected AppiumDriver appiumdriver;
	//final String uipickerxpath = ".popover().pickers()[0]";
	//final String uipickerxpath = "//XCUIElementTypePicker";
	
	public iOSBaseScreen(AppiumDriver driver) {
		appiumdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/*private MobileElement w(WebElement element) {
		return (MobileElement) element;
	}
	
	private List<MobileElement> w(List<WebElement> elements) {
		List list = new ArrayList(elements.size());
		for (WebElement element : elements) {
			list.add(w(element));
		}

		return list;
	}*/
	
	protected boolean elementExists(By locator) {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = appiumdriver.findElements(locator).size() != 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;
	}
	
	public void selectNextScreen(String screenname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElement(By.xpath("//UIANavigationBar[1]/UIAButton[4]")))).click();
		if (Helpers.elementExists("//UIAPopover[1]")) {
			appiumdriver.findElement(By.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell/UIAStaticText[contains(@name,\""+ screenname + "\")]")).click();
		} else {
			appiumdriver.findElement(By.xpath("//UIATableView[1]/UIATableCell/UIAStaticText[contains(@name,\""+ screenname + "\")]")).click();
		}
	}
	
	public By for_text(int xpathIndex) {
		return By.xpath("//UIAStaticText[" + xpathIndex + "]");
	}
	
	/*public MobileElement text(String text) {
		return element(for_text(text));
	}
	
	public IOSElement element(By locator) {
		return (IOSElement) w(appiumdriver.findElement(locator));
	}
	
	public List<MobileElement> elements(By locator) {
		return w(appiumdriver.findElements(locator));
	}*/
	
	public By for_text(String text) {
		String up = text.toUpperCase();
		String down = text.toLowerCase();
		return By
				.xpath("//UIAStaticText[@visible=\"true\" and (contains(translate(@name,\""
						+ up
						+ "\",\""
						+ down
						+ "\"), \""
						+ down
						+ "\") or contains(translate(@hint,\""
						+ up
						+ "\",\""
						+ down
						+ "\"), \""
						+ down
						+ "\") or contains(translate(@label,\""
						+ up
						+ "\",\""
						+ down
						+ "\"), \""
						+ down
						+ "\") or contains(translate(@value,\""
						+ up
						+ "\",\""
						+ down + "\"), \"" + down + "\"))]");
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
	
	public boolean isAlertExists() {
		boolean exists = false;
		try {
			appiumdriver.switchTo().alert();
			exists = true;
		} catch (NoAlertPresentException e) {
			exists = false;
		}
		return exists;
	}

	public boolean elementExists(String xpath) {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = appiumdriver.findElements(By.xpath(xpath)).size() != 0;
		appiumdriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;
	}
	
	/*public void selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		Helpers.waitABit(500);
		IOSElement pickerwhl = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel");
		while (!(pickerwhl.getAttribute("value").contains(value))) {
			//MobileElement slider = appiumdriver.findElementByClassName("UIASlider");
			pickerwhl.setValue(value);
			Helpers.waitABit(1000);
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}

	}*/
	
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
		
		TouchAction swipe = new TouchAction(appiumdriver).press(table, table.getSize().width/2, table.getSize().height-10)
                .waitAction(Duration.ofSeconds(2)).moveTo(table, table.getSize().width/2, 10).release();
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
		TouchAction swipe = new TouchAction(appiumdriver).press(endx, starty)
                .waitAction(Duration.ofSeconds(2)).moveTo(startx, starty).release();
        swipe.perform();
		//appiumdriver.swipe(endx, starty, startx, starty, 2000);
	}
	
	public void swipeScreenLeft() {
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.20);
		int endx = (int) (size.width * 0.80);
		int starty = size.height / 3;	
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(1000) .moveTo(endx, starty).release().perform();
		
		TouchAction swipe = new TouchAction(appiumdriver).press(startx, starty)
                .waitAction(Duration.ofSeconds(2)).moveTo(endx, starty).release();
        swipe.perform();
		//appiumdriver.swipe(startx, starty, endx, starty, 2000);
	}

	public void hideKeyboard() {
		appiumdriver.hideKeyboard();
		Helpers.waitABit(1000);
	}
}
