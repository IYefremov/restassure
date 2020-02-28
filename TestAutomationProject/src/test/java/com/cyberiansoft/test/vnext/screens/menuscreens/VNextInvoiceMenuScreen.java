package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextInvoiceMenuScreen extends VNextBasicMenuScreen {

	@FindBy(xpath = "//*[@data-name='email']")
	private WebElement emailinvoicebtn;

	@FindBy(xpath = "//*[@data-name='edit']")
	private WebElement editinvoicebtn;

	@FindBy(xpath = "//*[@data-name='approve']")
	private WebElement approveinvoicebtn;

	@FindBy(xpath = "//*[@data-name='void']")
	private WebElement voidinvoicebtn;

	@FindBy(xpath = "//*[@data-name='changePO']")
	private WebElement invoicechangeponumbtn;

	@FindBy(xpath = "//*[@data-name='notes']")
	private WebElement invoicenotesbtn;

	@FindBy(xpath = "//*[@data-name='refresh']")
	private WebElement invoicerfreshpicturesbtn;

	@FindBy(xpath = "//*[@data-name='payMulti']")
	private WebElement invoicepaymenubtn;

	@FindBy(xpath = "//*[@data-name='payPORO']")
	private WebElement invoicepayporomenubtn;

	@FindBy(xpath = "//*[@data-name='cancel']")
	private WebElement invoicecancelmenubtn;


    public VNextInvoiceMenuScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
	}

	
	public VNextApproveScreen clickApproveInvoiceMenuItem() {
		clickMenuItem(approveinvoicebtn);
		return new VNextApproveScreen(appiumdriver);
	}

	public boolean isApproveInvoiceMenuItemExists() {
		return  WaitUtils.isElementPresent(approveinvoicebtn);
	}
	
	public VNextChangeInvoicePONumberDialog clickInvoiceChangePONumberMenuItem() {
		clickMenuItem(invoicechangeponumbtn);
		return new VNextChangeInvoicePONumberDialog(appiumdriver);
	}
	
	public VNextNotesScreen clickInvoiceNotesMenuItem() {
		clickMenuItem(invoicenotesbtn);
		VNextNotesScreen notesScreen = new VNextNotesScreen();
		WaitUtils.elementShouldBeVisible(notesScreen.getRootElement(), true);
		return new VNextNotesScreen();
	}
	
	public void clickInvoiceRefreshPicturesMenuItem() {
		clickMenuItem(invoicerfreshpicturesbtn);
	}
	
	public void refreshInvoicePictures() {
		clickInvoiceRefreshPicturesMenuItem();
		BaseUtils.waitABit(2000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}

	public boolean isInvoiceChangePONumberMenuItemExists() {
		return WaitUtils.isElementPresent(invoicechangeponumbtn);
	}

	public void clickCancelInvoiceMenuItem() {
		clickMenuItem(invoicecancelmenubtn);
	}
}
