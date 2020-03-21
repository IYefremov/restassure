package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextSelectDamagesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='visual/selector']")
	private WebElement selectdamagesscreen;
	
	@FindBy(xpath="//div[@class='list-block breakage-types']")
	private WebElement damagetypeslist;
	
	@FindBy(xpath="//div[@class='buttons-row']/a[@data-tab='default']")
	private WebElement defaulttab;
	
	@FindBy(xpath="//div[@class='buttons-row']/a[@data-tab='custom']")
	private WebElement alltab;

	public VNextSelectDamagesScreen(WebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(defaulttab));
	}

	public VNextSelectDamagesScreen() {
	}
	
	public void selectAllDamagesTab() {
		tap(alltab);
	}
	
	public void clickDefaultDamageType(String damagetype) {
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
	}

	public void clickCustomDamageType(String damagetype) {
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
	}

}
