package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SelectedServiceBundleScreen extends iOSHDBaseScreen {
	
	@iOSFindBy(uiAutomator = ".popovers()[0].navigationBars()[0].buttons()['Cancel']")
	private IOSElement cancelbundlepopupbtn;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].toolbars()[0].buttons()['services']")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(uiAutomator = ".popovers()[1].toolbars()[0].buttons()['Close']")
	private IOSElement tollbarcloseservicesbtn;
	
	public SelectedServiceBundleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertBundleIsSelected(String bundle) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"selected\"]").isDisplayed());
	}

	public void assertBundleIsNotSelected(String bundle) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"unselected\"]").isDisplayed());
	}

	public void selectBundle(String bundle) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"unselected\"]").click();

	}

	public void openBundleInfo(String bundle) {
		appiumdriver.findElementByXPath("//UIAPopover[1]/UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"custom detail button\"]").click();
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
		return appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[1].scrollViews()[0].staticTexts()['" + bundle + "']")).isDisplayed();
	}
	
	

}
