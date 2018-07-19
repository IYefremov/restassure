package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextInvoiceMenuScreen extends VNextBasicMenuScreen {

	@FindBy(xpath="//a[@data-name='email']")
	private WebElement emailinvoicebtn;

	@FindBy(xpath="//a[@data-name='edit']")
	private WebElement editinvoicebtn;
	
	@FindBy(xpath="//a[@data-name='approve']")
	private WebElement approveinvoicebtn;
	
	@FindBy(xpath="//a[@handler='_voidInvoice']")
	private WebElement voidinvoicebtn;
	
	@FindBy(xpath="//a[@handler='_changePO']")
	private WebElement invoicechangeponumbtn;
	
	@FindBy(xpath="//a[@handler='_notes']")
	private WebElement invoicenotesbtn;
	
	@FindBy(xpath="//a[@handler='_refreshPictures']")
	private WebElement invoicerfreshpicturesbtn;

	@FindBy(xpath="//a[@data-name='pay']")
	private WebElement invoicepaymenubtn;

	@FindBy(xpath="//a[@data-name='cancel']")
	private WebElement invoicecancelmenubtn;
	
	//@FindBy(xpath="//div[@class='actions-layer popup modal-in' and @data-menu='popup']")
	//private WebElement invoicemenuscreen;
	
	public VNextInvoiceMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		//wait.until(ExpectedConditions.visibilityOf(invoicemenuscreen));
	}
	
	public VNextEmailScreen clickEmailInvoiceMenuItem() {
		clickMenuItem(emailinvoicebtn);
		return new VNextEmailScreen(appiumdriver);
	}
	
	public VNextApproveScreen clickApproveInvoiceMenuItem() {
		clickMenuItem(approveinvoicebtn);
		return new VNextApproveScreen(appiumdriver);
	}

	public void clickCancelInvoiceMenuItem() {
		clickMenuItem(invoicecancelmenubtn);
	}

	public boolean isApproveInvoiceMenuItemExists() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@data-name='approve']")));
		return approveinvoicebtn.isDisplayed();
	}
	
	public void clickVoidInvoiceMenuItem() {
		clickMenuItem(voidinvoicebtn);
	}
	
	public VNextChangeInvoicePONumberDialog clickInvoiceChangePONumberMenuItem() {
		clickMenuItem(invoicechangeponumbtn);
		return new VNextChangeInvoicePONumberDialog(appiumdriver);
	}
	
	public VNextNotesScreen clickInvoiceNotesMenuItem() {
		clickMenuItem(invoicenotesbtn);
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickInvoiceRefreshPicturesMenuItem() {
		clickMenuItem(invoicerfreshpicturesbtn);
	}
	
	public void refreshInvoicePictures() {
		clickInvoiceRefreshPicturesMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}

	public VNextPayInvoicesScreen clickPayInvoiceMenuItem() {
		clickMenuItem(invoicepaymenubtn);
		return new VNextPayInvoicesScreen(appiumdriver);
	}

	public boolean isInvoiceChangePONumberMenuItemExists() {
		return invoicechangeponumbtn.isDisplayed();
	}

	public boolean isInvoiceEditMenuItemExists() {
		return editinvoicebtn.isDisplayed();
	}
	
	public VNextInvoicesScreen clickCloseInvoiceMenuButton() {
		clickCloseMenuButton();
		return new VNextInvoicesScreen(appiumdriver);
	}

	public VNextInvoiceInfoScreen clickEditInvoiceMenuItem() {
		clickMenuItem(editinvoicebtn);
		return new VNextInvoiceInfoScreen(appiumdriver);
	}
}
