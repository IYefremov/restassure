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
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement pricematrixesscreen;

	@FindBy(xpath="//div[@data-autotests-id='matrices-list']")
	private WebElement matriceslist;
	
	public VNextPriceMatrixesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(matriceslist));
		BaseUtils.waitABit(1000);
	}

	
	public boolean isPriceMatrixExistsInTheList(String pricematrix) {
		return matriceslist.findElements(By.xpath(".//*[@action='select-matrix' and contains(text(), '" + pricematrix + "')]")).size() > 0;
	}
	
	public VNextVehiclePartsScreen selectPriceMatrix(String pricematrix) {
		tap(matriceslist.findElement(By.xpath(".//*[@action='select-matrix' and contains(text(), '" + pricematrix + "')]")));
		return new VNextVehiclePartsScreen(appiumdriver);
	}

}
