package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularVisualInteriorScreen extends RegularBaseWizardScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	@iOSXCUITFindBy(accessibility = "Quantity")
    private IOSElement quantityfld;
	
	/*@iOSXCUITFindBy(accessibility = "Price")
    private IOSElement pricefld;
	
	@iOSXCUITFindBy(xpath = "//UIATableCell[@name=\"Quantity\"]/UIATextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSXCUITFindBy(accessibility = "SubtotalAmount")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSXCUITFindBy(accessibility = "TotalAmount")
    private IOSElement toolbarpricevalue;*/
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;

	@iOSXCUITFindBy(accessibility = "services")
	private IOSElement servicetoolbarrtn;

	public RegularVisualInteriorScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitVisualScreenLoaded(String screenName) {

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'viewPrompt' and label = '" + screenName+ "'")));
	}
	
	public void switchToCustomTab() {	
		if (Helpers.elementExists(By.xpath("//XCUIElementTypeButton[@label=\"default services on\"]")))
			appiumdriver.findElementByXPath("//XCUIElementTypeButton[@label=\"default services on\"]").click();

	}

	public void selectService(String serviceName) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(serviceName)));
		swipeScrollViewElement(appiumdriver.findElementByAccessibilityId( serviceName));
		appiumdriver.findElementByAccessibilityId (serviceName ).click();
	}

	public void selectSubService(String _subservice) {
		swipeScrollViewElement(appiumdriver.findElementByAccessibilityId( _subservice));
		appiumdriver.findElementByAccessibilityId( _subservice).click();
	}

	public void setCarServiceQuantityValue(String _quantity) {
		quantityfld.findElementByClassName("XCUIElementTypeTextField").clear();
		quantityfld.findElementByClassName("XCUIElementTypeTextField").sendKeys(_quantity);
	}

	public void saveCarServiceDetails() {
		savebtn.click();
	}
	
	public static void tapInteriorWithCoords(int x, int y) {
		Helpers.tapInterior(x, y);
	}
	
	public void clickServicesToolbarButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(servicetoolbarrtn));
		servicetoolbarrtn.click();
	}

	public String getTotalPrice() {
		return appiumdriver.findElementByAccessibilityId("TotalAmount").getAttribute("value");
	}
	
	public String getSubTotalPrice() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("SubtotalAmount")).getAttribute("value");
	}


}
