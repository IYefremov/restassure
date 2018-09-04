package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens;

import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class SettingsScreen extends BaseAppScreen {

	public SettingsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void setInspectionToSinglePageInspection() {
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElementByAccessibilityId("switchSinglePageInspection");
		if (singlepagetoggle.getAttribute("value").equals("0"))
			singlepagetoggle.click();
		BaseTestCase.inspSinglePageMode = true;
	}

	public void setInspectionToNonSinglePageInspection() {
		IOSElement singlepagetoggle = (IOSElement) appiumdriver.findElementByAccessibilityId("switchSinglePageInspection");
		if (singlepagetoggle.getAttribute("value").equals("1"))
			singlepagetoggle.click();
		BaseTestCase.inspSinglePageMode = false;
	}

	public void setInsvoicesCustomLayoutOff() {
		swipeScreenUp();
		IOSElement invoicesCustomLayout = (IOSElement) appiumdriver.findElementByAccessibilityId("switchUseCustomLayoutInInvoices");
		if (invoicesCustomLayout.getAttribute("value").equals("1"))
			invoicesCustomLayout.click();
	}

	public void setCheckDuplicatesOn() {
		IOSElement dublicates = (IOSElement) appiumdriver.findElementByAccessibilityId("switchCheckDuplicates");
		if (dublicates.getAttribute("value").equals("0"))
			dublicates.click();
	}

	public void setCheckDuplicatesOff() {
		IOSElement dublicates = (IOSElement) appiumdriver.findElementByAccessibilityId("switchCheckDuplicates");
		if (dublicates.getAttribute("value").equals("1"))
			dublicates.click();
	}
	
	public void setShowTopCustomersOn() {
		IOSElement showtopcustomerstogle = (IOSElement) appiumdriver.findElementByAccessibilityId("switchMRU");
		if (showtopcustomerstogle.getAttribute("value").equals("0"))
			showtopcustomerstogle.click();
	}
	
	public void setShowTopCustomersOff() {
		IOSElement showtopcustomerstogle = (IOSElement) appiumdriver.findElementByAccessibilityId("switchMRU");
		if (showtopcustomerstogle.getAttribute("value").equals("1"))
			showtopcustomerstogle.click();
	}

}
