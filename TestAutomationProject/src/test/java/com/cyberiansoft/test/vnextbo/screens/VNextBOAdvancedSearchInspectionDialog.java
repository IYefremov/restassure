package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBOAdvancedSearchInspectionDialog extends VNextBOBaseWebPage {
	
	@FindBy(id = "advSearchEstimation-form")
	private WebElement advancedsearchform;
	
		
	public VNextBOAdvancedSearchInspectionDialog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOf(advancedsearchform));
		waitABit(2000);
	}
	
	public void deleteSavedSearchFilter() {
		advancedsearchform.findElement(By.xpath(".//span[contains(@data-bind, 'click: deleteSavedSearch')]")).click();
		driver.switchTo().alert().accept();
		wait.until(ExpectedConditions.invisibilityOf(advancedsearchform));
	}
	
	public void setAdvancedSearchByInspectionNumber(String inspNumber) {
		advancedsearchform.findElement(By.id("advSearchEstimation-Inspection")).clear();
		advancedsearchform.findElement(By.id("advSearchEstimation-Inspection")).sendKeys(inspNumber);
	}
	
	public String getAdvancedSearchInspectionNumberValue() {
		return advancedsearchform.findElement(By.id("advSearchEstimation-Inspection")).getAttribute("value");
	}
	
	public void setAdvancedSearchByStockNumber(String stocknumber) {
		advancedsearchform.findElement(By.id("advSearchEstimation-Stock")).clear();
		advancedsearchform.findElement(By.id("advSearchEstimation-Stock")).sendKeys(stocknumber);
	}
	
	public void setAdvancedSearchByPONumber(String ponumber) {
		advancedsearchform.findElement(By.id("advSearchEstimation-PO")).clear();
		advancedsearchform.findElement(By.id("advSearchEstimation-PO")).sendKeys(ponumber);
	}
	
	public void setAdvancedSearchByRONumber(String ronumber) {
		advancedsearchform.findElement(By.id("advSearchEstimation-RO")).clear();
		advancedsearchform.findElement(By.id("advSearchEstimation-RO")).sendKeys(ronumber);
	}
	
	public void setAdvencedSearchVINValue(String VIN) {
		advancedsearchform.findElement(By.id("advSearchEstimation-Vin")).clear();
		advancedsearchform.findElement(By.id("advSearchEstimation-Vin")).sendKeys(VIN);
	}
	
	public String getAdvencedSearchVINValue() {
		return advancedsearchform.findElement(By.id("advSearchEstimation-Vin")).getAttribute("value");
	}
	
	public void selectAdvancedSearchByCustomer(String customername) {
		advancedsearchform.findElement(By.id("advSearchEstimation-customer")).sendKeys(customername.substring(0, 4));
		WebElement customerslist = wait
		  .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("advSearchEstimation-customer_listbox"))));
		customerslist.findElement(By.xpath("./li[text()='" + customername + "']")).click();
	}
	
	public VNextBOAdvancedSearchInspectionDialog selectAdvancedSearchByStatus(String statussearch) {
		WebElement statusDropDown = advancedsearchform
                .findElement(By.xpath("//*[@aria-owns='advSearchEstimation-status_listbox']/span/span"));
		if (!statusDropDown.getText().trim().equals(statussearch)) {		
			wait.until(ExpectedConditions
                    .elementToBeClickable(advancedsearchform.findElement(By.id("advSearchEstimation-status_label"))))
                    .click();
			WebElement statuseslist = wait
                    .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("advSearchEstimation-status-list"))));
			waitABit(500);
			statuseslist.findElement(By.xpath("//ul[@id='advSearchEstimation-status_listbox']/li[text()='" + statussearch + "']")).click();
			waitABit(500);
		}
		return this;
	}
	
	public void setAdvancedSearchInspectionByStatusAndInspectionNumber(String inspNumber, String statussearch) {
		setAdvancedSearchByInspectionNumber(inspNumber);
		selectAdvancedSearchByStatus(statussearch);
	}
	
	public void setAdvancedSearchFilterNameAndSave(String filterName) {
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.elementToBeClickable(advancedsearchform.findElement(By.id("advSearchEstimation-Vin")))).click();
		setAdvancedSearchFilterName(filterName);  
		saveAdvancedSearchFilter();
	}
	
	public void setAdvancedSearchFilterName(String filterName) {
		waitShort.until(ExpectedConditions.visibilityOf(advancedsearchform.findElement(By.id("advSearchEstimation-SearchName")))).clear();
		advancedsearchform.findElement(By.id("advSearchEstimation-SearchName")).sendKeys(filterName);  
	}
	
	public String getAdvancedSearchFilterName() {
		return new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.visibilityOf(advancedsearchform.findElement(By.id("advSearchEstimation-SearchName")))).getAttribute("value");		
	}
	
	public void saveAdvancedSearchFilter() {
		clickSaveButton();
		waitShort.until(ExpectedConditions.invisibilityOf(advancedsearchform));
	}
	
	public void clickSaveButton() {
		advancedsearchform.findElement(By.xpath("//span[text()='Save']")).click();
	}
	
	public VNextBOInspectionsWebPage clickSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(advancedsearchform.findElement(By.xpath(".//*[@data-bind='click: search']")))).click();
		wait.until(ExpectedConditions.invisibilityOf(advancedsearchform));
		return PageFactory.initElements(driver, VNextBOInspectionsWebPage.class);
	}
	
	public void clickClearButton() {
		advancedsearchform.findElement(By.xpath("//span[text()='Clear']")).click();
	}
}