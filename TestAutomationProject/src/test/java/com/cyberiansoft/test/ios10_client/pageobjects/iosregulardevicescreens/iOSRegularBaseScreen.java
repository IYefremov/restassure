package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import java.time.Duration;
import java.util.HashMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class iOSRegularBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//XCUIElementTypePicker";
	
	//@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@visible=\"true\" and @name=\"Back\"]")
    //private IOSElement backbtn;
	
	@iOSFindBy(accessibility = "Back")
    private IOSElement backbtn;
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = uipickerxpath)
    private IOSElement picker;
	
	@iOSFindBy(className = "UIAPickerWheel")
    private IOSElement pickerwheel;
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[2]")
    private IOSElement changescreenbtn;
	
	public iOSRegularBaseScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), iOSRegularBaseScreen.class);
	}
	
	public RegularHomeScreen clickHomeButton() throws InterruptedException {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Back"))).click();
		//backbtn.click();
		Thread.sleep(1000);
		return new RegularHomeScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		if (appiumdriver.findElements(MobileBy.AccessibilityId("Save")).size() < 1) {
			clickChangeScreen();
		}
		savebtn.click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}
	
	public void cancelOrder() {
		clickChangeScreen();
		clickCancel();
		acceptAlert();
		Helpers.waitABit(1000);
	}
	
	public void clickCancel() {
		Helpers.waitABit(1000);
		cancelbtn.click();
	}
	
	public void clickChangeScreen() {
		changescreenbtn.click();
		Helpers.waitABit(1000);
	}
	
	public void acceptAlertByCoords() {
		int xx = appiumdriver.manage().window().getSize().getWidth();
		int yy = appiumdriver.manage().window().getSize().getHeight();
		TouchAction tap = new TouchAction(appiumdriver).tap(xx/2+50, yy/2+50);              
        tap.perform();
		//appiumdriver.tap(1, xx/2+50, yy/2+50, 1000);
	}
	
	public void declineAlertByCoords() {
		int xx = appiumdriver.manage().window().getSize().getWidth();
		int yy = appiumdriver.manage().window().getSize().getHeight();
		TouchAction tap = new TouchAction(appiumdriver).tap(xx/2-50, yy/2+50);              
        tap.perform();
		//appiumdriver.tap(1, xx/2-50, yy/2+50, 1000);
	}
	
	public void selectNextScreen(String screenname) {
		appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(MobileBy.iOSNsPredicateString("name contains '/'")).click();
		if (! appiumdriver.findElementByAccessibilityId(screenname).isDisplayed()) {
			swipeToElement(appiumdriver.
					findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + screenname + "']/..")));
			appiumdriver.findElementByAccessibilityId(screenname).click();
		}
		appiumdriver.findElementByAccessibilityId(screenname).click();
		Helpers.waitABit(1000);
	}
	
	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public void selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		
		WebElement picker = appiumdriver.findElementByClassName("XCUIElementTypePicker");
		while (!(pickerwheel.getAttribute("value").contains(value))) {
			TouchAction action = new TouchAction(appiumdriver);
			action.tap(picker.getSize().getWidth()/2, picker
					.getLocation().getY() + picker.getSize().getHeight()/2+40).perform();
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}
	}
	
	public void swipeToElement(WebElement elementtoswipe) {
		boolean swipe = true;
		int screenheight = (int) (appiumdriver.manage().window().getSize().getHeight()*0.90);
		
		while (swipe) {
			//System.out.println("+++++1" + (elementtoswipe.getLocation().getY() > screenheight));
			//System.out.println("+++++2" + (elementtoswipe.getLocation().getY() > appiumdriver.manage().window().getSize().getHeight()*0.80));
			//System.out.println("+++++3" + (elementtoswipe.getLocation().getY() > appiumdriver.manage().window().getSize().getHeight()*0.90));
			if ((elementtoswipe.getLocation().getY() > screenheight)) {
			//if (!elementtoswipe.isDisplayed())
				/*JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
		        HashMap scrollObject = new HashMap<>();
		        scrollObject.put("element", ((RemoteWebElement) elementtoswipe).getId());
	            //scrollObject.put("toVisible", "true"); // optional but needed sometimes
	            js.executeScript("mobile:scroll", scrollObject);
	           */
				JavascriptExecutor js1 = (JavascriptExecutor) appiumdriver;
				HashMap<String, String> scrollObject1 = new HashMap<String, String>();
				scrollObject1.put("direction", "up");
				//scrollObject.put("element", ((IOSElement) ELEMENT).getId());
				js1.executeScript("mobile: swipe", scrollObject1);
				
				//swipeScreenUp();
				//swipeTableUp();
			}
			else
				swipe = false;
		}
	}
	
	public void swipeTableUp() {
		
		MobileElement table = (MobileElement) appiumdriver.
				findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/.."));
		
		TouchAction swipe = new TouchAction(appiumdriver).press(table, table.getSize().width/2, table.getSize().height-10)
                .waitAction(Duration.ofSeconds(2)).moveTo(table, table.getSize().width/2, 10).release();
        swipe.perform();
	}

}
