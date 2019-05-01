package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBaseScreen {

	protected AppiumDriver<MobileElement> appiumdriver;
	
	@FindBy(xpath="//*[@data-autotests-id='change-screen-popover']")
	private WebElement changescrenpopover;

	public VNextBaseScreen(AppiumDriver<MobileElement> driver) {
		this.appiumdriver = driver;
	}
	
	public void tap(WebElement element) {

		BaseUtils.waitABit(300);
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		//wait.until(ExpectedConditions.elementToBeClickable(element));




		WaitUtils.click(element);
		//tapOldAndroidversion(element);

		//JavascriptExecutor executor = (JavascriptExecutor)appiumdriver;
		//executor.executeScript("arguments[0].click();", element);

		//element.click();
		//new TouchActions(appiumdriver).singleTap(element).perform();
		/*Action tapAction = new SingleTapAction(appiumdriver.getTouch(),  (org.openqa.selenium.interactions.internal.Locatable) element);
		CompositeAction action = new CompositeAction();
		action.addAction(tapAction).perform();*/
		
		/*if (VNextAppiumDriverBuilder.getPlatformName().toLowerCase().equals("android")) {
			new TouchActions(appiumdriver).singleTap(element).perform();
			
		} else {
			int xx = element.getLocation().getX() + element.getSize().getWidth()/2;
			int yy = element.getLocation().getY() + element.getSize().getHeight()/2;
			
			new TouchAction(appiumdriver).tap(xx, yy).perform();
		}		
		waitABit(300);*/
		
	}

	/*public void tapOldAndroidversion(WebElement element){
		float[] elementLocation = getElementCenter(element);
		int elementCoordinateX = (int) Math.round(elementLocation[0]);
		int elementCoordinateY = (int) Math.round(elementLocation[1]);
		System.out.println("+++++" + elementCoordinateX);
		System.out.println("+++++" + elementCoordinateY);
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		TouchAction action = new TouchAction(appiumdriver);
		new TouchAction((MobileDriver) appiumdriver).tap(PointOption.point((int) elementCoordinateX, (int) elementCoordinateY)).perform();
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}*/

	private float[] getElementCenter(WebElement element){
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;

		Long webviewWidth = (Long) js.executeScript("return screen.width");
		Long webviewHeight = (Long)  js.executeScript("return screen.height");

		int elementLocationX = element.getLocation().getX();
		int elementLocationY = element.getLocation().getY();

		int elementWidthCenter = element.getSize().getWidth() / 2;
		int elementHeightCenter = element.getSize().getHeight() / 2;
		int elementWidthCenterLocation = elementWidthCenter + elementLocationX;
		int elementHeightCenterLocation = elementHeightCenter + elementLocationY;

		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		float deviceScreenWidth, deviceScreenHeight;

		int offset = 84;

		deviceScreenWidth = appiumdriver.manage().window().getSize().getWidth();
		deviceScreenHeight = appiumdriver.manage().window().getSize().getHeight();

		float ratioWidth = deviceScreenWidth / webviewWidth.intValue();
		float ratioHeight = deviceScreenHeight / webviewHeight.intValue();

		float elementCenterActualX = elementWidthCenterLocation * ratioWidth;
		float elementCenterActualY = (float) 0.0;
		if ((elementHeightCenterLocation * ratioHeight) > 1000)
			elementCenterActualY = (elementHeightCenterLocation * ratioHeight)+offset*2;
		else
		    elementCenterActualY = (elementHeightCenterLocation * ratioHeight)+offset;
		float[] elementLocation = {elementCenterActualX, elementCenterActualY};

		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		return elementLocation;
	}

	public void setValue(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
		//appiumdriver.getKeyboard().sendKeys(value);
		try {
			appiumdriver.hideKeyboard();
		} catch (WebDriverException e) {
			//todo:
		}
		BaseUtils.waitABit(500);
	}
	
	public void tapListElement(WebElement scrollablelist, String value) {



		WebElement elem = scrollablelist.findElement(By.xpath(".//div[contains(text(), '" + value + "')]"));
		JavascriptExecutor je = (JavascriptExecutor) appiumdriver;
		je.executeScript("arguments[0].scrollIntoView(true);",elem);
		BaseUtils.waitABit(1000);
		scrollablelist.findElement(By.xpath(".//div[contains(text(), '" + value + "')]")).click();

		//tap(scrollablelist.findElement(By.xpath(".//div[contains(text(), '" + value + "')]")));
	}
	
	public void swipeScreenLeft() {	
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('.page-content').trigger('swipeleft')");
		BaseUtils.waitABit(1000);
	}
	
	public void swipeScreensLeft(int screensnumber) {		
		for (int i = 0; i < screensnumber; i++) {
			swipeScreenLeft();
			if (checkHelpPopupPresence())
				try {
					if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
						tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
				} catch (NoSuchElementException e) {
				//do nothing
				} catch (StaleElementReferenceException e1) {
					//do nothing
				}
			BaseUtils.waitABit(1000);
		}
	}
	
	public void swipeScreenRight() {
		
		if (appiumdriver instanceof JavascriptExecutor)
		    ((JavascriptExecutor)appiumdriver).executeScript("$('.page-content').trigger('swiperight');");		
		BaseUtils.waitABit(2000);
	}
	
	public void clickScreenBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='back']")));
		wait = new WebDriverWait(appiumdriver, 5);
		WebElement backBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@action='back']")));
		tap(backBtn);
	}
	
	public void clickScreenForwardButton() {
		tap(appiumdriver.findElementByXPath("//*[@action='forward']"));
	}
	
	public void swipeScreensRight(int screensnumber) {
		for (int i = 0; i < screensnumber; i++) 
			swipeScreenRight();	
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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(changescrenpopover));
		//BaseUtils.waitABit(1000);
		//AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		//appiumdriver.findElementByXPath("//android.view.View[@content-desc='Services All Services']").click();
		//AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		tap(changescrenpopover.findElement(By.xpath(".//span[text()='" + screenName + "']")));

	}
	
	public boolean isScreenPresentInChangeScreenPopoverList(String screenName) {
		return changescrenpopover.findElements(By.xpath(".//span[text()='" + screenName + "']")).size() > 0;
	}
	
	public void clickScreenTitleCaption() {
		tap(appiumdriver.findElement(By.xpath("//span[@class='page-title']")));
		BaseUtils.waitABit(1000);
		try {
			appiumdriver.findElement(By.xpath("//span[@class='page-title']")).click();
		} catch (WebDriverException e) {

		}
	}
	
	public boolean elementExists(String xpath) {
		boolean exists = false;
		appiumdriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		exists =  appiumdriver.findElementsByXPath(xpath).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
}
