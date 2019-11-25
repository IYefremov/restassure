package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SelectedServiceBundleScreen extends iOSHDBaseScreen {
	
	/*@iOSXCUITFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
	private IOSElement cancelbundlepopupbtn;
	
	@iOSXCUITFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSXCUITFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;*/
	
	public SelectedServiceBundleScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public boolean checkBundleIsSelected(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));
		return bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElements(MobileBy.AccessibilityId("selected")).size() > 0;
	}

	public boolean checkBundleIsNotSelected(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));	
		return bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElements(MobileBy.AccessibilityId("unselected")).size() > 0;
		
	}

	public void selectBundle(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));	
		bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("unselected")).click();
	}

	public SelectedServiceDetailsScreen openBundleInfo(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));
		if (!bundleview.findElement(MobileBy.AccessibilityId(bundle)).isDisplayed()) {
			scrollToElement(bundleview.findElement(MobileBy.AccessibilityId(bundle)));
		}

		bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("custom detail button")).click();
		return new SelectedServiceDetailsScreen();
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

	public void waitUntilBundlePopupOpened() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
	}

	public void saveSelectedServiceDetails() {

		MobileElement saveButton = (MobileElement) appiumdriver.findElementByClassName("XCUIElementTypePopover").
				findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' AND name == 'Save'"));
		saveButton.click();
	}

	public TechniciansPopup clickTechniciansIcon() {
		List<IOSElement> techtoolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (IOSElement techtoolbar : techtoolbars)
			if (techtoolbar.findElementsByAccessibilityId("technician").size() > 0)
				techtoolbar.findElementByAccessibilityId("technician").click();
		return new TechniciansPopup();
	}

	public void setServicePriceValue(String _price)	 {

		WebElement pricefldparent = appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='Price']/..");
		WebElement pricefld = pricefldparent.findElement(MobileBy.className("XCUIElementTypeTextField"));
		pricefld.click();
		if (pricefld.findElements(MobileBy.AccessibilityId("Clear text")).size() > 0)
			pricefld.findElement(MobileBy.AccessibilityId("Clear text")).click();
		pricefld.sendKeys(_price + "\n");
		BaseUtils.waitABit(1000);
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
