package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextBaseScreenWithListSelection  extends VNextBaseScreen {
	
	public VNextBaseScreenWithListSelection(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
	}
	
	public void selectListItem(String itemtoselect) {
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-item']/a[contains(text(), '" + itemtoselect + "')]")));
	}

}
