package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

	public RegularSelectedServiceDetailsScreen openBundleInfo(String bundle) {
		IOSElement bundleview = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'BundleItemsView' and type = 'XCUIElementTypeTable'"));	
		bundleview.findElement(MobileBy.AccessibilityId(bundle)).findElement(MobileBy.AccessibilityId("custom detail button")).click();
		return new RegularSelectedServiceDetailsScreen();
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

}
