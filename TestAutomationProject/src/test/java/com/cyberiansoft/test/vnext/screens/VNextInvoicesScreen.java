package com.cyberiansoft.test.vnext.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextInvoicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-autotests-id='invoices-list']")
	private WebElement invoiceslist;
	
	@FindBy(xpath="//*[@action='add']")
	private WebElement addinvoicebtn;
	
	@FindBy(xpath="//*[@action='my']")
	private WebElement myinvoicestab;
	
	@FindBy(xpath="//*[@action='team']")
	private WebElement teaminvoicestab;
	
	@FindBy(xpath="//*[@action='hide-multiselect-actions']")
	private WebElement cancelselectedinvoices;
	
	public VNextInvoicesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 25);
		wait.until(ExpectedConditions.visibilityOf(invoiceslist));
		BaseUtils.waitABit(1000);
	}
	
	public String getInvoicePriceValue(String invoicenumber) {
		String invoiceprice = null;
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (invoicecell != null)
			invoiceprice = invoicecell.findElement(By.xpath(".//div[@class='checkbox-list-title checkbox-list-bold']")).getText();
		else
			Assert.assertTrue(false, "Can't find invoice: " + invoicenumber);
		return invoiceprice;	
	}
	
	public String getInvoiceStatusValue(String invoicenumber) {
		String invoicecell = null;
		WebElement inspcell = getInvoiceCell(invoicenumber);
		if (inspcell != null)
			invoicecell = inspcell.findElement(By.xpath(".//div[@action='select']/div[contains(@class, 'entity-item-status-')]")).getText();
		else
			Assert.assertTrue(false, "Can't find invoice: " + invoicenumber);
		return invoicecell;
	}
	
	public ArrayList<String> getInvoiceWorkOrders(String invoicenumber) {
		ArrayList<String> workOrders = new ArrayList<String>();
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		expandInvoiceDetails(invoicenumber);
		if (invoicecell != null) {
			String wos = invoicecell.findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[@class='truncate']")).getText().trim();
			List<String> wosarray = Arrays.asList(wos.split(","));
			for (String wonumber : wosarray)
				workOrders.add(wonumber);
		}
		else
			Assert.assertTrue(false, "Can't find invoice: " + invoicenumber);
		return workOrders;
	}
	
	public String getInvoicePONumberValue(String invoicenumber) {
		String poNumber = "";
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		expandInvoiceDetails(invoicenumber);
		if (invoicecell != null) {
			poNumber = invoicecell.findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[contains(text(), 'PO#')]")).getText();
			poNumber = poNumber.substring(3, poNumber.length()).trim();
		} else
			Assert.assertTrue(false, "Can't find invoice: " + invoicenumber);
		return poNumber;
	}
	
	public void expandInvoiceDetails(String invoicenumber) {
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (!invoicecell.getAttribute("class").contains("expanded")) {
			tap(invoicecell.findElement(By.xpath(".//div[@action='toggle_item']")));
		}
		else
			Assert.assertTrue(false, "Can't find invoice: " + invoicenumber);
	}
	
	public WebElement getInvoiceCell(String invoicenumber) {
		WebElement invoicecell = null;
		List<WebElement> invoices = invoiceslist.findElements(By.xpath(".//*[@class='entity-item accordion-item']"));
		for (WebElement invcell : invoices)
			if (invcell.findElements(By.xpath(".//div[@class='checkbox-list-title' and text()='" + invoicenumber + "']")).size() > 0) {
				invoicecell = invcell;
				break;
			}
				
		return invoicecell;
	}
	
	public boolean isInvoiceExists(String invoicenumber) {
		return invoiceslist.findElements(By.xpath(".//div[@class='checkbox-list-title' and text()='" + invoicenumber + "']")).size() > 0;
	}
	
	public void unselectAllSelectedInvoices() {
		if (cancelselectedinvoices.isDisplayed())
			tap(cancelselectedinvoices);
		log(LogStatus.INFO, "Click unselect all Selected Invoices button");
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		log(LogStatus.INFO, "Click Invoices screen Back button");
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextInvoiceMenuScreen clickOnInvoiceByInvoiceNumber(String invoicenumber) {
		tap(invoiceslist.findElement(By.xpath(".//div[@class='checkbox-list-title' and text()='" + invoicenumber + "']")));
		log(LogStatus.INFO, "Tap on Invoice: " + invoicenumber);
		return new VNextInvoiceMenuScreen(appiumdriver);
	}
	
	public VNextEmailScreen clickOnInvoiceToEmail(String invoicenumber) {
		VNextInvoiceMenuScreen invoicemenulist = clickOnInvoiceByInvoiceNumber(invoicenumber);
		return invoicemenulist.clickEmailInvoiceMenuItem();
	}
	
	public VNextWorkOrdersScreen clickAddInvoiceButton() {	
		tap(addinvoicebtn);
		BaseUtils.waitABit(1000);
		log(LogStatus.INFO, "Tap Add inspection button");
		VNextWorkOrdersScreen woscreeen = new VNextWorkOrdersScreen(appiumdriver);
		if (appiumdriver.findElements(By.xpath("//div[text()='Tap a work order, and then tap Create Invoice.']")).size() > 0)
			new VNextInformationDialog(appiumdriver).clickInformationDialogOKButton();
		return woscreeen;
	}
	
	public void switchToTeamInvoicesView() {
		tap(teaminvoicestab);
		if (appiumdriver.findElements(By.xpath("//*[text()='Loading invoices']")).size() > 0) {
			try {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElement(By.xpath("//*[text()='Loading work orders']"))));
			} catch (NoSuchElementException e) {
				//do nothing
			}
		}
		log(LogStatus.INFO, "Switch to Team Invoices view");
	}
	
	public boolean isTeamInvoicesViewActive() {
		return teaminvoicestab.getAttribute("class").contains("active");
	}
	
	public void switchToMyInvoicesView() {
		tap(myinvoicestab);
		log(LogStatus.INFO, "Switch to My Invoices view");
	}
	
	public boolean isMyInvoicesViewActive() {
		return myinvoicestab.getAttribute("class").contains("active");
	}
	
	public void selectInvoice(String invoiceNumber) {
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		if (invoicecell != null)
			if (invoicecell.findElement(By.xpath(".//input[@type='checkbox']")).getAttribute("checked") == null)
				tap(invoicecell.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			Assert.assertTrue(false, "Can't find invoice: " + invoiceNumber);
	}
	
	public void clickOnSelectedInvoicesMailButton() {
		tap(appiumdriver.findElement(By.xpath(".//*[@action='multiselect-actions-send-email']")));
		log(LogStatus.INFO, "Tap selected invoices Mail Button");
	}

}
