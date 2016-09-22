package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVisualScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[contains(@data-page, 'visual')]")
	private WebElement visualscreen;
	
	@FindBy(xpath="//div[@class='car-image-wrapper']/img")
	private WebElement carimage;
	
	@FindBy(xpath="//div[@class='car-marker']/img")
	private WebElement carmarker;
	
	@FindBy(xpath="//div[@class='toolbar-inner bottom-bar']/div[@class='left repair-button']/i")
	private WebElement adddamagesbtn;
	
	public VNextVisualScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(adddamagesbtn));
	}

	public VNextSelectDamagesScreen clickAddServiceButton() {		
		tap(adddamagesbtn);
		log(LogStatus.INFO, "Tap Add Service button");
		return new VNextSelectDamagesScreen(appiumdriver);
	}
	
	public void clickCarImage() {
		tap(carimage);
		log(LogStatus.INFO, "Tap on Car image");
	}
	
	public VNextServiceDetailsScreen clickCarImageMarker() {
		tap(carmarker);
		log(LogStatus.INFO, "Tap on Car image marker");
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public void clickDamageCancelEditingButton() {
		tap(visualscreen.findElement(By.xpath(".//div[@class='right cancel-editing-button']/i")));
		log(LogStatus.INFO, "Tap Damage Cancel Editing button");
	}
}
