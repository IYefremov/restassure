package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class VisualInteriorScreen extends iOSHDBaseScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	@iOSFindBy(accessibility  = "Custom")
    private IOSElement customtab;
	
	@iOSFindBy(accessibility  = "Quantity")
    private IOSElement quantityfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name=\"Quantity\"]/XCUIElementTypeTextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[7]")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[8]")
    private IOSElement toolbarpricevalue;
	
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;

	public VisualInteriorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void switchToCustomTab() {
		customtab.click();
	}

	public void selectService(String _service) {
		appiumdriver.findElementByAccessibilityId(_service).click();
	}

	public void selectSubService(String _subservice) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId(_subservice)).waitAction(300).release().perform();
	}

	public void setCarServiceQuantityValue(String _quantity) throws InterruptedException {
		quantityfld.click();
		quantityfldvalue.setValue("");
		((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public void saveCarServiceDetails() {
		if (appiumdriver.findElementsByAccessibilityId("ServiceDetailsView").size() > 0)
			((IOSElement) appiumdriver.findElementsByAccessibilityId("ServiceDetailsView").get(1)).findElement(MobileBy.AccessibilityId("Save")).click();
		else
			appiumdriver.findElementByAccessibilityId("ServiceDetailsView").findElement(MobileBy.AccessibilityId("Save")).click();
	}

	public void tapInterior() {
		
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement imagecar = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int  xx = imagecar.getLocation().getX();
		int yy = imagecar.getLocation().getY();	
		action.press(appiumdriver.manage().window().getSize().width - yy - imagecar .getSize().getHeight()/2, xx + imagecar.getSize().getWidth()/2).waitAction(1000).
		release().perform();
	}
	
	public void tapInteriorWithCoords(int times) {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement imagecar = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int  xx = imagecar.getLocation().getX();
		int yy = imagecar.getLocation().getY();	
		action.press(appiumdriver.manage().window().getSize().width - yy - imagecar .getSize().getHeight()/2 + 30, xx + imagecar.getSize().getWidth()/(times+1)).waitAction(1000).
		release().perform();

		Helpers.waitABit(1000);
	}

	public void tapExterior() throws InterruptedException {
		Thread.sleep(1000);
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int x = element.getLocation().getX() + element.getSize().getWidth()/2;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2;
		//action.tap(element, x, y).perform();
		action.press(element, x, y).waitAction(1000).release().perform();
		Helpers.waitABit(1000);
		/*int x = element.getLocation().getX() + element.getSize().getWidth()/2;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2;
		//action.tap(element, x, y).perform();
		action.press(element, x, y).waitAction(1000).release().perform();
		Helpers.waitABit(1000);
		Helpers.tapExterior(50, 50);*/
	}
	
	public void tapCarImage() { 
		
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int x = element.getLocation().getX() + element.getSize().getWidth()/2;
		int y = element.getLocation().getY() + element.getSize().getHeight()/2;
		//action.tap(element, x, y).perform();
		action.press(element, x, y).waitAction(1000).release().perform();
		Helpers.waitABit(1000);
	}
	
	public static void tapExteriorWithCoords(int x, int y) throws InterruptedException {
		Thread.sleep(1000);
		Helpers.tapExterior(x, y);
	}

	public void assertPriceIsCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), price);
	}
	
	public void assertVisualPriceIsCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("SubtotalAmount").getAttribute("value"), price);
	}


	public void assertDefaultInteriorServicesPresent()
			throws InterruptedException {
		Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("Miscellaneous")).isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("Price Adjustment")).isDisplayed());
		Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("WHEEL REPAIR")).isDisplayed());
	}

	public static String getVisualInteriorCaption() {
		return visualinteriorcapt;
	}

	public static String getVisualExteriorCaption() {
		return visualexteriorcapt;
	}

}
