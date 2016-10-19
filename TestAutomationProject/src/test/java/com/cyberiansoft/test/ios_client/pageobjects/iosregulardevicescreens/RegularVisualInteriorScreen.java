package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularVisualInteriorScreen extends iOSRegularBaseScreen {
	
	final static String visualinteriorcapt = "Interior";
	final static String visualexteriorcapt = "Exterior";
	
	@iOSFindBy(accessibility  = "Quantity")
    private IOSElement quantityfld;
	
	@iOSFindBy(accessibility  = "Price")
    private IOSElement pricefld;
	
	@iOSFindBy(xpath = "//UIATableCell[@name=\"Quantity\"]/UIATextField[1]")
    private IOSElement quantityfldvalue;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAToolbar/UIAStaticText[1]")
    private IOSElement toolbarvisualpricevalue;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAToolbar/UIAStaticText[3]")
    private IOSElement toolbarpricevalue;
	
	
	@iOSFindBy(uiAutomator = ".navigationBar().buttons()[\"Save\"]")
    private IOSElement savebtn;

	public RegularVisualInteriorScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void switchToCustomTab() {	
		if (Helpers.elementExists(By.xpath("//UIAScrollView[2]/UIAToolbar[1]/UIAButton[@name=\"default services on\"]")))
			appiumdriver.findElementByXPath("//UIAScrollView[2]/UIAToolbar[1]/UIAButton[@name=\"default services on\"]").click();

	}
	
	public void clickServicesBackButton() {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[1].toolbars()[0].buttons()['Back']")).click();
	}

	public void selectService(String _service) {
		Helpers.text_exact(_service).click();
	}

	public void selectSubService(String _subservice) {
		Helpers.text_exact(_subservice).click();
	}

	public void setCarServiceQuantityValue(String _quantity) throws InterruptedException {
		Helpers.waitABit(2000);
		pricefld.click();
		Helpers.keyboadrType("\n");
		quantityfld.click();
		Thread.sleep(2000);
		quantityfld.click();
		Helpers.keyboadrType("\n");
		quantityfld.click();
		appiumdriver.findElement(By.xpath("//UIATableView[1]/UIATableCell[@name=\"Quantity\"]/UIATextField[1]/UIAButton[@name=\"Clear text\"]")).click();
		Helpers.keyboadrType(_quantity + "\n");
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
		appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[1].toolbars()[0].buttons()['services']")).click();
	}

	public void assertPriceIsCorrect(String price) {
		if (Helpers.elementExists(By.xpath("//UIAScrollView[2]/UIAToolbar/UIAStaticText[3]")))
			Assert.assertEquals(appiumdriver.findElement(By.xpath("//UIAScrollView[2]/UIAToolbar/UIAStaticText[3]")).getText(), price);
		else
			Assert.assertEquals(appiumdriver.findElement(By.xpath("//UIAScrollView[2]/UIAToolbar/UIAStaticText[2]")).getText(), price);
	}
	
	public void assertVisualPriceIsCorrect(String price) {
		Assert.assertEquals(toolbarvisualpricevalue.getText(), price);
	}


	public void assertDefaultInteriorServicesPresent()
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
