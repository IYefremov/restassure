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

public class VNextBORepairOrdersWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//*[@id='reconmonitor-orders']/table")
	private WebElement reapiroderstable;
	
	@FindBy(xpath = "//*[@id='repairOrdersFreeTextSearch']")
	private WebElement reapiroderssearchtextfld;
	
	public VNextBORepairOrdersWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(reapiroderstable));
	}
	
	public void searchRepairOrderByNumber(String repairorderNumber) {
		setRepairOrdersSearchText(repairorderNumber);
		clickSearchIcon();
		WebDriverWait wait = new WebDriverWait(driver, 15, 1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='k-loading-mask']")));
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
		return wotablerow.findElement(By.xpath(".//td[@class='phase']")).getText().trim();
	}
	
	public String getWorkOrderDaysInProgressValue(String orderNumber) {
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		return wotablerow.findElement(By.xpath(".//td[@class='days']/div/p")).getText().trim();
	}
	
	public void clickWorkOrderOtherMenuButton(String orderNumber) {
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		wotablerow.findElement(By.xpath(".//i[@data-bind='click: dropMenuClicked']")).click();
	}
	
	public void clickStartRoForWorkOrder(String orderNumber) {
		clickWorkOrderOtherMenuButton(orderNumber);
		driver.findElement(By.xpath("//i[@class='icon-start-ro']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 15, 1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='k-loading-mask']")));
	}
	
	public void clickWorkOrderOtherPhaseMenu(String orderNumber) {
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		wotablerow.findElement(By.xpath(".//i[@data-bind='click: phaseMenuClicked']")).click();
	}
	
	public void completeWorkOrderServiceStatus(String orderNumber, String serviceName) {
		clickWorkOrderOtherPhaseMenu(orderNumber);
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		List<WebElement> services = wotablerow.findElements(
				By.xpath(".//*[@data-bind='click: changeServiceStatus']"));
		for (WebElement srv : services)
			if (srv.getText().trim().contains(serviceName)) {
				srv.click();
				break;
			}

		WebDriverWait wait = new WebDriverWait(driver, 15, 1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='k-loading-mask']")));
	}
	
	public String getCompletedWorkOrderValue(String orderNumber) {
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		return wotablerow.findElement(
				By.xpath("./td[8]/span")).getText();
	}
	
	public boolean isRepairOrderPresentInTable(String orderNumber) {
		return getRepairOrdersTableBody().findElements(By.xpath(".//strong[text()='" + orderNumber + "']")).size() > 0;
	}
	
	public WebElement getRepairOrdersTableBody() {
		return reapiroderstable.findElement(By.xpath(".//*[@id='tableBody']"));
	}
	
	public VNextBORepairOrderDetailsPage openWorkOrderDetailsPage(String orderNumber) {
		WebElement wotablerow = getTableRowWithWorkOrder(orderNumber);
		wotablerow.findElement(By.xpath(".//a/strong[text()='" + orderNumber + "']")).click();
		return new VNextBORepairOrderDetailsPage(driver);
	}

}
