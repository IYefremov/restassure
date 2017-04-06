package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextBaseScreenWithListSelection  extends VNextBaseScreen {
	
	public VNextBaseScreenWithListSelection(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
	}
	
	public void selectListItem(String itemtoselect) {
		tap(appiumdriver.findElement(By.xpath("//a[@action='select-item']/div[@class='item-inner']/div[@class='item']/div[text()='" + itemtoselect + "']")));
	}

}
