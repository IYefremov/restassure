package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularSelectedServiceBundleScreen extends iOSRegularBaseScreen {
	
	/*@iOSXCUITFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSXCUITFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;*/
	
	public RegularSelectedServiceBundleScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public boolean checkBundleIsSelected(String bundle) {

		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='selected']").size() > 0;
	}

	public boolean checkBundleIsNotSelected(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='unselected']").size() > 0;
	}

	public void selectBundle(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='unselected']").click();
	}

	public RegularSelectedServiceDetailsScreen openBundleInfo(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='BundleItemsView']/XCUIElementTypeCell[@name='" + bundle + "']/XCUIElementTypeButton[@name='custom detail button']").click();
		return new RegularSelectedServiceDetailsScreen();
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

	public String getServiceDetailsPriceValue() {

		IOSElement toolbar = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeToolbar");
		return toolbar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
	}

	public void changeAmountOfBundleService(String newamount) {
		List<WebElement> toolbarbtns = appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElements(MobileBy.className("XCUIElementTypeButton"));
		for (WebElement btn : toolbarbtns)
			if (btn.getAttribute("name").contains("$")) {
				btn.click();
				break;
			}
		IOSElement amountfld = (IOSElement) appiumdriver.findElementByClassName("XCUIElementTypeAlert").findElement(MobileBy.className("XCUIElementTypeTextField"));
		amountfld.clear();
		amountfld.sendKeys(newamount);
		appiumdriver.findElementByAccessibilityId("Override").click();
	}

	public void clickSaveButton() {
		appiumdriver.findElementByAccessibilityId("Save").click();
	}

	public void clickTechniciansIcon() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("technician")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("technician"))).click();
	}

}
