package com.cyberiansoft.test.vnextbo.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.VNextWebTable;

public class VNexBOServicesWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//button[contains(@class, 'btn-add-new-service')]/i")
	private WebElement addservicebtn;
	
	@FindBy(xpath = "//div[@id='serviceTable-wrapper']/table")
	private VNextWebTable servicestable;
	
	@FindBy(id = "advSearchServices-freeText")
	private TextField searchservicefld;
	
	@FindBy(xpath = "//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']")
	private WebElement searchicon;
	
	@FindBy(xpath = "//div[@id='pagingPanel']/button[contains(text(), 'Next')]")
	private WebElement nextpagebtn;
	
	public VNexBOServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(servicestable.getWrappedElement()));
	}
	
	public VNextBOAddNewServiceDialog clickAddNewserviceButton() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(addservicebtn));
		addservicebtn.click();
		return PageFactory.initElements(
				driver, VNextBOAddNewServiceDialog.class);
	}
	
	public void searchServiceByServiceName(String servicename) {
		searchservicefld.clearAndType(servicename);
		List<WebElement> customsearchs = driver.findElements(By.xpath("//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']"));
		for (WebElement srch : customsearchs) {
			if (srch.isDisplayed()) {
				srch.click();
				break;
			}
		}
		waitABit(1000);
	}
	
	public boolean isServicePresentOnCurrentPageByServiceName(String servicename) {
		boolean founded = false;
		WebElement row = getTableRowWithServiceByServiceName(servicename);
		if (row != null)
			founded = true;
		return founded;
		
	}
	
	private WebElement getTableRowWithServiceByServiceName(String servicename) {
		waitABit(300);
		WebElement servicerow = null;
		List<WebElement> rows = getServicesTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Name") + "]")).getText().equals(servicename)) {
				servicerow = row;
				break;
			}			
		} 
		return servicerow;
	}
	
	public List<WebElement>  getServicesTableRows() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
		return servicestable.getTableRows();
	}
	
	public int getServicesTableRowCount() {
		return getServicesTableRows().size();
	}
	
	public void deleteServiceByServiceName(String servicename) {
		WebElement servicerow = getTableRowWithServiceByServiceName(servicename);
		Actions act = new Actions(driver);
		act.moveToElement(servicerow.findElement(By.xpath("./td[@class='grid__actions']"))).click(servicerow.findElement(By.xpath(".//span[@class='icon-trash-bin']"))).build().perform();
		waitABit(5000);
	}

}
