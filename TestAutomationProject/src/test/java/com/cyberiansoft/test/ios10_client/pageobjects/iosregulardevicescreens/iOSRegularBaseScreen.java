package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public abstract class iOSRegularBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//XCUIElementTypePicker";
	
	public iOSRegularBaseScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), iOSRegularBaseScreen.class);
	}
		
	public boolean elementExists(String elementName) {
		boolean exists = false;
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exists =  appiumdriver.findElementsByAccessibilityId(elementName).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	public RegularHomeScreen clickHomeButton() throws InterruptedException {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();

		return new RegularHomeScreen(appiumdriver);
	}
	
	public void clickCancel() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Cancel")).click();
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

	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public void selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		WebElement picker = wait.until(ExpectedConditions.presenceOfElementLocated( MobileBy.className("XCUIElementTypePicker")));

		while (!(appiumdriver.findElementByClassName("XCUIElementTypePickerWheel").getAttribute("value").contains(value))) {
			TouchAction action = new TouchAction(appiumdriver);
			action.tap(picker.getSize().getWidth()/2, picker
					.getLocation().getY() + picker.getSize().getHeight()/2+40).perform();
			Helpers.waitABit(5000);
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
			if (elementtoswipe.isDisplayed()) {
				swipe = false;
				break;
			} else if ((elementtoswipe.getLocation().getY() > screenheight)) {
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
