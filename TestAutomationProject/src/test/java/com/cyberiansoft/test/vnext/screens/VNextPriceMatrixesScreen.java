package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPriceMatrixesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='priceMatrices']")
	private WebElement pricematrixesscreen;
	
	public VNextPriceMatrixesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(pricematrixesscreen));
		BaseUtils.waitABit(1000);
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
		return new VNextVehiclePartsScreen(appiumdriver);
	}

}
