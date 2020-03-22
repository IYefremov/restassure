package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VNextSelectDamagesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='visual/selector']")
	private WebElement selectdamagesscreen;
	
	@FindBy(xpath="//div[@class='list-block breakage-types']")
	private WebElement damagetypeslist;
	
	@FindBy(xpath="//div[@class='buttons-row']/a[@data-tab='default']")
	private WebElement defaulttab;
	
	@FindBy(xpath="//div[@class='buttons-row']/a[@data-tab='custom']")
	private WebElement alltab;

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
