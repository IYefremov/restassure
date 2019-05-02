package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
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
	
	public VNextSelectDamagesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(defaulttab));
	}
	
	public void selectAllDamagesTab() {
		tap(alltab);
	}
	
	public VNextVisualScreen clickDefaultDamageType(String damagetype) {
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
		return new VNextVisualScreen(appiumdriver);
	}

	public VNextVisualServicesScreen clickCustomDamageType(String damagetype) {
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
		return new VNextVisualServicesScreen(appiumdriver);
	}
	
	
}
