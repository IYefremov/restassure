package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), iOSHDBaseScreen.class);
	}
	
	public HomeScreen clickHomeButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(backbtn).waitAction(Duration.ofSeconds(1)).release().perform();
		Helpers.waitABit(1000);
		return new HomeScreen(appiumdriver);		
	}
	
	public void clickSaveButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(savebtn).waitAction(Duration.ofSeconds(1)).release().perform();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
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
		IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
		navbar.findElementByXPath("//XCUIElementTypeButton[contains(@name, 'WizardStepsButton')]").click();
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + screenname + "']")).waitAction(Duration.ofSeconds(1)).release().perform();
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
        Helpers.waitABit(1000);
	}
	
	public void closeDublicaterServicesWarningByClickingOverride() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Override").click();
	}
	
	public void swipeScreenRight() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height *0.85);
		
		int startx = (int) (size.width * 0.15);
		int endx = (int) (size.width * 0.80);
		//TouchAction act = new TouchAction(appiumdriver);
		//act.press(startx, starty).waitAction(3000) .moveTo(endx, starty).release().perform();
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
        HashMap<String, String> swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "left");
        js.executeScript("mobile:swipe", swipeObject);
		
	}
	
	public void swipeScreenRight1() {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
        HashMap<String, String> swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "left");
        js.executeScript("mobile:swipe", swipeObject);
	}
	
	public boolean selectUIAPickerValue(String value) {
		int defaultwheelnumer = 10;
		int clicks = 0;
		boolean found = false;
		Helpers.waitABit(1000);
		//selectUIAPickerValue(year);
		IOSElement picker = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePicker");
		IOSElement pickerwhl = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel");
		int  xx = picker.getSize().getWidth()/2;
		int yy = (int) (picker.getSize().getHeight()*0.75);

		//170-210
		while (!found) {
			found = pickerwhl.getAttribute("value").contains(value);
			if (!found) {
				
				TouchAction action = new TouchAction(appiumdriver);
				action.tap(picker, xx, yy).perform();
				
				
				Helpers.waitABit(1000);
				
			} else {
				break;
			}
			
			clicks = clicks+1;
			if (clicks > defaultwheelnumer)
				break;
		}
		return found;
	}
	
	public void swipeScreenUp() {
		JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
        HashMap<String, String> swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "down");
        js.executeScript("mobile:swipe", swipeObject);
	}
	
	public void swipeTableUp(WebElement tableCell, WebElement table) {
		int tableHeight = (int) (table.getSize().getHeight());
		boolean swipe = true;
		
		while (swipe) {
			//if (!tableCell.isDisplayed()) {
			if ((tableCell.getLocation().getY()*0.9 > tableHeight)) {
				JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
				HashMap<String, String> scrollObject = new HashMap<String, String>();
				scrollObject.put("direction", "up");
				scrollObject.put("element", ((IOSElement) table).getId());
				js.executeScript("mobile: swipe", scrollObject);
			} else
				swipe = false;
		}
		
		
	}
}
