package com.cyberiansoft.test.vnextbo.screens.Inspections;

import java.util.concurrent.TimeUnit;

import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextBOInspectionInfoWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='print-page']")
	private WebElement inspinfopage;
	
	@FindBy(xpath = "//tr[@class='total-row line-item']")
	private WebElement insptotalinfopanel;

	
	public VNextBOInspectionInfoWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(inspinfopage));
	}
	
	public String getInspectionTotalPriceValue() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(insptotalinfopanel));
		return insptotalinfopanel.findElement(By.xpath(".//tbody/tr/td[2]/div")).getText();
	}

}
