package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class VNextBaseScreenWithListSelection  extends VNextBaseScreen {
	
	public VNextBaseScreenWithListSelection(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void selectListItem(String itemtoselect) {
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-item']/a[contains(text(), '" + itemtoselect + "')]")));
	}

}
