package com.cyberiansoft.test.vnext.screens;

import java.util.Set;

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

public class VNextBaseScreen {
	
	SwipeableWebDriver appiumdriver;
	ExtentTest testReporter;

	public VNextBaseScreen(SwipeableWebDriver driver) {
		this.appiumdriver = driver;
		testReporter = ExtentReportFactory.getTest();
	}
	
	public void tap(WebElement element) {
		waitABit(300);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		new TouchActions(appiumdriver).singleTap(element).perform();
		waitABit(300);
	}
	
	public void setValue(WebElement element, String value) {
		element.clear();
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
	
	public void swipingVertical() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height * 0.70);
		int endy = (int) (size.height * 0.30);
		int startx = size.width / 2;
		appiumdriver.swipe(startx, starty, startx, endy, 3000);
		//Swipe from Top to Bottom.
		//appiumdriver.swipe(startx, endy, startx, starty, 3000);
		switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void swipeScreenLeft() {		
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.75);
		int endx = (int) (size.width * 0.25);
		int starty = size.height / 10;
		new TouchActions(appiumdriver).down(startx, starty).move(endx, starty).up(endx, starty).perform();
		switchApplicationContext(AppContexts.WEB_CONTEXT);		
		waitABit(2000);
		testReporter.log(LogStatus.INFO, "Swipe To Next Screen");
	}
	
	public void swipeScreenRight() {
		waitABit(1000);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		Dimension size = appiumdriver.manage().window().getSize();
		int startx = (int) (size.width * 0.30);
		int endx = (int) (size.width * 0.70);
		int starty = size.height / 10;
		new TouchActions(appiumdriver).down(startx, starty).move(endx, starty).up(endx, starty).perform();
		switchApplicationContext(AppContexts.WEB_CONTEXT);		
		testReporter.log(LogStatus.INFO, "Swipe To Next Screen");
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
}
