package com.cyberiansoft.test.ios10_client.utils;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.ElementOption.element;

public abstract class Helpers {

	//private static AppiumDriver<MobileElement> driver;
	private static WebDriverWait driverWait;

	/**
	 * Initialize the webdriver. Must be called before using any helper methods.
	 * *
	 */

	/**
	 * Wrap WebElement in MobileElement *
	 */
	private static MobileElement w(WebElement element) {
		return (MobileElement) element;
	}

	/**
	 * Wrap WebElement in MobileElement *
	 */
	private static List<MobileElement> w(List<WebElement> elements) {
		List list = new ArrayList(elements.size());
		for (WebElement element : elements) {
			list.add(w(element));
		}

		return list;
	}

	/**
	 * Return a tag name locator *
	 */
	public static By for_tags(String tagName) {
		return By.className(tagName);
	}



	/**
	 * Return a static text locator that contains text *
	 */
	public static By for_text(String text) {
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



	/**
	 * Return a static text locator by exact text *
	 */
	public static By for_text_exact(String text) {
		return By.xpath("//UIAStaticText[@visible=\"true\" and (@name=\""
				+ text + "\" or @hint=\"" + text + "\" or @label=\"" + text
				+ "\" or @value=\"" + text + "\")]");
	}

	/**
	 * Wait 30 seconds for locator to find an element *
	 */
	public static MobileElement wait(By locator) {
		return w(driverWait.until(ExpectedConditions
				.visibilityOfElementLocated(locator)));
	}

	/**
	 * Wait 60 seconds for locator to find all elements *
	 */
	public static List<MobileElement> waitAll(By locator) {
		return w(driverWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(locator)));
	}

	public static void waitForAlert() {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//WebDriverWait wait = new WebDriverWait(driver, 300);
		//wait.until(ExpectedConditions.alertIsPresent());
		FluentWait<WebDriver> wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 300);

		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeAlert")));
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void acceptAlert() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		alert.accept();
	}

	public static String getAlertTextAndAccept() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		String alertetxt = alert.getText();
		alert.accept();
		return alertetxt.replace("  ", " ");
	}
	
	public static String getAlertTextAndCancel() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		String alertetxt = alert.getText().replace("  ", " ");
		alert.dismiss();
		return alertetxt;
	}
	
	public static String getAlertText() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		String alertetxt = alert.getText();
		return alertetxt;
	}
	
	public static String getAlertTextAndClickEdit() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		String alertetxt = alert.getText();
		DriverBuilder.getInstance().getAppiumDriver().findElementByXPath("//XCUIElementTypeButton[@name=\"Edit\"]").click();
		return alertetxt;
	}
	
	public static String getAlertTextAndClickOverride() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		String alertetxt = alert.getText();
		DriverBuilder.getInstance().getAppiumDriver().findElementByXPath("//XCUIElementTypeButton[@name=\"Override\"]").click();
		return alertetxt;
	}
	
	public static String getAlertTextAndClickCancel() {
		waitForAlert();
		Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
		String alertetxt = alert.getText();
		DriverBuilder.getInstance().getAppiumDriver().findElementByXPath("//XCUIElementTypeButton[@name=\"Cancel\"]").click();
		return alertetxt;
	}

	public static void acceptAlertIfExists() {

		waitABit(2000);
		try {
			Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			ex.printStackTrace();
		}
	}

	public static void setDefaultTimeOut() {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void setTimeOut(int seconds) {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public static void scroolTo(String scroll) {
		WebElement row = DriverBuilder.getInstance().getAppiumDriver().findElement(By.name(scroll));
		JavascriptExecutor js = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("element", ((RemoteWebElement) row).getId());
		js.executeScript("mobile: scroll", swipeObject);
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

	}
	
	public static void scroolToByXpath(String xpath) {
		WebElement row = DriverBuilder.getInstance().getAppiumDriver().findElement(By.xpath(xpath));
		JavascriptExecutor js = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("element", ((RemoteWebElement) row).getId());
		js.executeScript("mobile: scrollTo", swipeObject);
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

	}
	
	public static void scroolToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: scrollTo", swipeObject);
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	public static void keyboadrType(String value) {
		for (int i = 0; i < value.length(); i++) {
			DriverBuilder.getInstance().getAppiumDriver().getKeyboard().sendKeys(value.substring(i, i + 1));
			waitABit(200);
		}

	}

	public static boolean elementExists(String xpath) {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = DriverBuilder.getInstance().getAppiumDriver().findElements(By.xpath(xpath)).size() != 0;
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;
	}
	
	public static boolean elementExists(By locator) {
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = DriverBuilder.getInstance().getAppiumDriver().findElements(locator).size() != 0;
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;

	}

	public static void tapInterior(int x, int y) {
		TouchAction action = new TouchAction(DriverBuilder.getInstance().getAppiumDriver());
		/*WebElement element = driver
				.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[1]");*/
		MobileElement element = (MobileElement) DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		action.tap(element(element, x, y)).perform();
		//action = new TouchAction(driver);
		//action.press(element, x, y).waitAction(1000).release().perform();
		//action.press(element).moveTo(element, 30, 30).release().perform();
		waitABit(2000);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("target.frontMostApp().mainWindow().images()[\"car_interior_color.png\"].tapWithOptions({tapOffset:{x:0.19, y:0.16}}) ;");
	}

	public static void tapExterior(int x, int y) {
		/*TouchAction action = new TouchAction(driver);

		MobileElement element = (MobileElement) driver
				.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		action.tap(element, x, y).perform();
		Thread.sleep(1000);*/
		
		TouchAction action = new TouchAction(DriverBuilder.getInstance().getAppiumDriver());
		MobileElement element = DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeImage");
		
		//int x = element.getLocation().getX() + element.getSize().getWidth()/2+5;
		//int y = element.getLocation().getY() + element.getSize().getHeight()/2-5;
		action.tap(element(element, x, y)).perform();
		
		//element.tap(1, x, y, 1000);
		waitABit(1000);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("target.frontMostApp().mainWindow().images()[\"car_exterior_color.png\"].tapWithOptions({tapOffset:{x:0.19, y:0.16}}) ;");
	}
	
	public static void tapCarImage() { 
		
		TouchAction action = new TouchAction(DriverBuilder.getInstance().getAppiumDriver());
		MobileElement element = DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeImage");
		
		int x = element.getLocation().getX() + element.getSize().getWidth()/2+5;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2-5;
		action.tap(element(element, x, y)).perform();
		waitABit(1000);
	}
	
	public static void tapRegularCarImage() {
		TouchAction action = new TouchAction(DriverBuilder.getInstance().getAppiumDriver());
		MobileElement element = DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeImage");
		
		int x = element.getLocation().getX() + element.getSize().getWidth()/2+5;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2-5;
		action.tap(element(element, x, y)).perform();
		
		//element.tap(1, x, y, 1000);
		waitABit(1000);
	}
	
	public static void drawRegularQuestionsSignature() {
		MobileElement element = DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeStaticText[1]");
		element.click();
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		int xxd = xx + 200;
		int yyd = yy + 200;

		TouchAction action = new TouchAction(DriverBuilder.getInstance().getAppiumDriver());
		action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(xxd, yyd)).release().perform();
		
		//driver.swipe(xx+100, yy+100, xxd, yyd, duration);
		DriverBuilder.getInstance().getAppiumDriver().findElementByAccessibilityId("Done").click();
	}
	
	public static void drawQuestionsSignature() {
		MobileElement element = DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[2]");
		element.click();
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		int xxd = xx + 200;
		int yyd = yy + 200;

		int duration = 1000;
		TouchAction action = new TouchAction(DriverBuilder.getInstance().getAppiumDriver());
		action.press(PointOption.point(xx + 100,yy + 100)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).moveTo(PointOption.point(xx + 200, yy + 200)).release().perform();
		TouchAction swipe = new TouchAction(DriverBuilder.getInstance().getAppiumDriver()).press(PointOption.point(xx+100, yy+100))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(xxd, yyd)).release();
        swipe.perform();
		//driver.swipe(xx+100, yy+100, xxd, yyd, duration);
		
		//driver.findElementByXPath("//UIAScrollView/UIATableView[2]/UIATableCell[2]/UIAButton[@name='Done']").click();
		MobileElement signatureview  = DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTable/XCUIElementTypeCell[2]");

		//int x = signatureview.getLocation().getX() + signatureview.getSize().getWidth()/2+5;
		//int y = (signatureview.getLocation().getY() + signatureview.getSize().getHeight())-5;
		int x = signatureview.getLocation().getX() + 5;
		int y = (signatureview.getLocation().getY() + signatureview.getSize().getHeight())-5;
		
		//driver.tap(1, element1.getLocation().getX()+10, element1.getLocation().getY() + element1.getSize().getHeight()-10, 1000);
		TouchAction tap = new TouchAction(DriverBuilder.getInstance().getAppiumDriver()).tap(PointOption.point(x, y)).perform();
		//driver.tap(1, x, y, duration);                 
	}

	public static void setVIN(String vin) throws InterruptedException {

		//element(MobileBy.xpath("//UIATableCell[@name=\"VIN#\"]/UIATextField"))
		//		.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//XCUIElementTypeStaticText[@name=\"VIN#\"]")
				.click();
		keyboadrType(vin + "\n");
	}

	public static void makeCapture()  {
		waitABit(2000);
		if (elementExists(By.xpath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAButton[@name=\"OK\"]"))) {
			DriverBuilder.getInstance().getAppiumDriver()
					.findElementByXPath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAButton[@name=\"OK\"]")
					.click();
		}
		DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//UIAButton[@name=\"PhotoCapture\"]")
				.click();
		DriverBuilder.getInstance().getAppiumDriver()
				.findElementByXPath("//UIAButton[@name=\"Use Photo\"]")
				.click();
	}
	
	public static void waitUntilCheckLicenseDialogDisappears() {
		DriverBuilder.getInstance().getAppiumDriver().findElement(By.xpath("//UIAButton[@name=\"Licenses\"]"));
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		for (int i =0; i<60; i++) {
			if (DriverBuilder.getInstance().getAppiumDriver().findElements(By.name("LoginViewCheckLicense")).size() < 1) {
				break;
			} else {
				waitABit(1000);
			}
		}
		DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		//WebDriverWait wait = new WebDriverWait(driver, 40);
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("LoginViewCheckLicense")));
		
	}
	
	public static WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public static WebElement waitUntilVisibleBy(By locator) {
		WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 10);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));		
	}
	
	public static void waitABit(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
