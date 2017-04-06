package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextSelectDamagesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='visual/selector']")
	private WebElement selectdamagesscreen;
	
	@FindBy(xpath="//div[@class='list-block breakage-types']")
	private WebElement damagetypeslist;
	
	@FindBy(xpath="//div[@class='buttons-row']/a[@data-tab='default']")
	private WebElement defaulttab;
	
	@FindBy(xpath="//div[@class='buttons-row']/a[@data-tab='custom']")
	private WebElement alltab;
	
	public VNextSelectDamagesScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(defaulttab));
	}
	
	public void selectAllDamagesTab() {
		tap(alltab);
		log(LogStatus.INFO, "Tap All Damages tab");
	}
	
	public VNextVisualScreen clickDefaultDamageType(String damagetype) {
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
		log(LogStatus.INFO, "Tap Damage Type: " + damagetype);
		return new VNextVisualScreen(appiumdriver);
	}

	public VNextVisualServicesScreen clickCustomDamageType(String damagetype) {
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
		log(LogStatus.INFO, "Tap Damage Type: " + damagetype);
		return new VNextVisualServicesScreen(appiumdriver);
	}
	
	
}
