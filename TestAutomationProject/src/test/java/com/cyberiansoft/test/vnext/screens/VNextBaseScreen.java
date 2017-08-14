package com.cyberiansoft.test.vnext.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.CompositeAction;
import org.openqa.selenium.interactions.touch.SingleTapAction;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.Locatable;

import com.cyberiansoft.test.reporting.ExtentReportFactory;
import com.cyberiansoft.test.vnext.builder.VNextAppiumDriverBuilder;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;

public class VNextBaseScreen {
	
	SwipeableWebDriver appiumdriver;
	ExtentTest testReporter;

	public VNextBaseScreen(SwipeableWebDriver driver) {
		this.appiumdriver = driver;
		testReporter = ExtentReportFactory.getTest();
	}
	
	public void tap(WebElement element) {
		waitABit(300);
		//new TouchActions(appiumdriver).singleTap(element).perform();
		
		if (VNextAppiumDriverBuilder.getPlatformName().toLowerCase().equals("android")) {
			Action tapAction = new SingleTapAction(appiumdriver.getTouch(), (Locatable) element);
			CompositeAction action = new CompositeAction();
			action.addAction(tapAction).perform();
		} else {
			int xx = element.getLocation().getX() + element.getSize().getWidth()/2;
			int yy = element.getLocation().getY() + element.getSize().getHeight()/2;
			
			new TouchAction(appiumdriver).tap(xx, yy).perform();
		}		
		waitABit(300);
	}
	
	public void setValue(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}
	
	public void tapListElement(WebElement scrollablelist, String value) {
		while (scrollablelist.findElements(By.xpath(".//div[text()='" + value + "']")).size() < 1) {
			swipingVertical();
			
		}		
		WebElement elem = scrollablelist.findElement(By.xpath(".//div[text()='" + value + "']"));	
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);
		tap(scrollablelist.findElement(By.xpath(".//div[text()='" + value + "']")));
	}
	
	public void clickHardwareBackButton() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		appiumdriver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
		//appiumdriver.navigate().back();
		try {
			appiumdriver.hideKeyboard();
            } catch (Exception e) {
            }
		switchToWebViewContext();
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
		log(LogStatus.INFO, "Click Hardware Back Button");
	}
	
	public void swipingVertical() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height * 0.75);
		int endy = (int) (size.height * 0.25);
		int startx = size.width / 2;
		appiumdriver.swipe(startx, starty, startx, endy, 2000);
		waitABit(4000);
		switchToWebViewContext();
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void swipeScreenLeft() {	
		//if (appiumdriver instanceof JavascriptExecutor)
		 //   ((JavascriptExecutor)appiumdriver).executeScript("$('.page').trigger('swipeleft');");
		tap(appiumdriver.findElementByXPath("//*[@action='forward']"));
		log(LogStatus.INFO, "Swipe To Next Screen");
		waitABit(1000);
	}
	
	public void swipeScreensLeft(int screensnumber) {
		/*switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.75);
		int endx = (int) (size.width * 0.25);
		int starty = size.height / 8;
		for (int i = 0; i < screensnumber; i++) {
			new TouchActions(appiumdriver).down(startx, starty).move(endx, starty).up(endx, starty).perform();
			waitABit(4000);
			log(LogStatus.INFO, "Swipe To Next Screen");
		}
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
		Assert.assertTrue(switchToWebViewContext())
		*/
		for (int i = 0; i < screensnumber; i++) {
			if (appiumdriver instanceof JavascriptExecutor)
			    ((JavascriptExecutor)appiumdriver).executeScript("$('.page').trigger('swipeleft');");
			waitABit(1000);	
		}	
	}
	
	public void swipeScreenRight() {
		
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('.page').trigger('swiperight');");
		//tap(appiumdriver.findElementByXPath("//i[@action='back']"));		
		log(LogStatus.INFO, "Swipe Back To Previous Screen");
		waitABit(1000);
	}
	
	public void swipeScreensRight(int screensnumber) {
		//if (appiumdriver instanceof JavascriptExecutor)
		//    ((JavascriptExecutor)appiumdriver).executeScript("$('.page').trigger('swiperight');");
		for (int i = 0; i < screensnumber; i++) {
			tap(appiumdriver.findElementByXPath("//*[@action='back']"));
			log(LogStatus.INFO, "Swipe To Next Screen");
		}
		
		/*switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.25);
		int endx = (int) (size.width * 0.75);
		int starty = size.height / 10;
		for (int i = 0; i < screensnumber; i++) {
			new TouchActions(appiumdriver).down(startx, starty).move(endx, starty).up(endx, starty).perform();
			waitABit(4000);
			log(LogStatus.INFO, "Swipe Back To Previous Screen");
		}
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
		Assert.assertTrue(switchToWebViewContext());*/
		
	}
	
	public void switchToWebViewContext() {
		Set<String> contextNames = appiumdriver.getContextHandles();
		List<String> handlesList = new ArrayList(contextNames);
		if (handlesList.size() > 2)
			appiumdriver.context(handlesList.get(2));
		else
			appiumdriver.context(handlesList.get(1));
	}
	
	public void switchApplicationContext(String appcontext) {
		Set<String> contextNames = appiumdriver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains(appcontext)) {
				appiumdriver.context(contextName);			
			}
		}
	}	
	
	public void waitABit(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void log(LogStatus logstatus, String logmessage) {
		if (testReporter != null)
			testReporter.log(logstatus, logmessage);		
	}
	
	protected boolean checkHelpPopupPresence() {
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean exists = false;
		try {
			exists = appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed();
		} catch (NoSuchElementException ignored) {
			exists = false;
		}
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
}
