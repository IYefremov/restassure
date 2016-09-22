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

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class VNextBOAddNewServiceDialog extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//input[contains(@data-bind, 'value: name')]")
	private TextField servicenamefld;
	
	@FindBy(xpath = "//span[@aria-owns='popup-services-type_listbox']/span/span/span")
	private WebElement servicetypecmb;
	
	@FindBy(xpath = "//textarea[@data-bind='value: description']")
	private TextField servicedescfld;
	
	@FindBy(xpath = "//span[@aria-owns='price-type_listbox']/span/span")
	private WebElement servicepricetypecmb;
	
	@FindBy(id = "priceForMoneyType")
	private TextField servicepricefld;
	
	@FindBy(id = "priceForPercentageType")
	private TextField servicepercentagefld;
	
	@FindBy(xpath = "//button[contains(@data-bind, 'click: saveBtn.click')]")
	private WebElement serviceaddbtn;
	
	@FindBy(xpath = "//div[@class='errorMessege']/p")
	private WebElement errormsg;
	
	public VNextBOAddNewServiceDialog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(servicenamefld.getWrappedElement()));
	}
	
	public VNexBOServicesWebPage addNewService(String servicename, String servicetype, String servicedesc, String servicepricetype, String serviceprice) {
		setServiceName(servicename);
		selectServiceType(servicetype);
		setServiceDescription(servicedesc);
		selectServicePriceType(servicepricetype);
		setServicePriceValue(serviceprice);
		clickServiceAddButton();
		waitABit(1000);
		return PageFactory.initElements(
				driver, VNexBOServicesWebPage.class);
	}
	
	public VNexBOServicesWebPage addNewPercentageService(String servicename, String servicetype, String servicedesc, String servicepricetype, String serviceprice) {
		setServiceName(servicename);
		selectServiceType(servicetype);
		setServiceDescription(servicedesc);
		selectServicePriceType(servicepricetype);
		setServicePercentageValue(serviceprice);
		return saveNewService();
	}
	
	public void setServiceName(String servicename) {
		servicenamefld.clearAndType(servicename);
	}
	
	public String getServiceName() {
		return servicenamefld.getValue();
	}
	
	public String getServiceType() {
		return servicetypecmb.getText();
	}
	
	public void selectServiceType(String servicetype) {
		servicetypecmb.click();
		waitABit(300);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//ul[@id='popup-services-type_listbox']/li/span[text()='" + servicetype + "']")))).click();
	}
	
	public void setServiceDescription(String servicedesc) {
		servicedescfld.clearAndType(servicedesc);
	}
	
	public String getServiceDescription() {
		return servicedescfld.getValue();
	}
	
	public void selectServicePriceType(String servicepricetype) {
		servicepricetypecmb.click();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//ul[@id='price-type_listbox']/li/div/span[text()='" + servicepricetype + "']")))).click();
	}
	
	public boolean isServicePriceTypeVisible() {
		return driver.findElements(By.xpath("//span[@aria-owns='price-type_listbox']/span/span")).size() > 0;
	}
	
	public void setServicePriceValue(String servicepricevalue) {
		WebElement pricepercentagefld = getServicePricePercentageValueTxtField();				
		Actions act = new Actions(driver);
		act.click(new WebDriverWait(driver, 30)
			.until(ExpectedConditions.elementToBeClickable(pricepercentagefld)));
		pricepercentagefld.clear();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(servicepricefld.getWrappedElement()));
		servicepricefld.clearAndType(servicepricevalue);
	}
	
	public void setServicePercentageValue(String servicepercentagevalue) {
		WebElement pricepercentagefld = getServicePricePercentageValueTxtField();				
		Actions act = new Actions(driver);
		act.click(new WebDriverWait(driver, 30)
			.until(ExpectedConditions.elementToBeClickable(pricepercentagefld)));
		pricepercentagefld.clear();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(servicepercentagefld.getWrappedElement()));
		servicepercentagefld.clearAndType(servicepercentagevalue);
	}
	
	public WebElement getServicePricePercentageValueTxtField() {
		WebElement pricepercentagefld = null;
		List<WebElement> priceflds = driver.findElements(By.xpath("//span[@class='k-numeric-wrap k-state-default k-expand-padding']/input[@class='k-formatted-value k-input']"));
		for (WebElement elm : priceflds) {
			if (elm.isDisplayed()) {				
				pricepercentagefld = elm;
			}
		}
		return pricepercentagefld;
	}
	
	public VNexBOServicesWebPage saveNewService() {
		clickServiceAddButton();		
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[contains(@data-bind, 'click: saveBtn.click')]"))); 
		waitABit(500);
		return PageFactory.initElements(
				driver, VNexBOServicesWebPage.class);
	}
	
	public void clickServiceAddButton() {
		serviceaddbtn.click();
	}

}
