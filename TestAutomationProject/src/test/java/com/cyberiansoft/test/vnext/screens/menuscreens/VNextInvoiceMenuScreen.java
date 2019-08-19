package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	
	public VNextInvoiceMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
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
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-name='approve']")));
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

	public VNextPayMenu clickPayInvoiceMenuItem() {
		clickMenuItem(invoicepaymenubtn);
        return new VNextPayMenu(appiumdriver);
	}

	public boolean isPayInvoiceMenuItemExists() {
		return invoicepaymenubtn.isDisplayed();
	}

	public VNextPayInvoicesScreen clickPayCreditCardMenuItem() {
		VNextPayMenu payMenu = clickPayInvoiceMenuItem();
		return payMenu.clickPayCreditCardMenuItem();
	}

	public VNextPayCashCheckScreen clickPayCachCheckMenuItem() {
		VNextPayMenu payMenu = clickPayInvoiceMenuItem();
		return payMenu.clickPayCachCheckMenuItem();
	}

	public VNextPayPOROScreen clickPayPOROMenuItem() {
		VNextPayMenu payMenu = clickPayInvoiceMenuItem();
		return payMenu.clickPayPOROMenuItem();
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

	public boolean isInvoicePayPOROMenuItemExists() {
		return invoicepayporomenubtn.isDisplayed();
	}
}
