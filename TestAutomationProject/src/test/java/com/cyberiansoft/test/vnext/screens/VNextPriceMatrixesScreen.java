package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.webelements.decoration.FiledDecorator;
import org.openqa.selenium.By;
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

	public VNextPriceMatrixesScreen() {
		PageFactory.initElements(new FiledDecorator(ChromeDriverProvider.INSTANCE.getMobileChromeDriver()), this);
	}

	public void selectHailMatrix(String pricematrix) {
		tap(matriceslist.findElement(By.xpath(".//*[@action='select-matrix' and contains(text(), '" + pricematrix + "')]")));
	}

}
