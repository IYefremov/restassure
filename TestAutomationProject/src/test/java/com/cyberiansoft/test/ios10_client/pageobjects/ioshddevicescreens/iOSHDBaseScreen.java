package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
public class iOSHDBaseScreen extends iOSBaseScreen {
	
	
	
	//@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"].withValueForKey(1, \"isVisible\")")
	//@iOSFindBy(accessibility = "Back")
	//private IOSElement backbtn;
	
	/*@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;*/

	
	
	public iOSHDBaseScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
	}
	
	public HomeScreen clickHomeButton() {
		appiumdriver.findElementByAccessibilityId("Back").click();
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		//wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Back"))).click();
		//TouchAction action = new TouchAction(appiumdriver);
		//action.press(appiumdriver.findElementByAccessibilityId("Back")).waitAction(waitOptions(ofSeconds(2))).release().perform();
		return new HomeScreen(appiumdriver);		
	}
	
	public boolean elementExists(String elementName) {
		boolean exists = false;
		appiumdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exists =  appiumdriver.findElementsByAccessibilityId(elementName).size() > 0;
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return exists;
	}
	
	
	public void clickSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
		Helpers.waitABit(500);
	}
	
	public void cancelOrder() {
		clickCancelButton();
		acceptAlert();
	}
	
	public void clickCancelButton() {
		appiumdriver.findElementByAccessibilityId("Cancel").click();		
	}
	
	public void selectNextScreen(String screenname) {
		IOSElement navbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar");
		navbar.findElementByIosNsPredicate("name ENDSWITH 'WizardStepsButton'").click();
		//navbar.findElementByXPath("//XCUIElementTypeButton[contains(@name, 'WizardStepsButton')]").click();
		appiumdriver.findElementByAccessibilityId(screenname).click();
		//action.press(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + screenname + "']")).waitAction(Duration.ofSeconds(1)).release().perform();
		//appiumdriver.findElementByAccessibilityId(screenname).click();
	}
	
	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	public void closeDublicaterServicesWarningByClickingEdit() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByAccessibilityId("Edit").click();
	}
	
	public void closeDublicaterServicesWarningByClickingCancel() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Duplicate services")));
        appiumdriver.findElementByXPath("//XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton[@name='Cancel']").click();
        Helpers.waitABit(1000);
	}
	
	public void closeDublicaterServicesWarningByClickingOverride() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated (MobileBy.AccessibilityId("Duplicate services")));
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
		//selectUIAPickerValue(year);
		IOSElement picker = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePicker");
		IOSElement pickerwhl = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypePickerWheel");
		int  xx = picker.getSize().getWidth()/2;
		int yy = (int) (picker.getSize().getHeight()*0.75);

		while (!found) {
			found = pickerwhl.getAttribute("value").contains(value);
			if (!found) {
				
				TouchAction action = new TouchAction(appiumdriver);
				action.tap(picker, xx, yy).perform();
				
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
        swipeObject.put("direction", "up");
        js.executeScript("mobile:swipe", swipeObject);
        
        /*js = (JavascriptExecutor) appiumdriver;
        swipeObject = new HashMap<String, String>();
        swipeObject.put("direction", "up");
        js.executeScript("mobile:swipe", swipeObject);*/
	}
	
	public void swipeTableUp(WebElement tableCell, WebElement table) {
		int tableHeight = (int) (table.getSize().getHeight());
		boolean swipe = true;
		
		while (swipe) {
			//if (!tableCell.isDisplayed()) {
			if (tableCell.isDisplayed()) {
				swipe = false;
				break;
			} else if ((tableCell.getLocation().getY()*0.9 > tableHeight)) {
				JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
				HashMap<String, String> scrollObject = new HashMap<String, String>();
				scrollObject.put("direction", "up");
				//scrollObject.put("element", ((IOSElement) table).getId());
				scrollObject.put("element", ((IOSElement) table).getId());
				js.executeScript("mobile: swipe", scrollObject);
			} else
				swipe = false;
		}
	}
		
		public void scrollToElement(String elementValue) {
			JavascriptExecutor js = (JavascriptExecutor) appiumdriver;
	        HashMap scrollObject = new HashMap<>();
	        scrollObject.put("predicateString", "value == '" + elementValue + "'");
	        js.executeScript("mobile: scroll", scrollObject);
		
		}
	
}
