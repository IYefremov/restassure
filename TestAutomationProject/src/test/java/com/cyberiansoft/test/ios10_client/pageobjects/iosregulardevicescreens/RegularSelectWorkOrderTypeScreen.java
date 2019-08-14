package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class RegularSelectWorkOrderTypeScreen extends iOSRegularBaseScreen {
	
	public RegularSelectWorkOrderTypeScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

	}
	
	public <T extends IBaseWizardScreen> T selectWorkOrderType(WorkOrdersTypes workordertype) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 15);
		IOSElement wostable = (IOSElement) wait.until(ExpectedConditions.elementToBeClickable(MobileBy.iOSNsPredicateString("name = 'OrderTypeSelector' and type = 'XCUIElementTypeTable'")));

		if (!wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).isDisplayed()) {
			swipeToElement(wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()));
			wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
		} else
			wostable.findElementByAccessibilityId(workordertype.getWorkOrderTypeName()).click();
		return workordertype.getFirstVizardScreen();
	}

}
