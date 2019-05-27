package com.cyberiansoft.test.vnext.screens.wizardscreens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VNextVisualScreen extends VNextBaseWizardScreen {
	
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
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@data-page, 'visual')]")));
		BaseUtils.waitABit(1000);
		if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		
		
	}

	public VNextSelectDamagesScreen clickAddServiceButton() {
		tap(adddamagesbtn);
		tap(appiumdriver.findElement(By.xpath("//a[@action='add-other']")));
		return new VNextSelectDamagesScreen(appiumdriver);
	}
	
	public void selectDefaultDamage(String damageType) {
		clickAddServiceButton();
		clickDefaultDamageType(damageType);
	}
	
	public VNextSelectDamagesScreen clickOtherServiceOption() {		
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='Other']")));
		return new VNextSelectDamagesScreen(appiumdriver);
	}
	
	public void clickCarImage() {
		int servicesAdded = getNumberOfImageMarkers();
		if (servicesAdded > 0) {
			clickCarImageSecondTime();
		} else
			tap(carimage);
	}
	
	public void clickCarImageSecondTime() {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		BaseUtils.waitABit(300);
		TouchAction tch = new TouchAction(appiumdriver);
		tch.tap(PointOption.point(Math.round(appiumdriver.manage().window().getSize().getWidth() / 3), Math.round(appiumdriver.manage().window().getSize().getHeight() / 3))).perform();
		BaseUtils.waitABit(300);	
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		if (appiumdriver.findElements(By.xpath("//div[@class='car-marker']")).size() < 2) {
			AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			tch = new TouchAction(appiumdriver);
			tch.tap(PointOption.point(Math.round(appiumdriver.manage().window().getSize().getWidth() / 3), Math.round(appiumdriver.manage().window().getSize().getHeight() / 3))).perform();
			BaseUtils.waitABit(300);
			AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		}
	}
	
	public void clickCarImageACoupleTimes(int touchTimes) {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		BaseUtils.waitABit(1300);
		
		for (int i = 0; i < touchTimes; i++) {
			TouchAction tch = new TouchAction(appiumdriver);
			tch.tap(PointOption.point(Math.round(appiumdriver.manage().window().getSize().getWidth() / (i+2)), Math.round(appiumdriver.manage().window().getSize().getHeight() / (i+2)))).perform();
			BaseUtils.waitABit(1000);
		}
		
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
	}
	
	public VNextServiceDetailsScreen clickCarImageMarker() {
		tap(carmarker);
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public VNextServiceDetailsScreen clickCarImageMarker(int markerItemIndex) {
		tap(appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img")).get(markerItemIndex));
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public void clickDamageCancelEditingButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='icon cancel-editing-button']")));
		tap(visualscreen.findElement(By.xpath(".//span[@class='icon cancel-editing-button']")));
	}
	
	public int getNumberOfImageMarkers() {
		return appiumdriver.findElements(By.xpath("//div[@class='car-marker']/img")).size();
	}
	
	public VNextVisualScreen clickDefaultDamageType(String damagetype) {
		tap(defaulttab);
		tap(damagetypeslist.findElement(By.xpath(".//span[text()='" + damagetype + "']")));
		return new VNextVisualScreen(appiumdriver);
	}
}
