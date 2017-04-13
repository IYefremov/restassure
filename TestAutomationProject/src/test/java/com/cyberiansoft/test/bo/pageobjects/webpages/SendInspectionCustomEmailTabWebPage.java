package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.clickAndWait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class SendInspectionCustomEmailTabWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_Content_inspectionSendEmail_txtEmailTo")
	private TextField emailtofld;
	
	@FindBy(id = "ctl00_Content_inspectionSendEmail_txtEmailSubject")
	private TextField emailsubjectfld;
	
	@FindBy(id = "ctl00_Content_inspectionSendEmail_txtEmailMessage")
	private TextField emailmessagefld;
	
	@FindBy(xpath = "//label[@for='ctl00_Content_inspectionSendEmail_chkReportInspection']")
	private WebElement includeinvoicepdfchkbox;
	
	@FindBy(id = "ctl00_Content_inspectionSendEmail_btnSend")
	private WebElement sendbtn;
	
	@FindBy(id = "ctl00_Content_inspectionSendEmail_btnClose")
	private WebElement closebtn;
	
	public SendInspectionCustomEmailTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Your message has been sent.']")));
	}
	
	public void clickCloseButton() {
		closebtn.click();
	}

}
