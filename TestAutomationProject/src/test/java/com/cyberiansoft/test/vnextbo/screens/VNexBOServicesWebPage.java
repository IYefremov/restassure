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
import org.testng.Assert;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.VNextWebTable;

public class VNexBOServicesWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//button[contains(@class, 'btn-add-new-service')]/i")
	private WebElement addservicebtn;
	
	@FindBy(xpath = "//div[@id='serviceTable-wrapper']/table")
	private VNextWebTable servicestable;
	
	@FindBy(id = "services-search")
	private WebElement searchservicespanel;
	
	@FindBy(id = "advSearchServices-freeText")
	private TextField searchservicefld;
	
	@FindBy(xpath = "//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']")
	private WebElement searchicon;
	
	@FindBy(xpath = "//div[@id='pagingPanel']/button[contains(text(), 'Next')]")
	private WebElement nextpagebtn;
	
	@FindBy(id = "advSearchServices-form")
	private WebElement advancedsearchform;
	
	@FindBy(id = "advSearchServices-name")
	private WebElement advancedsearchnamefld;
	
	@FindBy(xpath = "//span[@aria-owns='advSearchServices-type_listbox']/span")
	private WebElement advancedsearchtypefld;
	
	public VNexBOServicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(servicestable.getWrappedElement()));
	}
	
	public VNextBOAddNewServiceDialog clickAddNewserviceButton() {
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(addservicebtn));
		addservicebtn.click();
		return PageFactory.initElements(
				driver, VNextBOAddNewServiceDialog.class);
	}
	
	public void setSearchFreeTextValue(String searchtext) {
		searchservicefld.clearAndType(searchtext);
	}
	
	public void searchServiceByServiceName(String searchtext) {
		setSearchFreeTextValue(searchtext);
		searchservicespanel.findElement(By.xpath(".//div[@class='custom-search__input']/i[@data-bind='click: freeTextSearch']")).click();
		waitABit(1000);
	}
	
	public void advancedSearchService(String searchtext, boolean archived) {
		setSearchFreeTextValue(searchtext);
		openAdvancedSearchPanel();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(searchservicespanel.findElement(By.xpath(".//input[@type='checkbox']"))));
		if (archived)
			checkboxSelect(searchservicespanel.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			checkboxUnselect(searchservicespanel.findElement(By.xpath(".//input[@type='checkbox']")));
		searchservicespanel.findElement(By.xpath(".//button[@type='submit']")).click();
		waitABit(500);
	}
	
	public void openAdvancedSearchPanel() {
		searchservicespanel.findElement(By.xpath(".//i[@data-bind='click: showAdvancedSearch']")).click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(advancedsearchform));
	}
	
	public boolean isServicePresentOnCurrentPageByServiceName(String servicename) {
		boolean founded = false;
		if (!driver.findElement(By.xpath("//div[@id='services-list']/div/p[text()='No records found. Please refine search criteria ...']")).isDisplayed()) {
			WebElement row = getTableRowWithServiceByServiceName(servicename);
			if (row != null)
				founded = true;
		}
		return founded;
		
	}
	
	public String getServiceTypeValue(String servicename) {
		String servicetype = null;
		WebElement row = getTableRowWithServiceByServiceName(servicename); 
		if (row != null) {
			servicetype = row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Type") + "]")).getText().trim();
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");	
		}
		return servicetype; 
	}
	
	public String getServicePriceValue(String servicename) {
		String serviceprice = null;
		WebElement row = getTableRowWithServiceByServiceName(servicename); 
		if (row != null) {
			serviceprice = row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Price") + "]")).getText().trim();
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");	
		}
		return serviceprice; 
	}
	
	public String getServiceDescriptionValue(String servicename) {
		String serviceprice = null;
		WebElement row = getTableRowWithServiceByServiceName(servicename); 
		if (row != null) {
			Actions act = new Actions(driver);
			act.moveToElement(row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Description") + "]/i/i"))).perform();
			serviceprice = row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Description") + "]/i/span")).getText().trim();
		} else {
			Assert.assertTrue(false, "Can't find " + servicename + " service");	
		}
		return serviceprice; 
	}
	
	private WebElement getTableRowWithServiceByServiceName(String servicename) {
		waitABit(300);
		WebElement servicerow = null;
		List<WebElement> rows = getServicesTableRows();
		if (rows.size() > 0) {
			for (WebElement row : rows) {
				if (row.findElement(By.xpath("./td[" + servicestable.getTableColumnIndex("Name") + "]")).getText().equals(servicename)) {
					servicerow = row;
					break;
				}			
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
	
	public VNextBOAddNewServiceDialog clickEditServiceByServiceName(String servicename) {
		WebElement tablerow = getTableRowWithServiceByServiceName(servicename);
		Actions act = new Actions(driver);
		act.moveToElement(tablerow.findElement(By.xpath("./td[@class='grid__actions']"))).click(tablerow.findElement(By.xpath(".//span[@class='icon-pencil2']"))).build().perform();
		return PageFactory.initElements(
				driver, VNextBOAddNewServiceDialog.class);
	}
	
	public void deleteServiceByServiceName(String servicename) {
		WebElement tablerow = getTableRowWithServiceByServiceName(servicename);
		clickDeleteServiceButton(tablerow);
		VNextConfirmationDialog confirmdialog = PageFactory.initElements(
				driver, VNextConfirmationDialog.class);
		Assert.assertEquals(confirmdialog.clickYesAndGetConfirmationDialogMessage(), 
				"Are you sure you want to delete \"" + servicename + "\" service?");
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(addservicebtn));
	}
	
	public void clickDeleteServiceButton(WebElement tablerow) {
		Actions act = new Actions(driver);
		act.moveToElement(tablerow.findElement(By.xpath("./td[@class='grid__actions']"))).click(tablerow.findElement(By.xpath(".//span[@class='icon-trash-bin']"))).build().perform();
	}
	
	public void clickDeleteServiceButtonAndAcceptAlert (WebElement tablerow) {
		clickDeleteServiceButton(tablerow);
		VNextConfirmationDialog confirmdialog = PageFactory.initElements(
				driver, VNextConfirmationDialog.class);
		confirmdialog.clickYesButton();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(addservicebtn));
	}
	
	
	public void unarchiveServiceByServiceName(String servicename) {
		VNextConfirmationDialog confirmdialog = clickUnarchiveButtonForService(servicename);
		Assert.assertEquals(confirmdialog.clickYesAndGetConfirmationDialogMessage(), 
				"Are you sure you want to restore \"" + servicename + "\" service?");
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.id("dialogModal")));
	}
	
	public VNextConfirmationDialog clickUnarchiveButtonForService(String servicename) {
		WebElement servicerow = getTableRowWithServiceByServiceName(servicename);
		Actions act = new Actions(driver);
		act.moveToElement(servicerow.findElement(By.xpath("./td[@class='grid__actions']"))).click(servicerow.findElement(By.xpath(".//span[@data-bind='click: unArchiveService']"))).build().perform();
		return PageFactory.initElements(
				driver, VNextConfirmationDialog.class);
	}
	

}
