package com.cyberiansoft.test.vnext.screens;

import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.reporting.ExtentReportFactory;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.NoSuchContextException;
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
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(element));
		new TouchActions(appiumdriver).singleTap(element).perform();
		waitABit(300);
	}
	
	public void setValue(WebElement element, String value) {
		element.clear();
		//appiumdriver.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
		element.sendKeys(value);
	}
	
	public void tapListElement(WebElement scrollablelist, String value) {
		while (scrollablelist.findElements(By.xpath(".//div[text()='" + value + "']")).size() < 1) {
			swipingVertical();
			
			/*switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			int yscreenresolution = appiumdriver.manage().window().getSize().getHeight();
			appiumdriver.swipe(20, yscreenresolution-180, 20, 140, 1000);
			switchApplicationContext(AppContexts.WEB_CONTEXT);*/
			
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
		Assert.assertTrue(switchToWebViewContext());
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
		Assert.assertTrue(switchToWebViewContext());
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void swipeScreenLeft() {		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.75);
		int endx = (int) (size.width * 0.25);
		int starty = size.height / 10;
		new TouchActions(appiumdriver).down(startx, starty).move(endx, starty).up(endx, starty).perform();
		//switchApplicationContext(AppContexts.WEB_CONTEXT);		
		Assert.assertTrue(switchToWebViewContext());
		waitABit(4000);
		log(LogStatus.INFO, "Swipe To Next Screen");
	}
	
	public void swipeScreensLeft(int screensnumber) {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
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
		Assert.assertTrue(switchToWebViewContext());
		
	}
	
	public void swipeScreenRight() {
		waitABit(1000);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.30);
		int endx = (int) (size.width * 0.70);
		int starty = size.height / 10;
		new TouchActions(appiumdriver).down(startx, starty).move(endx, starty).up(endx, starty).perform();
		Assert.assertTrue(switchToWebViewContext());
		//switchApplicationContext(AppContexts.WEB_CONTEXT);		
		log(LogStatus.INFO, "Swipe Back To Previous Screen");
	}
	
	public void swipeScreensRight(int screensnumber) {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
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
		Assert.assertTrue(switchToWebViewContext());
		
	}
	
	public boolean switchToWebViewContext() {
		boolean switched = false;
		final int ITERATIONS_COUNT = 200;
		for (int i = 0; i < ITERATIONS_COUNT; i++) {
			Set<String> contextNames = appiumdriver.getContextHandles();
			for (String contextName : contextNames) {
				System.out.println("++++++" + contextName);
				if (contextName.equals("WEBVIEW_com.automobiletechnologies.ReconPro")) {
					System.out.println("----------" + contextName);
					try {
						appiumdriver.context(contextName);
						System.out.println("---------- Success");
						i = ITERATIONS_COUNT;
						switched = true;
						break;
					} catch (NoSuchContextException ex) {
						System.out.println("---------- Fail");
						waitABit(1000);
					}
				} else if (!contextName.equals("NATIVE_APP")){
					System.out.println("=============" + contextName);
					waitABit(1000);
				}
			}
		}
		return switched;
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
}
