package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextInvoiceInfoScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement invoiceinfoscreen;
	
	@FindBy(xpath="//input[@name='Invoices.PONo']")
	private WebElement invoicepo;
	
	@FindBy(xpath="//span[@class='more-wrapper open-popup']")
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
		BaseUtils.waitABit(1000);
	}
	
	public void setInvoicePONumber(String ponumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(invoicepo));
		invoicepo.clear();
		invoicepo.sendKeys(ponumber);
		appiumdriver.hideKeyboard();
	}
	
	public void addQuickNoteToInvoice(String quicknote) {
		clickMenuButton();
		VNextNotesScreen notesscreen = clickNotesMenuItem();
		notesscreen.addQuickNote(quicknote);
		notesscreen.clickNotesBackButton();
	}
	
	public void addTextNoteToInvoice(String notetext) {
		clickMenuButton();
		VNextNotesScreen notesscreen = clickNotesMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
	}
	
	public void clickMenuButton() {
		tap(menubtn);
	}
	
	public VNextNotesScreen clickNotesMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(By.xpath("//div[@class='actions-modal modal-in']"))));
		tap(appiumdriver.findElement(By.xpath("//div[@class='actions-modal modal-in']")).findElement(By.xpath(".//div[@class='actions-modal-button']")));
		return new VNextNotesScreen(appiumdriver);
	}
	
	public VNextInvoicesScreen saveInvoice() {
		clickSaveInvoiceButton();
		return new VNextInvoicesScreen(appiumdriver);
	}
	
	public void clickSaveInvoiceButton() {
		tap(savebtn);
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

}
