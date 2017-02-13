package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularVisualInteriorScreen extends iOSRegularBaseScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	@iOSFindBy(accessibility = "Quantity")
    private IOSElement quantityfld;
	
	@iOSFindBy(accessibility = "Price")
    private IOSElement pricefld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Quantity\"]/UIATextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSFindBy(accessibility = "SubtotalAmount")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSFindBy(accessibility = "TotalAmount")
    private IOSElement toolbarpricevalue;
	
	
	@iOSFindBy(accessibility = "Save")
    private IOSElement savebtn;

	public RegularVisualInteriorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void switchToCustomTab() {	
		if (Helpers.elementExists(By.xpath("//XCUIElementTypeButton[@label=\"default services on\"]")))
			appiumdriver.findElementByXPath("//XCUIElementTypeButton[@label=\"default services on\"]").click();

	}
	
	public void clickServicesBackButton() {
		appiumdriver.findElementByAccessibilityId("Back").click();
	}

	public void selectService(String _service) {
		appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@value='" + _service + "']").click();
	}

	public void selectSubService(String _subservice) {
		appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@value='" + _subservice + "']").click();
	}

	public void setCarServiceQuantityValue(String _quantity) throws InterruptedException {
		Helpers.waitABit(2000);
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Quantity']//XCUIElementTypeTextField[1]")).clear();
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='Quantity']//XCUIElementTypeTextField[1]")).sendKeys(_quantity + "\n");
		/*pricefld.click();
		Helpers.keyboadrType("\n");
		quantityfld.click();
		Thread.sleep(2000);
		quantityfld.click();
		Helpers.keyboadrType("\n");
		quantityfld.click();
		appiumdriver.findElementByAccessibilityId("Clear text").click();
		Helpers.keyboadrType(_quantity + "\n");*/
	}

	public void saveCarServiceDetails() {
		savebtn.click();
	}

	public static void tapInterior() throws InterruptedException {
		Thread.sleep(1000);
		Helpers.tapInterior(50, 50);
	}
	
	public static void tapInteriorWithCoords(int x, int y) throws InterruptedException {
		Thread.sleep(1000);
		Helpers.tapInterior(x, y);
	}

	public static void tapExterior() throws InterruptedException {
		Thread.sleep(1000);
		Helpers.tapExterior(50, 50);
	}
	
	public static void tapExteriorWithCoords(int x, int y) throws InterruptedException {
		Thread.sleep(1000);
		Helpers.tapExterior(x, y);
	}
	
	public void clickServicesToolbarButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("services")).click();
		Helpers.waitABit(500);
	}

	public void assertPriceIsCorrect(String price) {
		Assert.assertEquals(appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value"), price);
	}
	
	public void assertVisualPriceIsCorrect(String price) {
		Assert.assertEquals(toolbarvisualpricevalue.getAttribute("value"), price);
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
