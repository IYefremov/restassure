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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	@FindBy(xpath="//*[@data-autotests-id='change-screen-popover']")
	private WebElement changescrenpopover;

	public VNextBaseScreen(SwipeableWebDriver driver) {
		this.appiumdriver = driver;
		testReporter = ExtentReportFactory.getTest();
	}
	
	public void tap(WebElement element) {
		waitABit(300);
		//new TouchActions(appiumdriver).singleTap(element).perform();
		
		
		if (VNextAppiumDriverBuilder.getPlatformName().toLowerCase().equals("android")) {
			new TouchActions(appiumdriver).singleTap(element).perform();
			/*Action tapAction = new SingleTapAction(appiumdriver.getTouch(),  (org.openqa.selenium.interactions.internal.Locatable) element);
			CompositeAction action = new CompositeAction();
			action.addAction(tapAction).perform();*/
		} else {
			int xx = element.getLocation().getX() + element.getSize().getWidth()/2;
			int yy = element.getLocation().getY() + element.getSize().getHeight()/2;
			
			new TouchAction(appiumdriver).tap(xx, yy).perform();
		}		
		waitABit(300);
	}
	
	public void setValue(WebElement element, String value) {
		tap(element);
		element.clear();
		appiumdriver.getKeyboard().sendKeys(value);
		appiumdriver.hideKeyboard();
		//element.sendKeys(value);
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
		//appiumdriver.swipe(startx, starty, startx, endy, 2000);
		waitABit(4000);
		switchToWebViewContext();
		//switchApplicationContext(AppContexts.WEB_CONTEXT);
	}
	
	public void swipeScreenLeft() {	
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('.page-content').trigger('swipeleft')");
		log(LogStatus.INFO, "Swipe To Next Screen");
		waitABit(1000);
	}
	
	public void swipeScreensLeft(int screensnumber) {		
		for (int i = 0; i < screensnumber; i++) {
			swipeScreenLeft();
			if (checkHelpPopupPresence())
				if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
					tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		}
	}
	
	public void swipeScreenRight() {
		
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('.page-content').trigger('swiperight');");		
		log(LogStatus.INFO, "Swipe Back To Previous Screen");
		waitABit(2000);
	}
	
	public void clickScreenBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='back']")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@action='back']")));
		tap(appiumdriver.findElementByXPath("//*[@action='back']"));
		//log(LogStatus.INFO, "Tap Back screen Back button");
	}
	
	public void clickScreenForwardButton() {
		tap(appiumdriver.findElementByXPath("//*[@action='forward']"));
		//log(LogStatus.INFO, "Tap Back screen Forward button");
	}
	
	public void swipeScreensRight(int screensnumber) {
		for (int i = 0; i < screensnumber; i++) 
			swipeScreenRight();	
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
			exists = appiumdriver.findElementsByXPath("//div[@class='help-button' and text()='OK, got it']").size() > 0;
		} catch (NoSuchElementException ignored) {
			exists = false;
		}
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	public void changeScreen(String screenName) {
		clickScreenTitleCaption();		
		tap(changescrenpopover.findElement(By.xpath(".//span[text()='" + screenName + "']")));
		waitABit(1000);
		log(LogStatus.INFO, "Change screen to: " + screenName);
	}
	
	public boolean isScreenPresentInChangeScreenPopoverList(String screenName) {
		return changescrenpopover.findElements(By.xpath(".//span[text()='" + screenName + "']")).size() > 0;
	}
	
	public void clickScreenTitleCaption() {
		tap(appiumdriver.findElement(By.xpath("//span[@class='page-title']")));
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(changescrenpopover));
		log(LogStatus.INFO, "Click Screen Title Caption");
	}
}
