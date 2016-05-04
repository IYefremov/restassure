package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class RegularSelectedServiceBundleScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(name = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(uiAutomator = ".scrollViews()[1].toolbars()[1].buttons()['Close']")
	private IOSElement tollbarcloseservicesbtn;
	
	public RegularSelectedServiceBundleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertBundleIsSelected(String bundle) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"selected\"]").isDisplayed());
	}

	public void assertBundleIsNotSelected(String bundle) {
		Assert.assertTrue(appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"unselected\"]").isDisplayed());
	}

	public void selectBundle(String bundle) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"unselected\"]").click();

	}

	public void openBundleInfo(String bundle) {
		appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name=\""
						+ bundle + "\"]/UIAButton[@name=\"custom detail button\"]").click();
	}
	
	public void clickServicesIcon() {
		tollbarservicesbtn.click();
	}
	
	public void clickCloseServicesPopup() {
		tollbarcloseservicesbtn.click();
	}
	
	public boolean isBundleServiceExists(String bundle) {
		return appiumdriver.findElement(MobileBy.IosUIAutomation(".scrollViews()[1].scrollViews()[0].staticTexts()['" + bundle + "']")).isDisplayed();
	}

}
