package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextPriceMatrixesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement pricematrixesscreen;

	@FindBy(xpath="//div[@data-autotests-id='matrix-parts-list']")
	private WebElement matrixpartslist;

	@FindBy(xpath="//div[@data-autotests-id='matrices-list']")
	private WebElement matriceslist;
	
	public VNextPriceMatrixesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		BaseUtils.waitABit(1000);
		if (appiumdriver.findElementsByXPath("//div[@class='help-button' and text()='OK, got it']").size() > 0)
			if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
				tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		BaseUtils.waitABit(2000);
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		//wait.until(ExpectedConditions.visibilityOf(pricematrixesscreen));
	}

	
	public boolean isPriceMatrixExistsInTheList(String pricematrix) {
		return matrixpartslist.findElements(By.xpath(".//*[@class='checkbox-item-title' and contains(text(), '" + pricematrix + "')]")).size() > 0;
	}
	
	public VNextVehiclePartInfoPage selectPriceMatrix(String pricematrix) {
		tap(matrixpartslist.findElement(By.xpath(".//*[@class='checkbox-item-title' and contains(text(), '" + pricematrix + "')]")));
		return new VNextVehiclePartInfoPage(appiumdriver);
	}

	public VNextVehiclePartsScreen selectHailMatrix(String pricematrix) {
		tap(matriceslist.findElement(By.xpath(".//*[@action='select-matrix' and contains(text(), '" + pricematrix + "')]")));
		return new VNextVehiclePartsScreen(appiumdriver);
	}

}
