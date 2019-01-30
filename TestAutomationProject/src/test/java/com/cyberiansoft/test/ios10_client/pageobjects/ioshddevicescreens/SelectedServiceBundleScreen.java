package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

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
	
	/*@iOSFindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[@name='Cancel']")
	private IOSElement cancelbundlepopupbtn;
	
	@iOSFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(accessibility = "Close")
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

	public List<String> getListOfBundleServices() {
		List<String> servicesList = new ArrayList<>();
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));
		List<MobileElement> servicesCells = bundleview.findElementsByClassName("XCUIElementTypeCell");
		for (MobileElement cell : servicesCells)
			servicesList.add(cell.getAttribute("name"));
		return servicesList;

	}

	public void waitUntilBundlePopupOpened() {
		WebDriverWait wait = new WebDriverWait(appiumdriver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'")));
	}

	public void saveSelectedServiceDetails() {

		MobileElement navBar = (MobileElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("type == 'XCUIElementTypeNavigationBar' AND visible == 1"));
		navBar.findElementByAccessibilityId("Save").click();
	}

	public TechniciansPopup clickTechniciansIcon() {
		List<IOSElement> techtoolbars = appiumdriver.findElementsByClassName("XCUIElementTypeToolbar");
		for (IOSElement techtoolbar : techtoolbars)
			if (techtoolbar.findElementsByAccessibilityId("technician").size() > 0)
				techtoolbar.findElementByAccessibilityId("technician").click();
		return new TechniciansPopup();
	}

}
