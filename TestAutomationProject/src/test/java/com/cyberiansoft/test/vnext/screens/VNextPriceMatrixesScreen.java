package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextPriceMatrixesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='priceMatrices']")
	private WebElement pricematrixesscreen;
	
	public VNextPriceMatrixesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(pricematrixesscreen));
		waitABit(1000);
	}
	
	public WebElement getPriceMatrixesList() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(pricematrixesscreen.findElement(By.xpath(".//div[contains(@class, 'list-block')]"))));
		return pricematrixesscreen.findElement(By.xpath(".//div[contains(@class, 'list-block')]"));
	}
	
	public boolean isPriceMatrixExistsInTheList(String pricematrix) {
		return getPriceMatrixesList().findElements(By.xpath(".//*[contains(@class, 'item-title') and text()='" + pricematrix + "']")).size() > 0;
	}
	
	public VNextVehiclePartsScreen selectPriceMatrix(String pricematrix) {
		tap(getPriceMatrixesList().findElement(By.xpath(".//*[contains(@class, 'item-title') and text()='" + pricematrix + "']")));
		log(LogStatus.INFO, "Select Price Matrix: " + pricematrix);
		return new VNextVehiclePartsScreen(appiumdriver);
	}

}
