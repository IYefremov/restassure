package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class TechRevenueScreen extends iOSHDBaseScreen {
	
	public TechRevenueScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public boolean isTechIsPresentInReport(String techname) {
		return appiumdriver.findElementsByAccessibilityId(techname).size() > 0;
	}

	public MyWorkOrdersScreen clickBackButton() {
		appiumdriver.findElementByAccessibilityId("Back").click();
		return new MyWorkOrdersScreen();
	}

}
