package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextRegistrationLineOfBusinessScreen extends VNextBaseScreen {
	
	@FindBy(id="business-type-view")
	private WebElement businesstypescreen;
	
	@FindBy(xpath="//li[@data-name='edition']/label/input")
	private WebElement editionfld;
	
	@FindBy(xpath="//li[@data-name='businessType']/label/input")
	private WebElement businesstypefld;
	
	public VNextRegistrationLineOfBusinessScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(businesstypescreen));
		BaseUtils.waitABit(1000);
	}

	public void selectEdition(String businessedition) {
		tap(editionfld);	
		BaseUtils.waitABit(500);
		tap(appiumdriver.findElement(By.xpath("//ul/li/a/span[text()='" + businessedition + "']")));
	}
	
	public void selectLineOfBusiness(String lineofbusiness) {
		tap(businesstypefld);
		BaseUtils.waitABit(500);
		tap(appiumdriver.findElement(By.xpath("//ul/li/a/span[text()='" + lineofbusiness + "']")));
	}
	
	public void clickDoneButton() {
		tap(businesstypescreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span/i")));
	}
	
}
