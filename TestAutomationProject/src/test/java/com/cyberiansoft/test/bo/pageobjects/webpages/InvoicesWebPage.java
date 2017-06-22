package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.webelements.ComboBox;
import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.bo.webelements.WebTable;

public class InvoicesWebPage extends WebPageWithTimeframeFilter {

	public final static String WOTABLE_DATE_COLUMN_NAME = "Date";

	// Search Panel

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlDevices_DropDown")
	private DropDown searchdevicesinputdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_Input")
	private TextField searchclientscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlClients_DropDown")
	private DropDown searchclientsdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tRONo")
	private TextField searchwonofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbPo")
	private TextField searchponofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbStockNo")
	private TextField searchwostockfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboOrderType_Input")
	private WebElement searchwotypecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboServiceGroups_Input")
	private WebElement searchpackagecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlInsurance_Input")
	private WebElement searchinsurancecmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_comboEmployee_Input")
	private WebElement searchtechniciancmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")
	private ComboBox searchstatuscmb;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_DropDown")
	private DropDown searchstatusdd;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_ddlPORequired_Input")
	private WebElement searchbillingcmb;

	@FindBy(xpath = "//*[@for='ctl00_ctl00_Content_Main_ctl04_filterer_cbArchived']")
	private WebElement searcharchivedchkbox;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbVIN")
	private TextField searchvinfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_txtInvoiceNo")
	private TextField searchinvoicenofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbClaimNum")
	private TextField searchclaimofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbAmountFrom")
	private TextField searchamountfromfld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_tbAmountTo")
	private TextField searchamounttofld;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_popupButton")
	private WebElement searchfrompopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_popupButton")
	private WebElement searchtopopupbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_ctl04_filterer_BtnFind")
	private WebElement findbtn;

	@FindBy(id = "ctl00_ctl00_Content_Main_grdInvoices_ctl00")
	private WebTable invoicestable;

	@FindBy(className = "rmBottomArrow")
	private WebElement botArrow;

	@FindBy(className = "updateProcess")
	private WebElement updateProcess;

	@FindBy(linkText = "Select")
	private WebElement selectBTN;

	@FindBy(linkText = "Mark as Paid")
	private WebElement markAsPaidOption;

	@FindBy(id = "ctl00_ctl00_Content_Main_panelPopup")
	private WebElement paymentNote;

	@FindBy(className = "rmVertical")
	private WebElement ivoiceOptions;

	@FindBy(id = "ctl00_ctl00_Content_Main_popupPaymentNotesEditable")
	private WebElement paymentTextField;

	@FindBy(id = "ctl00_ctl00_Content_Main_btnMarkAsPaidWithNotes")
	private WebElement markAsPaidBTN;

	@FindBy(xpath = "//tr[contains(@class, 'order-row border-row custom-order-row')]")
	WebElement editableRow;

	@FindBy(xpath = "//div[contains(@title, 'Save')]")
	WebElement saveVehicleInfoBTN;

	// @FindBy(className = "rfdSkinnedButton")
	// private WebElement voidBTN;

	public InvoicesWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void verifyInvoicesTableColumnsAreVisible() {
		wait.until(ExpectedConditions.visibilityOf(invoicestable.getWrappedElement()));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chkAllInvoices")));
		Assert.assertTrue(invoicestable.isTableColumnExists("Invoice #"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Date"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Status"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Reason"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Amount"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Paid"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Customer"));
		Assert.assertTrue(invoicestable.isTableColumnExists("PO#"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Notes"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Media"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Duplicates"));
		Assert.assertTrue(invoicestable.isTableColumnExists("Action"));
	}

	public void verifySearchFieldsAreVisible() {
		Assert.assertTrue(searchclientscmb.isDisplayed());
		Assert.assertTrue(searchwonofld.isDisplayed());
		Assert.assertTrue(searchponofld.isDisplayed());
		Assert.assertTrue(searchwostockfld.isDisplayed());
		Assert.assertTrue(searchwotypecmb.isDisplayed());
		Assert.assertTrue(searchpackagecmb.isDisplayed());
		Assert.assertTrue(searchinsurancecmb.isDisplayed());
		Assert.assertTrue(searchtechniciancmb.isDisplayed());
		Assert.assertTrue(searchstatuscmb.isDisplayed());
		Assert.assertTrue(searchbillingcmb.isDisplayed());
		Assert.assertTrue(searcharchivedchkbox.isDisplayed());
		Assert.assertTrue(searchvinfld.isDisplayed());
		Assert.assertTrue(searchinvoicenofld.isDisplayed());
		Assert.assertTrue(searchclaimofld.isDisplayed());
		Assert.assertTrue(searchamountfromfld.isDisplayed());
		Assert.assertTrue(searchamounttofld.isDisplayed());
	}

	public WebTable getInvoicesTable() {
		return invoicestable;
	}

	public int getInvoicesTableRowCount() throws InterruptedException {
		Thread.sleep(4000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		return getInvoicesTableRows().size();
	}

	public List<WebElement> getInvoicesTableRows() {
		return invoicestable.getTableRows();
	}

	public void clickFindButton() {
		clickAndWait(findbtn);
	}

	public boolean invoicesTableIsVisible() {
		wait.until(ExpectedConditions.visibilityOf(invoicestable.getWrappedElement()));
		return invoicestable.isDisplayed();
	}

	public void selectSearchCustomer(String customer) {
		searchclientscmb.click();
		searchclientscmb.clearAndType(customer);
		wait.until(ExpectedConditions.visibilityOf(searchclientsdd.getWrappedElement()));
		waitABit(1000);
		searchclientsdd.selectByVisibleText(customer);
	}

	public void setSearchPONumber(String ponumber) {
		clearAndType(searchponofld, ponumber);
	}

	public void setSearchFromDate(String date, String month, String year) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(searchfrompopupbtn)).click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//tr/td/a[text()='" + date + "']")).click();
	}

	public void setSearchFromDate(String date) throws InterruptedException {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateFrom_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date) throws InterruptedException {
		// Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_dateInput")).sendKeys(date);
	}

	public void setSearchToDate(String date, String month, String year) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(searchtopopupbtn)).click();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_calDateTo_calendar_Title")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + month + "']")).click();
		driver.findElement(By.xpath("//tr/td/a[text()='" + year + "']")).click();
		driver.findElement(By.id("rcMView_OK")).click();
		updateWait.until(ExpectedConditions.visibilityOf(updateProcess));
		updateWait.until(ExpectedConditions.invisibilityOf(updateProcess));
		// Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td/a[text()='" + date + "']"))).click();
	}

	public void selectSearchStatus(WebConstants.InvoiceStatuses status) {
		selectComboboxValue(searchstatuscmb, searchstatusdd, status.getName());
	}

	public void setSearchAmountFrom(String amount) {
		clearAndType(searchamountfromfld, amount);
	}

	public void setSearchAmountTo(String amount) {
		clearAndType(searchamounttofld, amount);
	}

	public void setSearchInvoiceNumber(String invoicenum) {
		clearAndType(searchinvoicenofld, invoicenum);
	}

	public String getFirstInvoiceNumberInTable() {
		return invoicestable.getWrappedElement().findElement(By.xpath(".//tr/td[5]/a")).getText();
	}

	public boolean isInvoiceNumberExists(String invoicenum) {
		boolean exists = invoicestable.getWrappedElement()
				.findElements(By.xpath(".//tr/td/a[text()='" + invoicenum + "']")).size() > 0;
		return exists;
	}

	public String getInvoiceStatus(String invoicenumber) throws InterruptedException {
		String status = null;
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			status = row
					.findElement(
							By.xpath("./td[" + invoicestable.getTableColumnIndex("Status") + "]/table/tbody/tr/td"))
					.getText();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		return status;
	}

	public String getInvoicePONumber(String invoicenumber) throws InterruptedException {
		String status = null;
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			status = row.findElement(By.xpath("./td[" + invoicestable.getTableColumnIndex("PO#") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		return status;
	}

	public String getInvoicePOPaidValue(String invoicenumber) throws InterruptedException {
		String status = null;
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			status = row.findElement(By.xpath("./td[" + invoicestable.getTableColumnIndex("Paid") + "]")).getText();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		return status;
	}

	public void changeInvoiceStatus(String invoicenumber, String invoicestatus) throws InterruptedException {
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			row.findElement(By.xpath(".//a[contains(@id, 'comboStatus_Arrow')]")).click();
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//div[contains(@id, 'comboStatus_DropDown')]"))));
			driver.findElement(By.xpath("//div[contains(@id, 'comboStatus_DropDown')]"))
					.findElement(By.xpath(".//ul/li/a[text()='" + invoicestatus + "']")).click();
			wait.until(ExpectedConditions.alertIsPresent()).accept();
			waitUntilPageReloaded();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
	}

	public WebElement getTableRowWithInvoiceNumber(String invoicenumber) throws InterruptedException {
		Thread.sleep(4000);
		List<WebElement> rows = getInvoicesTableRows();
		Thread.sleep(4000);
		for (WebElement row : rows) {
			if (row.findElement(By.xpath(".//td[" + invoicestable.getTableColumnIndex("Invoice #") + "]/a")).getText()
					.equals(invoicenumber)) {
				return row;
			}
		}
		return null;
	}

	public WebElement clickSelectButtonForInvoice(String invoicenumber) throws InterruptedException {
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			Actions act = new Actions(driver);
			act.moveToElement(row.findElement(By.xpath(".//span[text()='Select']"))).click().build().perform();
			// waitABit(300);
			// act.click(row.findElement(By.xpath(".//span[text()='Select']"))).build().perform();
		}
		return row;
	}

	public void clickInvoiceSelectExpandableMenu(String invoicenumber, String menuitem) throws InterruptedException {

		WebElement row = clickSelectButtonForInvoice(invoicenumber);
		waitABit(1000);
		if (row != null) {
			wait.until(ExpectedConditions.visibilityOf(row.findElement(By.xpath(".//div[@class='rmSlide']"))));
			Actions act = new Actions(driver);
			if (!getTableRowWithInvoiceNumber(invoicenumber).findElement(By.xpath(".//span[text()='" + menuitem + "']"))
					.isDisplayed()) {
				act.moveToElement(getTableRowWithInvoiceNumber(invoicenumber)
						.findElement(By.xpath(".//a[@class='rmBottomArrow']"))).perform();
			}
			wait.until(ExpectedConditions.elementToBeClickable((WebElement) getTableRowWithInvoiceNumber(invoicenumber)
					.findElement(By.xpath(".//span[text()='" + menuitem + "']"))));
			act.click(getTableRowWithInvoiceNumber(invoicenumber)
					.findElement(By.xpath(".//span[text()='" + menuitem + "']"))).perform();
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
	}

	public boolean sendInvoiceEmail(String invoicenumber, String email) throws InterruptedException {
		boolean emailsended = false;
		clickInvoiceSelectExpandableMenu(invoicenumber, "Send Email");
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).sendKeys(email);
		clickAndWait(driver.findElement(By.id("ctl00_ctl00_Content_Main_btnSendEmail")));
		emailsended = true;
		return emailsended;
	}

	public SendInvoiceCustomEmailTabWebPage clickSendCustomEmail(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Send Custom Email");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, SendInvoiceCustomEmailTabWebPage.class);
	}

	public InvoiceEmailActivityTabWebPage clickEmailActivity(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Email Activity");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoiceEmailActivityTabWebPage.class);
	}

	public InvoiceEditTabWebPage clickEditInvoice(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		System.out.println(mainWindowHandle);
		clickInvoiceSelectExpandableMenu(invoicenumber, "Edit");
		waitForNewTab();
		// driver.switchTo().defaultContent();
		// driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		// Set windows = driver.getWindowHandles();
		// driver.close();
		// windows.remove(mainWindowHandle);
		// driver.switchTo().window((String) windows.iterator().next());

		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoiceEditTabWebPage.class);
	}

	public InvoicePaymentsTabWebPage clickInvoicePayments(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Payments");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoicePaymentsTabWebPage.class);
	}

	public void clickInvoicePrintPreview(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Print preview (server)");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
	}

	public String getPrintPreviewServiceListValue(String servicename) {
		WebElement parentrow = driver
				.findElement(By.xpath("//table/tbody/tr/td/div[text()='" + servicename + "']/../.."));
		return parentrow.findElement(By.xpath("./td[2]/div/div/div/span")).getText();
	}

	public String getPrintPreviewServiceNetValue(String servicename) {
		WebElement parentrow = driver
				.findElement(By.xpath("//table/tbody/tr/td/div[text()='" + servicename + "']/../.."));
		return parentrow.findElement(By.xpath("./td[3]/div")).getText();
	}

	public String getPrintPreviewTestMartrixLaborServiceListValue(String servicename) {
		WebElement parentrow = driver.findElement(
				By.xpath("//table/tbody/tr/td/div/table/tbody/tr/td[text()='" + servicename + "']/../../../../../.."));
		return parentrow.findElement(By.xpath("./td[2]/div/table/tbody/tr/td")).getText();
	}

	public String getPrintPreviewTestMartrixLaborServiceNetValue(String servicename) {
		WebElement parentrow = driver.findElement(
				By.xpath("//table/tbody/tr/td/div/table/tbody/tr/td[text()='" + servicename + "']/../../../../../.."));
		return parentrow.findElement(By.xpath("./td[3]/div/table/tbody/tr/td")).getText();
	}

	public void clickInvoiceInternalTechInfo(String invoicenumber) throws InterruptedException {
		String mainWindowHandle = driver.getWindowHandle();
		clickInvoiceSelectExpandableMenu(invoicenumber, "Internal Tech. Info");
		waitForNewTab();
		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
	}

	public void archiveInvoiceByNumber(String invoicenumber) throws InterruptedException {
		WebElement row = getTableRowWithInvoiceNumber(invoicenumber);
		if (row != null) {
			checkboxSelect(row.findElement(By.xpath(".//td/input[@type='checkbox']")));
		} else {
			Assert.assertTrue(false, "Can't find " + invoicenumber + " invoice");
		}
		driver.findElement(By.xpath("//span[@class='rtbText' and text()='Archive']")).click();
		;
		wait.until(ExpectedConditions.alertIsPresent()).accept();
		waitUntilPageReloaded();
	}

	public WebElement getTechInfoServicesTable() {
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td/div[text()='SERVICES']/../../../.."));
	}

	public String getTechInfoServicesTableServiceValue(String columnname, String servicename) {
		int icolumn = getTechInfoServicesTableColumnIndex(columnname);
		int irow = getTechInfoServicesTableServiceRowIndex(servicename);
		return getTechInfoServicesTable().findElement(By.xpath("./tbody/tr[" + irow + "]/td[" + icolumn + "]"))
				.getText();
	}

	public int getTechInfoServicesTableColumnIndex(String columnname) {
		int iterator = 0;
		int icolumn = -1;
		List<WebElement> columns = getTechInfoServicesTable().findElements(By.xpath("./tbody/tr[4]/td"));
		for (WebElement column : columns) {
			++iterator;
			if (column.getText().contains(columnname)) {
				icolumn = iterator;
				break;
			}
		}
		return icolumn;
	}

	public int getTechInfoServicesTableServiceRowIndex(String servicename) {
		int iterator = 0;
		int irow = -1;
		List<WebElement> rows = getTechInfoServicesTable().findElements(By.xpath("./tbody/tr"));
		for (WebElement row : rows) {
			++iterator;
			if (row.getText().contains(servicename)) {
				irow = iterator;
				break;
			}
		}
		return irow;
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public String selectActionForFirstInvoice(String string, boolean swichArrow) throws InterruptedException {
		String mainWindow = driver.getWindowHandle();
		Actions act = new Actions(driver);
		act.moveToElement(selectBTN).click().build().perform();
		if (string.equals("Mark as Paid")) {
			ivoiceOptions.findElement(By.linkText(string)).click();
			wait.until(ExpectedConditions.visibilityOf(paymentNote));
			paymentTextField.sendKeys("test");
			markAsPaidBTN.click();
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			return mainWindow;
		} else if (string.equals("Edit")) {
			ivoiceOptions.findElement(By.linkText(string)).click();
			Set frames = driver.getWindowHandles();
			Thread.sleep(10000);
			frames.remove(mainWindow);
			driver.switchTo().window((String) frames.iterator().next());
			return (String) frames.iterator().next();
		} else {
			while (true) {
				if (swichArrow) {
					act.moveToElement(driver.findElement(By.className("rmTopArrow"))).perform();
				} else if (driver.findElement(By.className("rmBottomArrow")).isDisplayed()) {
					act.moveToElement(driver.findElement(By.className("rmBottomArrow"))).perform();
				}
				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + string + "')]")))
							.click();
					break;
				} catch (Exception e) {
				}
			}
			Set frames = driver.getWindowHandles();
			if (frames.size() > 1) {
				frames.remove(mainWindow);
				return (String) frames.iterator().next();
			}
			return "";
		}

	}

	public boolean firstInvoiceMarkedAsPaid() {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(selectBTN).click().build().perform();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Mark as Unpaid")));
			act.moveToElement(selectBTN).click().build().perform();
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public void editVehicleInfo(String editText) throws AWTException {

		List<WebElement> editableFields = driver.findElements(By.tagName("td"));
		Actions act = new Actions(driver);
		for (WebElement element : editableFields) {
			act.moveToElement(element).click().sendKeys(editText).build().perform();
		}
		saveVehicleInfoBTN.click();
	}

	public InvoiceEditTabWebPage clickEditFirstInvoice() throws InterruptedException {

		String mainWindowHandle = driver.getWindowHandle();
		Actions act = new Actions(driver);
		act.moveToElement(selectBTN).click().build().perform();
		ivoiceOptions.findElement(By.linkText("Edit")).click();
		waitForNewTab();

		for (String activeHandle : driver.getWindowHandles()) {
			if (!activeHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(activeHandle);
			}
		}
		return PageFactory.initElements(driver, InvoiceEditTabWebPage.class);
	}

	public boolean checkInvoiceFrameOpened() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_panelPopup")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isInvoiceAbleToChange() {
		try {
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_popupInvoiceNoSuffix")))
					.sendKeys("test");
			driver.findElement(By.id("ctl00_ctl00_Content_Main_btnPopupCancel")).click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isWindowOpened() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return driver.getWindowHandles().size() > 1 ? true : false;
	}

	public void closeTab(String newTab) {
		driver.switchTo().window(newTab);
		driver.close();
	}

	public boolean recalcTechSplitProceed() {
		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public int countEmailActivities(String emailWindow) throws InterruptedException {
		String mainWindow = "";
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(emailWindow))
				driver.switchTo().window(window);
			else {
				mainWindow = window;
			}
		}
		try {
			int size = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("tr"))).size();
			driver.close();
			driver.switchTo().window(mainWindow);
			return size;
		} catch (TimeoutException e) {
			driver.close();
			driver.switchTo().window(mainWindow);
			return 0;
		}
	}

	public boolean isSendEmailBoxOpened() {
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void setEmailAndSend(String string) throws InterruptedException {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_popupEmailRecipients")).sendKeys(string);
		driver.findElement(By.id("ctl00_ctl00_Content_Main_btnSendEmail")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}

	public void setCustomEmailAndSend(String email, String emailWindow) {
		String mainWindow = "";
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(emailWindow))
				driver.switchTo().window(window);
			else {
				mainWindow = window;
			}
		}
		driver.findElement(By.id("ctl00_Content_InvoiceSendEmail1_txtEmailTo")).clear();
		driver.findElement(By.id("ctl00_Content_InvoiceSendEmail1_txtEmailTo")).sendKeys(email);
		driver.findElement(By.id("ctl00_Content_InvoiceSendEmail1_btnSend")).click();
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Your message has been sent.')]")));
		driver.close();
		driver.switchTo().window(mainWindow);
	}

	public void setSearchByYear(String s) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_comboYear_Input")).click();
		driver.findElement(By.xpath("//li[contains(text(), '" + s + "')]")).click();
	}

	public void setSearchByMake(String string) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtMake")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtMake")).sendKeys(string);
	}

	public void setSearchByModel(String string) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtModel")).clear();
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_txtModel")).sendKeys(string);
	}

	public boolean checkSearchResult() throws InterruptedException {
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));

		return driver.findElements(By.className("rgRow")).size() > 0;
	}

	public void selectSearchStatus(String s) {
		driver.findElement(By.id("ctl00_ctl00_Content_Main_ctl04_filterer_ddlStatus_Input")).click();
		driver.findElement(By.xpath("//li[contains(text(), '" + s + "')]")).click();
	}

	public boolean checkWindowContent(String tab ,String...content) {
		driver.switchTo().window(tab);
		for(String str: content){
			try{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(str)));
			}catch(TimeoutException e){
				return false;
			}
		}
		return true;
	}

	public boolean checkPayBoxContent() throws InterruptedException {
		Thread.sleep(1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
		try{
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Invoice#:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'PO#:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'TOTAL:')]")));
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtPoRoNumber"))).sendKeys("123");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_chkPoRoApprove"))).click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_error")));
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Cash/Check')]"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Check #:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Amount:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Notes:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_chkCashApprove")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_btnCashPay")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCheckNumber")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_tbCashAmount")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCashNotes")));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), 'Credit Card')]"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Amount:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'First Name:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Last Name:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Card#:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'Expiration:')]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_tbCardAmount")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCardFirstName")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCardLastName")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCardNumber")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_txtCardCVV")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_ctl00_Content_Main_ctl01_ctl00_Card_btnCardPay")));
			
		}catch(TimeoutException e){
			return false;
		}
		
		return true;
	}

	public boolean checkAuditLogWindowContent(String auditLogWindow) {
		String mainWindow = "";
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (window.equals(auditLogWindow))
				driver.switchTo().window(window);
			else {
				mainWindow = window;
			}
		}
		try{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Invoice #')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Date')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Customer')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'PO#')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Amount')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(), 'Paid')]")));
		}catch(TimeoutException e){
			return false;
		}
		return true;
	}
}
