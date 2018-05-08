package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class VisualInteriorScreen extends iOSHDBaseScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	/*@iOSFindBy(accessibility  = "Custom")
    private IOSElement customtab;
	
	@iOSFindBy(accessibility  = "Quantity")
    private IOSElement quantityfld;
	
	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name=\"Quantity\"]/XCUIElementTypeTextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[7]")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSFindBy(xpath = "//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[8]")
    private IOSElement toolbarpricevalue;*/
	
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;

	public VisualInteriorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Visual")));
	}
	
	public void switchToCustomTab() {
		appiumdriver.findElementByAccessibilityId("Custom").click();
	}

	public void selectService(String _service) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(_service)));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(_service))).click();
	}

	public void selectSubService(String _subservice) {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId(_subservice)).waitAction(Duration.ofSeconds(1)).release().perform();
	}

	public void setCarServiceQuantityValue(String _quantity) throws InterruptedException {
		IOSElement quantityfld = (IOSElement) appiumdriver.findElementByAccessibilityId("Quantity");
		quantityfld.click();
		quantityfld.findElement(MobileBy.className("XCUIElementTypeTextField")).setValue("");
		//quantityfldvalue.setValue("");
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
		//action.press(appiumdriver.manage().window().getSize().width - yy - imagecar .getSize().getHeight()/2, xx + imagecar.getSize().getWidth()/2).waitAction(1000).
		action.press(xx + imagecar.getSize().getWidth()/2,  imagecar .getSize().getHeight()/2).waitAction(Duration.ofSeconds(1)).
		
		release().perform();
	}
	
	public void tapInteriorWithCoords(int times) {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement imagecar = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int  xx = imagecar.getLocation().getX();
		int yy = imagecar.getLocation().getY();	
		
		//action.press(appiumdriver.manage().window().getSize().width - yy - imagecar .getSize().getHeight()/2 + 30, xx + imagecar.getSize().getWidth()/(times+1)).waitAction(1000).
		action.tap(imagecar, imagecar.getSize().getWidth()/(times+2), imagecar.getSize().getHeight()/2 + 130).perform();

	}

	public void tapExterior() throws InterruptedException {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int x = element.getSize().getWidth()/2;
		int y = element.getSize().getHeight()/2;
		action.tap(element, x, y).perform();
	}
	
	public void tapCarImage() {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int x = element.getSize().getWidth()/2;
		int y = element.getSize().getHeight()/2;
		action.tap(element, x, y).perform();
		}
	
	public static void tapExteriorWithCoords(int x, int y) throws InterruptedException {
		Helpers.tapExterior(x, y);
	}

	public String getTotalAmaunt() {
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}
	
	public String getSubTotalAmaunt() {
		return appiumdriver.findElementByAccessibilityId("SubtotalAmount").getAttribute("value");
	}

	public boolean isInteriorServicePresent(String interiorServiceName) {
		return appiumdriver.findElement(MobileBy.AccessibilityId(interiorServiceName)).isDisplayed();
	}

	public static String getVisualInteriorCaption() {
		return visualinteriorcapt;
	}

	public static String getVisualExteriorCaption() {
		return visualexteriorcapt;
	}

}
