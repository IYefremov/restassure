package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextBOInvoicesWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//ul[@data-automation-id='invoiceList']")
	private WebElement invoiceslist;
	
	@FindBy(id = "invoice-details")
	private WebElement invoicedetailspanel;
	
	public VNextBOInvoicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(invoiceslist));
	}
	
	public void selectInvoiceInTheList(String invoice) {
		invoiceslist.findElement(By.xpath(".//div[@class='entity-list__item__description']/div/b[text()='" + invoice + "']")).click();
		waitABit(4000);
	}
	
	public String getSelectedInvoiceCustomerName() {
		return invoicedetailspanel.findElement(By.xpath(".//h5[@data-bind='text: customer.clientName']")).getText();
	}
	
	public String getSelectedInvoiceNote() {
		return invoicedetailspanel.findElement(By.xpath(".//p[@data-bind='text: note']")).getText();
	}

}
