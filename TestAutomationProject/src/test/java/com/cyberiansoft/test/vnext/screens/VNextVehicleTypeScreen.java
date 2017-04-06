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

public class VNextVehicleTypeScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='list-block']")
	private WebElement typeslist;

	
	public VNextVehicleTypeScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(typeslist));
	}
	
	public void selectType(String vehicletype) {
		tap(typeslist.findElement(By.xpath("//div[@class='item-inner']/div[@class='item']/div[text()='" + vehicletype + "']")));
		log(LogStatus.INFO, "Select '" + vehicletype + "' vehicle type");
	}
}
