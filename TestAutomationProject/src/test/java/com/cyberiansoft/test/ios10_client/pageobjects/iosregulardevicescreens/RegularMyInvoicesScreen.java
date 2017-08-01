package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularMyInvoicesScreen extends iOSRegularBaseScreen {

	@iOSFindBy(accessibility = "Notes")
    private IOSElement notesmenu;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editmenu;
	
	@iOSFindBy(accessibility = "Change\nCustomer")
    private IOSElement changecustomermenu;
	
	@iOSFindBy(accessibility = "Change\nPO#")
    private IOSElement changeponumbermenu;
	
	@iOSFindBy(accessibility = "My Invoices")
    private IOSElement myinvoicesmenu;
	
	@iOSFindBy(accessibility = "Team Invoices")
    private IOSElement teaminvoicesmenu;
	
	@iOSFindBy(accessibility = "Summary")
    private IOSElement summarymenu;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement donebtn;
	
	@iOSFindBy(accessibility = "Single Email")
    private IOSElement singlemailbtn;
	
	@iOSFindBy(accessibility = "Send\nEmail")
    private IOSElement sendemailbtn;
	
	@iOSFindBy(accessibility = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(accessibility = "Send")
    private IOSElement sendbtn;
	
	@iOSFindBy(accessibility = "Add")
    private IOSElement sendmailaddmailbtn;
	
	@iOSFindBy(xpath = "//UIAToolbar[1]/UIAButton[@visible=\"true\" and (contains(@name,\"Share\"))] ")
    private IOSElement sharebtn;
	
	public RegularMyInvoicesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public boolean myInvoiceExists(String invoice) {
		return appiumdriver.findElements(MobileBy.AccessibilityId(invoice)).size() > 0;
	}
	
	public String getWOsForInvoice(String invoice) {
		
		WebElement invoicestable = appiumdriver.findElementByAccessibilityId("MyInvoicesTable");
		return invoicestable.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + invoice + "']/XCUIElementTypeStaticText[@name='labelInfo2']")).getAttribute("value");
	}
	
	public void selectInvoice(String invoice) {
		appiumdriver.findElement(MobileBy.AccessibilityId(invoice)).click();
	}
	
	/*public void selectFirstInvoice(String invoice) {
		getFirstInvoice().click();
	}*/
	
	public String getPriceForFirstInvoice() {
		return appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[1]/UIAStaticText[3]").getAttribute("name");
	}
	
	public boolean isInvoiceHasInvoiceNumberIcon(String invoice) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='MyInvoicesTable']/XCUIElementTypeCell[@name='" + invoice + "']/XCUIElementTypeImage[@name='INVOICE_NO']")).isDisplayed();
	}
	
	public boolean isInvoiceHasInvoiceSharedIcon(String invoice) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='MyInvoicesTable']/XCUIElementTypeCell[@name='" + invoice + "']/XCUIElementTypeImage[@name='INVOICE_SHARED']")).isDisplayed();
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
		return (IOSElement) appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]"));
	}
	
	public String getFirstInvoiceValue() {
		return getFirstInvoice().getAttribute("name");
	}
	
	public String getInvoicePrice(String invoicenumber) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[@name='MyInvoicesTable']/XCUIElementTypeCell[@name='" + invoicenumber + "']/XCUIElementTypeStaticText[@name='labelInvoiceAmount']")).getAttribute("label");
	}
	
	public RegularNotesScreen clickNotesPopup() {
		notesmenu.click();
		return new RegularNotesScreen(appiumdriver);
	}
	
	public RegularInvoiceInfoScreen clickEditPopup() {
		editmenu.click();
		Helpers.waitABit(1000);
		return new RegularInvoiceInfoScreen(appiumdriver);
	}
	
	public void clickChangeCustomerPopup() {
		changecustomermenu.click();
	}
	
	public void clickChangePOPopup() {
		changeponumbermenu.click();
		Helpers.waitABit(1000);
	}
	
	public void clickSummaryPopup() {
		summarymenu.click();
	}
	
	protected void clickApproveInvoiceButton() {
		approvepopupmenu.click();
		Helpers.waitABit(2000);
	}
	
	public boolean isSummaryPDFExists() {
		return appiumdriver.findElementsByXPath("//UIAStaticText[@name=\"Generating PDF file...\"]").size() > 0;
	}
	
	public void changePO(String newpo) {	
		appiumdriver.findElementByClassName("XCUIElementTypeCollectionView").findElement(By.className("XCUIElementTypeTextField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(newpo);
		appiumdriver.switchTo().alert().accept();
		Helpers.waitABit(1000);
	}
	
	public boolean myInvoicesIsDisplayed() throws InterruptedException {
		return myinvoicesmenu.isEnabled();
	}
	
	public boolean teamInvoicesIsDisplayed() throws InterruptedException {
		return teaminvoicesmenu.isDisplayed();
	}
	
	public void clickActionButton() {
		appiumdriver.findElement(MobileBy.AccessibilityId("Share")).click();
		//sharebtn.click();
	}
	
	public void selectInvoiceForApprove(String inspnumber) {
		selectInvoice(inspnumber);
		clickApproveInvoiceButton();
	}
	
	public void selectEmployee(String employee) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10); 
		wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByName(employee)));
		appiumdriver.findElementByName(employee).click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByXPath("//UIASecureTextField[@value=\"Enter password here\"]")).setValue(password);
		Helpers.acceptAlert();
	}
	
	public void selectInvoiceForActionByIndex(int invoiceindex) {
		appiumdriver.findElementByXPath("//XCUIElementTypeTable[@name='MyInvoicesTable']/XCUIElementTypeCell[" + invoiceindex + "]/XCUIElementTypeOther").click();
		//appiumdriver.findElementByXPath("//UIATableView[1]/UIATableCell[\"" + invoiceindex + "\"]/UIAButton[@name=\"EntityInfoButtonUnchecked\"] ").click();
	}
	
	public boolean isInvoiceApproveButtonExists(String invoicenumber) {
		
		appiumdriver.findElementByAccessibilityId(invoicenumber).click();
		boolean approved = appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
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
		appiumdriver.findElementByAccessibilityId("Send\nEmail").click();
	}
	
	public void clickSendButton() {
		sendbtn.click();
	}
	
	public void clickSendEmailAddMailButton() {
		sendmailaddmailbtn.click();
	}
	
	public void enterEmailAddress(String email) {
		((IOSElement) appiumdriver.findElementByXPath("//XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell/XCUIElementTypeTextField")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(email);
		Helpers.waitABit(500);
		Helpers.acceptAlert();
	}
	
	public String getPriceForInvoice(String invoicenumber) {
		return appiumdriver.findElementByAccessibilityId("labelInvoiceAmount").getAttribute("value");
	}
	
	public void selectInvoices(int numberInvoicesToSelect) {
		for (int i = 0; i < numberInvoicesToSelect; i++)
			appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[" + i + "]")).click();
	}
}
