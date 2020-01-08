package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
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

    public VNextRegistrationLineOfBusinessScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("business-type-view")));
		BaseUtils.waitABit(1000);
	}

	public void selectEdition(String businessedition) {
		tap(editionfld);
		BaseUtils.waitABit(500);
		tap(appiumdriver.findElement(By.xpath("//ul/li/a/span[text()='" + businessedition + "']")));
	}
	
	public void selectLineOfBusiness(String lineofbusiness) {
		//tap(businesstypefld);
		BaseUtils.waitABit(500);
		tap(appiumdriver.findElement(By.xpath("//ul/li/a/span[text()='" + lineofbusiness + "']")));
		tap(appiumdriver.findElement(By.id("business-type-view")).findElement(By.xpath(".//div[@class='pull-right']/div[contains(@data-bind, 'navigateNext')]")));
	}
	
	public void clickDoneButton() {
		BaseUtils.waitABit(2000);
		try {
			tap(businesstypescreen.findElement(By.xpath(".//div[@class='pull-right']/div[contains(@data-bind, 'navigateNext')]")));
		} catch (UnhandledAlertException e) {
			BaseUtils.waitABit(1000);
			tap(businesstypescreen.findElement(By.xpath(".//div[@class='pull-right']/div[contains(@data-bind, 'navigateNext')]")));
		}
	}
	
}
