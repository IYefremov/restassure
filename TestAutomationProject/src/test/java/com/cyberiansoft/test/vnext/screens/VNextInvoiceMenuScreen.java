package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInvoiceMenuScreen extends VNextBaseScreen {

	@FindBy(xpath="//a[@action='email']/i")
	private WebElement emailinvoicebtn;
	
	@FindBy(xpath="//a[@handler='_approve']")
	private WebElement approveinvoicebtn;
	
	@FindBy(xpath="//a[@handler='_voidInvoice']")
	private WebElement voidinvoicebtn;
	
	@FindBy(xpath="//a[@handler='_changePO']")
	private WebElement invoicechangeponumbtn;
	
	@FindBy(xpath="//a[@handler='_notes']")
	private WebElement invoicenotesbtn;
	
	@FindBy(xpath="//a[@handler='_refreshPictures']")
	private WebElement invoicerfreshpicturesbtn;
	
	@FindBy(xpath="//div[@data-menu='popup']")
	private WebElement invoicemenuscreen;
	
	@FindBy(xpath="//div[@class='close-popup close-actions']")
	private WebElement closebtn;
	
	public VNextInvoiceMenuScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoicemenuscreen));
	}
	
	public VNextEmailScreen clickEmailInvoiceMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(emailinvoicebtn));
		tap(emailinvoicebtn);
		log(LogStatus.INFO, "Tap on Email Invoice Menu");
		return new VNextEmailScreen(appiumdriver);
	}
	
	public VNextApproveScreen clickApproveInvoiceMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveinvoicebtn));
		tap(approveinvoicebtn);
		log(LogStatus.INFO, "Tap on Approve Invoice Menu");
		return new VNextApproveScreen(appiumdriver);
	}
	
	public void clickVoidInvoiceMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(voidinvoicebtn));
		tap(voidinvoicebtn);
		log(LogStatus.INFO, "Tap on Void Invoice Menu");
	}
	
	public VNextChangeInvoicePONumberDialog clickInvoiceChangePONumberMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoicechangeponumbtn));
		tap(invoicechangeponumbtn);
		log(LogStatus.INFO, "Tap on Change PO Number Menu");
		return new VNextChangeInvoicePONumberDialog(appiumdriver);
	}
	
	public VNextNotesScreen clickInvoiceNotesMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoicenotesbtn));
		tap(invoicenotesbtn);
		log(LogStatus.INFO, "Tap on Invoice Notes Menu");
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickInvoiceRefreshPicturesMenuItem() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(invoicerfreshpicturesbtn));
		tap(invoicerfreshpicturesbtn);
		log(LogStatus.INFO, "Tap on Invoice Refresh Pictures Menu");
	}
	
	public void refreshInvoicePictures() {
		clickInvoiceRefreshPicturesMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
	
	public boolean isInvoiceChangePONumberMenuItemExists() {
		return invoicechangeponumbtn.isDisplayed();
	}
	
	public VNextInvoicesScreen clickCloseInvoiceMenuButton() {
		tap(closebtn);
		log(LogStatus.INFO, "Tap on Close Invoice Menu button");
		return new VNextInvoicesScreen(appiumdriver);
	}

}
