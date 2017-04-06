package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextRegistrationLineOfBusinessScreen extends VNextBaseScreen {
	
	@FindBy(id="business-type-view")
	private WebElement businesstypescreen;
	
	@FindBy(xpath="//li[@data-name='edition']/label/input")
	private WebElement editionfld;
	
	@FindBy(xpath="//li[@data-name='businessType']/label/input")
	private WebElement businesstypefld;
	
	public VNextRegistrationLineOfBusinessScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(businesstypescreen));
	}

	public void selectEdition(String businessedition) {
		tap(editionfld);		
		tap(appiumdriver.findElement(By.xpath("//ul/li/a/span[text()='" + businessedition + "']")));
	}
	
	public void selectLineOfBusiness(String lineofbusiness) {
		tap(businesstypefld);
		tap(appiumdriver.findElement(By.xpath("//ul/li/a/span[text()='" + lineofbusiness + "']")));
	}
	
	public void clickDoneButton() {
		tap(businesstypescreen.findElement(By.xpath(".//div[@class='pull-right']/a[contains(@data-bind, 'navigateNext')]/span/i")));
		log(LogStatus.INFO, "Click Done button");
	}
	
}
