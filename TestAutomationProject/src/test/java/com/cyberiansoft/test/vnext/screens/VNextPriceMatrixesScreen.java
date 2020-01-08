package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextPriceMatrixesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement pricematrixesscreen;

	@FindBy(xpath="//*[@data-autotests-id='matrix-parts-list']")
	private WebElement matrixpartslist;

	@FindBy(xpath="//*[@data-autotests-id='matrices-list']")
	private WebElement matriceslist;

    public VNextPriceMatrixesScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
	}

	public VNextPriceMatrixesScreen() {
	}

	public boolean isPriceMatrixExistsInTheList(String pricematrix) {
		return matriceslist.findElements(By.xpath(".//*[@action='select-matrix' and contains(text(), '" + pricematrix + "')]")).size() > 0;
	}
	
	public VNextVehiclePartInfoPage selectPriceMatrix(String pricematrix) {
		tap(matrixpartslist.findElement(By.xpath(".//*[@class='checkbox-item-title' and contains(text(), '" + pricematrix + "')]")));
		return new VNextVehiclePartInfoPage(appiumdriver);
	}

	public void selectHailMatrix(String pricematrix) {
		tap(matriceslist.findElement(By.xpath(".//*[@action='select-matrix' and contains(text(), '" + pricematrix + "')]")));
	}

}
