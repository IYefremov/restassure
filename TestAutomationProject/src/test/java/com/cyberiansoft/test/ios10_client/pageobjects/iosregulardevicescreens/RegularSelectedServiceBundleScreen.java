package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.dataclasses.ServiceData;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegularSelectedServiceBundleScreen extends iOSRegularBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;
	
	public RegularSelectedServiceBundleScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public boolean checkBundleIsSelected(String bundle) {

		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		return bundleview.findElementByAccessibilityId(bundle).findElementsByAccessibilityId("selected").size() > 0;
	}

	public boolean checkBundleIsNotSelected(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		return bundleview.findElementByAccessibilityId(bundle).findElementsByAccessibilityId("unselected").size() > 0;
	}

	public void selectBundle(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		bundleview.findElementByAccessibilityId(bundle).findElementByAccessibilityId("unselected").click();
	}

	public void openBundleInfo(String bundle) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		MobileElement bundleview = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
		if (!bundleview.findElementByAccessibilityId(bundle).isDisplayed())
			swipeToElement(bundleview.
					findElement(MobileBy.AccessibilityId(bundle)));
		bundleview.findElement(MobileBy.AccessibilityId(bundle))
				.findElement(MobileBy.AccessibilityId("custom detail button")).click();
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
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("BundleItemsView_BundleServiceAmount")).click();
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

	public void openBundleMoneyServiceDetailsFromServicesScrollElement(ServiceData serviceData) {
		WebElement scrollElement = appiumdriver.findElementByClassName("XCUIElementTypeScrollView");
		swipeScrollViewElement(scrollElement.findElement(MobileBy.AccessibilityId(serviceData.getServiceName())));
		scrollElement.findElement(MobileBy.AccessibilityId(serviceData.getServiceName())).click();
	}

	public void selectBundlePercentageServiceFromServicesScrollElement(ServiceData serviceData) {
		WebElement scrollElement = appiumdriver.findElementByClassName("XCUIElementTypeScrollView");
		swipeScrollViewElement(scrollElement.findElement(MobileBy.AccessibilityId(serviceData.getServiceName())));
		WebElement cell = scrollElement.findElement(MobileBy.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[@name='" +
				serviceData.getServiceName() + "']/.."));
		cell.findElement(MobileBy.iOSNsPredicateString("name CONTAINS '%'")).click();

		cell.findElement(MobileBy.className("XCUIElementTypeTextField")).clear();
		cell.findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys(serviceData.getServicePrice()+"\n");

		scrollElement.findElement(MobileBy.AccessibilityId(serviceData.getServiceName())).click();
	}

}
