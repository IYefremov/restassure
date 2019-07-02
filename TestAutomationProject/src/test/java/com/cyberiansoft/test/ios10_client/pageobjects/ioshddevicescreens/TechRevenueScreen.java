package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TechRevenueScreen extends iOSHDBaseScreen {
	
	public TechRevenueScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public boolean isTechIsPresentInReport(String techname) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Tech Revenue")));
		return appiumdriver.findElementsByAccessibilityId(techname).size() > 0;
	}

	public MyWorkOrdersScreen clickBackButton() {
		appiumdriver.findElementByAccessibilityId("Back").click();
		return new MyWorkOrdersScreen();
	}

}
