package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class SelectedServiceBundleScreen extends iOSHDBaseScreen {
	
	/*@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
	private IOSElement cancelbundlepopupbtn;
	
	@iOSFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;*/
	
	public SelectedServiceBundleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertBundleIsSelected(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));
		Assert.assertTrue(bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("selected")).isDisplayed());
	}

	public void assertBundleIsNotSelected(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));	
		Assert.assertTrue(bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("unselected")).isDisplayed());
		
	}

	public void selectBundle(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));	
		bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("unselected")).click();
	}

	public void openBundleInfo(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));	
		bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("custom detail button")).click();
	}
	
	public void clickCancelBundlePopupButton() {
		appiumdriver.findElementByClassName("XCUIElementTypeNavigationBar").findElement(By.name("Cancel")).click();
	}
	
	public void clickServicesIcon() {
		appiumdriver.findElementByAccessibilityId("services").click();
	}
	
	public void clickCloseServicesPopup() {
		appiumdriver.findElementByAccessibilityId("Close").click();
	}
	
	public boolean isBundleServiceExists(String bundle) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(bundle)).size() > 0;
	}
	
	public void overrideBundleAmountValue(String newvalue) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIAToolbar[1]/UIAButton[3]").click();
		Helpers.waitABit(2000);
		
		List<WebElement> elems = appiumdriver.findElementsByAccessibilityId("Bundle service amount");
		for (WebElement el : elems) {
			if (el.getAttribute("value").equals("")) {
				el.findElement(By.xpath(".//UIATableView[1]/UIATableCell[1]/UIATextField[1]/UIATextField[1]")).clear();
				el.findElement(By.xpath(".//UIAScrollView[1]/UIATableView[1]/UIATableCell[1]/UIATextField[1]/UIATextField[1]")).click();
			}
			//System.out.println("++++" + el.getAttribute("className"));
			
		}
		Helpers.keyboadrType(newvalue);
		//appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[1]").click();
		//appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[1]/UIATextField[1]/UIATextField[1]").clear();
		//appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIATableView[1]/UIATableCell[1]/UIATextField[1]/UIATextField[1]").sendKeys(newvalue);
		appiumdriver.findElementByAccessibilityId("Override").click();
	}
	
	

}
