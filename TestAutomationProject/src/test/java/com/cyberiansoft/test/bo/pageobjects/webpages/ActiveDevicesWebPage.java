package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class ActiveDevicesWebPage extends BaseWebPage {

	@FindBy(xpath = "//a[text()='Search']")
	private WebElement searchbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl03_filterer_tbName")
	private WebElement filtercriterianame;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_devices_ctl00")
	private WebTable devicestable;

	@FindBy(xpath = "//input[@value='Find']")
	private WebElement findbtn;

	@FindBy(xpath = "//input[@value='Replace']")
	private WebElement replacemarker;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_devices_ctl00_ctl04_lRegCode")
	private WebElement regcodefld;

	public ActiveDevicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void setSearchCriteriaByName(String name) {
		wait.until(ExpectedConditions.visibilityOf(searchbtn)).click();
		filtercriterianame.sendKeys(name);
		clickAndWait(findbtn);
	}

	public String getFirstRegCodeInTable() {
		wait.until(ExpectedConditions.visibilityOf(devicestable.getWrappedElement()));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", devicestable.getWrappedElement().findElement(By.xpath(".//th[text()='Reg Code']")));
		if (regcodefld.getText().isEmpty())
			replacemarker.click();
		else {
			devicestable.getWrappedElement().findElement(By.xpath(".//a[text()='x']")).click();
			//waitABit(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", devicestable.getWrappedElement().findElement(By.xpath(".//th[text()='Reg Code']")));
			replacemarker.click();
		}
		//waitABit(3000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", devicestable.getWrappedElement().findElement(By.xpath(".//th[text()='Reg Code']")));	
		return regcodefld.getText();
	}

}