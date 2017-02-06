package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextPriceMatrixesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='priceMatrices']")
	private WebElement pricematrixesscreen;
	
	public VNextPriceMatrixesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(pricematrixesscreen));
	}
	
	public WebElement getPriceMatrixesList() {
		return pricematrixesscreen.findElement(By.xpath(".//div[@class='list-block']"));
	}
	
	public boolean isPriceMatrixExistsInTheList(String pricematrix) {
		return getPriceMatrixesList().findElements(By.xpath(".//div[@class='item-title' and text()='" + pricematrix + "']")).size() > 0;
	}
	
	public VNextVehiclePartsScreen selectPriceMatrix(String pricematrix) {
		tap(getPriceMatrixesList().findElement(By.xpath(".//div[@class='item-title' and text()='" + pricematrix + "']")));
		log(LogStatus.INFO, "Select Price Matrix: " + pricematrix);
		return new VNextVehiclePartsScreen(appiumdriver);
	}

}
