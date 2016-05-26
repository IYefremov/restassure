package com.cyberiansoft.test.ios_client.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Helpers {

	private static AppiumDriver driver;
	private static WebDriverWait driverWait;

	/**
	 * Initialize the webdriver. Must be called before using any helper methods.
	 * *
	 */
	public static void init(AppiumDriver webDriver) {
		driver = webDriver;
		int timeoutInSeconds = 240;
		driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
	}

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
	 * Return an element by locator *
	 */
	public static IOSElement element(By locator) {
		return (IOSElement) w(driver.findElement(locator));
	}

	/**
	 * Return a list of elements by locator *
	 */
	public static List<MobileElement> elements(By locator) {
		return w(driver.findElements(locator));
	}

	/**
	 * Press the back button *
	 */
	public static void back() {
		driver.navigate().back();
	}

	/**
	 * Return a list of elements by tag name *
	 */
	public static List<MobileElement> tags(String tagName) {
		return elements(for_tags(tagName));
	}

	/**
	 * Return a tag name locator *
	 */
	public static By for_tags(String tagName) {
		return By.className(tagName);
	}

	/**
	 * Return a static text element by xpath index *
	 */
	public static MobileElement text(int xpathIndex) {
		return element(for_text(xpathIndex));
	}

	/**
	 * Return a static text locator by xpath index *
	 */
	public static By for_text(int xpathIndex) {
		return By.xpath("//UIAStaticText[" + xpathIndex + "]");
	}

	/**
	 * Return a static text element that contains text *
	 */
	public static MobileElement text(String text) {
		return element(for_text(text));
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
	 * Return a static text element by exact text *
	 */
	public static MobileElement text_exact(String text) {
		return element(for_text_exact(text));
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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 150);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void acceptAlert() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public static String getAlertTextAndAccept() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		String alertetxt = alert.getText();
		alert.accept();
		return alertetxt;
	}
	
	public static String getAlertTextAndCancel() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		String alertetxt = alert.getText();
		alert.dismiss();
		return alertetxt;
	}
	
	public static String getAlertText() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		String alertetxt = alert.getText();
		return alertetxt;
	}
	
	public static String getAlertTextAndClickEdit() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		String alertetxt = alert.getText();
		element(By.xpath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Edit\"]/UIAButton[@name=\"Edit\"]")).click();
		return alertetxt;
	}
	
	public static String getAlertTextAndClickOverride() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		String alertetxt = alert.getText();
		element(By.xpath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Override\"]/UIAButton[@name=\"Override\"]")).click();
		return alertetxt;
	}
	
	public static String getAlertTextAndClickCancel() {
		waitForAlert();
		Alert alert = driver.switchTo().alert();
		String alertetxt = alert.getText();
		element(By.xpath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[@name=\"Cancel\"]/UIAButton[@name=\"Cancel\"]")).click();
		return alertetxt;
	}

	public static void acceptAlertIfExists() throws InterruptedException {

		Thread.sleep(2000);
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ex) {
			// Alert not present
			ex.printStackTrace();
		}
	}

	public static void setDefaultTimeOut() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void setTimeOut(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public static void scroolTo(String scroll) {
		WebElement row = driver.findElement(By.name(scroll));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("element", ((RemoteWebElement) row).getId());
		js.executeScript("mobile: scrollTo", swipeObject);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

	}
	
	public static void scroolToByXpath(String xpath) {
		WebElement row = driver.findElement(By.xpath(xpath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("element", ((RemoteWebElement) row).getId());
		js.executeScript("mobile: scrollTo", swipeObject);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

	}
	
	public static void scroolToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> swipeObject = new HashMap<String, String>();
		swipeObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: scrollTo", swipeObject);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	public static void keyboadrType2(String value) throws InterruptedException {

		for (int i = 0; i < value.length() - 1; i++) {
			element(
					By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAKeyboard[1]/UIAKey[@name=\""
							+ value.charAt(i) + "\"]")).click();
			// Thread.sleep(100);
		}
	}

	public static void keyboadrType(String value) throws InterruptedException {
		for (int i = 0; i < value.length(); i++) {
			driver.getKeyboard().sendKeys(value.substring(i, i + 1));
			Thread.sleep(200);
		}

	}

	public static boolean elementExists(String xpath) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.findElements(By.xpath(xpath)).size() != 0;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;
	}
	
	public static boolean elementExists(By locator) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.findElements(locator).size() != 0;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return exists;

	}

	public static void hideKeyboard() throws InterruptedException {
		// driver.getKeyboard().
		//driver.hideKeyboard();
		element(By.xpath("//UIAKeyboard[1]/UIAButton[@name=\"Hide keyboard\"]"))
		.click();
		// driver.executeScript("UIATarget.localTarget().frontMostApp().keyboard().buttons()[\"Hide keyboard\"].tap();");
	}

	public static void tapInterior(int x, int y) throws InterruptedException {
		TouchAction action = new TouchAction(driver);
		/*WebElement element = driver
				.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[1]");*/
		MobileElement element = element(By.name("car_interior_color.png"));
		action.tap(element, x, y).perform();
		//action.press(element).moveTo(element, 30, 30).release().perform();
		Thread.sleep(1000);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("target.frontMostApp().mainWindow().images()[\"car_interior_color.png\"].tapWithOptions({tapOffset:{x:0.19, y:0.16}}) ;");
	}

	public static void tapExterior(int x, int y) throws InterruptedException {
		TouchAction action = new TouchAction(driver);
		/*WebElement element = driver
				.findElementByXPath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[2]");*/
		MobileElement element = element(By.name("car_exterior_color.png"));
		action.tap(element, x, y).perform();
		Thread.sleep(1000);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("target.frontMostApp().mainWindow().images()[\"car_exterior_color.png\"].tapWithOptions({tapOffset:{x:0.19, y:0.16}}) ;");
	}
	
	public static void tapCarImage() throws InterruptedException {
		TouchAction action = new TouchAction(driver);
		MobileElement element = element(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[1]"));
		
		int x = element.getLocation().getX() + element.getSize().getWidth()/2+5;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2-5;
		driver.tap(1, x, y, 1000);
		Thread.sleep(1000);
	}
	
	public static void tapRegularCarImage() throws InterruptedException {
		TouchAction action = new TouchAction(driver);
		MobileElement element = element(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAScrollView[2]/UIAImage[1]"));
		
		int x = element.getLocation().getX() + element.getSize().getWidth()/2+5;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2-5;
		driver.tap(1, x, y, 1000);
		Thread.sleep(1000);
	}

	public static void drawSignature() {
		MobileElement element = element(By
				.xpath("//UIAApplication[1]/UIAWindow[1]/UIAPopover[1]"));
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		int xxd = xx + 100;
		int yyd = yy + 100;

		int duration = 800;
		driver.swipe(xx+100, yy+100, xxd, yyd, duration);

	}
	
	public static void drawRegularQuestionsSignature() throws InterruptedException {
		MobileElement element = element(By
				.xpath("//UIAScrollView[2]/UIAScrollView[1]/UIATableView[1]/UIATableCell[2]/UIAStaticText[1]"));
		element.click();
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		int xxd = xx + 200;
		int yyd = yy + 200;

		int duration = 1000;
		driver.swipe(xx+100, yy+100, xxd, yyd, duration);
		driver.findElement(By.xpath("//UIAScrollView[2]/UIAToolbar[1]/UIAButton[@name=\"Done\"]")).click();           
	}
	
	public static void drawQuestionsSignature() throws InterruptedException {
		MobileElement element = element(By
				.xpath("//UIAScrollView[1]/UIATableView[2]/UIATableCell[2]/UIAStaticText[1]"));
		element.click();
		int xx = element.getLocation().getX();

		int yy = element.getLocation().getY();
		int xxd = xx + 200;
		int yyd = yy + 200;

		int duration = 1000;
		driver.swipe(xx+100, yy+100, xxd, yyd, duration);
		
		//driver.findElementByXPath("//UIAScrollView/UIATableView[2]/UIATableCell[2]/UIAButton[@name='Done']").click();
		MobileElement signatureview  = element(By.xpath("//UIAScrollView/UIATableView[2]/UIATableCell[2]"));

		//int x = signatureview.getLocation().getX() + signatureview.getSize().getWidth()/2+5;
		//int y = (signatureview.getLocation().getY() + signatureview.getSize().getHeight())-5;
		int x = signatureview.getLocation().getX() + 5;
		int y = (signatureview.getLocation().getY() + signatureview.getSize().getHeight())-5;
		
		//driver.tap(1, element1.getLocation().getX()+10, element1.getLocation().getY() + element1.getSize().getHeight()-10, 1000);
		driver.tap(1, x, y, duration);                 
	}

	public static void selectUIAPickerWheelValue(MobileElement picker,
			MobileElement pickerwheel, String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		while (!(pickerwheel.getAttribute("name").contains(value))) {
			driver.tap(1, pickerwheel.getLocation().getX()
					+ picker.getSize().getWidth() - 100, pickerwheel
					.getLocation().getY() + picker.getSize().getHeight() + 10,
					100);
			waitABit(1000);
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}
	}

	public static void setVIN(String vin) throws InterruptedException {

		//element(MobileBy.xpath("//UIATableCell[@name=\"VIN#\"]/UIATextField"))
		//		.click();
		element(MobileBy.xpath("//UIATableCell[@name=\"VIN#\"]/UIAStaticText"))
				.click();
		keyboadrType(vin + "\n");
	}

	public static String getMake() {
		return element(
				MobileBy.xpath("//UIATableCell[@name=\"Make\"]/UIATextField"))
				.getAttribute("value");
	}

	public static String getModel() {
		return element(
				MobileBy.xpath("//UIATableCell[@name=\"Model\"]/UIATextField"))
				.getAttribute("value");
	}

	public static String getYear() {
		return element(
				MobileBy.xpath("//UIATableCell[@name=\"Year\"]/UIATextField"))
				.getAttribute("value");
	}

	public static boolean screenIsDisplayed(String screenname) throws InterruptedException {
		Thread.sleep(1000);
		return element(
				MobileBy.xpath("//UIAButton[@name=\""
						+ screenname + "\"]")).isDisplayed();
	}

	public static void selectNextScreen(String screenname) throws InterruptedException {
		//driver.findElement(MobileBy.IosUIAutomation(".navigationBars()[0].buttons()[3]")).click();
		waitUntilVisible("//UIANavigationBar[1]/UIAButton[4]").click();
		Helpers.scroolTo(screenname);
		element(
				MobileBy.xpath("//UIAStaticText[@name=\""
						+ screenname + "\"]")).click();
		Thread.sleep(1000);
	}
	
	public static void selectDefaultNextScreen(String screenname) throws InterruptedException {
		waitUntilVisible("//UIANavigationBar[1]/UIAButton[4]").click();
		element(
				MobileBy.xpath("//UIAStaticText[@name=\""
						+ screenname + "\"]")).click();
		Thread.sleep(1000);
	}
	
	public static void makeCapture() throws InterruptedException {
		Thread.sleep(2000);
		if (elementExists(By.xpath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAButton[@name=\"OK\"]"))) {
			element(
					MobileBy.xpath("//UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[1]/UIAButton[@name=\"OK\"]"))
					.click();
		}
		element(
				MobileBy.xpath("//UIAButton[@name=\"PhotoCapture\"]"))
				.click();
		element(
				MobileBy.xpath("//UIAButton[@name=\"Use Photo\"]"))
				.click();
	}
	
	public static void waitUntilCheckLicenseDialogDisappears() throws InterruptedException {
	    driver.findElement(By.xpath("//UIAButton[@name=\"Licenses\"]"));	
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		for (int i =0; i<60; i++) {
			if (driver.findElements(By.name("LoginViewCheckLicense")).size() < 1) {
				break;
			} else {
				Thread.sleep(1000);
			}
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		//WebDriverWait wait = new WebDriverWait(driver, 40);
	    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("LoginViewCheckLicense")));
		
	}
	
	public static WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public static WebElement waitUntilVisibleBy(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
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
