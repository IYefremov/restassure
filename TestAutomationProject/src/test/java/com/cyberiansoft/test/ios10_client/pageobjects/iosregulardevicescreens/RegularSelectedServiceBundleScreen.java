package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class RegularSelectedServiceBundleScreen extends iOSRegularBaseScreen {
	
	/*@iOSFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;*/
	
	public RegularSelectedServiceBundleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertBundleIsSelected(String bundle) {

		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		Assert.assertTrue(appiumdriver.findElementsByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='selected']").size() > 0);
	}

	public void assertBundleIsNotSelected(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		Assert.assertTrue(appiumdriver.findElementsByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='unselected']").size() > 0);
	}

	public void selectBundle(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='unselected']").click();
	}

	public void openBundleInfo(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='custom detail button']").click();
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

}
