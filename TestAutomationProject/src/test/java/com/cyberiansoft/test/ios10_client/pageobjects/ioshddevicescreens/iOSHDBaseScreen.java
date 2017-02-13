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
		action.press(backbtn).waitAction(1000).release().perform();	
		return new HomeScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(savebtn).waitAction(1000).release().perform();
		Helpers.waitABit(1000);
	}
	
	public void cancelOrder() {
		cancelbtn.click();
		acceptAlert();
	}
	
	public void selectNextScreen(String screenname) {
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
		int starty = (int) size.width / 2;	
		int startx = (int) (size.height * 0.10);
		int endx = (int) (size.height * 0.90);
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(endx, starty).waitAction(1000) .moveTo(startx, starty).release().perform();
		
		appiumdriver.swipe(starty, endx, starty, startx, 2000);
	}
	
	public void selectUIAPickerValue(String value) {
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
				action.press(appiumdriver.manage().window().getSize().width - picker.getLocation().getY() - picker.getSize().getHeight()/2 - 30, xx+30).waitAction(1000).
					release().perform();
			} else
				found = true;
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}
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
