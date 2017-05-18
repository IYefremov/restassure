package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.utils.Helpers;
public class iOSHDBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//UIAPicker";
	
	//@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"].withValueForKey(1, \"isVisible\")")
	@iOSFindBy(accessibility = "Back")
	private IOSElement backbtn;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = uipickerxpath)
    private IOSElement picker;
	
	@iOSFindBy(xpath = uipickerxpath + "/UIAPickerWheel[1]")
    private IOSElement pickerwheel;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[4]")
    private IOSElement changescreenbtn;
	
	public iOSHDBaseScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public HomeScreen clickHomeButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(backbtn).waitAction(300).release().perform();
		Helpers.waitABit(1000);
		return new HomeScreen(appiumdriver);		
	}
	
	public void clickSaveButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(savebtn).waitAction(1000).release().perform();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(500);
	}
	
	public void cancelOrder() {
		clickCancelButton();
		acceptAlert();
	}
	
	public void clickCancelButton() {
		cancelbtn.click();		
	}
	
	public void selectNextScreen(String screenname) {
		Helpers.waitABit(500);
		appiumdriver.findElementByXPath("//XCUIElementTypeButton[contains(@name, 'WizardStepsButton')]").click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + screenname + "']")).waitAction(300).release().perform();
		//appiumdriver.findElementByAccessibilityId(screenname).click();
		Helpers.waitABit(1000);
	}
	
	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	public void closeDublicaterServicesWarningByClickingEdit() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void closeDublicaterServicesWarningByClickingCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton[@name='Cancel']").click();
	}
	
	public void closeDublicaterServicesWarningByClickingOverride() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Override").click();
	}
	
	public void swipeScreenRight() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.width *0.7);
		
		int startx = (int) (size.width * 0.20);
		int endx = (int) (size.width * 0.80);
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		/*int starty = (int) size.height / 2;	
		int startx = (int) (size.width * 0.90);
		int endx = (int) (size.width * 0.10);*/
		
		appiumdriver.swipe(endx, starty, startx, starty, 2000);
		/*System.out.println("====" );
		appiumdriver.swipe(startx, starty, endx, starty, 2000);
		System.out.println("====" );
		TouchAction act = new TouchAction(appiumdriver);		
		act.press(endx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		System.out.println("====" );
		act = new TouchAction(appiumdriver);
		act.press(starty, startx).waitAction(3000) .moveTo(starty, endx).release().perform();
		//appiumdriver.swipe(startx, endy, startx, starty, 2000);*/
	}
	
	public void swipeScreenRight1() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.width/2);
		
		int startx = (int) (size.width * 0.10);
		int endx = (int) (size.width * 0.90);
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		/*int starty = (int) size.height / 2;	
		int startx = (int) (size.width * 0.90);
		int endx = (int) (size.width * 0.10);*/
		
		appiumdriver.swipe(endx, starty, startx, starty, 2000);
		/*System.out.println("====" );
		appiumdriver.swipe(startx, starty, endx, starty, 2000);
		System.out.println("====" );
		TouchAction act = new TouchAction(appiumdriver);		
		act.press(endx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		System.out.println("====" );
		act = new TouchAction(appiumdriver);
		act.press(starty, startx).waitAction(3000) .moveTo(starty, endx).release().perform();
		//appiumdriver.swipe(startx, endy, startx, starty, 2000);*/
	}
	
	public boolean selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		boolean found = false;
		Helpers.waitABit(1000);
		//selectUIAPickerValue(year);
		IOSElement picker = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePicker");
		IOSElement pickerwhl = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel");
		int  xx = pickerwhl.getLocation().getX();
		int yy = pickerwhl.getLocation().getY();
		while (!found) {
			
			if (!(pickerwhl.getAttribute("value").contains(value))) {
				TouchAction action = new TouchAction(appiumdriver);
				//action.press(appiumdriver.manage().window().getSize().width - picker.getLocation().getY() - picker.getSize().getHeight()/2 - 30, xx+30).waitAction(1000).
				//action.press(xx+picker.getSize().getWidth()/2, yy + picker.getSize().getHeight()/2 +70).waitAction(1000).
				action.press(xx+picker.getSize().getWidth()/2, (int) (yy + picker.getSize().getHeight()*0.8)).waitAction(1000).
				release().perform();
				
				
			} else {
				found = true;
				break;
			}
			
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}
		return found;
	}
	
	public void swipeScreenUp() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.width * 0.80);
		//Find endy point which is at top side of screen.
		int endy = (int) (size.width * 0.20);
		//Find horizontal point where you wants to swipe. It is in middle of screen width.
		int startx = size.height / 2;
		//System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);
		//Swipe from Bottom to Top.
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(2000) .moveTo(startx, endy).release().perform();
		//appiumdriver.swipe(startx, starty, startx, endy, 2000);
		Helpers.waitABit(2000);
	}
}
