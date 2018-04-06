package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextWorkOrderTypesList extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='entity-types']")
	private WebElement wotypeslist;
	
	public VNextWorkOrderTypesList(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		//PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, 15, TimeUnit.SECONDS), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(wotypeslist));
	}
	
	public void selectWorkOrderType(String workorderType) {
		tap(wotypeslist.findElement(By.xpath(".//div[@class='item-title']/div[text()='" + workorderType + "']")));
	}

}
