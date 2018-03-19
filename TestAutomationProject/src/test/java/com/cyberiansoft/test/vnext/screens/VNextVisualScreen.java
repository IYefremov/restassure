package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class VNextVisualScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[contains(@data-page, 'visual')]")
	private WebElement visualscreen;
	
	@FindBy(xpath="//div[@class='car-image-wrapper']/img")
	private WebElement carimage;
	
	@FindBy(xpath="//div[@class='car-marker']/img")
	private WebElement carmarker;
	
	@FindBy(xpath="//a[@class='floating-button color-red']")
	private WebElement adddamagesbtn;
	
	@FindBy(xpath="//div[@class='list-block breakage-types']")
	private WebElement damagetypeslist;
	
	@FindBy(xpath="//*[@data-tab='default']")
	private WebElement defaulttab;
	
	@FindBy(xpath="//*[@data-tab='custom']")
	private WebElement customtab;
	
	public VNextVisualScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@data-page, 'visual')]")));
		waitABit(1000);
		if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		
		
	}

	public VNextSelectDamagesScreen clickAddServiceButton() {		
		tap(adddamagesbtn);
		log(LogStatus.INFO, "Tap Add Service button");
		return new VNextSelectDamagesScreen(appiumdriver);
	}
	
	public void selectDefaultDamage(String damageType) {
		clickAddServiceButton();
		clickDefaultDamageType(damageType);
	}
	
	public VNextSelectDamagesScreen clickOtherServiceOption() {		
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='Other']")));
		log(LogStatus.INFO, "Tap Other Damage Type option");
		return new VNextSelectDamagesScreen(appiumdriver);
	}
	
	public void clickCarImage() {
		tap(carimage);
		log(LogStatus.INFO, "Tap on Car image");
	}
	
	public void clickCarImageSecondTime() {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(300);
		TouchAction tch = new TouchAction(appiumdriver);
		tch.tap(Math.round(appiumdriver.manage().window().getSize().getWidth() / 3), Math.round(appiumdriver.manage().window().getSize().getHeight() / 3) ).perform();
		waitABit(300);	
		switchToWebViewContext();
		log(LogStatus.INFO, "Tap on Car image");
	}
	
	public void clickCarImageACoupleTimes(int touchTimes) {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		waitABit(300);
		
		for (int i = 0; i < touchTimes; i++) {
			TouchAction tch = new TouchAction(appiumdriver);
			tch.tap(Math.round(appiumdriver.manage().window().getSize().getWidth() / (i+2)), Math.round(appiumdriver.manage().window().getSize().getHeight() / (i+2)) ).perform();
			waitABit(1000);
		}
		
		switchToWebViewContext();
		log(LogStatus.INFO, "Tap on Car image");
	}
	
	public VNextServiceDetailsScreen clickCarImageMarker() {
		tap(carmarker);
		log(LogStatus.INFO, "Tap on Car image marker");
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public VNextServiceDetailsScreen clickCarImageMarker(WebElement markeritem) {
		tap(markeritem);
		log(LogStatus.INFO, "Tap on Car image marker");
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public void clickDamageCancelEditingButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='icon cancel-editing-button']")));
		tap(visualscreen.findElement(By.xpath(".//span[@class='icon cancel-editing-button']")));
		log(LogStatus.INFO, "Tap Damage Cancel Editing button");
	}
	
	public List<MobileElement> getImageMarkers() {
		return appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img"));
	}
	
	public VNextVisualScreen clickDefaultDamageType(String damagetype) {
		tap(defaulttab);
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
		log(LogStatus.INFO, "Tap Damage Type: " + damagetype);
		return new VNextVisualScreen(appiumdriver);
	}
}
