package com.cyberiansoft.test.bo.pageobjects.webpages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class InvoicePaymentsTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_gv_ctl00")
	private WebTable paymentsnotestable;
	
	@FindBy(id = "popupPaymentNotes")
	private TextField paymentsnotesfldpopup;
	
	@FindBy(id = "ctl00_Content_btnPopupCancel")
	private WebElement paymentsnotesclosebtnpopup;
	
	public InvoicePaymentsTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public List<WebElement> getInvoicesPaymentsTableRows() {
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
}
