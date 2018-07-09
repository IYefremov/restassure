package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;

public class VNextInvoiceInfoScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement invoiceinfoscreen;
	
	@FindBy(xpath="//input[@name='Invoices.PONo']")
	private WebElement invoicepo;

	@FindBy(xpath="//input[@name='Invoices.InvoiceDate']")
	private WebElement invoicedate;
	
	@FindBy(xpath="//span[@action='more_actions']")
	private WebElement menubtn;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;
	
	@FindBy(xpath="//a[@action='create-invoice']/i")
	private WebElement createinvoicemenu;
	
	@FindBy(xpath="//div[@class='estimation-number']")
	private WebElement invoicenumberfld;
	
	@FindBy(id="total")
	private WebElement invoicetotalamont;
	
	@FindBy(xpath="//div[@class='invoce-info-container']")
	private WebElement invoiceinfopanel;
	
	public VNextInvoiceInfoScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoiceinfoscreen));
	}
	
	public void setInvoicePONumber(String ponumber) {
		WaitUtils.waitUntilElementIsClickable(invoicepo);
		invoicepo.clear();
		invoicepo.sendKeys(ponumber);
		appiumdriver.hideKeyboard();
	}

	public String getInvoicePONumberValue() {
		return invoicepo.getAttribute("value");
	}

	public String getInvoiceDateValue() {
		return invoicedate.getAttribute("value");
	}
	
	public void addQuickNoteToInvoice(String quicknote) {
		clickMenuButton();
		VNextNotesScreen notesscreen = clickNotesMenuItem();
		notesscreen.addQuickNote(quicknote);
		notesscreen.clickNotesBackButton();
	}
	
	public VNextInvoiceInfoScreen addTextNoteToInvoice(String notetext) {
		clickMenuButton();
		VNextNotesScreen notesscreen = clickNotesMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		return this;
	}

	public void cancelInvoice() {
		clickMenuButton();
		VNextInvoiceMenuScreen invoiceMenuScreen = new VNextInvoiceMenuScreen(appiumdriver);
		invoiceMenuScreen.clickCancelInvoiceMenuItem();
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickInformationDialogYesButton();
	}
	
	public void clickMenuButton() {
		tap(menubtn);
	}
	
	public VNextNotesScreen clickNotesMenuItem() {
        VNextInvoiceMenuScreen invoiceMenuScreen = new VNextInvoiceMenuScreen(appiumdriver);
        return invoiceMenuScreen.clickInvoiceNotesMenuItem();
	}
	
	public VNextInvoicesScreen saveInvoice() {
		clickSaveInvoiceButton();
		return new VNextInvoicesScreen(appiumdriver);
	}

	public VNextInvoicesScreen saveInvoiceAsDraft() {
		clickSaveInvoiceButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickDraftButton();
		return new VNextInvoicesScreen(appiumdriver);
	}

	public VNextInvoicesScreen saveInvoiceAsFinal() {
		clickSaveInvoiceButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
		informationDialog.clickFinalButton();
		return new VNextInvoicesScreen(appiumdriver);
	}
	
	public void clickSaveInvoiceButton() {
		WaitUtils.click(savebtn);
	}
	
	public String getInvoiceNumber() {
		return invoicenumberfld.getText().trim();
	}
	
	public String getInvoiceTotalAmount() {
		return invoicetotalamont.getText().trim();
	}
	
	public boolean isWorkOrderSelectedForInvoice(String wonumber) {
		return invoiceinfopanel.findElements(By.xpath(".//div[text()='" + wonumber + "']")).size() > 0;
	}
	
	public void clickInvoiceInfoBackButton() {
		clickScreenBackButton();
	}

	public void changeInvoiceDayValue(LocalDate date) {
		tap(invoicedate);
		appiumdriver.hideKeyboard();
		setInvoiceSelectedDateValue(date);
		closeInvoiceSelectDatePicker();
 	}

 	private WebElement getDatePickerWheel() {
		return appiumdriver.findElement(By.xpath("//div[contains(@class, 'picker-modal picker-columns')]"));
	}

	private WebElement getDatePickerWheelDateColumn() {
		return getDatePickerWheel().findElement(By.xpath(".//div[@class='picker-modal-inner picker-items']/div[2]"));
	}

	public int getInvoiceSelectedDateValue() {
		WebElement pickerwheeldatecolumn = getDatePickerWheelDateColumn();
		return Integer.valueOf(pickerwheeldatecolumn.findElement(By.xpath(".//div[@class='picker-item picker-selected']")).getAttribute("data-picker-value"));
	}

	public void setInvoiceSelectedDateValue(LocalDate date) {
		WebElement pickerwheeldatecolumn = getDatePickerWheelDateColumn();
		tap(pickerwheeldatecolumn.findElement(By.xpath(".//div[@data-picker-value='" + date.getDayOfMonth() + "']")));
	}

	private void closeInvoiceSelectDatePicker() {
		WebElement pickerwheel = getDatePickerWheel();
		tap(pickerwheel.findElement(By.xpath(".//a[@class='link close-picker']")));
	}

}
