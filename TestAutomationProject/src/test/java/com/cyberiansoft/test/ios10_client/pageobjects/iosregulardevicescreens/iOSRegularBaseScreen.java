package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	
	@iOSFindBy(className = "XCUIElementTypePickerWheel")
    private IOSElement pickerwheel;
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[2]")
    private IOSElement changescreenbtn;
	
	public iOSRegularBaseScreen(AppiumDriver driver) {
		super(driver);
	}
	
	public RegularHomeScreen clickHomeButton() throws InterruptedException {
		Thread.sleep(1000);
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		//wait.until(ExpectedConditions.visibilityOf(backbtn)).click();
		backbtn.click();
		//Thread.sleep(1000);
		return new RegularHomeScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		if (appiumdriver.findElements(MobileBy.AccessibilityId("Save")).size() < 1) {
			clickChangeScreen();
		}
		savebtn.click();
		Helpers.waitABit(500);
	}
	
	public void cancelOrder() {
		clickChangeScreen();
		clickCancel();
		acceptAlert();
	}
	
	public void clickCancel() {
		appiumdriver.tap(1, cancelbtn, 200);
	}
	
	public void clickChangeScreen() {
		changescreenbtn.click();
	}
	
	public void acceptAlertByCoords() {
		int xx = appiumdriver.manage().window().getSize().getWidth();
		int yy = appiumdriver.manage().window().getSize().getHeight();
		appiumdriver.tap(1, xx/2+50, yy/2+50, 1000);
	}
	
	public void declineAlertByCoords() {
		int xx = appiumdriver.manage().window().getSize().getWidth();
		int yy = appiumdriver.manage().window().getSize().getHeight();
		appiumdriver.tap(1, xx/2-50, yy/2+50, 1000);
	}
	
	public void selectNextScreen(String screenname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(changescreenbtn)).click();
		//Helpers.scroolTo(screenname);
		appiumdriver.findElementByAccessibilityId(screenname).click();
		Helpers.waitABit(1000);
	}
	
	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
	
	public void selectUIAPickerValue(String value) throws InterruptedException {
		int defaultwheelnumer = 10;
		int clicks = 0;
		if (!pickerwheel.getAttribute("value").contains(value))
			pickerwheel.swipe(SwipeElementDirection.UP, pickerwheel.getSize().getHeight()/6, 0, 100);
		
		/*IOSElement pkw =  (IOSElement)  appiumdriver.findElementByXPath("//XCUIElementTypePickerWheel");
		IOSElement pk =  (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypePicker");
		while (!(pkw.getAttribute("value").contains(value))) {
			pkw.setValue(value);
			//pk.setValue(value);
			TouchAction action = new TouchAction(appiumdriver);
			action.press(pk.getLocation().getX()
					+ pk.getSize().getWidth()/2, pk
					.getLocation().getY() + pk.getSize().getHeight()/2).waitAction(3000).release().perform();
			pickerwheel.swipe(SwipeElementDirection.UP, pickerwheel.getSize().getHeight()/6, 0, 100);
			//pickerwheel.tap(1, 100);*/
			/*appiumdriver.tap(1, pickerwheel.getLocation().getX()
					+ picker.getSize().getWidth() - 100, pickerwheel
					.getLocation().getY() + picker.getSize().getHeight() - 10,
					100);
			Thread.sleep(1000);*/
			/*clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}*/

	}

}
