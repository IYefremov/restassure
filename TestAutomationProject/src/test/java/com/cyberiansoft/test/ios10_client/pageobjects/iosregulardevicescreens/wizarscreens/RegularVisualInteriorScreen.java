package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularVisualInteriorScreen extends RegularBaseWizardScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	/*@iOSFindBy(accessibility = "Quantity")
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
    private IOSElement savebtn;*/

	public RegularVisualInteriorScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void switchToCustomTab() {	
		if (Helpers.elementExists(By.xpath("//XCUIElementTypeButton[@label=\"default services on\"]")))
			appiumdriver.findElementByXPath("//XCUIElementTypeButton[@label=\"default services on\"]").click();

	}
	
	public void clickServicesBackButton() {
		appiumdriver.findElementByAccessibilityId("Back").click();
	}

	public void selectService(String serviceName) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(serviceName)));
		appiumdriver.findElementByAccessibilityId (serviceName ).click();
	}

	public void selectSubService(String _subservice) {
		appiumdriver.findElementByAccessibilityId( _subservice).click();
	}

	public void setCarServiceQuantityValue(String _quantity) throws InterruptedException {
		IOSElement quantityrow = (IOSElement) appiumdriver.findElementByAccessibilityId("Quantity");
		quantityrow.findElementByClassName("XCUIElementTypeTextField").clear();
		quantityrow.findElementByClassName("XCUIElementTypeTextField").sendKeys(_quantity);
	}

	public void saveCarServiceDetails() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
	}

	public void tapInterior() throws InterruptedException {
		Helpers.tapInterior(50, 50);
	}
	
	public static void tapInteriorWithCoords(int x, int y) throws InterruptedException {
		Helpers.tapInterior(x, y);
	}

	public static void tapExterior() throws InterruptedException {
		Helpers.tapExterior(150, 150);
	}
	
	public static void tapExteriorWithCoords(int x, int y) throws InterruptedException {
		Helpers.tapExterior(x, y);
	}
	
	public void clickServicesToolbarButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("services")).click();
	}

	public String getTotalPrice() {
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}
	
	public String getSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("SubtotalAmount")).getAttribute("value");
	}


	public boolean isInteriorServicePresent(String serviceName) {
		return appiumdriver.findElement(MobileBy.AccessibilityId(serviceName)).isDisplayed();
		//Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("Miscellaneous")).isDisplayed());
		//Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("Price Adjustment")).isDisplayed());
		//Assert.assertTrue(appiumdriver.findElement(MobileBy.AccessibilityId("WHEEL REPAIR")).isDisplayed());
	}

	public static String getVisualInteriorCaption() {
		return visualinteriorcapt;
	}

	public static String getVisualExteriorCaption() {
		return visualexteriorcapt;
	}

	public RegularNotesScreen clickNotesButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Compose"))).click();
		return new RegularNotesScreen();
	}

}
