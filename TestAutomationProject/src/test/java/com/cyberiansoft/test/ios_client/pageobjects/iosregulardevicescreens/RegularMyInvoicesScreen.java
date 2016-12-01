package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class RegularMyInvoicesScreen extends iOSRegularBaseScreen {

	@iOSFindBy(accessibility  = "Notes")
    private IOSElement notesmenu;
	
	@iOSFindBy(accessibility  = "Edit")
    private IOSElement editmenu;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAButton[@name=\"Change\nCustomer\"]")
    private IOSElement changecustomermenu;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAButton[7]")
    private IOSElement changeponumbermenu;
	
	@iOSFindBy(accessibility  = "My Invoices")
    private IOSElement myinvoicesmenu;
	
	@iOSFindBy(accessibility  = "Team Invoices")
    private IOSElement teaminvoicesmenu;
	
	@iOSFindBy(accessibility  = "Summary")
    private IOSElement summarymenu;
	
	@iOSFindBy(accessibility  = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(accessibility  = "Single Email")
    private IOSElement singlemailbtn;
	
	@iOSFindBy(accessibility  = "Send\nEmail")
    private IOSElement sendemailbtn;
	
	@iOSFindBy(accessibility  = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(xpath = "//UIANavigationBar[@name=\"Email\"]/UIAButton[@name=\"Send\"] ")
    private IOSElement sendbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@name=\"Add\"] ")
    private IOSElement sendmailaddmailbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@visible=\"true\" and (contains(@name,\"Share\"))] ")
    private IOSElement sharebtn;
	
	public RegularMyInvoicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public boolean myInvoiceExists(String invoice) {
		return appiumdriver.findElements(MobileBy.IosUIAutomation(".tableViews()[0].cells()[\"" + invoice + "\"]")).size() > 0;
	}
	
	public boolean areWOsForInvoiceExists(String invoice, String wo1, String wo2) {
		final String wos = wo1 + ", " +  wo2;
		return appiumdriver.findElements(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoice + "\"]. staticTexts()[\"" + wos + "\"]")).size() > 0;
	}
	
	public void selectInvoice(String invoice) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoice + "\"]")).click();
	}
	
	/*public void selectFirstInvoice(String invoice) {
		getFirstInvoice().click();
	}*/
	
	public String getPriceForFirstInvoice() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[@name='labelInvoiceAmount']").getAttribute("value");
	}
	
	public String getPriceForInvoice(String invoicenumber) {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[@name='" + invoicenumber + "']/UIAStaticText[@name='labelInvoiceAmount']").getAttribute("value");
	}
	
	public boolean isInvoiceHasInvoiceNumberIcon(String invoice) {
		return appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoice + "\"].images()[\"INVOICE_NO\"]")).isDisplayed();
	}
	
	public boolean isInvoiceHasInvoiceSharedIcon(String invoice) {
		return appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoice + "\"].images()[\"INVOICE_SHARED\"]")).isDisplayed();
	}
	
	public void changeCustomerForInspection(String invoice, String customer) throws InterruptedException {
		selectInvoice(invoice);
		clickChangeCustomerPopup();
		selectCustomer(customer);
	}
	
	public void selectCustomer(String customer) {
		appiumdriver.findElementByName(customer).click();
	}
	
	public IOSElement getFirstInvoice() {
		return (IOSElement) appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[0]"));
	}
	
	public String getFirstInvoiceValue() {
		return getFirstInvoice().getAttribute("name");
	}
	
	public void verifyInvoicePrice(String invoice, String price) {
		Assert.assertTrue(appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoice + "\"].staticTexts()[\"" + price + "\"]")).isDisplayed());
	}
	
	public RegularNotesScreen clickNotesPopup() {
		notesmenu.click();
		return new RegularNotesScreen(appiumdriver);
	}
	
	public RegularInvoiceInfoScreen clickEditPopup() {
		editmenu.click();
		return new RegularInvoiceInfoScreen(appiumdriver);
	}
	
	public void clickChangeCustomerPopup() {
		changecustomermenu.click();
	}
	
	public void clickChangePOPopup() {
		changeponumbermenu.click();
	}
	
	public void clickSummaryPopup() {
		summarymenu.click();
	}
	
	protected void clickApproveInvoiceButton() {
		approvepopupmenu.click();
	}
	
	public boolean isSummaryPDFExists() {
		return appiumdriver.findElementsByXPath("//UIAStaticText[@name=\"Generating PDF file...\"]").size() > 0;
	}
	
	public void changePO(String newpo) throws InterruptedException {	
		Helpers.keyboadrType(newpo);
		appiumdriver.switchTo().alert().accept();
	}
	
	public boolean myInvoicesIsDisplayed() throws InterruptedException {
		return myinvoicesmenu.isEnabled();
	}
	
	public boolean teamInvoicesIsDisplayed() throws InterruptedException {
		return teaminvoicesmenu.isDisplayed();
	}
	
	public void clickActionButton() {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".toolbars()[0].buttons()[\"Share\"]")).click();
		//sharebtn.click();
	}
	
	public void selectInvoiceForApprove(String inspnumber) {
		selectInvoice(inspnumber);
		clickApproveInvoiceButton();
	}
	
	public void selectEmployee(String employee) {
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByXPath("//UIASecureTextField[@value=\"Enter password here\"]")).setValue(password);
		Helpers.acceptAlert();
	}
	
	public void selectInvoiceForActionByIndex(int invoiceindex) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoiceindex + "\"].buttons()[0]")).click();
		//appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[\"" + invoiceindex + "\"]/UIAButton[@name=\"EntityInfoButtonUnchecked\"] ").click();
	}
	
	public boolean isInvoiceApproveButtonExists(String invoicenumber) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".tableViews()[\"MyInvoicesTable\"].cells()[\"" + invoicenumber + "\"]")).click();
		boolean approved = appiumdriver.findElements(MobileBy.IosUIAutomation(".scrollViews()[1].buttons()['Approve']")).size() > 0;
		clickCancel();
		return approved;		
	}
	
	public void clickDoneButton() {
		donebtn.click();
	}
	
	public void sendEmail(String email) {
		clickSendEmail();
		clickSendEmailAddMailButton();
		enterEmailAddress(email);
		clickSendButton();
	}
	
	public void sendSingleEmail(String email) {
		clickSendEmail();
		clickSendEmailAddMailButton();
		enterEmailAddress(email);
		clickSendButton();
		singlemailbtn.click();
	}
	
	public void clickSendEmail() {
		appiumdriver.findElement(By.xpath("//UIAScrollView[2]/UIAButton[@name='Send\nEmail']")).click();
	}
	
	public void clickSendButton() {
		sendbtn.click();
	}
	
	public void clickSendEmailAddMailButton() {
		sendmailaddmailbtn.click();
	}
	
	public void enterEmailAddress(String email) {
		((IOSElement) appiumdriver.findElementByXPath("//UIAAlert[1]/UIAScrollView[1]/UIACollectionView[1]/UIACollectionCell[1]/UIATextField[1]")).setValue(email);
		Helpers.acceptAlert();
	}
}
