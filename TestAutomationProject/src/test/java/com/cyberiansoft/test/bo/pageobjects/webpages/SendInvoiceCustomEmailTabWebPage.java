package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

public class SendInvoiceCustomEmailTabWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_Content_InvoiceSendEmail1_txtEmailTo")
	private TextField emailtofld;
	
	@FindBy(id = "ctl00_Content_InvoiceSendEmail1_txtEmailSubject")
	private TextField emailsubjectfld;
	
	@FindBy(id = "ctl00_Content_InvoiceSendEmail1_txtEmailMessage")
	private TextField emailmessagefld;
	
	@FindBy(xpath = "//label[@for='ctl00_Content_InvoiceSendEmail1_chkReportInvoice']")
	private WebElement includeinvoicepdfchkbox;
	
	@FindBy(id = "ctl00_Content_InvoiceSendEmail1_btnSend")
	private WebElement sendbtn;
	
	@FindBy(id = "ctl00_Content_InvoiceSendEmail1_btnClose")
	private WebElement closebtn;
	
	public SendInvoiceCustomEmailTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void setEmailToValue(String emailto) {
		clearAndType(emailtofld, emailto);
	}
	
	public void setEmailSubjectValue(String mailsubject) {
		clearAndType(emailsubjectfld, mailsubject);
	}
	
	public void setEmailMessageValue(String message) {
		clearAndType(emailmessagefld, message);
	}
	
	public void unselectIncludeInvoicePDFCheckbox() {
		checkboxUnselect(includeinvoicepdfchkbox);
	}
	
	public void selectIncludeInvoicePDFCheckbox() {
		checkboxSelect(includeinvoicepdfchkbox);
	}
	
	public void clickSendEmailButton() {
		clickAndWait(sendbtn);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Your message has been sent.']")));
	}

}
