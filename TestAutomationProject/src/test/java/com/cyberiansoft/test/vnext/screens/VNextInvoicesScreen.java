package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-autotests-id='invoices-list']")));
		WaitUtils.waitUntilElementIsClickable(By.xpath("//div[@data-autotests-id='invoices-list']"));
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading invoices']"));
	}
	
	public String getInvoicePriceValue(String invoicenumber) {
		String invoiceprice = null;
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (invoicecell != null)
			invoiceprice = invoicecell.findElement(By.xpath(".//div[@class='checkbox-item-title checkbox-item-price']")).getText();
		else
			Assert.fail( "Can't find invoice: " + invoicenumber);
		return invoiceprice;	
	}
	
	public String getInvoiceStatusValue(String invoicenumber) {
		String invoicecell = null;
		WebElement inspcell = getInvoiceCell(invoicenumber);
		if (inspcell != null)
			invoicecell = inspcell.findElement(By.xpath(".//div[@action='select']/div[contains(@class, 'entity-item-status')]/span[contains(@class, 'entity-item-status')]")).getText();
		else
			Assert.fail("Can't find invoice: " + invoicenumber);
		return invoicecell;
	}

	public String getInvoiceDateValue(String invoicenumber) {
		String invoicecell = null;
		WebElement inspcell = getInvoiceCell(invoicenumber);
		if (inspcell != null)
			invoicecell = inspcell.findElement(By.xpath(".//div[@action='select']/div[2]/span[@class='text-dim']")).getText();
		else
			Assert.fail("Can't find invoice: " + invoicenumber);
		return invoicecell;
	}
	
	public ArrayList<String> getInvoiceWorkOrders(String invoicenumber) {
		ArrayList<String> workOrders = new ArrayList<>();
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		expandInvoiceDetails(invoicenumber);
		if (invoicecell != null) {
			String wos = invoicecell.findElement(By.xpath(".//div[@class='accordion-item-content']")).
					findElement(By.xpath(".//div[@class='truncate']")).getText().trim();
			List<String> wosarray = Arrays.asList(wos.split(","));
			for (String wonumber : wosarray)
				workOrders.add(wonumber.trim());
		}
		else
			Assert.fail("Can't find invoice: " + invoicenumber);
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
			Assert.fail("Can't find invoice: " + invoicenumber);
		return poNumber;
	}

	public String getInvoiceCustomerValue(String invoicenumber) {
		String customerValue = "";
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (invoicecell != null) {
			customerValue = invoicecell.findElement(By.xpath(".//div[@class='entity-item-title']")).getText().trim();
		} else
			Assert.fail( "Can't find invoice: " + invoicenumber);
		return customerValue;
	}
	
	public void expandInvoiceDetails(String invoicenumber) {
		WebElement invoicecell = getInvoiceCell(invoicenumber);
		if (!invoicecell.getAttribute("class").contains("expanded")) {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(invoicecell.findElement(By.xpath(".//div[@action='toggle_item']"))));
			try {
				tap(invoicecell.findElement(By.xpath(".//div[@action='toggle_item']")));
			} catch (WebDriverException e) {
				((JavascriptExecutor) appiumdriver).executeScript("arguments[0].scrollIntoView(true);", invoicecell.findElement(By.xpath(".//div[@action='toggle_item']")));
				tap(invoicecell.findElement(By.xpath(".//div[@action='toggle_item']")));
			}

		}
		else
			Assert.fail("Can't find invoice: " + invoicenumber);
	}
	
	public WebElement getInvoiceCell(String invoicenumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'entity-item accordion-item')]")));
		WebElement invoicecell = null;
		List<WebElement> invoices = invoiceslist.findElements(By.xpath(".//*[contains(@class, 'entity-item accordion-item')]"));;
		for (WebElement invcell : invoices) {
			if (invcell.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim().equals(invoicenumber)) {
				invoicecell = invcell;
				break;
			}
		}
		return invoicecell;
	}
	
	public boolean isInvoiceExists(String invoicenumber) {
		return invoiceslist.findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']")).size() > 0;
	}
	
	public void unselectAllSelectedInvoices() {
		if (cancelselectedinvoices.isDisplayed())
			tap(cancelselectedinvoices);
	}
	
	public VNextHomeScreen clickBackButton() {
		clickScreenBackButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextInvoiceMenuScreen clickOnInvoiceByInvoiceNumber(String invoicenumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-autotests-id='invoices-list']")));
		tap(invoiceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + invoicenumber + "']")));
		return new VNextInvoiceMenuScreen(appiumdriver);
	}
	
	public VNextEmailScreen clickOnInvoiceToEmail(String invoicenumber) {
		VNextInvoiceMenuScreen invoicemenulist = clickOnInvoiceByInvoiceNumber(invoicenumber);
		return invoicemenulist.clickEmailInvoiceMenuItem();
	}
	
	public VNextWorkOrdersScreen clickAddInvoiceButton() {	
		tap(addinvoicebtn);
		VNextWorkOrdersScreen woscreeen = new VNextWorkOrdersScreen(appiumdriver);
		if (appiumdriver.findElements(By.xpath("//div[text()='Tap a work order, and then tap Create Invoice.']")).size() > 0)
			new VNextInformationDialog(appiumdriver).clickInformationDialogOKButton();
		return woscreeen;
	}
	
	public void switchToTeamInvoicesView() {
		tap(teaminvoicestab);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='button active' and @action='team']")));
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading invoices']"));
		/*if (appiumdriver.findElements(By.xpath("//*[text()='Loading invoices']")).size() > 0) {
			try {
			WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
			wait.until(ExpectedConditions.invisibilityOf(appiumdriver.findElement(By.xpath("//*[text()='Loading work orders']"))));
			} catch (NoSuchElementException e) {
				//do nothing
			}
		}*/
	}
	
	public boolean isTeamInvoicesViewActive() {
		return teaminvoicestab.getAttribute("class").contains("active");
	}
	
	public void switchToMyInvoicesView() {
		WaitUtils.waitUntilElementInvisible(By.xpath("//*[text()='Loading invoices']"));
		WaitUtils.waitUntilElementIsClickable(By.xpath("//*[@action='my']"));
		tap(myinvoicestab);
	}
	
	public boolean isMyInvoicesViewActive() {
		return myinvoicestab.getAttribute("class").contains("active");
	}
	
	public void selectInvoice(String invoiceNumber) {
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		if (invoicecell != null)
			if (invoicecell.findElement(By.xpath(".//input[@action='check-item']")).getAttribute("checked") == null)
				tap(invoicecell.findElement(By.xpath(".//input[@action='check-item']")));
		else
			Assert.fail("Can't find invoice: " + invoiceNumber);
	}
	
	public void clickOnSelectedInvoicesMailButton() {
		tap(appiumdriver.findElement(By.xpath(".//*[@action='multiselect-actions-send-email']")));
	}


	public boolean isInvoiceHasApproveIcon(String invoiceNumber) {
		boolean exists = false;
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		if (invoicecell != null)
			exists = invoicecell.findElements(By.xpath(".//div[@data-autotests-id='invoice_signed']")).size() > 0;
			else
				Assert.fail("Can't find invoice: " + invoiceNumber);
		return exists;
	}

	public boolean isInvoiceHasNotesIcon(String invoiceNumber) {
		boolean exists = false;
		WebElement invoicecell = getInvoiceCell(invoiceNumber);
		if (invoicecell != null)
			exists = invoicecell.findElements(By.xpath(".//div[@data-autotests-id='invoice_notes']")).size() > 0;
		else
			Assert.fail("Can't find invoice: " + invoiceNumber);
		return exists;
	}
}
