package com.cyberiansoft.test.bo.pageobjects.webpages;

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
import com.cyberiansoft.test.bo.webelements.WebTable;

public class InvoicePaymentsTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_gvInvoice_ctl00__0")
	private WebTable invoiceinfotable;
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable paymentsnotestable;
	
	@FindBy(id = "popupPaymentNotes")
	private TextField paymentsnotesfldpopup;
	
	@FindBy(id = "ctl00_Content_btnPopupCancel")
	private WebElement paymentsnotesclosebtnpopup;
	
	public InvoicePaymentsTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public List<WebElement> getInvoicesPaymentsTableRows() {
		wait.until(ExpectedConditions.visibilityOf(paymentsnotestable.getWrappedElement()));
		return paymentsnotestable.getTableRows();
	}
	
	public WebElement getInvoicesPaymentsLastTableRow() {
		List<WebElement> rows = getInvoicesPaymentsTableRows();
		return rows.get(rows.size() - 1);
	}
	
	public String getInvoicesPaymentsLastTableRowPaidColumnValue() {
		WebElement row = getInvoicesPaymentsLastTableRow();
		return row.findElement(By.xpath("./td[5]")).getText();
	}
	
	public String getInvoicesPaymentsLastTableRowDescriptionColumnValue() {
		WebElement row = getInvoicesPaymentsLastTableRow();
		return row.findElement(By.xpath("./td[3]")).getText();
	}
	
	public void clickNotesForInvoicesPaymentsLastTableRow() {
		WebElement row = getInvoicesPaymentsLastTableRow();
		Actions act  = new Actions(driver);
		act.click(row.findElement(By.xpath(".//a[@id='linkViewNotes']"))).perform();
	}
	
	
	public String getInvoicePaymentNoteValue() {
		return paymentsnotesfldpopup.getValue();
	}
	
	public void clickClosePaymentsPopup() {
		paymentsnotesclosebtnpopup.click();
	}
	
	public WebElement getTableRowWithPaymentsType(String paymentstype) {
		List<WebElement> rows = getInvoicesPaymentsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + paymentsnotestable.getTableColumnIndex("Type") + "]")).getText().equals(paymentstype)) {
				return row;
			}
		} 
		return null;
	}
	
	public WebElement getTableRowWithPaymentDescription(String paymentdesc) {
		List<WebElement> rows = getInvoicesPaymentsTableRows();
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + paymentsnotestable.getTableColumnIndex("Description") + "]")).getText().equals(paymentdesc)) {
				return row;
			}
		} 
		return null;
	}
	
	public String getPaymentsTypeAmountValue(String paymentstype) {
		String amount = null;
		WebElement row = getTableRowWithPaymentsType(paymentstype); 
		if (row != null) {
			amount = row.findElement(By.xpath("./td[" + paymentsnotestable.getTableColumnIndex("Amount") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + paymentstype + " payment type");	
		}
		return amount; 
	}
	
	public String getPaymentDescriptionTypeAmountValue(String paymentdesc) {
		String amount = null;
		WebElement row = getTableRowWithPaymentDescription(paymentdesc); 
		if (row != null) {
			amount = row.findElement(By.xpath("./td[" + paymentsnotestable.getTableColumnIndex("Amount") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find payment with the following description: " + paymentdesc + " payment type");	
		}
		return amount; 
	}
	
	public String getPaymentsTypeCreatedByValue(String paymentstype) {
		String amount = null;
		WebElement row = getTableRowWithPaymentsType(paymentstype); 
		if (row != null) {
			amount = row.findElement(By.xpath("./td[" + paymentsnotestable.getTableColumnIndex("Created By") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + paymentstype + " payment type");	
		}
		return amount; 
	}
}
