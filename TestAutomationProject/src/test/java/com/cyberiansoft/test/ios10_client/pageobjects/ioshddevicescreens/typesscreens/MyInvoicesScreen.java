package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.NotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.PrintSelectorPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class  MyInvoicesScreen extends BaseTypeScreenWithTabs {

	private final TypeScreenContext INVOICECONTEXT = TypeScreenContext.INVOICE;

	/*@iOSFindBy(accessibility  = "My Invoices")
    private IOSElement myinvoicesmenu;
	
	@iOSFindBy(accessibility  = "Team Invoices")
    private IOSElement teaminvoicesmenu;
	
	@iOSFindBy(accessibility  = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(accessibility  = "Single Email")
    private IOSElement singlemailbtn;
	
	@iOSFindBy(accessibility  = "Send Email")
    private IOSElement sendemailbtn;
	
	@iOSFindBy(accessibility = "Send")
    private IOSElement sendbtn;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement sendmailaddmailbtn;*/

	@iOSFindBy(accessibility  = "InvoicesPageTableLeft")
	private IOSElement invoicestable;


	@iOSFindBy(accessibility  = "Notes")
	private IOSElement notesmenu;

	@iOSFindBy(accessibility  = "Approve")
	private IOSElement approvemenu;

	@iOSFindBy(accessibility  = "Change Customer")
	private IOSElement changecustomermenu;

	@iOSFindBy(accessibility  = "Print")
	private IOSElement printmenu;

	@iOSFindBy(accessibility  = "Change PO#")
	private IOSElement changeponumbermenu;

	@iOSFindBy(accessibility  = "Edit")
	private IOSElement editmenu;

	@iOSFindBy(accessibility  = "Summary")
	private IOSElement summarymenu;
	
	public MyInvoicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Invoices")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("InvoicesPageTableLeft")));
		wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("InvoicesPageTableLeft")));
	}

	public boolean myInvoiceExists(String invoice) {
		return appiumdriver.findElementsByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + invoice + "']").size() > 0;
	}
	
	public String getInvoiceInfoLabel(String invoicenumber) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='"
								+ invoicenumber + "']/XCUIElementTypeStaticText[@name='labelInfo1']").getAttribute("value");
	}
	
	public void selectInvoice(String invoiceNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(invoiceNumber)));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + invoiceNumber + "']"))).click();
	}

	public String getPriceForInvoice(String invoiceNumber) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + invoiceNumber + "']/XCUIElementTypeStaticText[@name='labelInvoiceAmount']").getAttribute("value");
	}


	public boolean isInvoiceHasInvoiceNumberIcon(String invoiceNumber) {
		return invoicestable.findElementByAccessibilityId(invoiceNumber).findElementByAccessibilityId("INVOICE_NO").isDisplayed();
	}

	public boolean isInvoiceHasInvoiceSharedIcon(String invoiceNumber) {
		return invoicestable.findElementByAccessibilityId(invoiceNumber).findElementByAccessibilityId("INVOICE_SHARED").isDisplayed();
	}

	public void printInvoice(String invoicenum, String printserver) {
		selectInvoice(invoicenum);
		PrintSelectorPopup printselectorpopup = clickPrintPopup();
		printselectorpopup.checkRemotePrintServerAndSelectPrintServer(printserver);
		printselectorpopup.clickPrintSelectorPrintButton();
		BaseUtils.waitABit(500);
		printselectorpopup.clickPrintOptionsPrintButton();
	}
	
	public MyInvoicesScreen changeCustomerForInvoice(String invoice, String customer) {
		selectInvoice(invoice);
		clickChangeCustomerPopup();
		selectCustomer(customer);
		if (appiumdriver.findElementsByAccessibilityId("Customer changing...").size() > 0) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Customer changing...")));
		}
		return this;
	}
	
	public void selectCustomer(String customer) {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='CustomerSelectorTable']/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + customer + "']")).click();
		//appiumdriver.findElementByAccessibilityId(customer).click();
	}
	
	public IOSElement getFirstInvoice() {
		return (IOSElement) appiumdriver.findElementByAccessibilityId("InvoicesPageTableLeft").findElement(MobileBy.xpath("//XCUIElementTypeCell[1]"));
		//return (IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]");
	}
	
	public String getFirstInvoiceValue() {
		return getFirstInvoice().getAttribute("name");
	}
	
	public String getInvoicePrice(String invoicenumber) {
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='"
				+ invoicenumber + "']/XCUIElementTypeStaticText[@name='labelInvoiceAmount']").getAttribute("value");
	}
	
	public NotesScreen clickNotesPopup() {
		notesmenu.click();
		return new NotesScreen();
	}
	
	public PrintSelectorPopup clickPrintPopup() {
		printmenu.click();
		return new PrintSelectorPopup();
	}
	
	public void clickEditPopup() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Edit")));
		editmenu.click();
		BaseWizardScreen.typeContext = INVOICECONTEXT;
	}
	
	public void clickChangeCustomerPopup() {
		changecustomermenu.click();
	}
	
	public void clickChangePOPopup() {
		changeponumbermenu.click();
	}
	
	public SummaryScreen clickSummaryPopup() {
		summarymenu.click();
		return new SummaryScreen();
	}
	
	protected void clickApprovePopup() {
		approvemenu.click();
	}

	public void selectInvoiceForApprove(String invoicenumber) {
		selectInvoice(invoicenumber);
		clickApprovePopup();
	}
	
	public MyInvoicesScreen changePO(String newpo) {
		WebElement par = appiumdriver.findElementByXPath("//XCUIElementTypeStaticText[@name='PO#']/..");
		par.findElement(By.className("XCUIElementTypeTextField")).clear();
		par.findElement(By.className("XCUIElementTypeTextField")).sendKeys(newpo);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(newpo);
		appiumdriver.findElementByAccessibilityId("Done").click();
		return this;
	}
	
	public boolean myInvoicesIsDisplayed() {
		return appiumdriver.findElementByAccessibilityId("Invoices").isDisplayed();
	}
	
	public void clickActionButton() {
		appiumdriver.findElementByXPath("//XCUIElementTypeOther/XCUIElementTypeToolbar").findElement(By.xpath("//XCUIElementTypeButton[contains(@name,'Share')]")).click();
	}
	
	public void selectInvoiceForActionByIndex(int invoiceindex) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[" + invoiceindex + "]/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonUnchecked\")]").click();
	}
	
	public void clickDoneButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Done")));
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.AccessibilityId("Done")).click();
	}
	
	public void sendEmail(String email) {
		clickSendEmail();
		clickSendEmailAddMailButton();
		enterEmailAddress(email);
		clickSendButton();
	}
	
	public MyInvoicesScreen sendSingleEmail(String email) {
		clickSendEmail();
		clickSendEmailAddMailButton();
		enterEmailAddress(email);
		clickSendButton();
		appiumdriver.findElementByAccessibilityId("Single Email").click();
		return this;
	}
	
	public void clickSendEmail() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Send Email")));

		appiumdriver.findElementByAccessibilityId("Send Email").click();
	}
	
	public void clickSendButton() {
		appiumdriver.findElementByAccessibilityId("Send").click();
	}
	
	public void clickSendEmailAddMailButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Add")));
		appiumdriver.findElementByAccessibilityId("Add").click();
	}
	
	public void enterEmailAddress(String email) {
		//((IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTextField")).setValue(email);
		MobileElement mailAlert = (MobileElement) appiumdriver.findElementByAccessibilityId("Enter email address");
		mailAlert.findElementByClassName("XCUIElementTypeCollectionView").findElementByClassName("XCUIElementTypeTextField").sendKeys(email);

		//((IOSDriver) appiumdriver).getKeyboard().pressKey(email);
		Helpers.acceptAlert();
	}
	
	public void clickInvoiceApproveButton(String invoicenumber) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name=\"" + invoicenumber + "\"]/XCUIElementTypeOther[contains(@name, \"EntityInfoButtonUnchecked\")]").click();		
	}
	
	public boolean isInvoiceApproveButtonExists(String invoicenumber) {
		selectInvoice(invoicenumber);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = '" +
				invoicenumber + "' and type = 'XCUIElementTypeNavigationBar'")));
		boolean approved = appiumdriver.findElements(MobileBy.AccessibilityId("Approve")).size() > 0;
		return approved;	
	}
	
	public boolean isInvoiceApproveRedButtonExists(String invoicenumber) {
		return appiumdriver.findElementByAccessibilityId("InvoicesPageTableLeft").findElements(MobileBy.
				xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\"" + invoicenumber + "\"]/XCUIElementTypeOther[@name=\"EntityInfoButtonUnchecked, ButtonImageId_70\"]")).size() > 0;
	}
	
	public boolean isInvoiceApproveGreyButtonExists(String invoicenumber) {
		return appiumdriver.findElementByAccessibilityId("InvoicesPageTableLeft").findElements(MobileBy.
				xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name=\"" + invoicenumber + "\"]/XCUIElementTypeOther[@name=\"EntityInfoButtonUnchecked, ButtonImageId_32\"]")).size() > 0;
	}
	
	public boolean isInvoicePrintButtonExists(String invoicenumber) {		
		return appiumdriver.findElementByAccessibilityId("InvoicesPageTableLeft").findElements(MobileBy.
				xpath("//XCUIElementTypeCell[@name='" + invoicenumber + "']/XCUIElementTypeImage[@name='INVOICE_PRINTED']")).size() > 0;
	}

	public void clickVoidInvoiceMenu() {
		appiumdriver.findElementByAccessibilityId("Void").click();
	}

	public void clickBackButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Back"))).click();
	}

}
