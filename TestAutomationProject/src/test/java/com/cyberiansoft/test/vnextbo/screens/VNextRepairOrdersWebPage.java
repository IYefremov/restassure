package com.cyberiansoft.test.vnextbo.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextRepairOrdersWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//*[@id='reconmonitor-orders']/table")
	private WebElement reapiroderstable;
	
	@FindBy(xpath = "//*[@id='repairOrdersFreeTextSearch']")
	private WebElement reapiroderssearchtextfld;
	
	public VNextRepairOrdersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(reapiroderstable));
	}
	
	public void searchRepairOrderByNumber(String repairorderNumber) {
		setRepairOrdersSearchText(repairorderNumber);
		clickSearchIcon();
	}
	
	public void setRepairOrdersSearchText(String repairordertext) {
		reapiroderssearchtextfld.clear();
		reapiroderssearchtextfld.sendKeys(repairordertext);
	}
	
	public void clickSearchIcon() {
		driver.findElement(By.xpath("//div[@class='search-wrapper']")).findElement(By.xpath(".//*[@class='icon-search']")).click();
	}
	
	public WebElement getTableRowWithWorkOrder(String orderNumber) {
		WebElement wotablerow = null;
		List<WebElement> wotablerows = getRepairOrdersTableBody().findElements(By.xpath("./tr"));
		for (WebElement row : wotablerows)
			if (row.findElement(By.xpath(".//*[@class='order-no']/strong")).getText().trim().equals(orderNumber)) {
				wotablerow = row;
				break;				
			}
		return wotablerow;
	}
	
	public String getWorkOrderActivePhaseValue(String orderNumber) {
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		return wotablerow.findElement(By.xpath(".//td[@class='phase']/p")).getText().trim();
	}
	
	public boolean isRepairOrderPresentInTable(String orderNumber) {
		return getRepairOrdersTableBody().findElements(By.xpath(".//strong[text()='" + orderNumber + "']")).size() > 0;
	}
	
	public WebElement getRepairOrdersTableBody() {
		return reapiroderstable.findElement(By.xpath(".//*[@id='tableBody']"));
	}

}
