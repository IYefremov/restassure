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
	
	@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
	private IOSElement cancelbundlepopupbtn;
	
	@iOSFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;
	
	public SelectedServiceBundleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertBundleIsSelected(String bundle) {
		Assert.assertTrue(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name=\"selected\"]")).isDisplayed());
	}

	public void assertBundleIsNotSelected(String bundle) {
		Assert.assertTrue(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name=\"unselected\"]")).isDisplayed());
		
	}

	public void selectBundle(String bundle) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name=\"unselected\"]")).click();
	}

	public void openBundleInfo(String bundle) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name=\"custom detail button\"]")).click();
	}
	
	public void clickCancelBundlePopupButton() {
		cancelbundlepopupbtn.click();
	}
	
	public void clickServicesIcon() {
		tollbarservicesbtn.click();
	}
	
	public void clickCloseServicesPopup() {
		tollbarcloseservicesbtn.click();
	}
	
	public boolean isBundleServiceExists(String bundle) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(bundle)).size() > 0;
	}
	
	public void overrideBundleAmountValue(String newvalue) throws InterruptedException {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIAToolbar[1]/UIAButton[3]").click();
		Helpers.waitABit(2000);
		
		List<WebElement> elems = appiumdriver.findElementsByAccessibilityId("Bundle service amount");
		for (WebElement el : elems) {
			System.out.println("++++" + el.getAttribute("value"));
			if (el.getAttribute("value").equals("")) {
				System.out.println("++++" + el.getAttribute("value"));
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
