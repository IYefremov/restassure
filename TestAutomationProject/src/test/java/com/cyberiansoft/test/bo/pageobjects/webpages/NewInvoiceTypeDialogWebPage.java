package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class NewInvoiceTypeDialogWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//input[contains(@id, 'Card_name')]")
	private TextField invoicetypenamefld;
	
	@FindBy(xpath = "//textarea[contains(@id, 'Card_description')]")
	private TextField invoicetypedescfld;
	
	//@FindBy(xpath = "//label[contains(@for, 'Card_cbPOVisible')]")
	@FindBy(id="ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbPOVisible")
	private WebElement visiblechkbx;
	
	//@FindBy(xpath = "//label[contains(@for, 'Card_cbPORequired')]")
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl01_Card_cbPORequired")
	private WebElement requiredchkbx;
	
	@FindBy(xpath = "//label[contains(@for, 'Card_cbUseNewPrinting')]")
	private WebElement usenewprintingcmbox;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_comboSharingType_Input')]")
	private ComboBox sharingcmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_comboSharingType_DropDown')]")
	private DropDown sharingdd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlQuestionTemplate_Input')]")
	private ComboBox questionstemplatecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlQuestionTemplate_DropDown')]")
	private DropDown questionstemplatedd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlEmailTemplate_Input')]")
	private ComboBox wholesaleemailtemplatecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlEmailTemplate_DropDown')]")
	private DropDown wholesaleemailtemplatedd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlRetailEmailTemplate_Input')]")
	private ComboBox retailemailtemplatecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlRetailEmailTemplate_DropDown')]")
	private DropDown retailemailtemplatedd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlReportTemplateApplication_Input')]")
	private ComboBox wholesaleprinttemplatecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlReportTemplateApplication_DropDown')]")
	private DropDown wholesaleprinttemplatedd;
	
	@FindBy(xpath = "//input[contains(@id, 'Card_ddlRetailReportTemplateApplication_Input')]")
	private ComboBox retailprinttemplatecmb;
	
	@FindBy(xpath = "//*[contains(@id, 'Card_ddlRetailReportTemplateApplication_DropDown')]")
	private DropDown retailprinttemplatedd;
	
	@FindBy(xpath = "//select[contains(@id, 'Card_dlForms_lAvailable')]")
	private WebElement availablequestionsformscmb;
	
	@FindBy(xpath = "//select[contains(@id, 'Card_dlForms_lAssigned')]")
	private WebElement assignedquestionsformscmb;
	
	@FindBy(xpath = "//a[contains(@id, 'Card_dlForms_btnAddToAssigned')]")
	private WebElement assignquestionsformbtn;
	
	@FindBy(xpath = "//span[text()='Question Forms']")
	private WebElement questionformstab;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnOk")
	private WebElement invoicetypeOKbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_ctl01_ctl02_BtnCancel")
	private WebElement invoicetypeCancelbtn;
	
	public NewInvoiceTypeDialogWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void createInvoiceType(String invoicetype) {
		setInvoiceTypeName(invoicetype);
		clickOKAddInvoiceTypeButton();
	}
	
	public boolean isRequiredCheckBoxVisible() throws InterruptedException {
		Thread.sleep(3000);
		return requiredchkbx.isDisplayed();
	}

	public void selectVisibleCheckBox() throws InterruptedException {
		Thread.sleep(1000);
		checkboxSelect(visiblechkbx);
		Thread.sleep(3000);
	}
	
	public void unselectVisibleCheckBox() {
		checkboxUnselect(visiblechkbx);
	}
	
	public void clickCancelAddInvoiceTypeButton() {
		click(invoicetypeCancelbtn);
	}
	
	public void selectRequiredCheckBox() throws InterruptedException {
		Thread.sleep(3000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[contains(@for, 'Card_cbPORequired')]")));
		wait.until(ExpectedConditions.visibilityOf(requiredchkbx));
		checkboxSelect(requiredchkbx);
	}
	
	public void clickOKAddInvoiceTypeButton() {
		clickAndWait(invoicetypeOKbtn);
	}
	
	public void setInvoiceTypeName(String invoicetypename) {
		clearAndType(invoicetypenamefld, invoicetypename);
	}
	
	public void selectSharingOption(String sharingoption) {
		selectComboboxValue(sharingcmb, sharingdd, sharingoption);
	}
	
	public void selectQuestionsTemplate(String questiontemplate) {
		selectComboboxValue(questionstemplatecmb, questionstemplatedd, questiontemplate);
	}
	
	public void selectNewInvoiceTypeWholesaleEmailTemplate(String wholesailemailtemplate) {
		selectComboboxValue(wholesaleemailtemplatecmb, wholesaleemailtemplatedd, wholesailemailtemplate);
	}
	
	public void selectNewInvoiceTypeRetailEmailTemplate(String retailmailtemplate) {
		selectComboboxValue(retailemailtemplatecmb, retailemailtemplatedd, retailmailtemplate);
	}
	
	public void selectNewInvoiceTypeWholesalePrintTemplate(String wholesailprinttemplate) {
		waitABit(300);
		selectComboboxValue(wholesaleprinttemplatecmb, wholesaleprinttemplatedd, wholesailprinttemplate);
	}
	
	public void selectNewInvoiceTypeRetailPrintTemplate(String retailprinttemplate) {
		selectComboboxValue(retailprinttemplatecmb, retailprinttemplatedd, retailprinttemplate);
	}
	
	public void selectUseNewPrintingCheckBox() {
		waitABit(300);
		checkboxSelect(usenewprintingcmbox);
	}
	
	public void selectQuestionForm(String questionformname) {
		questionformstab.click();
		Select selectBox = new Select(availablequestionsformscmb);
		selectBox.selectByVisibleText(questionformname);
		click(assignquestionsformbtn);
	}
	
	public void selectNewInvoiceTypeOption(String optionname) throws InterruptedException {
		checkboxSelect(optionname);
	}
	
	public void setInvoiceTypeDescription(String invoicetypedesc) {
		clearAndType(invoicetypedescfld, invoicetypedesc);
	}

}
