package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class VisualInteriorScreen extends iOSHDBaseScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	@iOSFindBy(accessibility  = "Custom")
    private IOSElement customtab;
	
	@iOSFindBy(accessibility  = "Quantity")
    private IOSElement quantityfld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Quantity\"]/UIATextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSFindBy(xpath = "//UIAScrollView[1]/UIAToolbar[1]/UIAStaticText[7]")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSFindBy(xpath = "//UIAScrollView[1]/UIAToolbar[1]/UIAStaticText[8]")
    private IOSElement toolbarpricevalue;
	
	
	@iOSFindBy(uiAutomator = ".popover().navigationBar().buttons()[\"Save\"]")
    private IOSElement savebtn;

	public VisualInteriorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void switchToCustomTab() {
		customtab.click();
	}

	public void selectService(String _service) {
		appiumdriver.findElementByXPath("//UIAScrollView[1]/UIATableView[1]/UIATableCell[@name='" + _service + "']").click();
	}

	public void selectSubService(String _subservice) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name='" + _subservice + "']").click();
	}

	public void setCarServiceQuantityValue(String _quantity) throws InterruptedException {
		quantityfld.click();
		quantityfldvalue.setValue("");
		Helpers.keyboadrType(_quantity);
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

	public void assertPriceIsCorrect(String price) {
		Assert.assertEquals(toolbarpricevalue.getText(), price);
	}
	
	public void assertVisualPriceIsCorrect(String price) {
		Assert.assertEquals(toolbarvisualpricevalue.getText(), price);
	}


	public static void assertDefaultInteriorServicesPresent()
			throws InterruptedException {
		Assert.assertTrue(Helpers.text_exact("Miscellaneous").isDisplayed());
		Assert.assertTrue(Helpers.text_exact("Price Adjustment - 2")
				.isDisplayed());
		Assert.assertTrue(Helpers.text_exact("WHEEL REPAIR").isDisplayed());
	}

	public static String getVisualInteriorCaption() {
		return visualinteriorcapt;
	}

	public static String getVisualExteriorCaption() {
		return visualexteriorcapt;
	}

}
