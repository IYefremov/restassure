package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class VisualInteriorScreen extends BaseWizardScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	/*@iOSXCUITFindBy(accessibility  = "Custom")
    private IOSElement customtab;
	
	@iOSXCUITFindBy(accessibility  = "Quantity")
    private IOSElement quantityfld;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[@name=\"Quantity\"]/XCUIElementTypeTextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[7]")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeToolbar[1]/XCUIElementTypeStaticText[8]")
    private IOSElement toolbarpricevalue;*/
	
	
	@iOSXCUITFindBy(accessibility = "Save")
    private IOSElement savebtn;

	public VisualInteriorScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Visual")));
	}

	public void waitVisualScreenLoaded(String screenName) {

		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'viewPrompt' and label = '" + screenName+ "'")));
	}
	
	public void switchToCustomTab() {
		appiumdriver.findElementByAccessibilityId("Custom").click();
	}

	public void selectService(String _service) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(_service)));
		BaseUtils.waitABit(1000);
		appiumdriver.findElementByAccessibilityId(_service).click();
	}

	public void selectSubService(String _subservice) {
		appiumdriver.findElementByAccessibilityId(_subservice).click();
	}

	public void setCarServiceQuantityValue(String _quantity)  {
		IOSElement quantityfld = (IOSElement) appiumdriver.findElementByAccessibilityId("Quantity");
		quantityfld.click();
		quantityfld.findElement(MobileBy.className("XCUIElementTypeTextField")).setValue("");
		//quantityfldvalue.setValue("");
		quantityfld.findElement(MobileBy.className("XCUIElementTypeTextField")).setValue(_quantity+"\n");
	}

	public VisualInteriorScreen saveCarServiceDetails() {
		List<MobileElement> savebtns = appiumdriver.findElementsByAccessibilityId("Save");
		for (MobileElement savebtn : savebtns)
			if (savebtn.isDisplayed()) {
				savebtn.click();
				break;
			}
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.numberOfElementsToBeLessThan(MobileBy.AccessibilityId("Details"), 1));
		return this;
	}

	public void tapInterior() {
		

		MobileElement imagecar = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		TouchAction action = new TouchAction(appiumdriver);
		action.tap(element(imagecar, imagecar.getSize().getWidth()/2, imagecar.getSize().getHeight()/2)).perform();
	}
	
	public void tapInteriorWithCoords(int times) {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement imagecar = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int  xx = imagecar.getLocation().getX();
		int yy = imagecar.getLocation().getY();	
		
		//action.press(appiumdriver.manage().window().getSize().width - yy - imagecar .getSize().getHeight()/2 + 30, xx + imagecar.getSize().getWidth()/(times+1)).waitAction(1000).
		action.tap(element(imagecar, imagecar.getSize().getWidth()- times*40, imagecar.getSize().getHeight()/2 + 130)).perform();

	}

	public void tapExterior() {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int x = element.getSize().getWidth()/2;
		int y = element.getSize().getHeight()/2;
		action.tap(element(element, x, y)).perform();
	}
	
	public void tapCarImage() {
		TouchAction action = new TouchAction(appiumdriver);
		MobileElement element = (MobileElement) appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage");
		
		int x = element.getSize().getWidth()/2;
		int y = element.getSize().getHeight()/2;
		action.tap(element(element, x, y)).perform();
		}
	
	public static void tapExteriorWithCoords(int x, int y) {
		Helpers.tapExterior(x, y);
	}

	public String getTotalAmaunt() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("TotalAmount")));
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
